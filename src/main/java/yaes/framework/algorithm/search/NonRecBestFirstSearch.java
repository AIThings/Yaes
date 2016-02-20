/*
 * Created on Sep 17, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.algorithm.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import yaes.world.physical.location.Location;
import yaes.world.physical.map.IMap;
import yaes.world.physical.map.MapConstants;
import yaes.world.physical.map.MapHelper;
import yaes.world.physical.path.PlannedPath;
import yaes.world.physical.pathplanning.IPathPlanner;

/**
 * @author lboloni
 * 
 */
public class NonRecBestFirstSearch implements IPathPlanner {
    List<Location>  currentPath;
    Stack<Location> frontier;
    IMap            theMap;

    List<Location> findNeighbors(Location currentPosition,
            Comparator<Location> comparator) {
        final List<Location> neighbors = MapHelper.getNeighbors(theMap,
                currentPosition);
        final List<Location> acceptableNeighbors = new ArrayList<>();
        for (final Location n : neighbors) {
            // loop avoidance
            if (currentPath.contains(n)) {
                continue;
            }
            // accessible points in the map
            if (theMap.getPropertyAtAsBoolean(MapConstants.ACCESSIBLE, n) == false) {
                continue;
            }
            acceptableNeighbors.add(n);
        }
        // sort the acceptableNeighbors
        Collections.sort(acceptableNeighbors, comparator);
        return acceptableNeighbors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.algorithms.IPathPlanner#planPath(simulation.environment.maps
     * .PlannedPath, simulation.environment.maps.IMap)
     */
    @Override
    public boolean planPath(PlannedPath path, IMap map) {
        theMap = map;
        currentPath = path.getLocationList();
        solve(path.getSource(), path.getDestination());
        return false;
    }

    /**
     * Best first search, non-recursive
     * 
     * @param source
     * @param destination
     * @return
     */
    List<Location> solve(Location source, Location destination) {
        final Location currentPosition = source;
        final Comparator<Location> comparator = new DestinationComparator(
                destination);
        frontier.push(currentPosition);
        while (true) {
            // currentPosition = frontier.pop();
            if (MapHelper.distance(currentPosition, destination) == 0.0) {
                return currentPath;
            }
            // generate the neighbors of the current position
            final List<Location> acceptableNeighbors = findNeighbors(
                    currentPosition, comparator);
            // call solve recursively for all of them.
            for (Location n : acceptableNeighbors) {
                currentPath.add(n);
                final List<Location> s = solve(n, destination);
                if (s != null) {
                    return currentPath;
                }
                currentPath.remove(n);
            }
            return null;
        }
    }
}