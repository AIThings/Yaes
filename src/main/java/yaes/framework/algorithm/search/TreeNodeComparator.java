/*
 * Created on Sep 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.algorithm.search;

import java.util.Comparator;

import yaes.world.physical.location.Location;

/**
 * @author Lotzi Boloni
 * 
 *         This class compares two treenodes, but delegates its comparison to an
 *         embedded comparator
 */
public class TreeNodeComparator implements Comparator<TreeSearchNode> {
    @SuppressWarnings("rawtypes")
    private final Comparator embedded;

    public TreeNodeComparator() {
        super();
        this.embedded = null;
    }

    /**
     * @param embedded
     */
    public TreeNodeComparator(Comparator<Object> embedded) {
        super();
        this.embedded = embedded;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public int compare(TreeSearchNode tsn0, TreeSearchNode tsn1) {
        if (tsn0.equals(tsn1)) {
            return 0;
        }
        // default: based on the tmp value
        if (embedded == null) {
            return Double.compare(tsn0.tmp, tsn1.tmp);
        }
        final int value = embedded.compare(tsn0.getObject(), tsn1.getObject());
        if (value == 0) {
            final Location l0 = (Location) tsn0.getObject();
            final Location l1 = (Location) tsn1.getObject();
            if (l0.getX() > l1.getX()) {
                return 1;
            } else if (l0.getX() < l1.getX()) {
                return -1;
            } else if (l0.getY() > l1.getY()) {
                return 1;
            } else {
                return 0;
            }
        }
        return value;
    }
}
