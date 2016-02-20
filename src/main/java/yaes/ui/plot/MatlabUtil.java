/*
   This file is part of the Yet Another Extensible Simulator
 
   Created on: Jul 18, 2008
 
   yaestest.ui.plot.testTimeSeriesPlot
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import java.io.File;
import java.util.List;

import yaes.framework.simulation.RandomVariable;
import yaes.framework.simulation.RandomVariable.Probe;
import yaes.framework.simulation.SimulationOutput;
import yaes.framework.simulation.TimestampedValue;

/**
 * 
 * Various utilities for Matlab code generation
 * 
 * <code>yaes.ui.plot.MatlabUtil</code>
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class MatlabUtil {

    public static int ITEM_IN_A_LINE = 10;

    /**
     * Generates the basic graph prefix
     * 
     * @param buffer
     */
    public static String generateBasicGraphPrefix(int xsize, int ysize) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("figure1 = figure('Position', [0 0 " + xsize + " " + ysize + "]);\n");
        buffer.append("axes1 = axes('Parent',figure1);\n");
        // buffer.append("legend('show')\n");
        buffer.append("hold('all')\n");
        return buffer.toString();
    }

    /**
     * Generates matlab text for basic graph properties
     */
    public static String generateBasicGraphProperties(
            AbstractGraphDescription graphDescription) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("hold on\n");
        if (graphDescription.getTitle() != null) {
            buffer.append("title('" + graphDescription.getTitle() + "')\n");
        }
        buffer.append("hold on\n");
        buffer.append("xlabel('"
                + graphDescription.getExperimentPack().getVariableDescription(
                        graphDescription.getXLabel()) + "')\n");
        buffer.append("hold on\n");
        buffer.append("ylabel('"
                + graphDescription.getExperimentPack().getVariableDescription(
                        graphDescription.getYLabel()) + "')\n");
        return buffer.toString();
    }

    /**
     * Returns a two dimensional matrix description in Matlab
     * 
     * The assumption here is that the first is the x and the second is the y
     * 
     * @param varName
     * @param z
     * @return
     */
    public static String getMatlabMatrix(String varName, double[][] values) {
        final StringBuffer buffer = new StringBuffer(varName + " = [");
        int ysize = values[0].length;
        int xsize = values.length;
        for (int i = 0; i < xsize; i++) {
            int count = 0;
            for (int j = 0; j < ysize; j++) {
                buffer.append(values[i][j] + " ");
                count++;
                if (count % MatlabUtil.ITEM_IN_A_LINE == 0) {
                    buffer.append("...\n       ");
                }
            }
            buffer.append("\n");
        }
        
        buffer.append("];\n");
        return buffer.toString();
    }

    /**
     * Returns a two dimensional matrix description in Matlab
     * 
     * @param varName
     * @param z
     * @return
     */
    public static String getMatlabMatrixFromSOP(String name,
            List<List<SimulationOutput>> data, String randomVar, Probe probe) {
        final StringBuffer buffer = new StringBuffer(name + " = [");
        int count = 0;
        for (List<SimulationOutput> row : data) {
            for (SimulationOutput output : row) {
                buffer.append(output.getRandomVar(randomVar).getValue(probe)
                        + " ");
                count++;
                if (count % MatlabUtil.ITEM_IN_A_LINE == 0) {
                    buffer.append("...\n       ");
                }
            }
            buffer.append(";\n");
        }
        buffer.append("];\n");
        buffer.append(name + " = transpose(" + name + ");\n");
        return buffer.toString();
    }

    /**
     * Returns a one dimensional vector description in Matlab for a List<String>
     * of form {'one','two','three'}
     */
    public static String getMatlabVector(List<String> values) {
        final StringBuffer buffer = new StringBuffer("{");
        for (int i = 0; i != values.size(); i++) {
            String s = values.get(i);
            buffer.append("'" + s + "'");
            if (i != values.size() - 1) {
                buffer.append(",");
            }
        }
        buffer.append("}");
        return buffer.toString();
    }

    /**
     * Returns a one dimensional vector description in Matlab.
     * 
     * @return
     */
    public static String getMatlabVector(String varName, double[] values) {
        final StringBuffer buffer = new StringBuffer();
        if (varName == null) {
            buffer.append("[");
        } else {
            buffer.append(varName + " = [");
        }
        int count = 0;
        for (double element : values) {
            buffer.append(element + " ");
            count++;
            if (count % MatlabUtil.ITEM_IN_A_LINE == 0) {
                buffer.append("...\n       ");
            }
        }
        buffer.append("]");
        if (varName != null) {
            buffer.append(";\n");
        }
        return buffer.toString();
    }

    /**
     * Returns a one dimensional vector description in Matlab for a List<Double>
     */
    public static String getMatlabVector(String varName,
            List<? extends Number> values) {
        final StringBuffer buffer = new StringBuffer();
        if (varName == null) {
            buffer.append("[");
        } else {
            buffer.append(varName + " = [");
        }
        int count = 0;
        for (Number n : values) {
            buffer.append(n + " ");
            count++;
            if (count % MatlabUtil.ITEM_IN_A_LINE == 0) {
                buffer.append("...\n       ");
            }
        }
        buffer.append("]");
        if (varName != null) {
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * Generates a matlab vector from a list of simulation outputs and a
     * specified probe
     * 
     * @param name
     * @param data
     * @param randomVar
     * @param probe
     * @return
     */
    public static String getMatlabVectorFromSOP(String name,
            List<SimulationOutput> data, String randomVar, Probe probe,
            boolean allowUnreal) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(name + " = [");
        int count = 0;
        for (SimulationOutput output : data) {
            // skip if it is not a real value
            if (!allowUnreal
                    && (output.getValue(SimulationOutput.REAL_DATA,
                            Probe.LASTVALUE) == 0.0)) {
                continue;
            }
            buffer.append(output.getRandomVar(randomVar).getValue(probe) + " ");
            count++;
            if (count % MatlabUtil.ITEM_IN_A_LINE == 0) {
                buffer.append("...\n       ");
            }
        }
        buffer.append("];\n");
        return buffer.toString();
    }

    /**
     * Generates two matlab vectors of equal size for the time and value
     * components of a time series. Adds a newline after every 10-th number
     * 
     */
    public static String getTimeSeriesVectors(String timeName,
            String valueName, RandomVariable variable) {
        final StringBuffer timeValues = new StringBuffer();
        final StringBuffer valueValues = new StringBuffer();
        timeValues.append(timeName + " = [");
        valueValues.append(valueName + " = [");
        int count = 0;
        if (variable.getTimeSeries() == null) {
            throw new Error("Trying to extract time series from variable "
                    + variable.getName() + " which does not have it.");
        }
        for (TimestampedValue tv : variable.getTimeSeries()) {
            timeValues.append(tv.getTime() + " ");
            valueValues.append(tv.getValue() + " ");
            // add newlines after every ITEM_IN_A_LINE items
            count++;
            if (count % MatlabUtil.ITEM_IN_A_LINE == 0) {
                timeValues.append("...\n       ");
                valueValues.append("...\n       ");
            }
        }
        timeValues.append("];\n");
        valueValues.append("];\n");
        return timeValues.toString() + valueValues.toString();
    }

    
    
    /**
     * Generates a file for processing all the figures in a directory and convert them
     * to pdf...
     * 
     * -Note: this had been originally developed for Octave, to be seen whether it works for
     * matlab or not. 
     */
    public static void generateProcessAll(File dir) {
        OctaveUtil.generateProcessAll(dir);
    }
    
	/**
	 * Generates a 3D surface in gray colormap
	 * 
	 * @return
	 */
	public static String generateSurface(double[][] values, String firstLabel,
			double[] firstValues, String secondLabel, double[] secondValues) {
		StringBuffer buf = new StringBuffer();
		buf.append(MatlabUtil.getMatlabMatrix("values", values));
		buf.append(MatlabUtil.getMatlabVector("x", firstValues));
		buf.append(MatlabUtil.getMatlabVector("y", secondValues));
		buf.append("surf(x,y,values);\n");
		buf.append("colormap(gray);\n");
		buf.append("xlabel('" + firstLabel + "');\n");
		buf.append("ylabel('" + secondLabel + "');\n");
		return buf.toString();
	}

}
