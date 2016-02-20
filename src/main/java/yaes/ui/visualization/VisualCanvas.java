package yaes.ui.visualization;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import yaes.ui.visualization.painters.IPainter;

/**
 * This is a panel which handles a the visual representation of the objects
 * 
 * @author Lotzi Boloni
 * 
 */
public class VisualCanvas implements ILayers, AdjustmentListener {
    private final JPanel                  bigPanel;
    // the current layer being painted
    private int                           currentLayer;
    // private final double                  magnification    = 1.0;
    private double                        magnify          = 1;
    private final List<Object>            objects;

    private final Map<Object, IPainter>   painters         = new HashMap<>();
    private final JPanel                  panel;
    private final Visualizer              parent;
    private final JScrollBar              scrollHorizontal;
    private final JScrollBar              scrollVertical;
    private AffineTransform               theTransform;
    private double                        translateX       = 0;
    private double                        translateY       = 0;
    private boolean                       update           = true;
    @SuppressWarnings("unused")
    private final VisualizationProperties visualizationProperties;

    /**
     * Creates a visual map panel
     * 
     * @param parent
     */
    public VisualCanvas(Visualizer parent,
            VisualizationProperties visualizationProperties) {
        this.parent = parent;
        this.visualizationProperties = visualizationProperties;
        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        scrollHorizontal = new JScrollBar(Adjustable.HORIZONTAL);
        scrollHorizontal.addAdjustmentListener(this);
        scrollHorizontal.setMaximum(1000);
        scrollHorizontal.setMinimum(-1000);
        scrollHorizontal.setValue(0);
        bigPanel.add(scrollHorizontal, BorderLayout.SOUTH);
        scrollVertical = new JScrollBar(Adjustable.VERTICAL);
        scrollVertical.addAdjustmentListener(this);
        scrollVertical.setMaximum(1000);
        scrollVertical.setMinimum(-1000);
        scrollVertical.setValue(0);
        bigPanel.add(scrollVertical, BorderLayout.EAST);

        panel = new JPanel() {
            /**
             * 
             */
            private static final long serialVersionUID = 6988446626330640195L;

            @Override
            public void paint(Graphics g) {
                final Graphics2D g2 = (Graphics2D) g;
                invalidate();
                g2.clearRect(0, 0, getSize().width, getSize().height);
                paintVisualMap(g2);
            }
        };
        panel.setPreferredSize(new Dimension(800, 800));
        panel.setDoubleBuffered(true);
        panel.setOpaque(true);
        bigPanel.add(panel, BorderLayout.CENTER);
        // the list of objects
        objects = new ArrayList<>();
        theTransform = new AffineTransform();
    }

    /**
     * Adds an object with the associated painter.
     * 
     * The layer is inferred from the painter object.
     * 
     * @param o
     * @param painter
     */
    public synchronized void addObject(Object o, IPainter painter) {
        objects.add(o);
        painters.put(o, painter);
    }

    /**
     * @param arg0
     */
    @Override
    public void adjustmentValueChanged(AdjustmentEvent arg0) {
        if (arg0.getSource().equals(scrollHorizontal)) {
            translateX = -arg0.getValue();
            if (parent != null) {
                parent.update();
            }
        }
        if (arg0.getSource().equals(scrollVertical)) {
            translateY = -arg0.getValue();
            if (parent != null) {
                parent.update();
            }
        }
    }

    public void changeMagnify(double multiply) {
        magnify = magnify * multiply;
        parent.update();
    }

    public int getCurrentLayer() {
        return currentLayer;
    }

    public double getMagnify() {
        return magnify;
    }

    public JPanel getPanel() {
        return bigPanel;
    }
    
    public JPanel getInternalPanel() {
    	return panel;
    }

    /**
     * Returns the global, scalable transform.
     */
    public AffineTransform getTheTransform() {
        return theTransform;
    }

    public double getTranslateX() {
        return translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public Visualizer getVisualizer() {
        return parent;
    }

    /**
     * @return the update
     */
    public boolean isUpdate() {
        return update;
    }

    /**
     * Paints all the layers starting from the background.
     * 
     * @param g
     */
    synchronized void paintVisualMap(Graphics2D g) {
        final AffineTransform theTransform = g.getTransform();
        //theTransform.scale(magnification, magnification);
        theTransform.translate(0, 0);
        g.setTransform(theTransform);
        for (currentLayer = ILayers.BACKGROUND_LAYER; currentLayer >= ILayers.FOREGROUND_LAYER; currentLayer--) {
            for (Object element : objects) {
                final IPainter painter = painters.get(element);
                if ((painter.getLayer() == currentLayer)
                        || (painter.getLayer() == ILayers.ALL_LAYERS)) {
                    painter.paint(g, element, this);
                }
            }
        }
    }

    /**
     * Removes all objects
     * 
     * @param o
     */
    public synchronized void removeAllObjects() {
        List<Object> toRemove = new ArrayList<>();
        toRemove.addAll(objects);
        for (Object o : toRemove) {
            removeObject(o);
        }
    }

    /**
     * Removes the object from all the layers
     * 
     * @param o
     */
    public synchronized void removeObject(Object o) {
        objects.remove(o);
        painters.remove(o);
    }

    /**
     * @param update
     *            the update to set
     */
    public void setUpdate(boolean update) {
        this.update = update;
    }

    /**
     * Creates a visual canvas in a dialog - for debugging, visualization etc.
     * 
     * @return
     */
    public JDialog showInADialog() {
        JDialog dlg = new JDialog();
        dlg.setModal(true);
        dlg.getContentPane().add(getPanel());
        dlg.pack();
        dlg.setVisible(true);
        return dlg;
    }

    /**
     * Update the visual canvas
     */
    public void update() {
        if (update) {
            getTheTransform().setToIdentity();
            getTheTransform().translate(translateX, translateY);
            getTheTransform().scale(magnify, magnify);
            panel.invalidate();
            panel.repaint();
        }
    }

    /**
     * @param magnify the magnify to set
     */
    public void setMagnify(double magnify) {
        this.magnify = magnify;
    }

}
