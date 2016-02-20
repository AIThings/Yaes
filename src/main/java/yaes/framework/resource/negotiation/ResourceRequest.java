package yaes.framework.resource.negotiation;

import yaes.framework.resource.ResourceSet;

public class ResourceRequest {
    public static final int   STARTING_TIME_NOT_SPECIFIED = -1;
    private final int         duration;
    private final ResourceSet resourceSet;
    private final int         startingTime;                    // -1 not
                                                                // important

    /**
     * @param set
     * @param duration
     * @param time
     */
    public ResourceRequest(ResourceSet set, int duration, int time) {
        super();
        // TODO Auto-generated constructor stub
        resourceSet = set;
        this.duration = duration;
        startingTime = time;
    }

    public int getDuration() {
        return duration;
    }

    public ResourceSet getResourceSet() {
        return resourceSet;
    }

    public int getStartingTime() {
        return startingTime;
    }

    @Override
    public String toString() {
        return "[" + startingTime + ".." + (startingTime + duration) + "]   "
                + resourceSet;
    }
}
