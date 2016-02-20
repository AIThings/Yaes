package yaes.ui.plot;

import java.util.ArrayList;
import java.util.List;

import yaes.framework.simulation.parametersweep.ExperimentPackage;

/**
 * Describes a plot.
 */
public class PlotDescription extends AbstractGraphDescription {
    private final List<PlotLineDescription> plds = new ArrayList<>();

    /**
     * Creates a plot description with the specified labels
     * 
     * @param title
     * @param xLabel
     * @param yLabel
     */
    public PlotDescription(String title, String xLabel, String yLabel,
            ExperimentPackage experimentPack) {
        super(title, xLabel, yLabel, null, experimentPack);
    }

    /**
     * Adds a plot line
     * 
     * @param pld
     */
    public void addPlotLine(PlotLineDescription pld) {
        plds.add(pld);
    }

    /**
     * Generates a simple graph in the matlab format
     */
    @Override
    public String generate(int xsize, int ysize) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MatlabUtil.generateBasicGraphPrefix(xsize, ysize));
        // buffer.append(plds.get(plds.size() - 1).generateXValues("xValues"));
        int count = 0;
        for (PlotLineDescription pld : plds) {
            String xLabel = String.format("x_%03d", count);
            buffer.append(pld.generateXValues(xLabel));
            String yLabel = String.format("y_%03d", count);
            buffer.append(pld.generateYValues(yLabel));
            // String lineStyleSpecification = getLineStyle(count);
            String signSpecification = ", 'b-"
                    + AbstractGraphDescription.sign[count
                            % AbstractGraphDescription.sign.length] + "'";
            String labelSpecification = ",'DisplayName','"
                    + getExperimentPack()
                            .getVariableDescription(pld.getLabel()) + "'";
            String plotLabel = String.format("plot_%03d", count);
            buffer.append(plotLabel + " = plot(" + xLabel + ", " + yLabel
                    + signSpecification + labelSpecification + ")\n");
            count++;
        }
        buffer.append(MatlabUtil.generateBasicGraphProperties(this));
        buffer.append("legend1 = legend(axes1,'show');\n");
        buffer.append("hold off\n");
        return buffer.toString();
    }

    /**
     * @return
     */
    @Override
    public String generateErrorBarPlot(int xsize, int ysize) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MatlabUtil.generateBasicGraphPrefix(xsize, ysize));
        buffer.append(plds.get(plds.size() - 1).generateXValues("xValues"));
        int count = 0;
        for (PlotLineDescription pld : plds) {
            String yLabel = String.format("y_%03d", count);
            String radiusLabel = String.format("yradius_%03d", count);
            buffer.append(pld.generateErrorBarValues(yLabel, radiusLabel));
            // String signSpecification = ", 'b-" + sign[count % sign.length]
            // + "'";
            String signSpecification = ", 'b-'";
            String lineStyleSpecification = getLineStyle(count);
            // String lineStyleSpecification = ",'LineStyle','"
            // + type[(count / 2) % type.length] + "'";
            String labelSpecification = ",'DisplayName','"
                    + getExperimentPack()
                            .getVariableDescription(pld.getLabel()) + "'";
            // String labelSpecification = ",'DisplayName','" + pld.getLabel()
            // + "'";
            String ebLabel = String.format("eb_%03d", count);
            buffer.append(ebLabel + " = errorbar(xValues, " + yLabel + ", "
                    + radiusLabel);
            buffer.append(signSpecification);
            buffer.append(lineStyleSpecification);
            buffer.append(labelSpecification);
            buffer.append(")\n");
            count++;
        }
        buffer.append(MatlabUtil.generateBasicGraphProperties(this));
        buffer.append("legend1 = legend(axes1,'show');\n");
        buffer.append("hold off\n");
        return buffer.toString();
    }

    /**
     * Generates a simple graph in the matlab format
     */
    @Override
    public String generateOctave(int xsize, int ysize) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(OctaveUtil.generateBasicGraphPrefix(xsize, ysize));
        buffer.append(plds.get(plds.size() - 1).generateXValues("xValues"));
        int count = 0;
        for (PlotLineDescription pld : plds) {
            String yLabel = String.format("y_%03d", count);
            buffer.append(pld.generateYValues(yLabel));
            // String lineStyleSpecification = getLineStyleOctave(count);
            String signSpecification = ", '" + count % 6 + ";" + pld.getLabel()
                    + ";'";
            // String labelSpecification = ",'DisplayName','" + pld.getLabel()
            // + "'";
            buffer.append("plot(xValues, " + yLabel + signSpecification + ")\n");
            count++;
        }
        buffer.append(OctaveUtil.generateBasicGraphProperties(this));
        buffer.append("legend('show');\n");
        buffer.append("hold off\n");
        return buffer.toString();
    }

}
