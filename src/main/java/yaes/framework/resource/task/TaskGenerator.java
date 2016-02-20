package yaes.framework.resource.task;

import umontreal.iro.lecuyer.probdist.NormalDist;
import umontreal.iro.lecuyer.randvar.NormalGen;
import umontreal.iro.lecuyer.rng.RandMrg;
import yaes.framework.resource.Resource;
import yaes.framework.resource.ResourceSet;

public class TaskGenerator {
    /**
     * Generates tasks with random requirements with a normal distribution.
     * 
     * @param resourceRequirements
     * @param taskExecTime
     * @return
     */
    public static Task generateTask(int resourceRequirements, int taskExecTime) {
        // for the resourceRequirements
        final NormalGen gen_r = new NormalGen(new RandMrg(), new NormalDist(
                resourceRequirements, resourceRequirements / 2));
        // for the targetExecutionTime
        final NormalGen gen_e = new NormalGen(new RandMrg(), new NormalDist(
                taskExecTime, taskExecTime / 2));
        double d = gen_r.nextDouble();
        while (d < 0) {
            d = gen_r.nextDouble();
        }
        // safety:
        if (d > 200) {
            d = 200;
        }
        double e = gen_e.nextDouble();
        while (e < 0) {
            e = gen_e.nextDouble();
        }
        final ResourceSet rb = new ResourceSet();
        rb.addResource(Resource.RESOURCETYPE_MEMORY, (int) (d * 0.9));
        return new Task(rb, (int) e, (int) (e * 0.1));
    }
}
