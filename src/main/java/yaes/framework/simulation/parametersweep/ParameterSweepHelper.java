/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 18, 2009
 
   yaes.framework.simulation.parametersweep.ParameterSweepHelper
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.ParameterSweepHelper</code>
 * helper functions to generate parameters
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ParameterSweepHelper {

    /**
     * Generates an array of scenario distinguishers which are iterating over a
     * linear range of double
     * 
     * FIXME: depending on the range of the numbers, sometimes the format might
     * not be good
     * 
     * @param name
     * @return
     */
    public static List<ScenarioDistinguisher> generateLinearRangeOfDouble(
            String label, String variableName, int count, double rangeLow,
            double rangeHigh) {
        List<ScenarioDistinguisher> retVal = new ArrayList<>();
        double currentValue = rangeLow;
        double increment = (rangeHigh - rangeLow) / (count - 1);
        for (int i = 0; i != count; i++) {
            ScenarioDistinguisher s = new ScenarioDistinguisher(label + " - "
                    + String.format("%03d", i));
            s.setDistinguisher(variableName, currentValue);
            retVal.add(s);
            currentValue = currentValue + increment;
        }
        return retVal;
    }

    /**
     * @param variableName
     * @param i
     * @param j
     * @return
     */
    public static List<ScenarioDistinguisher> generateLinearRangeOfIntegers(
            String label, String variableName, int i, int j, int step) {
        List<ScenarioDistinguisher> retVal = new ArrayList<>();
        for (int cnt = i; cnt <= j; cnt = cnt + step) {
            ScenarioDistinguisher s = new ScenarioDistinguisher(label + " - "
                    + String.format("%03d", cnt));
            s.setDistinguisher(variableName, cnt);
            retVal.add(s);
        }
        return retVal;
    }

    /**
     * Generates a parameter sweep, by default of type range, which covers a
     * range of doubles
     * 
     * @param label
     * @param variableName
     * @param count
     * @param rangeLow
     * @param rangeHigh
     * @return
     */
    public static ParameterSweep generateParameterSweepDouble(String label,
            String variableName, int count, double rangeLow, double rangeHigh) {
        List<ScenarioDistinguisher> list = ParameterSweepHelper
                .generateLinearRangeOfDouble(label, variableName, count,
                        rangeLow, rangeHigh);
        ParameterSweep retval = new ParameterSweep(label, variableName, list);
        String description = "double " + variableName + " in the range ["
                + rangeLow + ", " + rangeHigh + "] - " + count + " values";
        retval.setDescription(description);
        retval.setType(ParameterSweep.ParameterSweepType.Range);
        return retval;
    }

    public static ParameterSweep generateParameterSweepInteger(String label,
            String variableName, int i, int j) {
        return ParameterSweepHelper.generateParameterSweepInteger(label,
                variableName, i, j, 1);
    }

    /**
     * Generates a parameter sweep object (by default of type range) which
     * covers a range of integers
     * 
     * @param label
     * @param variableName
     * @param i
     * @param j
     * @return
     */
    public static ParameterSweep generateParameterSweepInteger(String label,
            String variableName, int i, int j, int step) {
        List<ScenarioDistinguisher> list = ParameterSweepHelper
                .generateLinearRangeOfIntegers(label, variableName, i, j, step);
        ParameterSweep retval = new ParameterSweep(label, variableName, list);
        retval.setType(ParameterSweep.ParameterSweepType.Range);
        String description = "integer " + variableName + " in the range " + i
                + "..." + j + " step " + step;
        retval.setDescription(description);
        return retval;
    }
}
