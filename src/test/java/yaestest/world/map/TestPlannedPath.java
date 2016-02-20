package yaestest.world.map;

import java.util.Random;

import junit.framework.TestCase;
import yaes.world.physical.location.Location;
import yaes.world.physical.path.PathGenerator;
import yaes.world.physical.path.PlannedPath;

public class TestPlannedPath extends TestCase {
    public void testRandomLocationInPath() {
        final Random rand = new Random();
        final PlannedPath path = PathGenerator.generateLinearPath(new Location(
                10, 10), new Location(20, 20));
        try {
            for (int i = 0; i < 100; i++) {
                System.out.println(path.getRandomLocation(rand));
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
