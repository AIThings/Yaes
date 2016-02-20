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
public interface IPlotLineFilter {
    boolean considerThisResult(SimulationOutput sop);
}
