/*
 * Created on Sep 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.simulation;

/**
 * @author Lotzi Boloni
 * 
 *         The interface to be implemented by simulation code
 * 
 */
public interface ISimulationCode {
    void postprocess(SimulationInput sip, SimulationOutput sop,
            IContext theContext);

    void setup(SimulationInput sip, SimulationOutput sop, IContext theContext);

    /**
     * Called at every time slot
     * 
     * @param time
     *            - the current time
     * @param sip
     *            - simulation input
     * @param sop
     *            - simulation output, to be used to
     * @param theContext
     *            - the context of the simulation
     * @return - returns 0 to stop the simulation, timestep to continue
     *         (normally 1)
     */
    int update(double time, SimulationInput sip, SimulationOutput sop,
            IContext theContext);
}
