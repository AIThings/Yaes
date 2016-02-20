/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Oct 26, 2009
 
   yaes.ui.plot.PlotSurfaceDescription
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import java.util.ArrayList;
import java.util.List;

import yaes.framework.simulation.RandomVariable;
import yaes.framework.simulation.RandomVariable.Probe;
import yaes.framework.simulation.SimulationOutput;

/**
 * 
 * <code>yaes.ui.plot.PlotSurfaceDescription</code> Describes a plot surface, it
 * is the equivalent of the PlotLineDescription for 3D plots
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PlotSurfaceDescription {

    private List<List<SimulationOutput>> data = null;
    private final String                 label;
    private final String                 xVar;

    private final RandomVariable.Probe   xVarProbe;
    private final String                 yVar;
    private final RandomVariable.Probe   yVarProbe;
    private final String                 zVar;
    private final RandomVariable.Probe   zVarProbe;

    /**
     * Constructor with all the stuff
     * 
     * @param data
     * @param label
     * @param xVar
     * @param xVarProbe
     * @param yVar
     * @param yVarProbe
     * @param zVar
     * @param zVarProbe
     */
    public PlotSurfaceDescription(List<List<SimulationOutput>> data,
            String label, String xVar, Probe xVarProbe, String yVar,
            Probe yVarProbe, String zVar, Probe zVarProbe) {
        super();
        this.data = data;
        this.label = label;
        this.xVar = xVar;
        this.xVarProbe = xVarProbe;
        this.yVar = yVar;
        this.yVarProbe = yVarProbe;
        this.zVar = zVar;
        this.zVarProbe = zVarProbe;
    }

    /**
     * Generates an interpolated z matrix of the name label
     */
    public String generateInterpolationMatrix(String label) {
        List<Double> xVals = new ArrayList<>();
        for (int i = 0; i != data.size(); i++) {
            xVals.add(data.get(i).get(0).getValue(xVar, xVarProbe));
        }
        List<Double> yVals = new ArrayList<>();
        for (int i = 0; i != data.get(0).size(); i++) {
            yVals.add(data.get(0).get(i).getValue(yVar, yVarProbe));
        }
        List<Double> xPoints = new ArrayList<>();
        List<Double> yPoints = new ArrayList<>();
        List<Double> zPoints = new ArrayList<>();
        for (int i = 0; i != data.size(); i++) {
            for (int j = 0; j != data.get(0).size(); j++) {
                SimulationOutput so = data.get(i).get(j);
                if (so.getValue(SimulationOutput.REAL_DATA, Probe.LASTVALUE) == 1.0) {
                    xPoints.add(xVals.get(i));
                    yPoints.add(yVals.get(j));
                    zPoints.add(so.getValue(zVar, zVarProbe));
                }
            }
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(MatlabUtil.getMatlabVector(label + "_xpoints", xPoints));
        buffer.append(MatlabUtil.getMatlabVector(label + "_ypoints", yPoints));
        buffer.append(MatlabUtil.getMatlabVector(label + "_zpoints", zPoints));
        buffer.append(MatlabUtil.getMatlabVector(label + "_xPos", xVals));
        buffer.append(MatlabUtil.getMatlabVector(label + "_yPos", yVals));
        buffer.append("[" + label + "_xPos2, " + label + "_yPos2] = meshgrid("
                + label + "_xPos, " + label + "_yPos);\n");
        buffer.append(label + "=griddata(" + label + "_xpoints" + ", " + label
                + "_ypoints" + ", " + label + "_zpoints" + ", " + label
                + "_xPos2" + ", " + label + "_yPos2" + ");\n");
        return buffer.toString();
    }

    public String generateXValues(String label) {
        List<SimulationOutput> list = new ArrayList<>();
        for (int i = 0; i != data.size(); i++) {
            list.add(data.get(i).get(0));
        }
        return MatlabUtil.getMatlabVectorFromSOP(label, list, xVar, xVarProbe,
                true);
    }

    /**
     * For this one we need to create a cross-section
     * 
     * @param label
     * @return
     */
    public String generateYValues(String label) {
        return MatlabUtil.getMatlabVectorFromSOP(label, data.get(0), yVar,
                yVarProbe, true);
    }

    public String generateZMatrix(String label) {
        boolean allFilled = true;
        for (int i = 0; i != data.size(); i++) {
            for (int j = 0; j != data.get(0).size(); j++) {
                SimulationOutput so = data.get(i).get(j);
                if (so.getValue(SimulationOutput.REAL_DATA, Probe.LASTVALUE) == 0.0) {
                    allFilled = false;
                    break;
                }
            }
        }
        if (allFilled) {
            return MatlabUtil.getMatlabMatrixFromSOP(label, data, zVar,
                    zVarProbe);
        } else {
            return generateInterpolationMatrix(label);
        }
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
