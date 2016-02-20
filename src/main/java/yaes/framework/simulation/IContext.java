/*
 * Created on Sep 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.simulation;

import java.io.Serializable;

import yaes.ui.visualization.Visualizer;
import yaes.world.World;

/**
 * @author Lotzi Boloni
 * 
 */
public interface IContext extends Serializable {
    void createVisualRepresentation(Visualizer visualizer);

    SimulationInput getSimulationInput();

    SimulationOutput getSimulationOutput();

    World getWorld();

    void initialize(SimulationInput sip, SimulationOutput sop);
}
