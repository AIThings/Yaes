/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 28, 2009
 
   yaes.framework.simulation.parametersweep.actions.dtaCount
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep.actions;

import java.io.File;

import yaes.framework.simulation.parametersweep.ExperimentPackage;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.actions.dtaCount</code>
 * 
 * a visitor which counts the actions in various stages of execution
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class dtaCount implements IDirectoryTraversalAction {

    private int countRunsExecuted    = 0;
    private int countRunsInExecution = 0;
    private int countRunsNotExecuted = 0;
    private int countRunsTotal       = 0;

    /**
     * @param file
     */
    @Override
    public void action(File inputFile) {
        String inputfilepath = inputFile.getAbsolutePath();
        String root = inputfilepath.substring(0, inputfilepath.length()
                - ExperimentPackage.EXTENSION_INPUT.length());
        File outputFile = new File(root + ExperimentPackage.EXTENSION_OUTPUT);
        // File txtFile = new File(root + ExperimentPackage.EXTENSION_TXT);
        File lockFile = new File(root + ExperimentPackage.EXTENSION_LOCK);
        countRunsTotal++;
        if (outputFile.exists()) {
            countRunsExecuted++;
            return;
        }
        if (lockFile.exists()) {
            countRunsInExecution++;
            return;
        }
        countRunsNotExecuted++;
    }

    /**
     * @return the countRunsExecuted
     */
    public int getCountRunsExecuted() {
        return countRunsExecuted;
    }

    /**
     * @return the countRunsInExecution
     */
    public int getCountRunsInExecution() {
        return countRunsInExecution;
    }

    /**
     * @return the countRunsNotExecuted
     */
    public int getCountRunsNotExecuted() {
        return countRunsNotExecuted;
    }

    /**
     * @return the countRunsTotal
     */
    public int getCountRunsTotal() {
        return countRunsTotal;
    }

}
