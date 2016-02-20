/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lotzi Boloni
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SimulationHelper {

    /**
     * Returns a string with a set of variables printed. Probably should go more
     * into a helper.
     * 
     * @param varsToPrint
     * @return
     */
    public static String printVars(SimulationOutput sop, String varsToPrint) {
        final StringBuffer buffer = new StringBuffer("");
        final List<String> vars = new ArrayList<>();
        for (final Iterator<String> it = sop.getParameterIterator(); it
                .hasNext();) {
            vars.add(it.next());
        }
        Collections.sort(vars);
        for (final String key : vars) {
            if (varsToPrint.indexOf(key) == -1) {
                continue;
            }
            final RandomVariable rv = sop.getRandomVar(key);
            buffer.append("\t" + rv.toString());
        }
        return buffer.toString();
    }
}
