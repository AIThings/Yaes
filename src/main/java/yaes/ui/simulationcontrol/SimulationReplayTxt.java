/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Aug 6, 2010
 
   yaes.ui.simulationcontrol.SimulationReplayTxt
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.simulationcontrol;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import yaes.framework.simulation.IContext;
import yaes.framework.simulation.SimulationLog;
import yaes.ui.format.Formatter;
import yaes.ui.text.TextUi;
import yaes.ui.visualization.Visualizer;
import yaes.util.DirUtil;

/**
 * 
 * <code>yaes.ui.simulationcontrol.SimulationReplayTxt</code>
 * 
 * 
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class SimulationReplayTxt {

    private static final String MENU_CREATE_VIDEO = "Create video";
    private static final String MENU_LIST_CURRENT_STATE = "List current state";
    private static final String MENU_NEXT = "Next";
    private static final String MENU_PREVIOUS = "Previous";
    private static final String MENU_RUN_TO_END = "Run to end";
    private static final String MENU_RUN_TO_TIME = "Run to number";
    private static final String MENU_JUMP_TO_TIME = "Jump to number";
    private static final String MENU_SETTINGS = "Settings";
    private static final String MENU_QUIT = "Quit";
    private static Visualizer visual;
    private int currentStep = 0;
    private boolean defaultForward = true;
    private SimulationLog slog;

    /**
     * Returns the current simulation log object
     * @return
     */
    public SimulationLog getSlog() {
		return slog;
	}

	/**
     * Shows the visual display
     */
    private boolean sttngShowVisual = true;
    /**
     * The acceleration of time
     */
    private double sttngTimeAcceleration = 10.0;

    public SimulationReplayTxt(File logDirectory) {
        slog = new SimulationLog(logDirectory);
        slog.loadStep(currentStep);
        SimulationReplayTxt.visual =
                new Visualizer(800, 800, null, "Simulation replay", true);
    }

    /**
     * 
     */
    private void conciseDisplay() {
        TextUi.println("Current step: " + currentStep + " / "
                + slog.getTotalSteps());
    }

    /**
     * Creates a series of image files in the png format which can 
     * later be assembled into a video
     * 
     * 
     */
    private void doCreateVideo() {
        SimulationReplayTxt.visual.setUpdatedInspector(true);
        DirUtil.guaranteeDirectory("video");
        while (true) {
            boolean hasMore = doNext(false);
            if (!hasMore) {
                break;
            }
            java.util.Formatter f = new java.util.Formatter();            
            f.format("video/img-%05d.png", currentStep);
            String fileName = f.toString();
            try {
				SimulationReplayTxt.visual.saveImage(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }        
        TextUi.println("PNG files generated in the 'video' directory. Use appropriate software to assemble them into a video file.");
    }

    /**
     *  Lists the current state of the simulation: the context, the simulation 
     */
    private void doListCurrentState() {
    	IContext context = slog.getContext();
    	Formatter fmt = new Formatter();
    	fmt.add("SimulationInput:");
    	fmt.addIndented(context.getSimulationInput());
    	fmt.add("SimulationOutput:");
    	fmt.addIndented(context.getSimulationOutput());
    	fmt.add("Context:");
    	fmt.addIndented(context.toString());
        TextUi.println(fmt.toString());
    }

    /**
     * Moves to the next saved value It should be called with false when
     * continuous running
     * 
     * @param updateState
     *            - whether we want to update the status as well
     */
    private boolean doNext(boolean updateStatus) {
        defaultForward = true;
        if (currentStep < slog.getTotalSteps() - 1) {
            currentStep++;
            slog.loadStep(currentStep);
            updateVisual(updateStatus);
            return true;
        }
        return false;
    }

    /**
     * Moves to the previous saved value
     */
    private boolean doPrevious() {
        defaultForward = false;
        if (currentStep > 0) {
            currentStep--;
            slog.loadStep(currentStep);
            updateVisual(true);
            return true;
        }
        return false;
    }

    /**
     * 
     */
    private void doRunToEnd() {
        SimulationReplayTxt.visual.setUpdatedInspector(false);
        while (true) {
            boolean hasMore = doNext(false);
            if (!hasMore) {
                break;
            }
            try {
                Thread.sleep((long) (1000.0 / sttngTimeAcceleration));
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
        SimulationReplayTxt.visual.setUpdatedInspector(true);
    }

    /**
     * Run to time
     */
    private void doRunToTime() {
        int toTime =
                TextUi.inputInteger("Run until time " + currentStep + ".."
                        + slog.getTotalSteps());
        if (toTime <= currentStep) {
            TextUi.println("Cannot run backwards!");
            return;
        }
        if (toTime > slog.getTotalSteps()) {
            TextUi.println("Entered value larger than maximum: "
                    + slog.getTotalSteps());
            return;
        }
        SimulationReplayTxt.visual.setUpdatedInspector(false);
        while (currentStep < toTime) {
            boolean hasMore = doNext(false);
            if (!hasMore) {
                break;
            }
            try {
                Thread.sleep((long) (1000.0 / sttngTimeAcceleration));
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
        SimulationReplayTxt.visual.setUpdatedInspector(true);
    }

    /**
     * 
     */
    private void doSettings() {
        sttngShowVisual = TextUi.inputBoolean("Show visual?", sttngShowVisual);
        sttngTimeAcceleration =
                TextUi.inputDouble("Time acceleration", sttngTimeAcceleration);
    }

    /**
     * The main loop of the simulation
     */
    public void mainLoop() {
        while (true) {
            conciseDisplay();
            updateVisual(true);
            final List<String> menu = new ArrayList<>();
            menu.add(SimulationReplayTxt.MENU_NEXT);
            menu.add(SimulationReplayTxt.MENU_PREVIOUS);
            menu.add(SimulationReplayTxt.MENU_RUN_TO_END);
            menu.add(SimulationReplayTxt.MENU_RUN_TO_TIME);
            menu.add(SimulationReplayTxt.MENU_JUMP_TO_TIME);
            menu.add(SimulationReplayTxt.MENU_LIST_CURRENT_STATE);
            menu.add(SimulationReplayTxt.MENU_CREATE_VIDEO);            
            // menu.add("-");
            menu.add(SimulationReplayTxt.MENU_SETTINGS);
            menu.add(SimulationReplayTxt.MENU_QUIT);
            
            String defaultMenu = null;
            if (defaultForward) {
                defaultMenu = SimulationReplayTxt.MENU_NEXT;
            } else {
                defaultMenu = SimulationReplayTxt.MENU_PREVIOUS;
            }
            String result = TextUi.menu(menu, defaultMenu, "Choose:");
            if (result.equals(SimulationReplayTxt.MENU_NEXT)) {
                doNext(true);
                continue;
            }
            if (result.equals(SimulationReplayTxt.MENU_PREVIOUS)) {
                doPrevious();
                continue;
            }
            if (result.equals(SimulationReplayTxt.MENU_RUN_TO_END)) {
                doRunToEnd();
                continue;
            }
            if (result.equals(SimulationReplayTxt.MENU_RUN_TO_TIME)) {
                doRunToTime();
                continue;
            }
            if (result.equals(SimulationReplayTxt.MENU_JUMP_TO_TIME)) {
                doJumpToTime();
                continue;
            }
            if (result.equals(SimulationReplayTxt.MENU_LIST_CURRENT_STATE)) {
                doListCurrentState();
                continue;
            }
            if (result.equals(SimulationReplayTxt.MENU_CREATE_VIDEO)) {
                doCreateVideo();
                continue;
            }
            if (result.equals(SimulationReplayTxt.MENU_SETTINGS)) {
                doSettings();
                continue;
            }
            if (result.equals(SimulationReplayTxt.MENU_QUIT)) {
                TextUi.println("Quitting simulation replay");
                System.exit(0);
                continue;
            }
        }
    }

    /**
     *  Jumps to a time point
     */
    private void doJumpToTime() {
        int toTime =
                TextUi.inputInteger("Jump to time 0" + ".."
                        + slog.getTotalSteps());
        if (toTime <= 0) {
            TextUi.println("Cannot jump to negative time!");
            return;
        }
        if (toTime > slog.getTotalSteps()) {
            TextUi.println("Entered value larger than maximum: "
                    + slog.getTotalSteps());
            return;
        }
        defaultForward = true;
        currentStep = toTime;
        slog.loadStep(currentStep);
        updateVisual(true);
    }

    /**
     * Creates a visual representation
     */
    private void updateVisual(boolean updateStatusBar) {
        if (sttngShowVisual) {
            slog.getContext().createVisualRepresentation(
                    SimulationReplayTxt.visual);
            if (updateStatusBar) {
                SimulationReplayTxt.visual.update();
            } else {
                SimulationReplayTxt.visual.getVisualCanvas().update();
            }
        }
    }
}
