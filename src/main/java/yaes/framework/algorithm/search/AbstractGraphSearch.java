/*
 * Created on Sep 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.algorithm.search;

/**
 * @author lboloni
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractGraphSearch extends AbstractTreeSearch {
    private IClosedNodes closedNodes;

    /**
     * @param problemDescription
     * @param problemSpace
     */
    public AbstractGraphSearch(Object problemDescription, Object problemSpace) {
        super(problemDescription, problemSpace);
    }

    /**
     * 
     * 
     * @return
     */
    @Override
    public Object search() {
        initializeSearchTree(problemDescription, problemSpace);
        while (true) {
            if (getFringe().isEmpty()) {
                return null;
            }
            final TreeSearchNode expansionNode = getFringe()
                    .chooseNodeToExpand();
            if (closedNodes.alreadyExpanded(expansionNode)) {
                continue;
            }
            closedNodes.setAlreadyExpanded(expansionNode);
            if (isSolution(expansionNode)) {
                System.out.println("Found solution, reading it out!");
                return readOutSolution(expansionNode);
            } else {
                expandNode(expansionNode);
            }
        }
    }
}
