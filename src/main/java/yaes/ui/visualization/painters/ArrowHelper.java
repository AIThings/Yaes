package yaes.ui.visualization.painters;

import java.awt.Graphics2D;

/**
 * 
 * <code>yaes.ui.visualization.ArrowHelper</code> This class draws an arrow
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ArrowHelper {

    public static void drawArrow(Graphics2D g, int lineStartX, int lineStartY,
            int lineEndX, int lineEndY, ArrowConfiguration configuration) {
        final int le = (int) Math.sqrt(((lineEndX - lineStartX)
                * (lineEndX - lineStartX) + (lineEndY - lineStartY)
                * (lineEndY - lineStartY)));
        final int cf = lineEndX - lineStartX;
        final int sf = lineEndY - lineStartY;
        final int tx = (le - configuration.a0) * cf / le + lineStartX;
        final int ty = (le - configuration.a0) * sf / le + lineStartY;
        final int t1x = ((le - configuration.a0 - configuration.a1) * cf + configuration.a2
                * sf)
                / le + lineStartX;
        final int t1y = ((le - configuration.a0 - configuration.a1) * sf - configuration.a2
                * cf)
                / le + lineStartY;
        final int t2x = ((le - configuration.a0 - configuration.a1) * cf - configuration.a2
                * sf)
                / le + lineStartX;
        final int t2y = ((le - configuration.a0 - configuration.a1) * sf + configuration.a2
                * cf)
                / le + lineStartY;
        g.drawLine(t1x, t1y, tx, ty);
        g.drawLine(t2x, t2y, tx, ty);
        g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
    }

}