/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jun 22, 2009
 
   yaestest.framework.simulation.parametersweep.testParameterSweep
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.framework.simulation.parametersweep;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import yaes.framework.simulation.parametersweep.ParameterSweep;
import yaes.framework.simulation.parametersweep.ParameterSweepHelper;
import yaes.framework.simulation.parametersweep.ScenarioDistinguisher;
import yaes.ui.format.IDetailLevel;
import yaes.ui.text.TextUi;

/**
 * 
 * <code>yaestest.framework.simulation.parametersweep.testParameterSweep</code>
 * the canonical model of parameter sweep
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testParameterSweep implements IDetailLevel {

    /**
     * Creating and printing of parameter sweeps
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    @Test
    public void testParameterSweepCreatingAndPrintingRange()
            throws FileNotFoundException, IOException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        ParameterSweep sweepDoubleRange = ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1", "InputParam1",
                        10, -5, 5);
        String tmp = sweepDoubleRange.toStringDetailed(IDetailLevel.MIN_DETAIL);
        TextUi.println(tmp);
        ParameterSweep sweepIntegerRange = ParameterSweepHelper
                .generateParameterSweepInteger("inputParameter2",
                        "InputParam2", 1, 15);
        String tmp2 = sweepIntegerRange
                .toStringDetailed(IDetailLevel.MIN_DETAIL);
        TextUi.println(tmp2);
    }

    @Test
    public void testParameterSweepDiscrete() {
        ParameterSweep sweepDiscrete = new ParameterSweep("protocols");
        ScenarioDistinguisher sd1 = new ScenarioDistinguisher("Config1");
        sd1.setDistinguisher("val1", 1.0);
        sweepDiscrete.addDistinguisher(sd1);
        ScenarioDistinguisher sd2 = new ScenarioDistinguisher("Config2");
        sd1.setDistinguisher("val2", 2.0);
        sweepDiscrete.addDistinguisher(sd2);
        ScenarioDistinguisher sd3 = new ScenarioDistinguisher("Config3");
        sd1.setDistinguisher("val3", 4.0);
        sweepDiscrete.addDistinguisher(sd3);
        String tmp = sweepDiscrete.toStringDetailed(IDetailLevel.MIN_DETAIL);
        TextUi.println(tmp);
    }

}
