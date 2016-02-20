/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jun 22, 2009
 
   yaestest.framework.simulation.parametersweep.PSWContext
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.framework.simulation.parametersweep;

import java.util.Random;

import yaes.framework.simulation.AbstractContext;
import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.SimulationOutput;
import yaes.world.World;

/**
 * 
 * <code>yaestest.framework.simulation.parametersweep.PSWContext</code> the
 * context for the parameter sweep simulation test
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PSWContext extends AbstractContext implements PSWConstants {
    private static final long serialVersionUID = -1486692291111595389L;
    public double             inputparam1;
    public double             inputparam2;
    public double             inputparam3;
    public double             outputparam1;
    public double             outputparam2;
    public Random             rand;

    public PSWContext() {
    }

    /**
     * Initializes the environment
     * 
     */
    @Override
    public void initialize(SimulationInput sip, SimulationOutput sop) {
        super.initialize(sip, sop);
        theWorld = new World(sop);
        inputparam1 = sip.getParameterDouble(PSWConstants.INPUT_PARAM_1);
        inputparam2 = sip.getParameterDouble(PSWConstants.INPUT_PARAM_2);
        inputparam3 = sip.getParameterDouble(PSWConstants.INPUT_PARAM_3);
        rand = new Random(sip.getParameterInt(PSWConstants.INPUT_PARAM_4));
    }

}
