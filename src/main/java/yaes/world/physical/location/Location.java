/*
 * Created on Sep 9, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.location;

import java.awt.geom.Point2D;
import java.io.Serializable;

import yaes.world.physical.map.MapHelper;

/**
 * @author Lotzi Boloni
 */
public class Location implements Serializable, IMoving {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1721407397393871628L;

    public static void main(String args[]) {
        final Location locationA = new Location(13, 30);
        final Location locationB = new Location(11, 20);
        final Location locationC = new Location(17, 200);
        System.out.println(locationA.locationWithIn(locationB, locationC));
    }

    protected final double x;

    private final double   y;

    /**
     * @param x
     * @param y
     */
    public Location(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the location as a Point2D
     * 
     * @return
     */
    public Point2D.Double asPoint() {
        return new Point2D.Double(x, y);
    }

    /**
     * Returns the distance to the destination location
     * 
     * @param dest
     * @return
     */
    public double distanceTo(Location dest) {
        return MapHelper.distance(this, dest);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Location) {
            final Location loc = (Location) o;
            return (x == loc.x) && (y == loc.y);
        }
        return false;
    }

    /**
     * @return
     */
    @Override
    public Location getLocation() {
        return this;
    }

    public Location getRoundedLocation() {
        return new Location(Math.round(getX()), Math.round(getY()));
    }

    /**
     * @return Returns the x.
     */
    public double getX() {
        return x;
    }

    /**
     * @return Returns the y.
     */
    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return new Double(x).hashCode() + new Double(y).hashCode();
    }

    /**
     * see if given location is between this and other location FIXME: this is
     * utility function by Majid. Move it somewhere else.
     * 
     * @param loc1
     * @param loc2
     * @return
     */
    public boolean locationWithIn(Location loc1, Location loc2) {
        if ((getX() <= loc1.getX()) && (getX() >= loc2.getX())
                && (getY() <= loc1.getY()) && (getY() >= loc2.getY())) {
            return true;
        }
        if ((getX() <= loc1.getX()) && (getX() >= loc2.getX())
                && (getY() >= loc1.getY()) && (getY() <= loc2.getY())) {
            return true;
        }
        if ((getX() >= loc1.getX()) && (getX() <= loc2.getX())
                && (getY() >= loc1.getY()) && (getY() <= loc2.getY())) {
            return true;
        }
        if ((getX() >= loc1.getX()) && (getX() <= loc2.getX())
                && (getY() <= loc1.getY()) && (getY() >= loc2.getY())) {
            return true;
        }
        return false;
    }

    /**
     * @param location
     */
    @Override
    public void setLocation(Location location) {
        throw new Error("setLocation not supported");
    }

    @Override
    public String toString() {
        return "(" + String.format("%6.2f", x) + ", "
                + String.format("%6.2f", y) + ")";
    }
}
