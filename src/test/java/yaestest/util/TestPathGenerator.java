package yaestest.util;

import junit.framework.TestCase;
import yaes.world.physical.location.Location;
import yaes.world.physical.path.PathGenerator;
import yaes.world.physical.path.PlannedPath;

/**
 * I have no idea who created this (Majid? Yi?)
 * 
 * it is old JUnit
 * 
 * <code>yaestest.util.TestPathGenerator</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class TestPathGenerator extends TestCase {
    public void testLinearPath() {
        PlannedPath path = PathGenerator.generateLinearPath(
                new Location(10, 10), new Location(30, 30));
        System.out.println("path src:" + path.getSource()
                + " path Destination: " + path.getDestination());
        System.out.println("path:" + path);
        path = PathGenerator.generateLinearPath(new Location(20, 20),
                new Location(10, 10));
        System.out.println("path src:" + path.getSource()
                + " path Destination: " + path.getDestination());
        System.out.println("path:" + path);
        path = PathGenerator.generateLinearPath(new Location(10, 20),
                new Location(20, 10));
        System.out.println("path src:" + path.getSource()
                + " path Destination: " + path.getDestination());
        System.out.println("path:" + path);
        path = PathGenerator.generateLinearPath(new Location(20, 10),
                new Location(10, 20));
        System.out.println("path src:" + path.getSource()
                + " path Destination: " + path.getDestination());
        System.out.println("path:" + path);
        path = PathGenerator.generateLinearPath(new Location(10, 10),
                new Location(10, 20));
        System.out.println("path src:" + path.getSource()
                + " path Destination: " + path.getDestination());
        System.out.println("path:" + path);
    }
}
