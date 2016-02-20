/*
 * Created on Sep 9, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.pathplanning;

import yaes.framework.algorithm.search.StackFringe;
import yaes.world.physical.map.IAccessibilityChecker;
import yaes.world.physical.map.IMap;
import yaes.world.physical.path.PlannedPath;

/**
 * @author Lotzi B?l?ni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class DepthFirstPP extends AbstractTreePathPlanner {
	/**
	 * @param problemDescription
	 * @param problemSpace
	 */
	public DepthFirstPP(PlannedPath problemDescription, IMap problemSpace,
			IAccessibilityChecker accessibilityChecker) {
		super(problemDescription, problemSpace, accessibilityChecker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulation.algorithms.AbstractTreePathPlanner#initFringe()
	 */
	@Override
	protected void initFringe() {
		theFringe = new StackFringe();
	}
	// IMap theMap;
	// List currentPath;
	// Location destination;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * simulation.algorithms.IPathPlanner#planPath(simulation.environment.maps
	 * .PlannedPath, simulation.environment.maps.IMap)
	 */
	/*
	 * public boolean planPath(PlannedPath path, IMap map) { theMap = map;
	 * currentPath = path.getLocationList(); this.destination =
	 * path.getDestination(); solve(path.getSource()); return false; }
	 * 
	 * List solve(Location source) { if (MapHelper.distance(source, destination)
	 * == 0.0) { return currentPath; } // generate the neighbors of the source
	 * List neighbors = MapHelper.getNeighbors(theMap, source); // for those
	 * which are accesible
	 * 
	 * List acceptableNeighbors = new ArrayList(); for (Iterator iter =
	 * neighbors.iterator(); iter.hasNext();) { Location n = (Location)
	 * iter.next(); // loop avoidance if (currentPath.contains(n)) { continue; }
	 * if (theMap.getPropertyAtAsBoolean(MapConstants.ACCESSIBLE, n) == false) {
	 * continue; } acceptableNeighbors.add(n); } // sort the acceptableNeighbors
	 * Collections.sort(acceptableNeighbors, new
	 * DestinationComparator(destination)); // check if they are in the current
	 * source // call solve recursively for all of them. for (Iterator iter =
	 * acceptableNeighbors.iterator(); iter.hasNext();) { Location n =
	 * (Location) iter.next(); currentPath.add(n); List s = solve(n); if (s !=
	 * null) { return currentPath; } currentPath.remove(n); } return null; }
	 */
}
