/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 18, 2008
 
   yaes.ui.plot.TimeSeriesPlot
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import java.util.ArrayList;
import java.util.List;

import yaes.framework.simulation.parametersweep.ExperimentPackage;

/**
 * 
 * <code>yaes.ui.plot.TimeSeriesPlot</code>
 * 
 * Represents the description of and generates the matlab file for a graph which
 * is the representation of time series
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class TimeSeriesPlot extends AbstractGraphDescription {

    private final List<TimeSeriesLineDescription> lineDescriptions = new ArrayList<>();

    public TimeSeriesPlot(String title, String xLabel, String yLabel,
            ExperimentPackage experimentPack) {
        super(title, xLabel, yLabel, null, experimentPack);
    }

    public void addTimeSeriesLineDescription(TimeSeriesLineDescription tsld) {
        lineDescriptions.add(tsld);
    }

    /**
     * Generates the matlab file
     */
    @Override
    public String generate(int xsize, int ysize) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MatlabUtil.generateBasicGraphPrefix(xsize, ysize));
        int count = 0;
        for (TimeSeriesLineDescription tsld : lineDescriptions) {
            String lineStyle = getLineStyle(count);
            buffer.append(tsld.generate(lineStyle));
            count++;
        }
        buffer.append(MatlabUtil.generateBasicGraphProperties(this));
        buffer.append("legend1 = legend(axes1,'show');\n");
        buffer.append("hold off\n");
        return buffer.toString();
    }

    /**
     * Generates the matlab file
     */
    @Override
    public String generateOctave(int xsize, int ysize) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(OctaveUtil.generateBasicGraphPrefix(xsize, ysize));
        int count = 0;
        for (TimeSeriesLineDescription tsld : lineDescriptions) {
            String lineStyle = getLineStyleOctave(count);
            buffer.append(tsld.generateOctave(lineStyle));
            count++;
        }
        buffer.append(OctaveUtil.generateBasicGraphProperties(this));
        buffer.append("legend('show');\n");
        buffer.append("hold off\n");
        return buffer.toString();
    }

}
