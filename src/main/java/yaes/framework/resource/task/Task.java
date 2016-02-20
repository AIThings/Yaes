package yaes.framework.resource.task;

import yaes.framework.resource.ResourceSet;

public class Task {
    public enum State {
        FAILED, NOT_STARTED, RUNNING, TERMINATED
    };

    private int               predictedTerminationTime;
    private final ResourceSet requiredResources;
    private int               startingTime;
    private State             state;
    private final int         targetExecutionTime;
    private int               terminationTime;
    private double            variance;

    public Task(ResourceSet requiredResources, int targetExecutionTime,
            double variance) {
        this.requiredResources = requiredResources;
        this.targetExecutionTime = targetExecutionTime;
        this.variance = variance;
        state = State.NOT_STARTED;
    }

    public boolean fail(int currentTime) {
        if (state != State.RUNNING) {
            return false;
        }
        terminationTime = currentTime;
        state = State.FAILED;
        return true;
    }

    public int getEstimatedExecutionTime(ResourceSet allocatedResources) {
        // return getRealExecutionTime(allocatedResources);
        return targetExecutionTime;
    }

    /**
     * @return Returns the predictedTerminationTime.
     */
    public int getPredictedTerminationTime() {
        return predictedTerminationTime;
    }

    public int getRealExecutionTime(ResourceSet allocatedResources) {
        return getEstimatedExecutionTime(allocatedResources);
        /*
         * double r = allocatedResources.getAvailableQuantity(); if (r <
         * requiredResources.quantityLowerBound) { return Double.MAX_VALUE; }
         * else if (r < requiredResources.quantityUpperBound) { return
         * targetExecutionTime / r * targetExecutionTime + variance (r /
         * targetExecutionTime); } else { return targetExecutionTime + variance
         * * (r / targetExecutionTime); }
         */
    }

    public ResourceSet getRequiredResources() {
        return requiredResources;
    }

    /**
     * @return Returns the startingTime.
     */
    public int getStartingTime() {
        return startingTime;
    }

    public State getState() {
        return state;
    }

    /**
     * @return Returns the terminationTime.
     */
    public int getTerminationTime() {
        return terminationTime;
    }

    /**
     * @return Returns the variance.
     */
    public double getVariance() {
        return variance;
    }

    /**
     * @param variance
     *            The variance to set.
     */
    public void setVariance(double variance) {
        this.variance = variance;
    }

    public boolean start(int currentTime) {
        if (state != State.NOT_STARTED) {
            return false;
        }
        startingTime = currentTime;
        predictedTerminationTime = currentTime
                + getRealExecutionTime(requiredResources);
        state = State.RUNNING;
        return true;
    }

    public boolean terminate(int currentTime) {
        if (state != State.RUNNING) {
            return false;
        }
        terminationTime = currentTime;
        state = State.TERMINATED;
        return true;
    }

    @Override
    public String toString() {
        return "Task " + state + " Resource:" + requiredResources;
    }
}
