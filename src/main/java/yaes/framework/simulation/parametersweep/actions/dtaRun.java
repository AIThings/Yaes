/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 28, 2009
 
   yaes.framework.simulation.parametersweep.actions.dtaRun
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep.actions;

import java.io.File;
import yaes.framework.simulation.Simulation;
import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.SimulationOutput;
import yaes.framework.simulation.parametersweep.ExperimentPackage;
import yaes.ui.format.Formatter;
import yaes.ui.text.TextUi;
import yaes.ui.text.TextUiHelper;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.actions.dtaRun</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class dtaRun implements IDirectoryTraversalAction {

    private int countRunsExecuted    = 0;
    private int countRunsInExecution = 0;
    private int countRunsNotExecuted = 0;
    private int countRunsTotal       = 0;
    private String currentExecution = "none";

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
     * @param file
     * 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void action(File inputFile) {
        String inputfilepath = inputFile.getAbsolutePath();
        String root = inputfilepath.substring(0, inputfilepath.length() - 4);
        File outputFile = new File(root + ExperimentPackage.EXTENSION_OUTPUT);
        File lockFile = new File(root + ExperimentPackage.EXTENSION_LOCK);
        if (outputFile.exists()) {
            return;
        }
        try {
        	// if the lockfile cannot be created
            if (!lockFile.createNewFile()) {            	
                return;
            }
            printCurrentStatus();
            currentExecution = inputFile.toString();
            countRunsInExecution++;
            // lockFile.deleteOnExit();
            // create the lock file and keep it open
            SimulationInput sip = SimulationInput.restore(inputFile);
            String simulationClassName = sip.getSimulationClass();
            if (simulationClassName == null) {
            	TextUi.abort("dtaRun.action: the simulation class was not specified in the sip!");
            }
            Class simulationClass = Class.forName(simulationClassName);
            String contextClassName = sip.getContextClass();
            if (contextClassName == null) {
            	TextUi.abort("dtaRun.action: the context class was not specified in the sip!");
            }            
            Class contextClass = Class.forName(contextClassName);
            SimulationOutput sop = Simulation.simulate(sip, simulationClass,
                    contextClass);
            sop.store(outputFile.toString());
        } catch (Exception e) {
        	TextUi.errorPrint("We are at dtaRun.action and found exception:");
            TextUi.errorPrint(e.toString());
            Thread.dumpStack();
            return;
        }
        countRunsInExecution--;
        countRunsExecuted++;
        countRunsNotExecuted--;
        lockFile.delete();
    }

    /**
     * @return the countRunsTotal
     */
    public int getCountRunsTotal() {
        return countRunsTotal;
    }

    /**
     * Prints the current status
     */
    public void printCurrentStatus() {
        Formatter fmt = new Formatter();
        fmt.add(TextUiHelper.createLabeledSeparator("-ExperimentPackage:"
                + countRunsExecuted + "/" + countRunsTotal));
        fmt.add("Currently executing:" + currentExecution);
        TextUi.println(fmt.toString());
    }

    /**
     * @param countRunsTotal
     *            the countRunsTotal to set
     */
    public void setCountRunsTotal(int countRunsTotal) {
        this.countRunsTotal = countRunsTotal;
    }
}
