/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 22, 2008
 
   yaes.world.physical.PhysicalWorldPainter
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical;

import java.awt.Graphics2D;

import yaes.ui.visualization.VisualCanvas;
import yaes.ui.visualization.VisualizationProperties;
import yaes.ui.visualization.painters.IPainter;
import yaes.ui.visualization.painters.paintMobileNode;

/**
 * 
 * <code>yaes.world.physical.PhysicalWorldPainter</code> This is a class which
 * paints all the components of the embodied agent world.
 * 
 * In time, it will paint the flow of messages as well...
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PhysicalWorldPainter implements IPainter {

    private paintMobileNode agentPainter = new paintMobileNode();
    private PhysicalWorld   world;

    public PhysicalWorldPainter(PhysicalWorld world) {
        this.world = world;
    }

    /**
     * @return
     */
    @Override
    public int getLayer() {
        return 0;
    }

    /**
     * @param g
     * @param o
     * @param panel
     */
    @Override
    public void paint(Graphics2D g, Object o, VisualCanvas panel) {
        assert o == world;
        for (PhysicalAgent agent : world.getAgents()) {
            agentPainter.paint(g, agent, panel);
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
