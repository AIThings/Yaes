/*
 * Created on Sep 27, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.pathplanning;

import java.util.HashMap;

import yaes.framework.algorithm.search.IClosedNodes;
import yaes.framework.algorithm.search.TreeSearchNode;
import yaes.world.physical.path.AbstractPathCost;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class AStarClosedNodeSet implements IClosedNodes {
    // FIXME: why it is not a set?
    HashMap<TreeSearchNode, TreeSearchNode> map = new HashMap<>();

    /**
     * @param pathCost
     */
    public AStarClosedNodeSet(AbstractPathCost pathCost, AStarFringe theFringe) {
        super();
    }

    // FIXME: I don't feel quite good about this because this still leads to
    // the creation of a new TreeSearchNode.
    @Override
    public boolean alreadyExpanded(TreeSearchNode node) {
        final TreeSearchNode oldNode = map.get(node);
        if (oldNode == null) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.algorithms.treesearch.IClosedNodes#setAlreadyExpanded(simulation
     * .algorithms.treesearch.TreeSearchNode)
     */
    @Override
    public void setAlreadyExpanded(TreeSearchNode node) {
        map.put(node, node);
    }
}
