/*
 * Created on Sep 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.algorithm.search;

import java.util.Iterator;

import yaes.ui.text.TextUi;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public abstract class PruningSetFringe extends SetFringe {
    Object lastPruningBorder = null;

    /**
     * @param comparator
     */
    public PruningSetFringe() {
        super();
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

    public void prune(Object pruningBorder) {
        lastPruningBorder = pruningBorder;
        TextUi.println("Before pruning: " + set.size());
        // this might be done easier given the sorted map
        for (final Iterator<TreeSearchNode> iter = getIterator(); iter
                .hasNext();) {
            final TreeSearchNode element = iter.next();
            if (pruneDecision(element, pruningBorder)) {
                iter.remove();
            }
        }
        TextUi.println("After pruning: " + set.size());
    }

    public abstract boolean pruneDecision(TreeSearchNode object,
            Object pruningBorder);
}
