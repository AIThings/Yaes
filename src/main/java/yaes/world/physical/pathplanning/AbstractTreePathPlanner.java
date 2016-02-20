/*
 * Created on Sep 21, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.pathplanning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yaes.framework.algorithm.search.AbstractTreeSearch;
import yaes.framework.algorithm.search.IClosedNodes;
import yaes.framework.algorithm.search.TreeSearchNode;
import yaes.ui.text.TextUi;
import yaes.world.physical.location.Location;
import yaes.world.physical.map.IAccessibilityChecker;
import yaes.world.physical.map.IMap;
import yaes.world.physical.map.MapHelper;
import yaes.world.physical.path.PathLength;
import yaes.world.physical.path.PlannedPath;

/**
 * @author Lotzi Boloni
 * 
 */
public abstract class AbstractTreePathPlanner extends AbstractTreeSearch
		implements IPathPlanner {
	
	private static Object staticReadOutSolution(TreeSearchNode node) {
		final ArrayList<Object> l = new ArrayList<>();
		TreeSearchNode current = node;
		while (current != null) {
			l.add(current.getObject());
			current = current.getParent();
		}
		Collections.reverse(l);
		return l;
	}

	private IAccessibilityChecker accessibilityChecker;
	// this would make it a graph searcher
	IClosedNodes closed;
	private final Location destination;
	private final IMap theMap;

	protected PlannedPath thePath;

	/**
	 * @param problemDescription
	 * @param problemSpace
	 */
	public AbstractTreePathPlanner(PlannedPath problemDescription,
			IMap problemSpace, IAccessibilityChecker accessibilityChecker) {
		super(problemDescription, problemSpace);
		theMap = problemSpace;
		thePath = problemDescription;
		destination = problemDescription.getDestination();
		this.accessibilityChecker = accessibilityChecker;
		// accessibilityChecker = new GrayIsAccessible();
	}

	@Override
	protected void expandNode(TreeSearchNode node) {
		final Location current = (Location) node.getObject();
		final List<Location> neighbors = MapHelper
				.getNeighbors(theMap, current);
		for (final Location element : neighbors) {
			if (accessibilityChecker.isAccessible(theMap, element) == false) {
				continue;
			}
			final TreeSearchNode node2 = new TreeSearchNode(node, element);
			if ((closed == null) || !closed.alreadyExpanded(node2)) {
				getFringe().addNode(node2);
			}
		}
		if (closed != null) {
			closed.setAlreadyExpanded(node);
		}
	}

	/**
	 * @return Returns the accessibilityChecker.
	 */
	public IAccessibilityChecker getAccessibilityChecker() {
		return accessibilityChecker;
	}

	protected abstract void initFringe();

	@Override
	protected void initializeSearchTree(Object problemDescription,
			Object problemSpace) {
		initFringe();
		if (this instanceof AStarPP) {
			closed = new AStarClosedNodeSet(new PathLength(),
					(AStarFringe) theFringe);
		}
		final TreeSearchNode init = new TreeSearchNode(null,
				thePath.getSource());
		theFringe.addNode(init);
	}

	@Override
	protected boolean isSolution(TreeSearchNode node) {
		final Location current = (Location) node.getObject();
		if (current.equals(destination)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * simulation.algorithms.IPathPlanner#planPath(simulation.environment.maps
	 * .PlannedPath, simulation.environment.maps.IMap)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean planPath(PlannedPath path, IMap map) {
		final List<Location> l = (List<Location>) search();
		if (l == null) {
			TextUi.println("No path found");
			return false;
		}
		System.out.println("Solution found");
		for (final Location element : l) {
			path.addLocation(element);
		}
		return false;
	}

	@Override
	protected Object readOutSolution(TreeSearchNode node) {
		return AbstractTreePathPlanner.staticReadOutSolution(node);
	}

	/**
	 * @param accessibilityChecker
	 *            The accessibilityChecker to set.
	 */
	public void setAccessibilityChecker(
			IAccessibilityChecker accessibilityChecker) {
		this.accessibilityChecker = accessibilityChecker;
	}
}
