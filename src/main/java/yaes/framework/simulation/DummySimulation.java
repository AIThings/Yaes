/*
 * Created on Dec 6, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.simulation;

/**
 * @author Lotzi Boloni
 * 
 *         An empty simulation code.
 */
public class DummySimulation implements ISimulationCode {
    /*
     * (non-Javadoc)
     * 
     * @see
     * yaes.framework.simulation.ISimulationCode#postprocess(yaes.framework.
     * simulation.SimulationInput, yaes.framework.simulation.SimulationOutput,
     * yaes.framework.simulation.IContext)
     */
    @Override
    public void postprocess(SimulationInput sip, SimulationOutput sop,
            IContext theContext) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * yaes.framework.simulation.ISimulationCode#setup(yaes.framework.simulation
     * .SimulationInput, yaes.framework.simulation.IContext)
     */
    @Override
    public void setup(SimulationInput sip, SimulationOutput sop,
            IContext theContext) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see yaes.framework.simulation.ISimulationCode#update(double,
     * yaes.framework.simulation.SimulationInput,
     * yaes.framework.simulation.SimulationOutput,
     * yaes.framework.simulation.IContext)
     */
    @Override
    public int update(double time, SimulationInput sip, SimulationOutput sop,
            IContext theContext) {
        // TODO Auto-generated method stub
        return 0;
    }
}
