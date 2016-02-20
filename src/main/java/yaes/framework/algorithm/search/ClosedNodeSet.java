/*
 * Created on Sep 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.algorithm.search;

import java.util.HashSet;

/**
 * @author lboloni
 * 
 *         This is a closed node implementation which is storing the objects in
 *         the hashmap.
 * 
 *         It assumes that the objects have the correct Hash settings.
 * 
 */
public class ClosedNodeSet implements IClosedNodes {
    HashSet<TreeSearchNode> set = new HashSet<>();

    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.algorithms.treesearch.IClosedNodes#alreadyExpanded(simulation
     * .algorithms.treesearch.TreeSearchNode)
     */
    @Override
    public boolean alreadyExpanded(TreeSearchNode node) {
        return set.contains(node);
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
        // TextUi.println("Closed set:" + set.size());
        set.add(node);
    }
}
