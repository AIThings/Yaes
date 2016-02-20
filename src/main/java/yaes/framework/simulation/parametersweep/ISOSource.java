/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Oct 28, 2009
 
   yaes.framework.simulation.parametersweep.ISimulationOutputSource
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import yaes.framework.simulation.SimulationOutput;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.ISimulationOutputSource</code>
 * 
 * A source of list and list of list of outputs.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public interface ISOSource {
    List<SimulationOutput> getOneLevelOfSOs(File currentTopDir,
            ParameterSweep sweep);

    List<List<SimulationOutput>> getTwoLevelsOfSOs(File currentTopDir,
            ParameterSweep sweep1, ParameterSweep sweep2);

    /**
     * Gets two levels of SOs, the last being a stochastic one, over which we
     * integrate
     * 
     * @param directory
     * @param sweep1
     * @param sweep2
     * @param sweepRepetition
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    List<List<SimulationOutput>> getTwoLevelsOfSOsIntegrated(File directory,
            ParameterSweep sweep1, ParameterSweep sweep2,
            ParameterSweep sweepRepetition);
}
