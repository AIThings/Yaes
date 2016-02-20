package yaes.framework.resource.task;

import java.util.HashMap;

import yaes.framework.resource.ResourceAllocation;
import yaes.framework.resource.ResourceDB;
import yaes.framework.resource.ResourceSet;

/**
 * This class contains the abstraction of a resource provider which also handles
 * tasks. It will be used by the provider and marketmaker models. (in fact, by
 * simply adding some messaging to it, can be transformed into a provider)
 * 
 * @author Lotzi Boloni
 * 
 */
public class TaskProvider extends ResourceDB {
    private final HashMap<String, TaskRunner> taskRunners;

    public TaskProvider(ResourceAllocation resourceAllocation) {
        super(resourceAllocation);
        taskRunners = new HashMap<>();
    }

    public TaskProvider(ResourceSet resourceSet, String provider) {
        super(resourceSet, provider);
        taskRunners = new HashMap<>();
    }

    /**
     * Commit to the reservation
     */
    @Override
    public boolean commit(String key) {
        final ResourceAllocation rac = getResourceAllocationChunkByKey(key);
        if ((rac != null)
                && (rac.getState() == ResourceAllocation.States.RESERVED)) {
            rac.setState(ResourceAllocation.States.COMMITTED);
            taskRunners.put(key, new TaskRunner(rac));
            return true;
        }
        return false;
    }

    public boolean fail(int currentTime, String key, Task task) {
        return taskRunners.get(key).terminate(currentTime, task);
    }

    public boolean start(int currentTime, String key, Task task) {
        return taskRunners.get(key).start(currentTime, task);
    }

    public boolean terminate(int currentTime, String key, Task task) {
        return taskRunners.get(key).terminate(currentTime, task);
    }

    @Override
    public String toString() {
        final StringBuffer temp = new StringBuffer();
        temp.append(super.toString());
        for (final TaskRunner tr : taskRunners.values()) {
            temp.append(tr);
        }
        return temp.toString();
    }

    /**
     * Updates the running tasks, terminated tasks etc.
     * 
     * @param currentTime
     */
    public void updateTime(int currentTime) {
        for (final TaskRunner tr : taskRunners.values()) {
            tr.updateTime(currentTime);
        }
    }
}
