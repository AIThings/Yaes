/*
   This file is part of the Yet Another Extensible Simulator
   Created on Sep 15, 2004
 
   yaes.world.physical.location.Acceleration
 
   Copyright (c) 2004-2009 Ladislau Boloni

   This package is released under the LGPL version 2.
 */
package yaes.world.physical.location;

/**
 * Implements a vectorial, two dimensional acceleration model
 * 
 * <code>yaes.world.physical.location.Acceleration</code>
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */

public class Acceleration {
    private final double xAcceleration;
    private final double yAcceleration;

    /**
     * @param acceleration
     * @param acceleration2
     */
    public Acceleration(double acceleration, double acceleration2) {
        super();
        xAcceleration = acceleration;
        yAcceleration = acceleration2;
    }

    /**
     * @return Returns the xAcceleration.
     */
    public double getXAcceleration() {
        return xAcceleration;
    }

    /**
     * @return Returns the yAcceleration.
     */
    public double getYAcceleration() {
        return yAcceleration;
    }
}