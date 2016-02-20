package yaestest.framework.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.Assert;
import junit.framework.TestCase;
import yaes.framework.simulation.SimulationInput;
import yaes.ui.text.TextUi;

public class testSimulationInput extends TestCase {
    enum EnumTest {
        one, three, two
    };

    /**
	 * 
	 */
    public void testEquality() {
        final SimulationInput si1 = new SimulationInput();
        si1.setParameter(EnumTest.one);
        si1.setParameter("ivalue", 3);
        si1.setParameter("dvalue", 10.0);
        si1.setParameter("svalue", "x");
        final SimulationInput si2 = new SimulationInput();
        si2.setParameter(EnumTest.one);
        si2.setParameter("ivalue", 3);
        si2.setParameter("dvalue", 10.0);
        si2.setParameter("svalue", "x");
        if (!si1.equals(si2)) {
            Assert.fail();
        }
        si2.setParameter("s", 10);
        if (si1.equals(si2)) {
            Assert.fail();
        }
    }

    /**
     * Tests the creation of the SimulationInput based on the model
     */
    public void testModelBasedCreation() {
        final SimulationInput si1 = new SimulationInput();
        si1.setParameter(EnumTest.one);
        si1.setParameter("ivalue", 3);
        si1.setParameter("dvalue", 10.0);
        si1.setParameter("svalue", "x");
        final SimulationInput si2 = new SimulationInput(si1);
        if (!si1.equals(si2)) {
            Assert.fail();
        }
    }

    /**
     * Tests the setting and reading of the various parameter types and the
     * toString function.
     */
    public void testParameterSetting() {
        final SimulationInput si = new SimulationInput();
        // strings
        final String s1 = "string";
        si.setParameter("svalue", s1);
        final String s2 = si.getParameterString("svalue");
        if (!s1.equals(s2)) {
            Assert.fail();
        }
        // integers
        final int i1 = 67;
        si.setParameter("ivalue", i1);
        final int i2 = si.getParameterInt("ivalue");
        if (i1 != i2) {
            Assert.fail();
        }
        // doubles
        final double d1 = 89;
        si.setParameter("dvalue", d1);
        final double d2 = si.getParameterDouble("dvalue");
        if (d1 != d2) {
            Assert.fail();
        }
        // enums
        si.setParameter(EnumTest.one);
        final EnumTest x = si.getParameterEnum(EnumTest.class);
        if (x != EnumTest.one) {
            Assert.fail();
        }
        // toString
        TextUi.println(si);
    }

    /**
     * Tests the serialization
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("resource")
	public void testSerialization() throws IOException, ClassNotFoundException {
        final File temp = File.createTempFile("ser", null);
        temp.deleteOnExit();
        final SimulationInput si1 = new SimulationInput();
        si1.setParameter(EnumTest.one);
        si1.setParameter("ivalue", 3);
        si1.setParameter("dvalue", 10.0);
        si1.setParameter("svalue", "x");
        // writing
        final FileOutputStream fos = new FileOutputStream(temp);
        final ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(si1);
        oos.flush();
        oos.close();
        // reading
        final FileInputStream fis = new FileInputStream(temp);
        final ObjectInputStream ois = new ObjectInputStream(fis);
        final SimulationInput si2 = (SimulationInput) ois.readObject();
        ois.close();
        // comparison
        if (!si1.equals(si2)) {
            Assert.fail();
        }
        TextUi.println("Serialization test passed for: " + si2);
    }
}
