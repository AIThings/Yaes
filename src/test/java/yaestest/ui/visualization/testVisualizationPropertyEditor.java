/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Mar 1, 2009
 
   yaestest.ui.visualization.testVisualizationPropertyEditor
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.ui.visualization;

import java.awt.Color;

import org.junit.Test;

import yaes.ui.text.TextUi;
import yaes.ui.visualization.VisualizationProperties;
import yaes.ui.visualization.VisualizationPropertyEditor;

/**
 * 
 * <code>yaestest.ui.visualization.testVisualizationPropertyEditor</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testVisualizationPropertyEditor {

    private VisualizationProperties initVProps() {
        VisualizationProperties vprops = new VisualizationProperties();
        vprops.addVisualizationProperty("id1", "Integer value", "Main",
                new Integer(30));
        vprops.addVisualizationProperty("id2", "Boolean value", "Main",
                new Boolean(true));
        vprops.addVisualizationProperty("id3", "Color value", "Colors",
                Color.red);
        vprops.addVisualizationProperty("id4", "Double value", "Main",
                new Double(12.23));
        vprops.addVisualizationProperty("id5", "String value", "Main", "Hello");
        return vprops;
    }

    @Test
    public void testEditor() throws InterruptedException {
        VisualizationProperties vprops = initVProps();
        VisualizationPropertyEditor vpe = new VisualizationPropertyEditor(
                vprops, null);
        vpe.showFrame();

        while (true) {
            TextUi.println(vprops.toString());
            Thread.currentThread();
            Thread.sleep(1000);
        }
    }

    // / @Test
    public void testVisualizationProperties() {
        initVProps();
    }

}
