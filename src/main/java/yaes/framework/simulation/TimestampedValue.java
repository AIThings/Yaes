/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 18, 2008
 
   yaes.framework.simulation.TimestampedValue
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation;

import java.io.Serializable;

/**
 * 
 * <code>yaes.framework.simulation.TimestampedValue</code> a timestamped value,
 * to be used to represent observations of time series in a RandomVariable
 * 
 * This is an imutable class.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class TimestampedValue implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1826306141657749073L;
    private double            time;
    private double            value;

    public TimestampedValue(double time, double value) {
        super();
        this.time = time;
        this.value = value;
    }

    public double getTime() {
        return time;
    }

    public double getValue() {
        return value;
    }

}
