package yaes.framework.resource;

import java.util.HashSet;
import java.util.Set;

public class ResourceHelper {
    /**
     * Adds the resources sets
     * 
     * @param r1
     * @param r2
     * @return
     */
    public static ResourceSet add(ResourceSet r1, ResourceSet r2) {
        // create the set of resource id's
        final Set<String> resourceIds = new HashSet<>();
        for (final Resource r : r1.getResources()) {
            resourceIds.add(r.getId());
        }
        for (final Resource r : r2.getResources()) {
            resourceIds.add(r.getId());
        }
        // List<Resource> newResource = new ArrayList<Resource>();
        final ResourceSet newResource = new ResourceSet();
        for (final String resId : resourceIds) {
            newResource.addResource(resId, r1.getResourceQuantity(resId)
                    + r2.getResourceQuantity(resId));
        }
        return newResource;
    }

    /**
     * Returns true if the resourceset request can fit into the available
     * resourceset
     * 
     * @param request
     * @param available
     * @return
     */
    public static boolean canFit(ResourceSet request, ResourceSet available) {
        for (final Resource r : request.getResources()) {
            if (r.getQuantity() > available.getResourceQuantity(r.getId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Subtracts resource r2 from resource r1
     * 
     * @param r1
     * @param r2
     * @return
     */
    public static ResourceSet subtract(ResourceSet r1, ResourceSet r2) {
        final ResourceSet newResource = new ResourceSet();
        for (final Resource r : r1.getResources()) {
            newResource.addResource(r.getId(), r.getQuantity()
                    - r2.getResourceQuantity(r.getId()));
        }
        return newResource;
    }
}
