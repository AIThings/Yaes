/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 23, 2008
 
   yaes.ui.visualization.NamedAndHasLocationPainter
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.visualization.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JLabel;

import yaes.ui.visualization.ILayers;
import yaes.ui.visualization.VisualCanvas;
import yaes.ui.visualization.VisualizationProperties;
import yaes.world.physical.location.IMoving;
import yaes.world.physical.location.INamed;
import yaes.world.physical.location.Location;

/**
 * 
 * <code>yaes.ui.visualization.NamedAndWithLocationPainter</code> this is a
 * painter for object which can be cast both to INamed and IWithLocation
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class paintMobileNode implements IPainter {

    protected Color         borderColor;
    protected Color         fillColor;
    String                  forcedLabel   = null;
    protected int           labelDelta    = 10;
    protected String        labelFontName = "Sans";                       // "Verdana";
    HashMap<Object, JLabel> labels = new HashMap<>();
    protected boolean       paintLabel;
    //Set Size for Label
    protected int		labelSize;
    protected int           size          = 10;

    /**
     * Default constructor: yields a yellow rectangle of size 10 with black
     * border and the label painted
     */
    public paintMobileNode() {
        size = 10;
        borderColor = Color.black;
        fillColor = Color.yellow;
        paintLabel = true;
    }

    /**
     * Constructor, specify everything except the forced label
     * 
     * @param size
     * @param drawBorder
     * @param borderColor
     * @param drawFill
     * @param fillColor
     */
    public paintMobileNode(int size, Color borderColor, Color fillColor) {
        super();
        this.size = size;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.paintLabel = true;
    }

    /**
     * Constructor, specify everything, including the forced label. Used for
     * objects which do not inherit INamed etc
     * 
     * @param size
     * @param drawBorder
     * @param borderColor
     * @param drawFill
     * @param fillColor
     */
    public paintMobileNode(int size, Color borderColor, Color fillColor,
            String forcedLabel) {
        super();
        this.size = size;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.forcedLabel = forcedLabel;
        this.paintLabel = true;
    }

    @Override
    public int getLayer() {
        return ILayers.FOREGROUND_LAYER + 2;
    }

    @Override
    public void paint(Graphics2D g, Object o, VisualCanvas panel) {
        IMoving wl = (IMoving) o;
        paintNode(g, o, panel);
        if (paintLabel) {
            paintLabel(o, wl.getLocation(), g, panel);
        }
    }

    protected void paintLabel(Object o, Location location, Graphics2D g,
            VisualCanvas panel) {
        String text = "<html><font face='" + labelFontName
                + "' size="+this.labelSize+">Noname</font></html>";
        if (forcedLabel != null) {
            text = "<html><font face='" + labelFontName + "' size="+this.labelSize+">"
                    + forcedLabel + "</font></html>";
        } else if (o instanceof INamed) {
            INamed nmd = (INamed) o;
            text = "<html><font face='" + labelFontName + "' size="+this.labelSize+">"
                    + nmd.getName() + "</font></html>";
        }
        PainterHelper.paintHtmlLabel(text, location, labelDelta, labelDelta,
                Color.white, false, 1, Color.black, false, g, panel);
    }

    /**
     * Paints the node itself. This is extracted here, such that it can be
     * overwriteable by painters which inherit from this one and they want a
     * different shape for the node.
     * 
     * Default one: a small rectangle.
     * 
     * @param g
     * @param o
     * @param panel
     */
    protected void paintNode(Graphics2D g, Object o, VisualCanvas panel) {
        IMoving wl = (IMoving) o;
        PainterHelper.paintRectangleAtLocation(wl.getLocation(), size,
                borderColor, fillColor, g, panel);
    }

    /**
     * @param visualizationProperties
     */
    @Override
    public void registerParameters(
            VisualizationProperties visualizationProperties) {
        // not registering anything
    }

    public void setPaintLabel(boolean paintLabel) {
        this.paintLabel = paintLabel;
    }

    
	public void setPaintLabelSize(int size) {
		this.labelSize = size;
		
	}

}
