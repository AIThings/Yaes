/*
 * Created on Sep 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.algorithm.search;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Lotzi Boloni
 * 
 */
public abstract class PruningSortedFringe extends SortedFringe {
    Object lastPruningBorder = null;

    /**
     * @param comparator
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PruningSortedFringe(Comparator sortingComp) {
        super(sortingComp);
    }

    @Override
    public void addNode(TreeSearchNode node) {
        if (node.getObject() == null) {
            throw new Error("Adding node with no object, should not happen!!");
        }
        if ((lastPruningBorder == null)
                || !pruneDecision(node, lastPruningBorder)) {
            set.add(node);
        }
        // TextUi.println("Fringe size = " + set.size());
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#chooseNodeToExpand()
     */
    @Override
    public TreeSearchNode chooseNodeToExpand() {
        final TreeSearchNode toReturn = set.first();
        if (toReturn != null) {
            set.remove(toReturn);
        }
        // TextUi.println("fringe size" + set.size());
        return toReturn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    public void prune(Object pruningBorder) {
        lastPruningBorder = pruningBorder;
        // TextUi.println("Before pruning: " + set.size());
        // this might be done easier given the sorted map
        for (final Iterator<TreeSearchNode> iter = getIterator(); iter
                .hasNext();) {
            final TreeSearchNode element = iter.next();
            if (pruneDecision(element, pruningBorder)) {
                iter.remove();
            }
        }
        // TextUi.println("After pruning: " + set.size());
    }

    public abstract boolean pruneDecision(TreeSearchNode object,
            Object pruningBorder);
}
