/*
 * Created on Sep 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.world.physical.pathplanning;

import yaes.framework.algorithm.search.QueueFringe;
import yaes.world.physical.map.IAccessibilityChecker;
import yaes.world.physical.map.IMap;
import yaes.world.physical.path.PlannedPath;

/**
 * @author lboloni
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class BreadthFirstPP extends AbstractTreePathPlanner {
	/**
	 * @param problemDescription
	 * @param problemSpace
	 */
	public BreadthFirstPP(PlannedPath problemDescription, IMap problemSpace,
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
		theFringe = new QueueFringe();
	}
}
