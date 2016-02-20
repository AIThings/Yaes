/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Aug 5, 2010
 
   yaes.framework.simulation.SimulationLog
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation;

import java.io.File;
import java.util.Arrays;

import yaes.util.SaveLoadUtil;
import yaes.util.SimpleFileFilter;

/**
 * 
 * <code>yaes.framework.simulation.SimulationLog</code>
 * 
 * This function logs the simulation, by saving the context at each step. It is
 * also used to load
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class SimulationLog {

    private static final String EXTENSION = "yctx"; // YAES context
    private static final String PREFIX    = "time_";

    private IContext            context   = null;
    private File[]              files;
    private File                logDirectory;

    /**
     * Constructor for the situation when we are loading the log
     * 
     * @param context
     * @param logDirectory
     */
    public SimulationLog(File logDirectory) {
        this.logDirectory = logDirectory;
        files = logDirectory.listFiles(new SimpleFileFilter(
                SimulationLog.EXTENSION));
        Arrays.sort(files);
    }

    /**
     * Constructor for the situation when we are saving the log
     * 
     * @param context
     * @param logDirectory
     */
    public SimulationLog(IContext context, File logDirectory) {
        this.context = context;
        this.logDirectory = logDirectory;
    }

    public IContext getContext() {
        return context;
    }

    /**
     * Returns the number of steps
     * 
     * @return
     */
    public int getTotalSteps() {
        return files.length;
    }

    public void loadStep(int i) {
        File currentFile = files[i];
        SaveLoadUtil<IContext> saveload = new SaveLoadUtil<>();
        context = saveload.load(currentFile);
    }

    public void saveStep(double time) {
        File saveFile = new File(logDirectory, SimulationLog.PREFIX
                + String.format("%04d", (int) time) + "."
                + SimulationLog.EXTENSION);
        SaveLoadUtil<IContext> saveload = new SaveLoadUtil<>();
        saveload.save(context, saveFile);
    }

}
