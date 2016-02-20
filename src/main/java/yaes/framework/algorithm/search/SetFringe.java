/*
 * Created on Sep 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.algorithm.search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class SetFringe implements IFringe {
    public Set<TreeSearchNode> set;

    public SetFringe() {
        set = new HashSet<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.algorithms.treesearch.IFringe#addNode(simulation.algorithms
     * .treesearch.TreeSearchNode)
     */
    @Override
    public void addNode(TreeSearchNode node) {
        if (node.getObject() == null) {
            throw new Error("Adding node with no object, should not happen!!");
        }
        set.add(node);
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#chooseNodeToExpand()
     */
    @Override
    public TreeSearchNode chooseNodeToExpand() {
        final TreeSearchNode toReturn = set.iterator().next();
        if (toReturn != null) {
            set.remove(toReturn);
        }
        // TextUi.println("fringe size" + set.size());
        return toReturn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#getIterator()
     */
    @Override
    public Iterator<TreeSearchNode> getIterator() {
        return set.iterator();
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
}