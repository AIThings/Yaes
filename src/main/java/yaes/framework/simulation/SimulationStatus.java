/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 6, 2008
 
   yaes.framework.simulation.SimulationStatus
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation;

import yaes.ui.text.TextUi;
import yaes.ui.text.TextUiHelper;

/**
 * 
 * <code>yaes.framework.simulation.SimulationStatus</code>
 * 
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class SimulationStatus {
    private static final long HEARTBEAT_INTERVAL = 5000;
    private long              lastHeartBeatMS;
    private long              startingTimeMS;
    private double            totalIterations;

    public SimulationStatus(SimulationInput sim) {
        startingTimeMS = System.currentTimeMillis();
        totalIterations = sim.getStopTime();
        lastHeartBeatMS = startingTimeMS;
    }

    public void update(double currentIteration) {
        StringBuffer buffer = new StringBuffer();
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHeartBeatMS > SimulationStatus.HEARTBEAT_INTERVAL) {
            buffer.append("Iteration " + currentIteration + " / "
                    + totalIterations + ", ");
            buffer.append(TextUiHelper.formatTimeInterval(currentTime
                    - startingTimeMS)
                    + " passed, approximately ");
            long timeEstimated = (long) ((currentTime - startingTimeMS) / (currentIteration / totalIterations));
            buffer.append(TextUiHelper.formatTimeInterval(timeEstimated
                    - (currentTime - startingTimeMS))
                    + " remaining.");
            lastHeartBeatMS = currentTime;
            TextUi.println(buffer.toString());
        }

    }
}
