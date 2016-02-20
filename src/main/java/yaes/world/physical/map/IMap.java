/*
 * Created on Sep 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.world.physical.map;

import java.awt.Image;
import java.util.List;

import yaes.world.physical.location.Location;

/**
 * Interface for two dimensional maps, which support a number of named 
 * properties 
 * 
 * @author Lotzi Boloni
 * 
 */
public interface IMap extends IField {
    public Image getBackgroundImage();
    /**
     * Returns the internally buffered image
     * @return
     */
    public Image getBufferedImage();
    /**
     * Returns the name of the map
     * @return
     */
    String getMapName();

    /**
     * Returns a list of the property names
     * @return
     */
    List<String> getProperties();
    /**
     * 
     * @param propertyName
     * @param x
     * @param y
     * @return
     */
    Object getPropertyAt(String propertyName, double x, double y);

    boolean getPropertyAtAsBoolean(String propertyName, Location location);

    // returns the class of a given property
    Class<?> getPropertyClass(String propertyName);

    double getXPreferredStep();

    double getYPreferredStep();

    void setPropertyAt(String propertyName, double x, double y, Object value);
}
