/*
 * Created on Sep 9, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.algorithm.search;

import java.util.Comparator;

import yaes.world.physical.location.Location;
import yaes.world.physical.map.MapHelper;

/**
 * @author Lotzi Boloni
 */
public class DestinationComparator implements Comparator<Location> {
    private final Location destination;

    public DestinationComparator(Location destination) {
        this.destination = destination;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Location l1, Location l2) {
        if (destination == null) {
            System.err.println("Destination is zero!");
            return 0;
        }
        if (l1 == null) {
            System.err.println("Comparing with zero!");
            return 0;
        }
        if (l2 == null) {
            System.err.println("Comparing with zero!");
            return 0;
        }
        final Double d1 = new Double(MapHelper.distance(l1, destination));
        final Double d2 = new Double(MapHelper.distance(l2, destination));
        return d1.compareTo(d2);
    }
}