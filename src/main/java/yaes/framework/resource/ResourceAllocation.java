package yaes.framework.resource;

/*
 * Created on May 6, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ResourceAllocation {
    public enum States {
        COMMITTED, FREE, IN_USE, REQUEST, RESERVED
    };

    private final int         endTime;
    private String            provider;
    private final ResourceSet resourceSet;
    private final int         startTime;
    private States            state;

    /**
     * @param time
     * @param time
     * @param resources
     */
    public ResourceAllocation(int startTime, int endTime,
            ResourceSet resources, String provider) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.resourceSet = resources;
        this.state = States.REQUEST;
    }

    public int getDuration() {
        return endTime - startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getProvider() {
        return provider;
    }

    /**
     * @return Returns the resources.
     */
    public ResourceSet getResourceSet() {
        return resourceSet;
    }

    public int getStartTime() {
        return startTime;
    }

    /**
     * @return Returns the state.
     */
    public States getState() {
        return state;
    }

    /**
     * @param state
     *            The state to set.
     */
    public void setState(States state) {
        this.state = state;
    }

    public String toSimpleString() {
        final StringBuffer buffer = new StringBuffer(state + "\t [" + startTime
                + "," + endTime + "]\t");
        for (final Resource resource : resourceSet.getResources()) {
            buffer.append(resource.getId() + "=" + resource.getQuantity() + " ");
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer(
                "ResourceAllocationChunk time = [" + startTime + "," + endTime
                        + "] " + state + "\n");
        for (final Resource resource : resourceSet.getResources()) {
            buffer.append("\t" + resource.toString() + "\n");
        }
        return buffer.toString();
    }
}
