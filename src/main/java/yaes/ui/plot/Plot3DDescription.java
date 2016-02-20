/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Oct 26, 2009
 
   yaes.ui.plot.Plot3DDescription
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import java.util.ArrayList;
import java.util.List;

import yaes.framework.simulation.parametersweep.ExperimentPackage;
import yaes.ui.text.TextUi;

/**
 * 
 * <code>yaes.ui.plot.Plot3DDescription</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class Plot3DDescription extends AbstractGraphDescription {
    public enum Plot3DType {
        MESH, SURF, SURFL
    }

    private final List<PlotSurfaceDescription> psds = new ArrayList<>();

    private Plot3DType                         type = Plot3DType.MESH;

    /**
     * @param title
     * @param xLabel
     * @param yLabel
     * @param zLabel
     * @param experimentPack
     */
    public Plot3DDescription(String title, String xLabel, String yLabel,
            String zLabel, ExperimentPackage experimentPack) {
        super(title, xLabel, yLabel, zLabel, experimentPack);
    }

    /**
     * Adds a new plot surface
     * 
     * @param psd
     */
    public void addPlotSurface(PlotSurfaceDescription psd) {
        psds.add(psd);
    }

    /**
     * Generates a simple graph in the matlab format
     */
    @Override
    public String generate(int xsize, int ysize) {
        String plotFunction = "";
        switch (type) {
        case MESH:
            plotFunction = "mesh";
            break;
        case SURF:
            plotFunction = "surf";
            break;
        case SURFL:
            plotFunction = "surfl";
            break;
		default:
			break;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(MatlabUtil.generateBasicGraphPrefix(xsize, ysize));
        buffer.append("view([50 30]);\n");
        buffer.append(psds.get(psds.size() - 1).generateXValues("xValues"));
        buffer.append(psds.get(psds.size() - 1).generateYValues("yValues"));
        int count = 0;
        for (PlotSurfaceDescription psd : psds) {
            String zLabel = String.format("z_%03d", count);
            buffer.append(psd.generateZMatrix(zLabel));
            // String lineStyleSpecification = getLineStyle(count);
            // String signSpecification = ", 'b-"
            // + AbstractGraphDescription.sign[count
            // % AbstractGraphDescription.sign.length] + "'";
            String labelSpecification = "";
            if (type == Plot3DType.MESH) {
                labelSpecification = ",'DisplayName','"
                        + getExperimentPack().getVariableDescription(
                                psd.getLabel()) + "'";
            }
            buffer.append(plotFunction + "(xValues, yValues, " + zLabel
                    + labelSpecification + ")\n");
            count++;
        }
        buffer.append(MatlabUtil.generateBasicGraphProperties(this));
        switch (type) {
        case MESH:
            buffer.append("legend1 = legend(axes1,'show');\n");
            break;
        case SURF:
            break;
        case SURFL:
            buffer.append("shading interp;\n");
            buffer.append("colormap(gray);\n");
            break;
		default:
			break;
        }

        buffer.append("hold off\n");
        return buffer.toString();
    }

    /**
     * @return
     */
    @Override
    public String generateOctave(int xsize, int ysize) {
        TextUi.errorPrint("3D plotting not yet supported for Octave");
        return "xx";
    }

    /**
     * @return the type
     */
    public Plot3DType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Plot3DType type) {
        this.type = type;
    }

}
