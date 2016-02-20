/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Apr 13, 2008
 
   yaes.ui.visualization.MapBackgroundPainter
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.visualization.painters;

import java.awt.Graphics2D;
import java.awt.Image;

import yaes.ui.visualization.ILayers;
import yaes.ui.visualization.VisualCanvas;
import yaes.ui.visualization.VisualizationProperties;
import yaes.world.physical.map.IMap;

/**
 * 
 * <code>yaes.ui.visualization.MapBackgroundPainter</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class MapBackgroundPainter implements IPainter {

    /**
     * @return
     */
    @Override
    public int getLayer() {
        return ILayers.BACKGROUND_LAYER;
    }

    /**
     * @param g
     * @param o
     * @param panel
     */
    @Override
    public void paint(Graphics2D g, Object o, VisualCanvas panel) {
        IMap theMap = (IMap) o;
        // final Image theImage = theMap.getBufferedImage();
        final Image theImage = theMap.getBackgroundImage();
        if (theImage != null) {
            g.drawImage(theImage, panel.getTheTransform(), null);
        }
    }

    /**
     * @param visualizationProperties
     */
    @Override
    public void registerParameters(
            VisualizationProperties visualizationProperties) {
        // not registering anything
    }
}
