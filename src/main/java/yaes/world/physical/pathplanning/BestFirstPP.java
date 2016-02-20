/*
 * Created on Sep 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.pathplanning;

import java.util.Comparator;

import yaes.framework.algorithm.search.DestinationComparator;
import yaes.framework.algorithm.search.SortedFringe;
import yaes.framework.algorithm.search.TreeNodeComparator;
import yaes.framework.algorithm.search.TreeSearchNode;
import yaes.world.physical.map.IAccessibilityChecker;
import yaes.world.physical.map.IMap;
import yaes.world.physical.path.PlannedPath;

/**
 * @author Lotzi Boloni
 * 
 */
public class BestFirstPP extends AbstractTreePathPlanner {
	/**
	 * @param problemDescription
	 * @param problemSpace
	 */
	public BestFirstPP(PlannedPath problemDescription, IMap problemSpace,
			IAccessibilityChecker accessibilityChecker) {
		super(problemDescription, problemSpace, accessibilityChecker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * simulation.algorithms.pathplanning.AbstractTreePathPlanner#initFringe()
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initFringe() {
		final Comparator c1 = new DestinationComparator(
				thePath.getDestination());
		final Comparator<TreeSearchNode> c2 = new TreeNodeComparator(c1);
		theFringe = new SortedFringe(c2);
	}
}
