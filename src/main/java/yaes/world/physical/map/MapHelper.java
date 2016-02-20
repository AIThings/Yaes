/*
 * Created on Sep 9, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.map;

import java.util.ArrayList;
import java.util.List;

import yaes.world.physical.location.Location;
import yaes.world.physical.path.Path;

public class MapHelper {
    public static boolean contains(IMap map, Location location) {
        final double x = location.getX();
        final double y = location.getY();
        if (map.getXLow() > x) {
            return false;
        }
        if (map.getYLow() > y) {
            return false;
        }
        if (map.getXHigh() <= x) {
            return false;
        }
        if (map.getYHigh() <= y) {
            return false;
        }
        return true;
    }

    /**
     * Returns the distance between location a and location b
     * 
     * @param a
     * @param b
     * @return
     */
    public static double distance(Location a, Location b) {
        return Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX())
                + (a.getY() - b.getY()) * (a.getY() - b.getY()));
    }

    public static List<Location> getNeighbors(IMap map, Location location) {
        final List<Location> neighbors = new ArrayList<>();
        final double x = location.getX();
        final double y = location.getY();
        final double dx = map.getXPreferredStep();
        final double dy = map.getYPreferredStep();
        Location tent = new Location(x + dx, y - dy);
        if (MapHelper.contains(map, tent)) {
            neighbors.add(tent);
        }
        tent = new Location(x, y - dy);
        if (MapHelper.contains(map, tent)) {
            neighbors.add(tent);
        }
        tent = new Location(x - dx, y - dy);
        if (MapHelper.contains(map, tent)) {
            neighbors.add(tent);
        }
        tent = new Location(x - dx, y);
        if (MapHelper.contains(map, tent)) {
            neighbors.add(tent);
        }
        tent = new Location(x + dx, y);
        if (MapHelper.contains(map, tent)) {
            neighbors.add(tent);
        }
        tent = new Location(x - dx, y + dy);
        if (MapHelper.contains(map, tent)) {
            neighbors.add(tent);
        }
        tent = new Location(x, y + dy);
        if (MapHelper.contains(map, tent)) {
            neighbors.add(tent);
        }
        tent = new Location(x + dx, y + dy);
        if (MapHelper.contains(map, tent)) {
            neighbors.add(tent);
        }
        return neighbors;
    }

    public static Location getRoundedLocation(Location loc,
            boolean isWhiteAccessible, IMap map) {
        if (isWhiteAccessible) {
            return MapHelper.getRoundedLocation(loc, new WhiteIsAccessible(),
                    map);
        } else {
            return MapHelper.getRoundedLocation(loc, new GrayIsAccessible(),
                    map);
        }
    }

    /**
     * Returns the closest integer subscript location, which is accepted by the
     * checked. FIXME: this is not quite the closest, but it returns consistent
     * results...
     * 
     * @param loc
     * @param checker
     * @param map
     * @return
     */
    public static Location getRoundedLocation(Location loc,
            IAccessibilityChecker checker, IMap map) {
        final Location result_loc = loc.getRoundedLocation();
        Location result_loc2;
        // System.out.println("getRoundedLocation: "+result_loc);
        if (checker.isAccessible(map, result_loc)) {
            return result_loc;
        }
        result_loc2 = new Location(result_loc.getX(), result_loc.getY() + 1);
        if (checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX(), result_loc.getY() - 1);
        if (checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX() + 1, result_loc.getY());
        if (checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX() - 1, result_loc.getY());
        if (checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        // System.out.println("No safe location found for:" + loc);
        return loc.getRoundedLocation();
    }

    public static Location getRoundedLocationInPath(Path path, Location loc,
            boolean isWhiteAccessible, IMap map) {
        if (isWhiteAccessible) {
            return MapHelper.getRoundedLocationInPath(path, loc,
                    new WhiteIsAccessible(), map);
        } else {
            return MapHelper.getRoundedLocationInPath(path, loc,
                    new GrayIsAccessible(), map);
        }
    }

    public static Location getRoundedLocationInPath(Path path, Location loc,
            IAccessibilityChecker checker, IMap map) {
        // TextUi.println("RoundedLocation Path -->" + path);
        Location result_loc = loc.getRoundedLocation();
        Location result_loc2;
        // TextUi.println("RoundedLocation ResultLoc -->" + result_loc);
        if (path.containsLocationValue(result_loc)
                && checker.isAccessible(map, result_loc)) {
            return result_loc;
        }
        result_loc2 = new Location(result_loc.getX() - 1, result_loc.getY() - 1);
        if (path.containsLocationValue(result_loc2)
                && checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX(), result_loc.getY() - 1);
        if (path.containsLocationValue(result_loc2)
                && checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX() + 1, result_loc.getY() - 1);
        if (path.containsLocationValue(result_loc2)
                && checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX() + 1, result_loc.getY());
        // TextUi.println("RoundedLocation ResultLoc -->" + result_loc2);
        if (path.containsLocationValue(result_loc2)
                && checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX() - 1, result_loc.getY());
        // TextUi.println("RoundedLocation ResultLoc -->" + result_loc2);
        if (path.containsLocationValue(result_loc2)
                && checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX() - 1, result_loc.getY() + 1);
        if (path.containsLocationValue(result_loc2)
                && checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX(), result_loc.getY() + 1);
        if (path.containsLocationValue(result_loc2)
                && checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        result_loc2 = new Location(result_loc.getX() + 1, result_loc.getY() + 1);
        if (path.containsLocationValue(result_loc2)
                && checker.isAccessible(map, result_loc2)) {
            return result_loc2;
        }
        System.out.println("No safe location found for:" + loc + " in path:"
                + path);
        // Failed to find a safe location within given path.
        // Try to find any accesssible point around given location
        result_loc = MapHelper.getRoundedLocation(loc, checker, map);
        return result_loc;
    }

}
