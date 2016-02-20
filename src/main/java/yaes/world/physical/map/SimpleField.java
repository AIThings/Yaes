/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 22, 2008
 
   yaes.world.map.SimpleField
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.map;

/**
 * 
 * <code>yaes.world.map.SimpleField</code> an empty field for scenarios where
 * there is nothing but
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class SimpleField implements IField {
    /**
     * 
     */
    private static final long serialVersionUID = 53790513473734564L;
    private double            xHigh;
    private double            xLow;
    private double            yHigh;
    private double            yLow;

    public SimpleField(double xHigh, double yHigh) {
        this.xLow = 0.0;
        this.xHigh = xHigh;
        this.yLow = 0.0;
        this.yHigh = yHigh;
    }

    /**
     * @return
     */
    @Override
    public double getXHigh() {
        return xHigh;
    }

    /**
     * @return
     */
    @Override
    public double getXLow() {
        return xLow;
    }

    /**
     * @return
     */
    @Override
    public double getYHigh() {
        return yHigh;
    }

    /**
     * @return
     */
    @Override
    public double getYLow() {
        return yLow;
    }

}
