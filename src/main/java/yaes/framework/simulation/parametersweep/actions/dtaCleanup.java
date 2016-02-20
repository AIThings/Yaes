/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 28, 2009
 
   yaes.framework.simulation.parametersweep.actions.dtaCleanup
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep.actions;

import java.io.File;

import yaes.framework.simulation.parametersweep.ExperimentPackage;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.actions.dtaCleanup</code>
 * 
 * an action which performs a cleanup process
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class dtaCleanup implements IDirectoryTraversalAction {

    private boolean lockOnly = true;

    /**
     * Constructor
     * 
     * @param lockOnly
     *            cleans the lock only, ignores the output files
     */
    public dtaCleanup(boolean lockOnly) {
        this.lockOnly = lockOnly;
    }

    /**
     * 
     * Cleans the files (input,output,text and lock) if lockOnly is false Cleans
     * only the lock files if lockOnly is true (this should be called every new
     * run, as the lock file stays there if the program leaves).
     * 
     * @param lockOnly
     * @param file
     */
    @Override
    public void action(File inputFile) {
        String inputfilepath = inputFile.getAbsolutePath();
        String root = inputfilepath.substring(0, inputfilepath.length()
                - ExperimentPackage.EXTENSION_INPUT.length());
        File outputFile = new File(root + ExperimentPackage.EXTENSION_OUTPUT);
        File txtFile = new File(root + ExperimentPackage.EXTENSION_TXT);
        File lockFile = new File(root + ExperimentPackage.EXTENSION_LOCK);
        if (!lockOnly) {
            inputFile.delete();
            outputFile.delete();
            txtFile.delete();
        }
        lockFile.delete();
    }

}
