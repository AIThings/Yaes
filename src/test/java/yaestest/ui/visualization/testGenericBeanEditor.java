/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 17, 2009
 
   yaestest.ui.visualization.testGenericBeanEditor
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.ui.visualization;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.l2fprod.common.propertysheet.DefaultProperty;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;

import yaes.ui.text.TextUi;

/**
 * 
 * <code>yaestest.ui.visualization.testGenericBeanEditor</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testGenericBeanEditor {

    // @Test
    // public void testVisualization() {
    public static void main(final String[] args) throws InterruptedException {

        final HashMap<String, Object> map = new HashMap<>();
        map.put("This", new Integer(34));
        map.put("That", "text");
        map.put("Ok", new Double(3.1));

        final JPanel panel = new JPanel();
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

        final DefaultProperty property = new DefaultProperty();
        property.setName("This");
        property.setType(java.lang.Integer.class);
        property.setValue(new Integer(34));
        property.setDisplayName("This display");
        property.setCategory("main");
        property.setEditable(true);

        final ArrayList<Property> properties = new ArrayList<Property>();
        properties.add(property);
        sheet.setProperties(
                properties.toArray(new Property[properties.size()]));
        // sheet.setProperties(property);
        panel.add(sheet, "*");

        /*
         * 
         * 
         * DynaBean bean = new LazyDynaMap(map); bean.set("This1", 34);
         * TextUi.println(bean.get("This")); BaseBeanInfo beanInfo = new
         * BaseBeanInfo(bean.getClass());
         * beanInfo.addProperty("This1").setCategory("x");
         * 
         * 
         * beanInfo.addProperty("secondsToConsider").setCategory("Display style"
         * ) ; // choices
         * beanInfo.addProperty("paintPerceptionInSensorNode").setCategory
         * ("Toggles");
         * beanInfo.addProperty("paintSensorRange").setCategory("Toggles");
         * beanInfo
         * .addProperty("paintTransmissionRange").setCategory("Toggles");
         * beanInfo
         * .addProperty("paintPerceptionInMobileNode").setCategory("Toggles");
         * // colors
         * beanInfo.addProperty("overHearingColor").setCategory("Colors");
         * beanInfo.addProperty("sensorRangeColor").setCategory("Colors");
         * beanInfo.addProperty("sensorSensingColor").setCategory("Colors");
         * beanInfo.addProperty("transmissionRangeColor").setCategory("Colors");
         * return beanInfo; }
         */

        // new BeanBinder(bean, sheet, beanInfo);

        final JFrame frame = new JFrame("test");
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);

        while (true) {
            final Integer i = (Integer) property.getValue();
            TextUi.println("value" + i);
            Thread.currentThread();
            Thread.sleep(1000);
        }

    }

}
