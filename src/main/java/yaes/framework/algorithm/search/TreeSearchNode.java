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
 */
public class TreeSearchNode {
    private Object         object;
    private TreeSearchNode parent;
    public double          tmp;   // used for accelerating sorting etc.

    /**
     * @param parent
     * @param object
     */
    public TreeSearchNode(TreeSearchNode parent, Object object) {
        super();
        this.parent = parent;
        this.object = object;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TreeSearchNode)) {
            return false;
        }
        final TreeSearchNode comp2 = (TreeSearchNode) other;
        return this.object.equals(comp2.object);
    }

    /**
     * @return Returns the object.
     */
    public Object getObject() {
        return object;
    }

    /**
     * @return Returns the parent.
     */
    public TreeSearchNode getParent() {
        return parent;
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }

    /**
     * @param object2
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @param parent2
     */
    public void setParent(TreeSearchNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Location:" + object + " tmp:" + tmp;
    }
}