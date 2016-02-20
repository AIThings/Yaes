/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 23, 2008
 
   yaestest.world.physical.testPhysicalAgent
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.world.physical;

import org.junit.Test;

import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.SimulationOutput;
import yaes.ui.simulationcontrol.SimulationControlGui;

/**
 * 
 * <code>yaestest.world.physical.testPhysicalAgent</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testPhysicalAgent {

    @Test
    public void testTheEmbodiedAgent() {
        SimulationInput sip = new SimulationInput();
        sip.setStopTime(10);
        sip.setParameter("Agents", 5);
        samplePhysicalWorldContext context = new samplePhysicalWorldContext();
        context.initialize(sip, null);
        SimulationOutput sop = null;
        try {
            sop = SimulationControlGui.simulate(sip,
                    samplePhysicalWorldSimulation.class, context);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(sop);

    }

}
