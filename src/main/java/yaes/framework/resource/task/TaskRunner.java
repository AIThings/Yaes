/**
 *  Runs multiple tasks in a single allocation unit
 */
package yaes.framework.resource.task;

import java.util.ArrayList;

import yaes.framework.resource.ResourceAllocation;
import yaes.framework.resource.ResourceHelper;
import yaes.framework.resource.ResourceSet;
import yaes.ui.text.TextUi;

public class TaskRunner {
    private ResourceSet              currentResources;
    private final ArrayList<Task>    failedTasks     = new ArrayList<>();
    private int                      lastCurrentTime = -1;
    private final ResourceAllocation rac;
    private final ArrayList<Task>    runningTasks    = new ArrayList<>();
    private final ArrayList<Task>    terminatedTasks = new ArrayList<>();

    public TaskRunner(ResourceAllocation rac) {
        this.rac = rac;
        currentResources = rac.getResourceSet();
    }

    private void checkCurrentTime(int currentTime) {
        if (currentTime < lastCurrentTime) {
            throw new Error(
                    "This time is before the last current time. This class needs the temporal updates to be serialized");
        }
        lastCurrentTime = currentTime;
    }

    public boolean fail(int currentTime, Task task) {
        checkCurrentTime(currentTime);
        currentResources = ResourceHelper.add(currentResources, task
                .getRequiredResources());
        task.fail(currentTime);
        runningTasks.remove(task);
        failedTasks.add(task);
        return true;
    }

    public boolean start(int currentTime, Task task) {
        checkCurrentTime(currentTime);
        currentResources = ResourceHelper.subtract(currentResources, task
                .getRequiredResources());
        task.start(currentTime);
        runningTasks.add(task);
        return true;
    }

    public boolean terminate(int currentTime, Task task) {
        checkCurrentTime(currentTime);
        currentResources = ResourceHelper.add(currentResources, task
                .getRequiredResources());
        task.terminate(currentTime);
        runningTasks.remove(task);
        terminatedTasks.add(task);
        return true;
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("Task runner: "
                + lastCurrentTime + "\n");
        buffer.append(currentResources.toString() + "\n");
        buffer.append("RUNNING: " + runningTasks.size() + "\n");
        for (final Task t : runningTasks) {
            buffer.append("\t" + t.toString() + "\n");
        }
        buffer.append("TERMINATED: " + terminatedTasks.size() + "\n");
        for (final Task t : terminatedTasks) {
            buffer.append("\t" + t.toString() + "\n");
        }
        buffer.append("FAILED: " + failedTasks.size() + "\n");
        for (final Task t : failedTasks) {
            buffer.append("\t" + t.toString() + "\n");
        }
        return buffer.toString();
    }

    /**
     * Updates the running tasks, terminated tasks etc.
     * 
     * @param currentTime
     */
    public void updateTime(int currentTime) {
        checkCurrentTime(currentTime);
        // terminate the tasks which are terminated
        final ArrayList<Task> tasksToTerminate = new ArrayList<>();
        for (final Task t : runningTasks) {
            if (t.getPredictedTerminationTime() <= currentTime) {
                tasksToTerminate.add(t);
            }
        }
        for (final Task t : tasksToTerminate) {
            terminate(currentTime, t);
        }
        // if the time is over, fail all the tasks
        if (rac.getEndTime() <= currentTime) {
            TextUi.println("Failing the remainder");
            final ArrayList<Task> tasksToFail = new ArrayList<>();
            tasksToFail.addAll(runningTasks);
            for (final Task t : tasksToFail) {
                fail(currentTime, t);
            }
        }
    }
}