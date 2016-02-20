/*
 * Created on Sep 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.pathplanning;

import java.util.Comparator;

import yaes.framework.algorithm.search.IHeuristic;
import yaes.framework.algorithm.search.SortedFringe;
import yaes.framework.algorithm.search.TreeSearchNode;
import yaes.world.physical.location.Location;
import yaes.world.physical.path.AbstractPathCost;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class AStarFringe extends SortedFringe {
	public static double getTreeNodeCost(AbstractPathCost pathCost,
			TreeSearchNode object) {
		final double partialCost = pathCost.cost(object);
		return partialCost;
	}

	Location destination;
	IHeuristic heuristic;
	double ignoreBorder = Double.MAX_VALUE;
	AbstractPathCost pathCost;

	//Comparator<TreeSearchNode> sortingComp;

	/**
	 * @param sortingComp
	 * @param pathCost
	 */
	public AStarFringe(Comparator<TreeSearchNode> sortingComp,
			AbstractPathCost pathCost, Location destination,
			IHeuristic heuristic) {
		super(sortingComp);
		//this.sortingComp = sortingComp;
		this.pathCost = pathCost;
		this.heuristic = heuristic;
		this.destination = destination;
	}

	@Override
	public void addNode(TreeSearchNode node) {
		if (node.getObject() == null) {
			throw new Error("Adding node with no object, should not happen!!");
		}
		// System.out.println("added node:" + node + " heuristic:" +
		// heuristic.heuristic(node) + " path cost:" + pathCost.cost(node));
		node.tmp = 0.5 * heuristic.heuristic(node) + 0.5 * pathCost.cost(node);
		set.add(node);
	}

	/*
	 * Returns true if the node is to be ignored in the fringe, because it has
	 * low prospects.
	 */
	public boolean ignoreDecision(TreeSearchNode object) {
		return false;
        
		//TreeSearchNode node = (TreeSearchNode) object;
		//Location location = (Location) node.getObject();
		//double partialCost = getTreeNodeCost(pathCost, object);
		//double heuristics = MapHelper.distance(destination, location);
		// // double heuristics = 0;
		//if (ignoreBorder < (partialCost + heuristics)) {
		//	TextUi.println("Ignore: true = ignoreBorder = " + ignoreBorder
		//			+ " partialCost = " + partialCost + " heuristics = "
		//			+ heuristics);
		//	return true;
		//} else {
		//	TextUi.println("Ignore: false = ignoreBorder = " + ignoreBorder
		//			+ " partialCost = " + partialCost + "heuristics = "
		//			+ heuristics);
		//	return false;
		//}

		// return false; // never ignore
		// FIXME: if I set this to false, it will get the right solution. But
		// funny
		// enough it will also finish faster, which is weird.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulation.algorithms.treesearch.IFringe#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		for (final TreeSearchNode element : set) {
			if (!ignoreDecision(element)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param currentBestCost
	 */
	public void setIgnoreBorder(double currentBestCost) {
		ignoreBorder = currentBestCost;
	}
}
