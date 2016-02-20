/*
 * Created on Sep 9, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.algorithm.search;

import java.util.List;

import yaes.world.physical.location.Location;
import yaes.world.physical.map.IMap;
import yaes.world.physical.map.MapConstants;
import yaes.world.physical.map.MapHelper;
import yaes.world.physical.path.PlannedPath;
import yaes.world.physical.pathplanning.IPathPlanner;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class HillClimbing implements IPathPlanner {
    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.algorithms.IPathPlanner#planPath(simulation.environment.maps
     * .PlannedPath, simulation.environment.maps.IMap)
     */
    @Override
    public boolean planPath(PlannedPath path, IMap map) {
        Location currentLocation = path.getSource();
        while (true) {
            path.addLocation(currentLocation);
            if (currentLocation.equals(path.getDestination())) {
                return true;
            }
            // get the neighbors of the current location.
            final List<Location> neighbors = MapHelper.getNeighbors(map,
                    currentLocation);
            // find the neighbor which is (a) accessible, (b) closest to the
            // destination.
            Location bestNeighbor = currentLocation;
            for (final Location n : neighbors) {
                if (map.getPropertyAt(MapConstants.ACCESSIBLE, n.getX(),
                        n.getY()).equals(Boolean.FALSE)) {
                    continue;
                }
                if (MapHelper.distance(path.getDestination(), n) < MapHelper
                        .distance(path.getDestination(), bestNeighbor)) {
                    bestNeighbor = n;
                }
            }
            if (bestNeighbor == currentLocation) {
                return false;
            }
            currentLocation = bestNeighbor;
            // if the tentative new is not closer to the dest than the current,
            // fail.
            // otherwise that is the new current
        }
    }
}
