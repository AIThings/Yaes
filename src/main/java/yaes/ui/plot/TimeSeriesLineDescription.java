/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 18, 2008
 
   yaes.ui.plot.TimeSeriesLineDescription
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import yaes.framework.simulation.RandomVariable;
import yaes.framework.simulation.SimulationOutput;

/**
 * 
 * <code>yaes.ui.plot.TimeSeriesLineDescription</code>
 * 
 * Describes one line
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class TimeSeriesLineDescription {

    private SimulationOutput data = null;
    private final String     label;
    private final String     name;

    public TimeSeriesLineDescription(String name, String label,
            SimulationOutput data) {
        this.name = name;
        this.label = label;
        this.data = data;
    }

    /**
     * Generates the values for this line
     * 
     * @return
     */
    public String generate(String lineStyle) {
        StringBuffer buffer = new StringBuffer();
        RandomVariable rv = data.getRandomVar(name);
        String timeVectorName = name + "_time";
        String valueVectorName = name + "_value";
        String labelSpecification = ",'DisplayName','" + label + "'";
        buffer.append(MatlabUtil.getTimeSeriesVectors(timeVectorName,
                valueVectorName, rv));
        buffer.append("plot(" + timeVectorName + ", " + valueVectorName
                + lineStyle + labelSpecification + ");\n");
        return buffer.toString();
    }

    /**
     * Generate
     * 
     * @return
     */
    public String generateOctave(String lineStyle) {
        StringBuffer buffer = new StringBuffer();
        RandomVariable rv = data.getRandomVar(name);
        String timeVectorName = name + "_time";
        String valueVectorName = name + "_value";
        String labelSpecification = ",'keylabel','" + label + "'";
        buffer.append(MatlabUtil.getTimeSeriesVectors(timeVectorName,
                valueVectorName, rv));
        buffer.append("plot(" + timeVectorName + ", " + valueVectorName
                + lineStyle + labelSpecification + ");\n");
        return buffer.toString();
    }
}
