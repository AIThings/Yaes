/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Oct 28, 2009
 
   yaes.framework.simulation.parametersweep.FullExecutionSOSource
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import yaes.framework.simulation.IntegratedSOP;
import yaes.framework.simulation.SimulationOutput;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.FullExecutionSOSource</code>
 * 
 * a simulation output source which relies on full execution (ie. all the SOs
 * are in place).
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class FullExecutionSOSource implements ISOSource {
    /**
     * Utility function for gathering a list of so's
     * 
     * @param currentTopDir
     * @param sweep
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public List<SimulationOutput> getOneLevelOfSOs(File currentTopDir,
            ParameterSweep sweep) {
        List<SimulationOutput> outputs = new ArrayList<>();
        for (ScenarioDistinguisher sd : sweep.getList()) {
            File f = new File(currentTopDir, sd.getName()
                    + ExperimentPackage.EXTENSION_OUTPUT);
            SimulationOutput out = SimulationOutput.restore(f);
            outputs.add(out);
        }
        return outputs;
    }

    /**
     * Utility function for gathering list of list of so's
     * 
     * @param currentTopDir
     * @param sweep1
     * @param sweep2
     * @return
     */
    @Override
    public List<List<SimulationOutput>> getTwoLevelsOfSOs(File currentTopDir,
            ParameterSweep sweep1, ParameterSweep sweep2) {
        List<List<SimulationOutput>> outputsList = new ArrayList<>();
        for (ScenarioDistinguisher sd1 : sweep1.getList()) {
            List<SimulationOutput> outputs = new ArrayList<>();
            outputsList.add(outputs);
            File f1 = new File(currentTopDir, sd1.getName());
            // now the second level
            for (ScenarioDistinguisher sd2 : sweep2.getList()) {
                File f2 = new File(f1, sd2.getName()
                        + ExperimentPackage.EXTENSION_OUTPUT);
                SimulationOutput out = SimulationOutput.restore(f2);
                outputs.add(out);
            }
        }
        return outputsList;
    }

    /**
     * Gets two levels, integrate on the third one
     * 
     * @param sweep1
     * @param sweep2
     * @param sweepRepetition
     * @return
     */
    @Override
    public List<List<SimulationOutput>> getTwoLevelsOfSOsIntegrated(
            File directory, ParameterSweep sweep1, ParameterSweep sweep2,
            ParameterSweep sweepRepetition)  {
        List<List<SimulationOutput>> outputsList = new ArrayList<>();
        for (ScenarioDistinguisher sd1 : sweep1.getList()) {
            File f1 = new File(directory, sd1.getName());
            List<SimulationOutput> outputs = new ArrayList<>();
            outputsList.add(outputs);
            for (ScenarioDistinguisher sd2 : sweep2.getList()) {
                File f2 = new File(f1, sd2.getName());
                List<SimulationOutput> averageOver = new ArrayList<>();
                for (ScenarioDistinguisher sd3 : sweepRepetition.getList()) {
                    File f3 = new File(f2, sd3.getName()
                            + ExperimentPackage.EXTENSION_OUTPUT);
                    SimulationOutput out = SimulationOutput.restore(f3);
                    averageOver.add(out);
                }
                SimulationOutput avg = IntegratedSOP.integrate(averageOver);
                outputs.add(avg);
            }
        }
        return outputsList;
    }

}
