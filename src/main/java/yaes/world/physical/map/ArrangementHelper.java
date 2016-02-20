/*
 * Created on Jun 29, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.map;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

import yaes.ui.text.TextUi;
import yaes.world.physical.location.IMoving;
import yaes.world.physical.location.Location;
import yaes.world.physical.path.PathGenerator;

/**
 * This class is used to arrange a number of objects with location in a certain
 * configuration
 * 
 */
public class ArrangementHelper {

    public static void arrangeInAGrid(final int xMin, final int yMin,
            final int xMax, final int yMax, final List<IMoving> nodes) {
        ArrangementHelper.arrangeInAGridWithNoise(xMin, yMin, xMax, yMax,
                nodes, null, 0.0);
    }

    /**
     * Arrange the objects in a grid and adds a specific noise expressed by the
     * standard deviation.
     * 
     * @param standardDeviation
     *            for the value of 1 the average noise will be equal to the grid
     *            size
     * 
     */
    public static void arrangeInAGridWithNoise(final int xMin, final int yMin,
            final int xMax, final int yMax, final List<IMoving> nodes,
            Random randomSource, double standardDeviation) {
        // start by determining the number of rows and columns.
        final int xRange = xMax - xMin;
        final int yRange = yMax - yMin;
        final double ratio = (double) xRange / (double) yRange;
        int columns = 1;
        int rows = 1;
        while (true) {
            if (rows * columns >= nodes.size()) {
                break;
            }
            final double ratio2 = (double) columns / (double) rows;
            if (ratio < ratio2) {
                rows++;
            } else {
                columns++;
            }
        }
        // now arrange them in rows and columns as determined
        int count = 0;
        final int xStep = xRange / columns;
        final int yStep = yRange / rows;

        for (int i = 0; i != rows; i++) {
            for (int j = 0; j != columns; j++)	 {
                if (count >= nodes.size()) {
                    break;
                }
                final int yPos = yMin + yStep / 2 + i * yStep;
                final int xPos = xMin + xStep / 2 + j * xStep;
                int noiseX = 0;
                int noiseY = 0;
                if (randomSource != null) {
                    noiseX = (int) (xStep * standardDeviation * randomSource
                            .nextGaussian());
                    noiseY = (int) (yStep * standardDeviation * randomSource
                            .nextGaussian());
                }
                nodes.get(count).setLocation(
                        new Location(xPos + noiseX, yPos + noiseY));
                count++;
            }
        }
    }

    /**
     * Distributes the nodes in a uniform random way
     */
    public static void arrangeRandomlyInARectangle(Rectangle2D rect,
            final List<IMoving> nodes, Random randomSource) {
        for (final IMoving node : nodes) {
            final Location l = PathGenerator.getRandomLocationInArea(
                    randomSource, rect);
            node.setLocation(l);
            TextUi.println("Sensor node location:" + l);
        }
    }
}
