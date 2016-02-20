/*
 * Created on Dec 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import yaes.framework.simulation.RandomVariable.Probe;

/**
 * @author Lotzi Boloni
 * 
 *         This class provides an integration of the output parameters from a
 *         number of simulations.
 * 
 */
public class IntegratedSOP {
    /**
     * Integrates a set of simulation output parameters, by computing their
     * average
     * 
     * Takes care of the non-real data values
     * 
     * @param sops
     * @return
     */
    public static SimulationOutput integrate(List<SimulationOutput> sops) {
        final SimulationOutput sop = new SimulationOutput(null, null);
        int integratedCount = 0;
        for (final SimulationOutput element : sops) {
            // skip if it is not a real value
            if (element.getValue(SimulationOutput.REAL_DATA, Probe.LASTVALUE) == 0.0) {
                continue;
            }
            integratedCount = integratedCount + 1;
            for (final Iterator<String> it = element.getParameterIterator(); it
                    .hasNext();) {
                final String varName = it.next();
                sop.update(varName, element.getValue(varName,
                        RandomVariable.Probe.AVERAGE));
            }
        }
        // sets the count from how many we integrated, if it is zero,
        // it is not real data
        sop.set(SimulationOutput.INTEGRATED_COUNT, integratedCount);
        if (integratedCount == 0) {
            sop.set(SimulationOutput.REAL_DATA, 0);
        }
        return sop;
    }

    /**
     * Integrates a list of list of simulation output parameters -- the typical
     * output of a simulationSet
     * 
     * @param listOflists
     * @return
     */
    public static List<SimulationOutput> integrateListOfSops(
            List<List<SimulationOutput>> listOflists) {
        final List<SimulationOutput> first = listOflists.get(0);
        final int size = first.size();
        final List<SimulationOutput> outputs = new ArrayList<>();
        for (int i = 0; i != size; i++) {
            final List<SimulationOutput> prepared = new ArrayList<>();
            for (final List<SimulationOutput> element : listOflists) {
                prepared.add(element.get(i));
            }
            final SimulationOutput sop = IntegratedSOP.integrate(prepared);
            outputs.add(sop);
        }
        return outputs;
    }
}