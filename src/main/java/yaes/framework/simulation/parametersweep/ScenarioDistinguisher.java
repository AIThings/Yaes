/*
   This file is part of the Yet Another Extensible Simulator
   Created on: September 3, 2008
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep;

import java.util.HashMap;
import java.util.Map;

import yaes.framework.simulation.SimulationInput;

/**
 * 
 * <code>yaes.framework.simulation.ScenarioDistinguisher</code>
 * 
 * A scenario distinguisher packs together a set of related parameters and gives
 * them a name. The name will also be the name of the scenario
 * 
 * The distinguisher than can be applied to a simulation input.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ScenarioDistinguisher {

    private Map<String, Object> distinguishers = new HashMap<>();
    private String              name;

    public ScenarioDistinguisher(String name) {
        this.name = name;
    }

    /**
     * Apply the distinguishers to the simulation output
     * 
     * @param sip
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    public void apply(SimulationInput sip) {
        for (String key : distinguishers.keySet()) {
            Object value = distinguishers.get(key);
            if (value instanceof Double) {
                sip.setParameter(key, ((Double) value).doubleValue());
                continue;
            }
            if (value instanceof Integer) {
                sip.setParameter(key, ((Integer) value).intValue());
                continue;
            }
            if (value instanceof String) {
                sip.setParameter(key, (String) value);
                continue;
            }
            if (value instanceof Enum) {
                sip.setParameter((Enum) value);
                continue;
            }
            throw new Error("Unsupported parameter:" + value);
        }
    }

    public double getDistinguisherDouble(String name) {
        return (Double) distinguishers.get(name);
    }

    public int getDistinguisherInt(String name) {
        return (Integer) distinguishers.get(name);
    }

    public String getDistinguisherString(String name) {
        return (String) distinguishers.get(name);
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("rawtypes")
    public ScenarioDistinguisher setDistinguisher(Enum e) {
        distinguishers.put(e.getClass().getSimpleName(), e);
        return this;
    }

    public void setDistinguisher(String name, double value) {
        distinguishers.put(name, new Double(value));
    }

    public ScenarioDistinguisher setDistinguisher(String name, int value) {
        distinguishers.put(name, new Integer(value));
        return this;
    }

    public ScenarioDistinguisher setDistinguisher(String name, String value) {
        distinguishers.put(name, value);
        return this;
    }

    /**
     * Draw the distingui
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Scenario Distinguisher:" + name + "\n");
        for (String key : distinguishers.keySet()) {
            buffer.append("   " + key + " = " + distinguishers.get(key) + "\n");
        }
        return buffer.toString();
    }

}
