/*
 * Created on Sep 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.algorithm.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author lboloni
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class QueueFringe implements IFringe {
    HashSet<Object>      objects = new HashSet<>();
    List<TreeSearchNode> queue   = new ArrayList<>();

    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.algorithms.treesearch.IFringe#addNode(simulation.algorithms
     * .treesearch.TreeSearchNode)
     */
    @Override
    public void addNode(TreeSearchNode node) {
        System.out.println("Fringe size " + queue.size());
        final Object o = node.getObject();
        if (objects.contains(o)) {
            return;
        }
        objects.add(o);
        queue.add(node);
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#chooseNodeToExpand()
     */
    @Override
    public TreeSearchNode chooseNodeToExpand() {
        final TreeSearchNode result = queue.get(0);
        queue.remove(0);
        objects.remove(result.getObject());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#getIterator()
     */
    @Override
    public Iterator<TreeSearchNode> getIterator() {
        return queue.iterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
