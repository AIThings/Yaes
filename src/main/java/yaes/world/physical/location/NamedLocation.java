/*
 * Created on Sep 18, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.location;

import yaes.world.physical.PhysicalWorld;

public class NamedLocation extends Location implements INamedMoving {
    /**
	 * 
	 */
    private static final long serialVersionUID = 9090750604441804317L;
    private final String      name;

    /**
     * @param x
     * @param y
     */
    public NamedLocation(double x, double y, String name) {
        super(x, y);
        this.name = name;
    }

    /**
     * Creates a named location, and immediately adds it to the worlds named
     * location list
     * 
     * @param x
     * @param y
     * @param name
     * @param world
     */
    public NamedLocation(double x, double y, String name, PhysicalWorld world) {
        super(x, y);
        this.name = name;
        world.addNamedLocation(this);
    }

    /**
     * Creates a named location based on a previously available location
     * 
     * @param location
     * @param name
     */
    public NamedLocation(Location location, String name) {
        super(location.getX(), location.getY());
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
