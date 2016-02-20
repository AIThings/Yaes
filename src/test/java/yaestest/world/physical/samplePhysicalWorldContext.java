package yaestest.world.physical;

import yaes.framework.simulation.IContext;
import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.SimulationOutput;
import yaes.ui.visualization.Visualizer;
import yaes.world.World;
import yaes.world.physical.PhysicalWorld;
import yaes.world.physical.PhysicalWorldPainter;
import yaes.world.physical.location.Location;
import yaes.world.physical.map.SimpleField;

public class samplePhysicalWorldContext implements IContext {

    /**
     * 
     */
    private static final long serialVersionUID = 3072373274435549759L;
    @SuppressWarnings("unused")
    private SimulationInput   sip;
    @SuppressWarnings("unused")
    private SimulationOutput  sop;
    public Visualizer         visual;
    public PhysicalWorld      world;

    public samplePhysicalWorldContext() {
    }

    /**
     * @param sip
     */
    @Override
    public void createVisualRepresentation(Visualizer visualizer) {
        // not implemented
    }

    /**
     * @return
     */
    @Override
    public SimulationInput getSimulationInput() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public SimulationOutput getSimulationOutput() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    @Override
    public World getWorld() {
        return world;
    }

    /**
     * @param sip
     */
    @Override
    public void initialize(SimulationInput sip, SimulationOutput sop) {
        this.sip = sip;
        this.sop = sop;
        world = new PhysicalWorld(new SimpleField(1000, 1000), sop);
        int agents = sip.getParameterInt("Agents");
        for (int i = 0; i != agents; i++) {
            testAgent agent = new testAgent("agent" + i, new Location(i * 100,
                    i * 100), world);
            world.addEmbodiedAgent(agent);
        }
        visual = new Visualizer((int) world.getField().getXHigh(), (int) world
                .getField().getYHigh(), null, "testEmbodiedAgents");
        visual.addObject(world, new PhysicalWorldPainter(world));

    }

}
