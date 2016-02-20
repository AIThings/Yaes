package yaes.framework.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import yaes.framework.resource.negotiation.ResourceRequest;

/*
 * Created on May 6, 2005
 *
 *  Handles the resources from a single location
 *
 */
public class ResourceDB {
    private static int                                counter            = 0;
    private final HashMap<String, ResourceAllocation> chunks             = new HashMap<>();
    private String                                    provider;
    private ResourceAllocation                        resourceAllocation = null;
    private final HashMap<String, TimeLine>           tls                = new HashMap<>();
    private final ResourceSet                         totalResources;

    /**
     * Creates a resource database which allows the fractioning of a previously
     * allocated resource.
     * 
     * @param rb
     */
    public ResourceDB(ResourceAllocation rb) {
        totalResources = rb.getResourceSet();
        resourceAllocation = rb;
        for (final Resource r : rb.getResourceSet().getResources()) {
            addResource(r.getId(), r.getQuantity(), rb.getStartTime(), rb
                    .getEndTime());
        }
    }

    /**
     * Creates a resource database for host which owns the resources
     * 
     * @param resources
     */
    public ResourceDB(ResourceSet resources, String provider) {
        this.provider = provider;
        totalResources = resources;
        for (final Resource r : resources.getResources()) {
            addResource(r.getId(), r.getQuantity());
        }
    }

    private void addResource(String value, int initValue) {
        final TimeLine tl = new TimeLine(initValue);
        tls.put(value, tl);
    }

    private void addResource(String value, int initValue, int startTime,
            int endTime) {
        final TimeLine tl = new TimeLine(initValue);
        tl.accept(0, startTime, initValue);
        tl.accept(endTime, Integer.MAX_VALUE, initValue);
        tls.put(value, tl);
    }

    /**
     * Cancel the reservation
     */
    public boolean cancelReservation(String key) {
        final ResourceAllocation rac = getResourceAllocationChunkByKey(key);
        if ((rac != null)
                && (rac.getState() == ResourceAllocation.States.RESERVED)) {
            return release(key);
        }
        return false;
    }

    /**
     * Checks if a certain resource allocation is admissible in this resource
     * database
     * 
     * @param request
     * @return
     */
    public boolean check(ResourceAllocation request) {
        for (final Resource r : request.getResourceSet().getResources()) {
            final TimeLine t = tls.get(r.getId());
            if (t == null) {
                return false;
            }
            boolean c = t.check(request.getStartTime(), request.getEndTime(), r
                    .getQuantity());
            if (!c) {
                return false;
            }
        }
        return true;
    }

    /**
     * Commit to the reservation
     */
    public boolean commit(String key) {
        final ResourceAllocation rac = getResourceAllocationChunkByKey(key);
        if ((rac != null)
                && (rac.getState() == ResourceAllocation.States.RESERVED)) {
            rac.setState(ResourceAllocation.States.COMMITTED);
            return true;
        }
        return false;
    }

    private String createKey() {
        return "Key_" + ResourceDB.counter++;
    }

    /**
     * Returns a set of allocation alternatives, expressed as resource
     * allocation objects which satisfy the resource request
     * 
     */
    public List<ResourceAllocation> findAllocationAlternatives(
            ResourceRequest resourceRequest) {
        final List<ResourceAllocation> result = new ArrayList<>();
        if (!ResourceHelper.canFit(resourceRequest.getResourceSet(),
                totalResources)) {
            return result;
        }
        // if this was fine, then there will be some solution, although it might
        // be deep in the
        // future.
        final List<Integer> timepoints = getAllTimePoints();
        if (timepoints.size() != 0) {
            timepoints.remove(timepoints.size() - 1); // the last one is +
            // infinity
        }
        final List<Integer> timepoints2 = new ArrayList<>();
        timepoints2.add(resourceRequest.getStartingTime());
        // FIXME: special handling for the starting time not matter case
        for (final int tp : timepoints) {
            if (tp > resourceRequest.getStartingTime()) {
                timepoints2.add(tp);
            }
        }
        for (final int tp : timepoints2) {
            final ResourceAllocation totest = new ResourceAllocation(tp, tp
                    + resourceRequest.getDuration(), resourceRequest
                    .getResourceSet(), provider);
            if (check(totest)) {
                result.add(totest);
            }
        }
        return result;
    }

    public List<Integer> getAllTimePoints() {
        final SortedSet<Integer> sset = new TreeSet<>();
        for (final TimeLine tl : tls.values()) {
            sset.addAll(tl.getTimePoints());
        }
        final ArrayList<Integer> retval = new ArrayList<>();
        retval.addAll(sset);
        return retval;
    }

    public ResourceAllocation getResourceAllocation() {
        return resourceAllocation;
    }

    public ResourceAllocation getResourceAllocationChunkByKey(String key) {
        return chunks.get(key);
    }

    /**
     * Returns a hash with the free resources at time moment t
     * 
     * @param time
     * @return
     */
    public HashMap<String, Integer> getResourcesAtTime(int time) {
        final HashMap<String, Integer> map = new HashMap<>();
        for (final String resources : tls.keySet()) {
            final TimeLine tl = tls.get(resources);
            map.put(resources, tl.freeQuantityAtTime(time));
        }
        return map;
    }

    /**
     * Partial use -there is an interesting question here of how this will work,
     * but we'll see.
     */
    public boolean partialUse(String key, ResourceAllocation usageChunk) {
        throw new Error("Not implemented yet");
    }

    /**
     * Releases a previously allocated chunk
     * 
     * @param key
     * @return
     */
    public boolean release(String key) {
        final ResourceAllocation chunk = chunks.get(key);
        if (chunk == null) {
            return false;
        }
        for (final Resource r : chunk.getResourceSet().getResources()) {
            final TimeLine t = tls.get(r.getId());
            t.release(chunk.getStartTime(), chunk.getEndTime(), r.getQuantity());
        }
        chunks.remove(key);
        return true;
    }

    /**
     * Reserves a resource bundle and returns a key for which one can handle it.
     * 
     * @param request
     * @return
     */
    public String reserve(ResourceAllocation request) {
        if (!check(request)) {
            return null;
        }
        for (final Resource r : request.getResourceSet().getResources()) {
            final TimeLine t = tls.get(r.getId());
            t.accept(request.getStartTime(), request.getEndTime(), r
                    .getQuantity());
        }
        request.setState(ResourceAllocation.States.RESERVED);
        final String key = createKey();
        chunks.put(key, request);
        return key;
    }

    @Override
    public String toString() {
        final StringBuffer temp = new StringBuffer("ResourceDB\n");
        for (final ResourceAllocation rac : chunks.values()) {
            temp.append("\t" + rac.toSimpleString() + "\n");
        }
        return temp.toString();
    }

    /**
     * Use the full reservation
     */
    public boolean use(String key) {
        final ResourceAllocation rac = getResourceAllocationChunkByKey(key);
        if ((rac != null)
                && (rac.getState() == ResourceAllocation.States.COMMITTED)) {
            rac.setState(ResourceAllocation.States.IN_USE);
            return true;
        }
        return false;
    }
}
