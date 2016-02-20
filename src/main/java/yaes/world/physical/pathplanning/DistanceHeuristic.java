/*
 * Created on Oct 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.pathplanning;

import yaes.framework.algorithm.search.IHeuristic;
import yaes.framework.algorithm.search.TreeSearchNode;
import yaes.world.physical.location.Location;
import yaes.world.physical.map.MapHelper;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class DistanceHeuristic implements IHeuristic {
    private final Location destination;

    /**
     * @param destination
     */
    public DistanceHeuristic(Location destination) {
        super();
        this.destination = destination;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * yaes.framework.algorithm.search.IHeuristic#heuristic(yaes.framework.algorithm
     * .search.TreeSearchNode)
     */
    @Override
    public double heuristic(TreeSearchNode node) {
        final Location location = (Location) node.getObject();
        return MapHelper.distance(location, destination);
    }
}