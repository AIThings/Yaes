/*
   This file is part of the Yet Another Extensible Simulator
 
   Created on: Jul 18, 2008
 
   yaestest.ui.plot.testTimeSeriesPlot
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.ui.plot;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import yaes.framework.simulation.SimulationOutput;
import yaes.framework.simulation.parametersweep.ExperimentPackage;
import yaes.ui.plot.TimeSeriesLineDescription;
import yaes.ui.plot.TimeSeriesPlot;
import yaes.ui.text.TextUi;
import yaes.util.FileWritingUtil;

/**
 * 
 * <code>yaestest.ui.plot.testTimeSeriesPlot</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testTimeSeriesPlot {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Tests the creation of the time series as well as the time series plot.
     * 
     * @throws IOException
     */
    @Test
    public void testTimeSeries() throws IOException {
        SimulationOutput out = new SimulationOutput();
        String variable = "MyVar";
        // create a variable with time series collecting
        out.createVariable(variable, true);
        out.update(variable, 1.0);
        out.update(variable, 2.0);
        out.update(variable, 5.0);
        out.update(variable, 4.0);
        out.update(variable, 3.0);
        // now create the graph
        TimeSeriesPlot tsp = new TimeSeriesPlot("myplot", "time", "value",
                new ExperimentPackage(null, null));
        TimeSeriesLineDescription tsld = new TimeSeriesLineDescription("MyVar",
                "First value", out);
        tsp.addTimeSeriesLineDescription(tsld);
        String value = tsp.generate(300, 500);
        FileWritingUtil.writeToTextFile(new File("TimeSeriesTest.m"), value);
        TextUi.println(value);
    }

}
