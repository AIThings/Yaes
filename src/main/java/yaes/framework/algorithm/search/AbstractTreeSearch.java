/*
 * Created on Sep 21, 2004
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
public abstract class AbstractTreeSearch {
    protected Object  problemDescription;
    protected Object  problemSpace;
    protected IFringe theFringe;

    public AbstractTreeSearch(Object problemDescription, Object problemSpace) {
        this.problemDescription = problemDescription;
        this.problemSpace = problemSpace;
    }

    protected abstract void expandNode(TreeSearchNode node);

    public IFringe getFringe() {
        return theFringe;
    }

    protected abstract void initializeSearchTree(Object problemDescription,
            Object problemSpace);

    protected abstract boolean isSolution(TreeSearchNode node);

    protected abstract Object readOutSolution(TreeSearchNode node);

    /**
     * 
     * 
     * @return
     */
    public Object search() {
        initializeSearchTree(problemDescription, problemSpace);
        while (true) {
            if (getFringe().isEmpty()) {
                return null;
            }
            final TreeSearchNode expansionNode = getFringe()
                    .chooseNodeToExpand();
            if (isSolution(expansionNode)) {
                System.out.println("Found solution, reading it out!");
                return readOutSolution(expansionNode);
            } else {
                expandNode(expansionNode);
            }
        }
    }
}
