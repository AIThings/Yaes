/*
 * Created on Sep 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.pathplanning;

import java.util.ArrayList;
import java.util.Comparator;

import yaes.framework.algorithm.search.IHeuristic;
import yaes.framework.algorithm.search.TreeNodeComparator;
import yaes.framework.algorithm.search.TreeSearchNode;
import yaes.ui.text.TextUi;
import yaes.world.physical.location.Location;
import yaes.world.physical.map.GrayIsAccessible;
import yaes.world.physical.map.IAccessibilityChecker;
import yaes.world.physical.map.IMap;
import yaes.world.physical.path.AbstractPathCost;
import yaes.world.physical.path.PlannedPath;

/**
 * Implements an A* path planning
 * 
 * @author Lotzi Boloni
 * 
 * 
 */
public class AStarPP extends AbstractTreePathPlanner {
	protected TreeSearchNode currentBest;
	protected double currentBestCost;
	private final IHeuristic heuristic;
	private final AbstractPathCost pathCost;
	private boolean returnFirst = false;

	/**
	 * @param problemDescription - the problem description, a planned path 
	 *      in which the source and the destination are specified, but not the rest 
	 * @param problemSpace - a map object
	 * @param pathCost - the past cost function, which for any cost will return the path
	 * @param heuristic - the heuristic cost to be used
	 * @
	 */
	public AStarPP(PlannedPath problemDescription, IMap problemSpace,
			AbstractPathCost pathCost, IHeuristic heuristic,
			IAccessibilityChecker accessibilityChecker) {
		super(problemDescription, problemSpace, accessibilityChecker);
		this.pathCost = pathCost;
		this.heuristic = heuristic;
		currentBest = null;
		currentBestCost = Double.MAX_VALUE;
	}

	/**
	 * Simplified constructor, assumes gray is accessible
	 * 
	 * @param problemDescription
	 * @param problemSpace
	 * @param pathCost
	 * @param heuristic
	 */
	public AStarPP(PlannedPath problemDescription, IMap problemSpace,
			AbstractPathCost pathCost, IHeuristic heuristic) {
		this(problemDescription, problemSpace, pathCost, heuristic,
				new GrayIsAccessible());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * simulation.algorithms.pathplanning.AbstractTreePathPlanner#initFringe()
	 */
	@Override
	protected void initFringe() {
		final Comparator<TreeSearchNode> c2 = new TreeNodeComparator();
		theFringe = new AStarFringe(c2, pathCost, thePath.getDestination(),
				heuristic);
	}

	/**
	 * @return Returns the returnFirst.
	 */
	public boolean isReturnFirst() {
		return returnFirst;
	}

	@Override
	protected boolean isSolution(TreeSearchNode node) {
		if (super.isSolution(node)) {
			// evaluate the cost
			final double cost = AStarFringe.getTreeNodeCost(pathCost, node);
			TextUi.println("Path found with cost=" + cost);
			if (cost < currentBestCost) {
				TextUi.println("Solution found, current cost is:" + cost);
				currentBest = node;
				currentBestCost = cost;
				((AStarFringe) theFringe).setIgnoreBorder(currentBestCost);
			}
			if (returnFirst) {
				return true;
			}
		}
		// always return false, because we need to continue searching
		return false;
	}

	/**
	 * Should be generalizable for all optimizing searches.
	 */
	@Override
	public boolean planPath(PlannedPath path, IMap map) {
		TextUi.println("A* path planning from " + path.getSource() + " --> "
				+ path.getDestination());
		// check the feasibility of the source and destination points
		if (getAccessibilityChecker().isAccessible(map, path.getSource()) == false) {
			throw new Error("Source point " + path.getSource()
					+ " is not accessible");
		}
		if (getAccessibilityChecker().isAccessible(map, path.getDestination()) == false) {
			throw new Error("Destination point " + path.getDestination()
					+ " is not accessible");
		}
		final long startTime = System.currentTimeMillis();
		super.planPath(path, map);
		if (currentBest == null) {
			TextUi.println("No solution found for: " + path.getSource()
					+ " -->" + path.getDestination());
			return false;
		}
		// readout the optimal solution
		if (!returnFirst) {
			final ArrayList<Location> list = (ArrayList<Location>) readOutSolution(currentBest);
			for (final Location element : list) {
				thePath.addLocation(element);
			}
		}
		final double cost = pathCost.cost(path);
		TextUi.println("Best path found with cost=" + cost + "\ttime = "
				+ (System.currentTimeMillis() - startTime) / 1000 + " seconds");
		return true;
	}

	/**
	 * @param returnFirst
	 *            The returnFirst to set.
	 */
	public void setReturnFirst(boolean returnFirst) {
		this.returnFirst = returnFirst;
	}
}