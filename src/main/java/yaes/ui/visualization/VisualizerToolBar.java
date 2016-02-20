/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 6, 2008
 
   yaes.ui.visualization.CanvasToolbar
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.visualization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * 
 * <code>yaes.ui.visualization.CanvasToolbar</code>
 * 
 * This class contains the implementation of the canvas toolbar.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class VisualizerToolBar implements ActionListener {

    private JButton       iZoomIn;                // intelligent zoom
    private JButton       iZoomOut;
    private VisualCanvas  theCanvas;
    private JToolBar      theToolBar;
    private JToggleButton update;
    private JToggleButton visualizationProperties;
    private Visualizer    visualizer;

    /**
     * Constructor, creates a tool bar for a given canvas
     * 
     * @param theCanvas
     */
    public VisualizerToolBar(Visualizer visualizer) {
        this.visualizer = visualizer;
        this.theCanvas = visualizer.getVisualCanvas();
        this.theToolBar = new JToolBar();
        initToolbar();
    }

    /**
     * @param arg0
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == iZoomIn) {
            theCanvas.changeMagnify(1.25);
        }
        if (arg0.getSource() == iZoomOut) {
            theCanvas.changeMagnify(0.66);
        }
        if (arg0.getSource() == visualizationProperties) {
            if (visualizationProperties.isSelected()) {
                visualizer.showVisualizationProperties();
            } else {
                visualizer.hideVisualizationProperties();
            }
        }
        if (arg0.getSource() == update) {
            if (update.isSelected()) {
                theCanvas.setUpdate(true);
            } else {
                theCanvas.setUpdate(false);
            }
        }
        visualizer.update();
    }

    /**
     * @return the theToolBar
     */
    public JToolBar getTheToolBar() {
        return theToolBar;
    }

    /**
     * Initializes the buttons of the toolbar
     */
    private void initToolbar() {
        iZoomIn = makeNavigationButton("viewmag+.png", "iZoomIn", "iZoom In",
                "iZoom In");
        theToolBar.add(iZoomIn);
        iZoomOut = makeNavigationButton("viewmag-.png", "iZoomOut",
                "iZoom Out", "iZoom Out");
        theToolBar.add(iZoomOut);
        visualizationProperties = makeToggleButton("configure.png", "visProp",
                "Visualization properties", "Vis.prop.");
        theToolBar.add(visualizationProperties);
        update = makeToggleButton("viewmagfit.png", "update",
                "Enable/Disable update", "Enable/disable update.");
        update.setSelected(true);
        theToolBar.add(update);
    }

    /**
     * Creates a navigation button
     * 
     * @param imageName
     * @param actionCommand
     * @param toolTipText
     * @param altText
     * @return
     */
    protected JButton makeNavigationButton(String imageName,
            String actionCommand, String toolTipText, String altText) {
        // Look for the image.
        String imgLocation = "images/" + imageName;
        URL imageURL = VisualizerToolBar.class.getResource(imgLocation);

        // Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) { // image found
            button.setIcon(new ImageIcon(imageURL, altText));
        } else { // no image found
            button.setText(altText);
            System.err.println("Resource not found: " + imageURL);
        }

        return button;
    }

    /**
     * Creates a navigation button
     * 
     * @param imageName
     * @param actionCommand
     * @param toolTipText
     * @param altText
     * @return
     */
    protected JToggleButton makeToggleButton(String imageName,
            String actionCommand, String toolTipText, String altText) {
        // Look for the image.
        String imgLocation = "images/" + imageName;
        URL imageURL = VisualizerToolBar.class.getResource(imgLocation);

        // Create and initialize the button.
        JToggleButton button = new JToggleButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) { // image found
            button.setIcon(new ImageIcon(imageURL, altText));
        } else { // no image found
            button.setText(altText);
            System.err.println("Resource not found: " + imageURL);
        }

        return button;
    }

}
