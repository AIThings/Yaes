/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 2, 2011
 
   yaes.ui.visualization.painters.PaintSpec
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.visualization.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * <code>yaes.ui.visualization.painters.PaintSpec</code>
 * 
 * Paint specification. Makes the specification of painters easier, you don't
 * need the many parameters;
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PaintSpec {

    /**
     * @param darker
     * @return
     */
    public static PaintSpec createDraw(Color color) {
        PaintSpec retval = new PaintSpec();
        retval.setBorderColor(color);
        return retval;
    }

    /**
     * @param darkGray
     * @return
     */
    public static PaintSpec createFill(Color color) {
        PaintSpec retval = new PaintSpec();
        retval.setFillColor(color);
        return retval;
    }

    private Color borderColor  = null;

    private Color fillColor    = null;

    private float transparency = 1.0f;

    /**
     * Apply the border settings
     * 
     * @param g
     */
    public void applyBorder(Graphics2D g) {
        g.setStroke(new BasicStroke());
        g.setColor(borderColor);
    }

    /**
     * Applies the fill settings
     * 
     * @param g
     */
    public void applyFill(Graphics2D g) {
        g.setColor(fillColor);
    }

    public boolean doBorder() {
        return borderColor != null;
    }

    public boolean doFill() {
        return fillColor != null;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public float getTransparency() {
        return transparency;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

}
