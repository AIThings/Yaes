package yaes.ui.plot;

import java.util.List;

import yaes.framework.simulation.RandomVariable;
import yaes.framework.simulation.RandomVariable.Probe;
import yaes.framework.simulation.SimulationOutput;

/**
 * @author Lotzi Boloni
 * 
 *         Describes a plot line in a PlotLineDescription.
 */
public class PlotLineDescription {
    private List<SimulationOutput>     data           = null;
    private final String               label;
    private IPlotLineFilter            plotLineFilter;
    private final String               xVar;
    private final RandomVariable.Probe xVarProbe;
    private RandomVariable.Probe       yRadiusVarType = Probe.CONFINT_RADIUS;
    private final String               yVar;
    private final RandomVariable.Probe yVarProbe;

    /**
     * This constructor creates a plotline description for errorbars.
     * 
     * @param varName
     * @param varType
     * @param varName2
     * @param varType2
     * @param varType3
     * @param yVarLabel
     * @param myOutputs
     */
    public PlotLineDescription(String varName, RandomVariable.Probe varType,
            String varName2, RandomVariable.Probe varType2,
            RandomVariable.Probe varType3, String yVarLabel,
            List<SimulationOutput> myOutputs) {
        super();
        xVar = varName;
        xVarProbe = varType;
        yVar = varName2;
        yVarProbe = varType2;
        yRadiusVarType = varType3;
        this.label = yVarLabel;
        this.data = myOutputs;
    }

    /**
     * 
     * Create a plotline which plots the probe yVarProbe of variable yVar in
     * function of the probe xVarProbe and variable xVar. The line will be
     * labelled with label.
     * 
     * @param xVar
     * @param xVarProbe
     * @param yVar
     * @param yVarProbe
     * @param label
     * @param data
     */
    public PlotLineDescription(String xVar, RandomVariable.Probe xVarProbe,
            String yVar, RandomVariable.Probe yVarProbe, String label,
            List<SimulationOutput> data) {
        super();
        this.xVar = xVar;
        this.xVarProbe = xVarProbe;
        this.yVar = yVar;
        this.yVarProbe = yVarProbe;
        this.label = label;
        this.data = data;
    }

    /**
     * 
     * Simplified constructor. Create a plotline which plots the average of yVar
     * in function of the value of xVar. The line will be labelled with label.
     * 
     * @param xVar
     * @param yVar
     * @param label
     * @param data
     */
    public PlotLineDescription(String xVar, String yVar, String label,
            List<SimulationOutput> data) {
        super();
        this.xVar = xVar;
        this.xVarProbe = RandomVariable.Probe.AVERAGE;
        this.yVar = yVar;
        this.yVarProbe = RandomVariable.Probe.AVERAGE;
        this.label = label;
        this.data = data;
    }

    /**
     * @param element
     * @return
     */
    public boolean contains(SimulationOutput element) {
        if (plotLineFilter == null) {
            return true;
        }
        return plotLineFilter.considerThisResult(element);
    }

    public String generateErrorBarValues(String yLabel, String radiusLabel) {
        return MatlabUtil.getMatlabVectorFromSOP(yLabel, data, yVar, yVarProbe,
                false)
                + "\n"
                + MatlabUtil.getMatlabVectorFromSOP(radiusLabel, data, yVar,
                        yRadiusVarType, false);
    }

    public String generateXValues(String label) {
        return MatlabUtil.getMatlabVectorFromSOP(label, data, xVar, xVarProbe,
                false);
    }

    public String generateYValues(String label) {
        return MatlabUtil.getMatlabVectorFromSOP(label, data, yVar, yVarProbe,
                false);
    }

    /**
     * @return Returns the data.
     */
    public List<SimulationOutput> getData() {
        return data;
    }

    /**
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return Returns the plotLineFilter.
     */
    public IPlotLineFilter getPlotLineFilter() {
        return plotLineFilter;
    }

    /**
     * @return Returns the xVar.
     */
    public String getXVar() {
        return xVar;
    }

    /**
     * @return Returns the xVarProbe.
     */
    public RandomVariable.Probe getXVarProbe() {
        return xVarProbe;
    }

    public RandomVariable.Probe getYRadiusVarType() {
        return yRadiusVarType;
    }

    /**
     * @return Returns the yVar.
     */
    public String getYVar() {
        return yVar;
    }

    /**
     * @return Returns the yVarProbe.
     */
    public RandomVariable.Probe getYVarProbe() {
        return yVarProbe;
    }

    /**
     * @param plotLineFilter
     *            The plotLineFilter to set.
     */
    public void setPlotLineFilter(IPlotLineFilter plotLineFilter) {
        this.plotLineFilter = plotLineFilter;
    }

}
