/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 22, 2008
 
   yaes.ui.visualization.VisualizerStatusBar
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.visualization;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * <code>yaes.ui.visualization.VisualizerStatusBar</code>
 * 
 * a status bar for the visualizer, showing the current scroll, magnification
 * state (and other stuff in the future)
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class VisualizerStatusBar {

    private JTextField magnification;
    private JPanel     panel;
    private JTextField scrolling;
    private Visualizer visualizer;

    public VisualizerStatusBar(Visualizer visualizer) {
        this.visualizer = visualizer;
        createPanel();
    }

    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        scrolling = new JTextField();
        scrolling.setEditable(false);
        scrolling.setText("(0,0)");
        scrolling.setMaximumSize(new Dimension(200, 1000));
        Dimension d = scrolling.getPreferredSize();
        scrolling.setPreferredSize(new Dimension(100, (int) d.getHeight()));
        scrolling.setMinimumSize(new Dimension(200, 0));
        scrolling.setBorder(BorderFactory.createLoweredBevelBorder());
        panel.add(scrolling);
        magnification = new JTextField();
        magnification.setEditable(false);
        magnification.setText("100%");
        magnification.setBorder(BorderFactory.createLoweredBevelBorder());
        magnification.setMaximumSize(new Dimension(200, 1000));
        magnification.setPreferredSize(new Dimension(100, (int) d.getHeight()));
        magnification.setMinimumSize(new Dimension(200, 0));
        panel.add(magnification);
        panel.add(Box.createHorizontalGlue());
    }

    public JPanel getPanel() {
        return panel;
    }

    /**
     * Updates the status bar
     */
    public void update() {
        magnification.setText("" + visualizer.getVisualCanvas().getMagnify());
        scrolling.setText("(" + visualizer.getVisualCanvas().getTranslateX()
                + ", " + visualizer.getVisualCanvas().getTranslateY() + ")");
    }
}
