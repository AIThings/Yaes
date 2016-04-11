package yaestest.framework.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;

import junit.framework.TestCase;
import yaes.framework.simulation.SimulationException;
import yaes.framework.simulation.SimulationInputParameter;
import yaes.ui.text.TextUi;

public class testSimulationInputParameter extends TestCase {
    enum EnumTest {
        one, three, two
    };

    /**
     * Test the copy constructor
     */
    public void testCopyConstructor() {
        final SimulationInputParameter sip = new SimulationInputParameter(
                "test", 10);
        final SimulationInputParameter sip2 = new SimulationInputParameter(sip);
        if (sip.getName() != sip2.getName()) {
            Assert.fail();
        }
        if (sip.getType() != sip2.getType()) {
            Assert.fail();
        }
        if (sip.getInt() != sip2.getInt()) {
            Assert.fail();
        }
    }

    /**
     * Testing for the double values
     * 
     */
    @SuppressWarnings("rawtypes")
    public void testDouble() {
        final double data = 89;
        final SimulationInputParameter sip = new SimulationInputParameter(
                "test", data);
        TextUi.println(sip);
        if (sip.getType() != SimulationInputParameter.SimulationInputType.T_DOUBLE) {
            Assert.fail();
        }
        final double value = sip.getDouble();
        if (value != data) {
            Assert.fail();
        }
        // can not get integer out of it
        try {
            @SuppressWarnings("unused")
            final int value2 = sip.getInt();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
        // can not get string out of it
        try {
            @SuppressWarnings("unused")
            final String value2 = sip.getString();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
        // can not get enumeration out of it
        try {
            @SuppressWarnings("unused")
            final Enum value2 = sip.getEnum();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
    }

    /**
     * Testing for the enumeration values
     * 
     */
    @SuppressWarnings("rawtypes")
    public void testEnum() {
        final Enum data = EnumTest.one;
        final SimulationInputParameter sip = new SimulationInputParameter(data);
        TextUi.println(sip);
        if (sip.getType() != SimulationInputParameter.SimulationInputType.T_ENUM) {
            Assert.fail();
        }
        final Enum value = sip.getEnum();
        if (value != data) {
            Assert.fail();
        }
        // can not get integer out of it
        try {
            @SuppressWarnings("unused")
            final int value2 = sip.getInt();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
        // can not get doubles out of it
        try {
            @SuppressWarnings("unused")
            final double value2 = sip.getDouble();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
        // can not get string out of it
        try {
            @SuppressWarnings("unused")
            final String value2 = sip.getString();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
    }

    /**
     * Tests the equals function and the hashCode
     * 
     */
    public void testEquals() {
        final int ivalue1 = 10, ivalue2 = 20;
        // between integers
        final SimulationInputParameter sint1 = new SimulationInputParameter(
                "sint", ivalue1);
        final SimulationInputParameter sint2 = new SimulationInputParameter(
                "sint", ivalue1);
        final SimulationInputParameter sint3 = new SimulationInputParameter(
                "sint", ivalue2);
        if (sint1 == sint2) {
            Assert.fail();
        }
        if (!sint1.equals(sint2)) {
            Assert.fail();
        }
        if (sint1.equals(sint3)) {
            Assert.fail();
        }
        // between double
        final double dvalue1 = 10.34, dvalue2 = 20.45;
        final SimulationInputParameter sdouble1 = new SimulationInputParameter(
                "sdouble", dvalue1);
        final SimulationInputParameter sdouble2 = new SimulationInputParameter(
                "sdouble", dvalue1);
        final SimulationInputParameter sdouble3 = new SimulationInputParameter(
                "sdouble", dvalue2);
        if (!sdouble1.equals(sdouble2)) {
            Assert.fail();
        }
        if (sdouble1.equals(sdouble3)) {
            Assert.fail();
        }
        // between strings
        final String svalue1 = "10.34", svalue2 = "20.45";
        final SimulationInputParameter sstring1 = new SimulationInputParameter(
                "sstring", svalue1);
        final SimulationInputParameter sstring2 = new SimulationInputParameter(
                "sstring", svalue1);
        final SimulationInputParameter sstring3 = new SimulationInputParameter(
                "sstring", svalue2);
        if (!sstring1.equals(sstring2)) {
            Assert.fail();
        }
        if (sstring1.equals(sstring3)) {
            Assert.fail();
        }
        // between enums
        final SimulationInputParameter senum1 = new SimulationInputParameter(
                EnumTest.one);
        final SimulationInputParameter senum2 = new SimulationInputParameter(
                EnumTest.one);
        final SimulationInputParameter senum3 = new SimulationInputParameter(
                EnumTest.three);
        if (!senum1.equals(senum2)) {
            Assert.fail();
        }
        if (senum1.equals(senum3)) {
            Assert.fail();
        }
        // checking between different names
        TextUi.println("Equals test passed");
        final SimulationInputParameter sint4 = new SimulationInputParameter(
                "sint2", ivalue2);
        if (sint3.equals(sint4)) {
            Assert.fail();
        }
    }

    /**
     * Testing for the integer values
     * 
     */
    @SuppressWarnings("rawtypes")
    public void testInteger() {
        final int data = 77;
        final SimulationInputParameter sip = new SimulationInputParameter(
                "test", data);
        TextUi.println(sip);
        if (sip.getType() != SimulationInputParameter.SimulationInputType.T_INT) {
            Assert.fail();
        }
        final int value = sip.getInt();
        if (value != data) {
            Assert.fail();
        }
        // can not get doubles out of it
        try {
            @SuppressWarnings("unused")
            final double value2 = sip.getDouble();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
        // can not get string out of it
        try {
            @SuppressWarnings("unused")
            final String value2 = sip.getString();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
        // can not get enumeration out of it
        try {
            @SuppressWarnings("unused")
            final Enum value2 = sip.getEnum();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
    }

    /**
     * Test serialization
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void testSerialization() throws IOException, ClassNotFoundException {
        final File temp = File.createTempFile("ser", null);
        temp.deleteOnExit();
        final SimulationInputParameter sint1 = new SimulationInputParameter(
                "sint", 77);
        // writing
        final FileOutputStream fos = new FileOutputStream(temp);
        final ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(sint1);
        oos.flush();
        oos.close();
        // reading
        final FileInputStream fis = new FileInputStream(temp);
        final ObjectInputStream ois = new ObjectInputStream(fis);
        final SimulationInputParameter sint2 = (SimulationInputParameter) ois
                .readObject();
        ois.close();
        // comparison
        if (!sint1.equals(sint2)) {
            Assert.fail();
        }
        TextUi.println("Serialization test passed for: " + sint2);
    }

    /**
     * Testing for the string values
     * 
     */
    @SuppressWarnings("rawtypes")
    public void testString() {
        final String data = "aha";
        final SimulationInputParameter sip = new SimulationInputParameter(
                "test", data);
        TextUi.println(sip);
        if (sip.getType() != SimulationInputParameter.SimulationInputType.T_STRING) {
            Assert.fail();
        }
        final String value = sip.getString();
        if (value != data) {
            Assert.fail();
        }
        // can not get integer out of it
        try {
            @SuppressWarnings("unused")
            final int value2 = sip.getInt();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
        // can not get doubles out of it
        try {
            @SuppressWarnings("unused")
            final double value2 = sip.getDouble();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
        // can not get enumeration out of it
        try {
            @SuppressWarnings("unused")
            final Enum value2 = sip.getEnum();
            // should throw an exception
            Assert.fail();
        } catch (final SimulationException sex) {
            TextUi.println(sex);
            TextUi.println("Ok, expected");
        }
    }
}
