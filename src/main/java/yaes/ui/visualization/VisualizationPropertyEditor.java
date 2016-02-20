/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 15, 2008
 
   yaes.ui.visualization.VisualizationPropertyEditor
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.visualization;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;

/**
 * 
 * <code>yaes.ui.visualization.VisualizationPropertyEditor</code> Contains the
 * framework for the visualization property editor
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class VisualizationPropertyEditor implements PropertyChangeListener {

    private JFrame frame = null;
    private final JPanel panel;
    private final Visualizer visualizer;

    public VisualizationPropertyEditor(final VisualizationProperties data,
        final Visualizer visualizer) {
        this.visualizer = visualizer;
        panel = new JPanel();
        panel.setLayout(LookAndFeelTweaks.createVerticalPercentLayout());
        final JTextArea message = new JTextArea();
        message.setText("Visualization properties");
        LookAndFeelTweaks.makeMultilineLabel(message);
        panel.add(message);

        final PropertySheetPanel sheet = new PropertySheetPanel();
        sheet.setMode(PropertySheet.VIEW_AS_CATEGORIES);
        sheet.setDescriptionVisible(true);
        sheet.setSortingCategories(true);
        sheet.setSortingProperties(true);
        // sheet.setRestoreToggleStates(true);
        sheet.addPropertySheetChangeListener(this);
        final ArrayList<Property> properties = new ArrayList<Property>();
        for (final String id : data.getProperties().keySet()) {
            final Property property = data.getProperties().get(id);
            properties.add(property);
        }
        sheet.setProperties(
                properties.toArray(new Property[properties.size()]));
        panel.add(sheet, "*");
    }

    /**
     * Hides the independent frame
     */
    public void hideFrame() {
        if (frame != null) {
            frame.setVisible(false);
        }
    }

    /**
     * @param arg0
     */
    @Override
    public void propertyChange(final PropertyChangeEvent arg0) {
        if (visualizer != null) {
            visualizer.update();
        }
    }

    /**
     * Shows in an independent frame
     * 
     * @param args
     */
    public void showFrame() {
        if (frame == null) {
            frame = new JFrame("Visualization properties");
            JFrame.setDefaultLookAndFeelDecorated(true);
            frame.getContentPane().add(panel);
            frame.pack();
        }
        frame.setVisible(true);
    }

}
