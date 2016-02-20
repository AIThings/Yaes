/*
 * Created on Sep 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.algorithm.search;

import java.util.Iterator;

/**
 * @author lboloni
 * 
 */
public interface IFringe {
    // adds a node to the fringe
    void addNode(TreeSearchNode node);

    // picks a node to expand and removes it from the fringe
    TreeSearchNode chooseNodeToExpand();

    Iterator<TreeSearchNode> getIterator();

    boolean isEmpty();
}
