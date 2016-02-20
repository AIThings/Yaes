/*
 * Created on Nov 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.ui.plot;

import yaes.framework.simulation.SimulationOutput;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class IntegerValuePlotLineFilter implements IPlotLineFilter {
    String name;
    int    value;

    /**
     * @param name
     * @param value
     */
    public IntegerValuePlotLineFilter(String name, int value) {
        super();
        this.name = name;
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * yaes.ui.plot.IPlotLineFilter#considerThisResult(yaes.framework.simulation
     * .SimulationOutput)
     */
    @Override
    public boolean considerThisResult(SimulationOutput sop) {
        if ((int) sop.getRandomVar(name).getAvg() == value) {
            return true;
        }
        return false;
    }
}
