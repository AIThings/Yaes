/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jun 22, 2009
 
   yaestest.framework.simulation.parametersweep.PSWSimulation
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.framework.simulation.parametersweep;

import yaes.framework.simulation.IContext;
import yaes.framework.simulation.ISimulationCode;
import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.SimulationOutput;

/**
 * 
 * <code>yaestest.framework.simulation.parametersweep.PSWSimulation</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PSWSimulation implements ISimulationCode, PSWConstants {
    public static String simulationLabel = "unlabelled";

    /**
     * Collect data about the data.
     */
    @Override
    public void postprocess(SimulationInput sip, SimulationOutput sop,
            IContext theContext) {
        // nothing here
    }

    @Override
    public void setup(SimulationInput sip, SimulationOutput sop,
            IContext theContext) {
        final PSWContext context = (PSWContext) theContext;
        context.initialize(sip, sop);
    }

    @Override
    public String toString() {
        return "PSWSimulation";
    }

    @Override
    public int update(double time, SimulationInput sip, SimulationOutput sop,
            IContext theContext) {
        final PSWContext context = (PSWContext) theContext;
        context.getWorld().setTime(time);
        double x = context.inputparam1;
        double y = context.inputparam2;
        double randomNoise = context.rand.nextGaussian();
        double discrete = context.inputparam3;
        double temp = 3 * Math.pow((1 - x), 2)
                * Math.exp(-Math.pow(x, 2) - Math.pow((y + 1), 2)) - 10
                * (x / 5 - Math.pow(x, 3) - Math.pow(y, 5))
                * Math.exp(-Math.pow(x, 2) - Math.pow(y, 2)) - 1 / 3
                * Math.exp(-Math.pow((x + 1), 2) - Math.pow(y, 2));
        double z = 0;
        if (discrete == 1.0) {
            z = temp;
        }
        if (discrete == 2.0) {
            z = 4 - 0.5 * temp;
        }
        if (discrete == 3.0) {
            z = Math.sqrt(Math.abs(temp));
        }
        sop.update(PSWConstants.OUTPUT_PARAM_1, z);
        sop.update(PSWConstants.OUTPUT_PARAM_2, z + randomNoise);
        return 1;
    }
}
