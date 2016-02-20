/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Oct 28, 2009
 
   yaes.ui.plot.BarGraphDescription
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import java.util.ArrayList;
import java.util.List;

import yaes.framework.simulation.RandomVariable;
import yaes.framework.simulation.SimulationOutput;
import yaes.framework.simulation.parametersweep.ExperimentPackage;
import yaes.framework.simulation.parametersweep.ParameterSweep;
import yaes.framework.simulation.parametersweep.ScenarioDistinguisher;

/**
 * 
 * <code>yaes.ui.plot.BarGraphDescription</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class BarGraphDescription extends AbstractGraphDescription {

    private List<SimulationOutput> outputs;
    private RandomVariable.Probe   probe;
    private String                 variableName;

    /**
     * @param title
     * @param xLabel
     * @param yLabel
     * @param zLabel
     * @param experimentPack
     */
    public BarGraphDescription(String title, String variableName,
            RandomVariable.Probe probe, ExperimentPackage experimentPack,
            List<SimulationOutput> outputs) {
        super(title, null, null, null, experimentPack);
        this.variableName = variableName;
        this.probe = probe;
        this.outputs = outputs;
    }

    /**
     * @return
     */
    @Override
    public String generate(int xsize, int ysize) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MatlabUtil.generateBasicGraphPrefix(xsize, ysize));
        // this had been added with the assumption that it works for Matlab as well
        buffer.append("figure1 = figure('Position', [0 0 " + xsize + " " + ysize + "]);\n");
        //
        ParameterSweep sweepDiscrete = experimentPack.getParameterSweeps().get(
                0);
        List<String> labellist = new ArrayList<>();
        List<Double> itemlist = new ArrayList<>();
        double i = 1;
        for (ScenarioDistinguisher sd : sweepDiscrete.getList()) {
            labellist.add(sd.getName());
            itemlist.add(i);
            i++;
        }
        buffer.append("axes('Parent',figure1,'XTickLabel',"
                + MatlabUtil.getMatlabVector(labellist) + ",'XTick', "
                + MatlabUtil.getMatlabVector(null, itemlist) + ");\n");
        buffer.append("hold('all')\n");
        if (getTitle() != null) {
            buffer.append("title('" + getTitle() + "');\n");
        }
        buffer.append(MatlabUtil.getMatlabVectorFromSOP("data", outputs,
                variableName, probe, false));
        buffer.append("bar(data)\n");
        return buffer.toString();
    }

    /**
     * We go with the assumption that it is the same for Octave as for Matlab
     * 
     * @return
     */
    @Override
    public String generateOctave(int xsize, int ysize) {
        return generate(xsize, ysize);
    }

}
