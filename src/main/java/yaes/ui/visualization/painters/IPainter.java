package yaes.ui.visualization.painters;

import java.awt.Graphics2D;

import yaes.ui.visualization.ILayers;
import yaes.ui.visualization.VisualCanvas;
import yaes.ui.visualization.VisualizationProperties;

/**
 * An interface to be implemented by all the painters
 * 
 * <code>yaes.ui.visualization.IPainter</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public interface IPainter extends ILayers {
    /**
     * Return the layer or -1 if the painter will paint at every layer. If it
     * does, it needs to handle its own decisions about the layer
     * 
     * @return
     */
    int getLayer();

    /**
     * Called by VisualMapPanel to paint the object. The current layer can be
     * found by calling getCurrentLayer on the panel.
     * 
     * @param g
     * @param o
     * @param panel
     */
    void paint(Graphics2D g, Object o, VisualCanvas panel);

    void registerParameters(VisualizationProperties visualizationProperties);
}
