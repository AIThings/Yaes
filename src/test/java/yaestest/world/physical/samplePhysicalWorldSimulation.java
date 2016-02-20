/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 22, 2008
 
   yaestest.world.physical.samplePhysicalWorldSimulation
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.world.physical;

import yaes.framework.simulation.IContext;
import yaes.framework.simulation.ISimulationCode;
import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.SimulationOutput;

/**
 * 
 * <code>yaestest.world.physical.samplePhysicalWorldSimulation</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class samplePhysicalWorldSimulation implements ISimulationCode {

    /**
     * @param sip
     * @param sop
     * @param theContext
     */
    @Override
    public void postprocess(SimulationInput sip, SimulationOutput sop,
            IContext theContext) {
        // nothing here.
    }

    /**
     * @param sip
     * @param sop
     * @param theContext
     */
    @Override
    public void setup(SimulationInput sip, SimulationOutput sop,
            IContext theContext) {

    }

    /**
     * @param time
     * @param sip
     * @param sop
     * @param theContext
     * @return
     */
    @Override
    public int update(double time, SimulationInput sip, SimulationOutput sop,
            IContext theContext) {
        samplePhysicalWorldContext context = (samplePhysicalWorldContext) theContext;
        context.world.update((int) time);
        context.visual.update();
        return 1;
    }

}
