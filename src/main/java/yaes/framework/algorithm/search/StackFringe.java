/*
 * Created on Sep 21, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.algorithm.search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class StackFringe implements IFringe {
    HashSet<Object>                     objects = new HashSet<>();
    private final Stack<TreeSearchNode> stack   = new Stack<>();

    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.algorithms.treesearch.IFringe#addNode(simulation.algorithms
     * .treesearch.TreeSearchNode)
     */
    @Override
    public void addNode(TreeSearchNode node) {
        System.out.println("Fringe size " + stack.size());
        final Object o = node.getObject();
        if (objects.contains(o)) {
            return;
        }
        objects.add(o);
        stack.push(node);
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#chooseNodeToExpand()
     */
    @Override
    public TreeSearchNode chooseNodeToExpand() {
        return stack.pop();
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#getIterator()
     */
    @Override
    public Iterator<TreeSearchNode> getIterator() {
        return stack.iterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.algorithms.treesearch.IFringe#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
