package yaestest.framework.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;
import junit.framework.TestCase;
import yaes.framework.simulation.RandomVariable;
import yaes.ui.text.TextUi;

public class testRandomVariable extends TestCase {
    /**
     * Tests the main use of the random variable by comparing with the ssj
     * tally.
     */
    public void testCompare() {
        final RandomVariable rv = new RandomVariable("Test");
        final umontreal.iro.lecuyer.stat.Tally ssjtally = new umontreal.iro.lecuyer.stat.Tally();
        final double values[] = { 10, 30, 50, 100, 200, 5000 };
        for (final double d : values) {
            rv.update(d);
            ssjtally.add(d);
        }
        if (rv.getAvg() != ssjtally.average()) {
            Assert.fail();
        }
        if (rv.getMax() != ssjtally.max()) {
            Assert.fail();
        }
        if (rv.getMin() != ssjtally.min()) {
            Assert.fail();
        }
        if (rv.getStandardDeviation() != ssjtally.standardDeviation()) {
            Assert.fail();
        }
        if (rv.getVariance() != ssjtally.variance()) {
            Assert.fail();
        }
        final double level = 0.95;
        final double myconf[] = new double[2];
        final double ssjconf[] = new double[2];
        rv.confidenceIntervalStudent(level, myconf);
        ssjtally.confidenceIntervalStudent(level, ssjconf);
        if (myconf[0] != ssjconf[0]) {
            Assert.fail();
        }
        if (myconf[1] != ssjconf[1]) {
            Assert.fail();
        }
    }

    /**
     * Tests if the equality works
     * 
     */
    public void testEquality() {
        final RandomVariable rv1 = new RandomVariable("r");
        final RandomVariable rv2 = new RandomVariable("r");
        final double values[] = { 10, 30, 50, 100, 200, 5000 };
        for (final double d : values) {
            rv1.update(d);
            rv2.update(d);
        }
        // expect to be equals
        if (!rv1.equals(rv2)) {
            Assert.fail();
        }
        rv1.update(1000);
        // expect them to not be equals
        if (rv1.equals(rv2)) {
            Assert.fail();
        }
        rv2.update(1000);
        // expect them to be equals
        if (!rv1.equals(rv2)) {
            Assert.fail();
        }
    }

    /**
     * Tests if serialization works, tests if you can update after serialization
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("resource")
	public void testSerialization() throws IOException, ClassNotFoundException {
        final File temp = File.createTempFile("ser", null);
        temp.deleteOnExit();
        final RandomVariable rv1 = new RandomVariable("r");
        final RandomVariable rv2 = new RandomVariable("r");
        final double values[] = { 10, 30, 50, 100, 200, 5000 };
        for (final double d : values) {
            rv1.update(d);
            rv2.update(d);
        }
        // writing
        final FileOutputStream fos = new FileOutputStream(temp);
        final ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(rv1);
        oos.flush();
        oos.close();
        // reading
        final FileInputStream fis = new FileInputStream(temp);
        final ObjectInputStream ois = new ObjectInputStream(fis);
        final RandomVariable rv3 = (RandomVariable) ois.readObject();
        ois.close();
        // comparison
        if (!rv1.equals(rv3)) {
            Assert.fail();
        }
        // check if they can be updated after serialization
        final double values2[] = { 33, 34, 90 };
        for (final double d : values2) {
            rv1.update(d);
            rv3.update(d);
        }
        // comparison
        if (!rv1.equals(rv3)) {
            Assert.fail();
        }
        TextUi.println("Serialization test passed for: " + rv3);
    }
}
