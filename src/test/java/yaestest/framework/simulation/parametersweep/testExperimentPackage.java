/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jun 23, 2009
 
   yaestest.framework.simulation.parametersweep.testExperimentPackage
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.framework.simulation.parametersweep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.parametersweep.ExperimentPackage;
import yaes.framework.simulation.parametersweep.ParameterSweep;
import yaes.framework.simulation.parametersweep.ParameterSweepHelper;
import yaes.framework.simulation.parametersweep.ScenarioDistinguisher;
import yaes.ui.text.TextUi;

/**
 * 
 * <code>yaestest.framework.simulation.parametersweep.testExperimentPackage</code>
 * tests the operation of the experiment package, also serves as a root
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testExperimentPackage implements PSWConstants {

    File basedir  = new File(ExperimentPackage.dataYaes, "parametersweeptest");
    File graphdir = new File(ExperimentPackage.dataYaes,
                          "parametersweeptest_graph");

    /**
     * Helper function, to check how many of the files exist
     * 
     * @param files
     */
    public void countExistingFiles(List<File> files) {
        int count = 0;
        for (File f : files) {
            if (f.exists()) {
                count++;
            }
        }
        TextUi.println("Exists " + count + " from " + files.size());
    }

    public ExperimentPackage createD() {
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(new File(basedir, "D"),
                graphdir);
        pack.setModel(model);
        // discrete values
        ParameterSweep sweepDiscrete = createDiscreteSweep();
        pack.addParameterSweep(sweepDiscrete);
        return pack;
    }

    private SimulationInput createDefaultModel() {
        SimulationInput model = new SimulationInput();
        model.setContextClass(PSWContext.class);
        model.setSimulationClass(PSWSimulation.class);
        model.setParameter(PSWConstants.INPUT_PARAM_1, 0.0);
        model.setParameter(PSWConstants.INPUT_PARAM_2, 0.0);
        model.setParameter(PSWConstants.INPUT_PARAM_3, 1.0);
        model.setParameter(PSWConstants.INPUT_PARAM_4, 0);
        return model;
    }

    /**
     * Utility function, creates a discrete sweep
     * 
     * @return
     */
    private ParameterSweep createDiscreteSweep() {
        ParameterSweep sweepDiscrete = new ParameterSweep("protocols");
        ScenarioDistinguisher sd1 = new ScenarioDistinguisher("Function 1");
        sd1.setDistinguisher(PSWConstants.INPUT_PARAM_3, 1.0);
        sweepDiscrete.addDistinguisher(sd1);
        ScenarioDistinguisher sd2 = new ScenarioDistinguisher("Function 2");
        sd2.setDistinguisher(PSWConstants.INPUT_PARAM_3, 2.0);
        sweepDiscrete.addDistinguisher(sd2);
        ScenarioDistinguisher sd3 = new ScenarioDistinguisher("Function 3");
        sd3.setDistinguisher(PSWConstants.INPUT_PARAM_3, 3.0);
        sweepDiscrete.addDistinguisher(sd3);
        return sweepDiscrete;
    }

    public ExperimentPackage createDN() {
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(new File(basedir, "DN"),
                graphdir);
        pack.setModel(model);
        // discrete values
        ParameterSweep sweepDiscrete = createDiscreteSweep();
        pack.addParameterSweep(sweepDiscrete);
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1",
                        PSWConstants.INPUT_PARAM_1, 10, -5, 5));
        return pack;
    }

    /**
     * Test for a bargraph (D stack)
     */
    @Test
    public void testD() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        ExperimentPackage pack = createD();
        pack.cleanUp();
        pack.initialize();
        pack.run();
        pack.generateGraph(PSWConstants.OUTPUT_PARAM_1, "Stack: D", "D");
    }

    /**
     * Test for a multiple line graph (DN)
     */
    @Test
    public void testDN() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        ExperimentPackage pack = createDN();
        pack.cleanUp();
        pack.initialize();
        pack.run();
        pack.generateGraph(PSWConstants.OUTPUT_PARAM_1, "Stack: DN", "DN");
    }

    /**
     * Stack DNN: multiple 3D surfaces
     */
    @Test
    public void testDNN() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(
                new File(basedir, "DNN"), graphdir);
        pack.setModel(model);
        ParameterSweep sweepDiscrete = createDiscreteSweep();
        pack.addParameterSweep(sweepDiscrete);
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1",
                        PSWConstants.INPUT_PARAM_1, 10, -5, 5));
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter2",
                        PSWConstants.INPUT_PARAM_2, 10, -5, 5));
        pack.cleanUp();
        pack.initialize();
        pack.run();
        pack.generateGraph(PSWConstants.OUTPUT_PARAM_1, "Stack: DNN", "DNN");
    }

    /**
     * Stack DNNS: multiple 3D surfaces (with smoothing)
     */
    @Test
    public void testDNNS() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(
                new File(basedir, "DNSS"), graphdir);
        pack.setModel(model);
        ParameterSweep sweepDiscrete = createDiscreteSweep();
        pack.addParameterSweep(sweepDiscrete);
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1",
                        PSWConstants.INPUT_PARAM_1, 10, -5, 5));
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter2",
                        PSWConstants.INPUT_PARAM_2, 10, -5, 5));
        // randomization parameter
        ParameterSweep sweepRandom = ParameterSweepHelper
                .generateParameterSweepInteger("inputParameter4",
                        PSWConstants.INPUT_PARAM_4, 1, 50);
        sweepRandom.setType(ParameterSweep.ParameterSweepType.Repetition);
        pack.addParameterSweep(sweepRandom);
        // pack.cleanUp();
        pack.initialize();
        pack.run();
        pack.generateGraph(PSWConstants.OUTPUT_PARAM_1, "Stack: DNNS", "DNNS");
    }

    /**
     * Test for a multiple line graph (DN)
     */
    @Test
    public void testDNS() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        File basedir = new File("data/parametersweeptest");
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(
                new File(basedir, "DNS"), graphdir);
        pack.setModel(model);
        ParameterSweep sweepDiscrete = createDiscreteSweep();
        pack.addParameterSweep(sweepDiscrete);
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1",
                        PSWConstants.INPUT_PARAM_1, 10, -2, 2));
        // randomization parameter
        ParameterSweep sweepRandom = ParameterSweepHelper
                .generateParameterSweepInteger("inputParameter4",
                        PSWConstants.INPUT_PARAM_4, 1, 50);
        sweepRandom.setType(ParameterSweep.ParameterSweepType.Repetition);
        pack.addParameterSweep(sweepRandom);
        pack.cleanUp();
        pack.initialize();
        pack.run();
        pack.generateGraph(PSWConstants.OUTPUT_PARAM_2, "Stack: DNS", "DNS");
    }

    /**
     * Test for a simple single value graph: stack N
     */
    @Test
    public void testN() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(new File(basedir, "N"),
                graphdir);
        pack.setModel(model);
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1",
                        PSWConstants.INPUT_PARAM_1, 10, -5, 5));
        pack.cleanUp();
        pack.initialize();
        pack.run();
        pack.generateGraph(PSWConstants.OUTPUT_PARAM_1, "Stack: N", "N");
    }

    /**
     * Stack NN: a single 3D surface
     */
    @Test
    public void testNN() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(new File(basedir, "NN"),
                graphdir);
        pack.setModel(model);
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1",
                        PSWConstants.INPUT_PARAM_1, 10, -5, 5));
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter2",
                        PSWConstants.INPUT_PARAM_2, 10, -5, 5));
        pack.cleanUp();
        pack.initialize();
        pack.run();
        pack.generateGraph(PSWConstants.OUTPUT_PARAM_1, "Stack: NN", "NN");
    }

    /**
     * Stack NNS: single averaged 3D graph
     */
    @Test
    public void testNNS() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(
                new File(basedir, "NNS"), graphdir);
        pack.setModel(model);
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1",
                        PSWConstants.INPUT_PARAM_1, 10, -5, 5));
        // range parameter
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter2",
                        PSWConstants.INPUT_PARAM_2, 10, -5, 5));
        // randomization parameter
        ParameterSweep sweepRandom = ParameterSweepHelper
                .generateParameterSweepInteger("inputParameter4",
                        PSWConstants.INPUT_PARAM_4, 1, 50);
        sweepRandom.setType(ParameterSweep.ParameterSweepType.Repetition);
        pack.addParameterSweep(sweepRandom);
        // pack.cleanUp();
        pack.initialize();
        pack.run();
        pack.generateGraph(PSWConstants.OUTPUT_PARAM_2, "Stack: NNS", "NNS");
    }

    /**
     * Simple run
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    @Test
    public void testSimpleRun() throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        SimulationInput model = createDefaultModel();
        ExperimentPackage pack = new ExperimentPackage(new File(basedir,
                "simplerun"), basedir);
        pack.setModel(model);
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter1",
                        PSWConstants.INPUT_PARAM_1, 10, -5, 5));
        pack.addParameterSweep(ParameterSweepHelper
                .generateParameterSweepDouble("inputParameter2",
                        PSWConstants.INPUT_PARAM_2, 10, 0.01, 0.02));
        pack.cleanUp();
        pack.initialize();
        pack.run();
    }

    /**
     * Tests the handling of a slice
     */
    @Test
    public void testSliceD() {
        ExperimentPackage pack = createD();
        List<Integer> slice = pack.createFullSliceSpec();
        List<File> files = pack.getSliceFileNames(slice,
                ExperimentPackage.EXTENSION_OUTPUT);
        // TextUi.println(files);
        countExistingFiles(files);
    }

    /**
     * Tests the handling of a slice
     */
    @Test
    public void testSliceDN() {
        ExperimentPackage pack = createDN();
        List<Integer> slice = pack.createFullSliceSpec();
        List<File> files = pack.getSliceFileNames(slice,
                ExperimentPackage.EXTENSION_OUTPUT);
        countExistingFiles(files);
        slice.set(0, 2);
        files = pack.getSliceFileNames(slice,
                ExperimentPackage.EXTENSION_OUTPUT);
        countExistingFiles(files);
    }

}
