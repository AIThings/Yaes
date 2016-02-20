package yaes.ui.visualization.painters;

/**
 * 
 * <code>yaes.ui.visualization.ArrowConfiguration</code> Describes the
 * configuration of the arrow.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ArrowConfiguration {
    int a0 = 20; // distance from top to end of line
    int a1 = 20; // distance along line from top to bottom
    int a2 = 5; // distance of arrow end from line

    public ArrowConfiguration() {
    }

    public ArrowConfiguration(int tipToEnd, int length, int width) {
        this.a0 = tipToEnd;
        this.a1 = length;
        this.a2 = width / 2;
    }

    /**
     * Sets distance between ends of arrow (perpendicular to arrow axis
     * 
     * @param width
     *            distance between to line ends. Should to be even number
     */
    public void setArrowFunnelWidth(int width) {
        this.a2 = width / 2;
    }

    /**
     * Sets distance from arrow tip to arrow end along the line on which arrow
     * is applied
     * 
     * @param length
     */
    public void setArrowLengthAlongLine(int length) {
        this.a1 = length;
    }

    /**
     * Sets distance from arrow tip to end of line on which arrow is applied
     * 
     * @param tipToEnd
     */
    public void setDistanceTipToLineEnd(int tipToEnd) {
        this.a0 = tipToEnd;
    }
}