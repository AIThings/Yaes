package yaestest.framework.simulation.statutil;

import org.junit.Test;
import org.junit.Assert;

/**
 * Testing the local implementation of Tally against the one from the ssj
 * package
 * 
 * @author Lotzi Boloni
 * 
 */
public class testTally  {
    /**
     * Tests whether the code in our tally implementation gives the same results
     * as the one in SSJ.
     */
	@Test
    public void testCorrectness() {
        final yaes.framework.simulation.statutil.Tally mytally = new yaes.framework.simulation.statutil.Tally();
        final umontreal.iro.lecuyer.stat.Tally ssjtally = new umontreal.iro.lecuyer.stat.Tally();
        final double values[] = { 10, 30, 50, 100, 200, 5000 };
        for (final double d : values) {
            mytally.add(d);
            ssjtally.add(d);
        }
        if (mytally.average() != ssjtally.average()) {
            Assert.fail();
        }
        if (mytally.max() != ssjtally.max()) {
            Assert.fail();
        }
        if (mytally.min() != ssjtally.min()) {
            Assert.fail();
        }
        if (mytally.standardDeviation() != ssjtally.standardDeviation()) {
            Assert.fail();
        }
        if (mytally.variance() != ssjtally.variance()) {
            Assert.fail();
        }
        final double level = 0.95;
        final double myconf[] = new double[2];
        final double ssjconf[] = new double[2];
        mytally.confidenceIntervalStudent(level, myconf);
        ssjtally.confidenceIntervalStudent(level, ssjconf);
        if (myconf[0] != ssjconf[0]) {
            Assert.fail();
        }
        if (myconf[1] != ssjconf[1]) {
            Assert.fail();
        }
    }

    /**
     * Tests the equal function in Tally
     */
	@Test
    public void testEqual() {
        final yaes.framework.simulation.statutil.Tally mytally1 = new yaes.framework.simulation.statutil.Tally();
        final yaes.framework.simulation.statutil.Tally mytally2 = new yaes.framework.simulation.statutil.Tally();
        final double values[] = { 10, 30, 50, 100, 200, 5000 };
        for (final double d : values) {
            mytally1.add(d);
            mytally2.add(d);
        }
        // expect to be equals
        if (!mytally1.equals(mytally2)) {
            Assert.fail();
        }
        mytally1.add(1000);
        // expect to be equals
        if (mytally1.equals(mytally2)) {
            Assert.fail();
        }
    }
}
