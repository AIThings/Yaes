/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 15, 2008
 
   yaes.ui.visualization.VisualizationProperties
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.visualization;

import java.util.HashMap;
import java.util.Map;

import com.l2fprod.common.propertysheet.DefaultProperty;

/**
 * 
 * <code>yaes.ui.visualization.VisualizationProperties</code> Contains the basic
 * visualization properties
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class VisualizationProperties {

    private Map<String, DefaultProperty> properties = new HashMap<>();

    public VisualizationProperties() {
        // nothing here
    }

    /**
     * Adds a new visualization property. If the property already exists, it
     * does not touch it.
     * 
     * @param identifier
     * @param name
     * @param category
     * @param object
     */
    public void addVisualizationProperty(String identifier, String name,
            String category, Object object) {
        DefaultProperty property = properties.get(identifier);
        if (property != null) {
            return;
        }
        property = new DefaultProperty();
        property.setName(identifier);
        property.setType(object.getClass());
        property.setValue(object);
        property.setDisplayName(name);
        property.setCategory(category);
        property.setEditable(true);
        properties.put(identifier, property);
    }

    /**
     * @return the properties
     */
    public Map<String, DefaultProperty> getProperties() {
        return properties;
    }

    public Object getPropertyValue(String identifier) {
        return properties.get(identifier).getValue();
    }

    public void setPropertyValue(String identifier, Object o) {
        properties.get(identifier).setValue(o);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (String id : properties.keySet()) {
            DefaultProperty prop = properties.get(id);
            buf.append(prop.getDisplayName() + " = " + prop.getValue() + "\n");
        }
        return buf.toString();
    }

}
