package yaes.framework.simulation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import yaes.ui.simulationcontrol.SimulationControlPanel;

/**
 * @author Lotzi Boloni
 * 
 *         This class contains the input of a simulation. It is a collection of
 *         SimulationInputParameter objects, and the temporal dimension of the
 *         simulation.
 * 
 */
public class SimulationInput implements Serializable {
    private static final long serialVersionUID = 10L;

    /**
     * Creates a SimulationOutput object from a file
     * 
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static SimulationInput restore(File file)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        SimulationInput sip = null;
        final ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(file)));
        sip = (SimulationInput) in.readObject();
        in.close();
        return sip;
    }

    private String                                          contextClass    = null;

    private final HashMap<String, SimulationInputParameter> parameters      = new HashMap<>();
    private String                                          simulationClass = null;
    private transient SimulationControlPanel                simulationControlPanel;
    private double                                          startTime       = 0;
    private double                                          stopTime        = 1000;

    private double                                          timeResolution  = 1;

    /**
     * Creates an empty simulation input object
     * 
     */
    public SimulationInput() {
        // nothing here
    }

    /**
     * Creates a simulation input object initialized with the parameters of the
     * model.
     * 
     * @param model
     */
    public SimulationInput(SimulationInput model) {
        this.startTime = model.getStartTime();
        this.stopTime = model.getStopTime();
        this.timeResolution = model.getTimeResolution();
        this.contextClass = model.contextClass;
        this.simulationClass = model.simulationClass;
        for (final String name : model.parameters.keySet()) {
            parameters.put(name, model.parameters.get(name));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * Rewrite the equality for a value based one.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SimulationInput)) {
            return false;
        }
        final SimulationInput other = (SimulationInput) o;
        if (startTime != other.startTime) {
            return false;
        }
        if (stopTime != other.stopTime) {
            return false;
        }
        if (timeResolution != other.timeResolution) {
            return false;
        }
        if (!parameters.equals(other.parameters)) {
            return false;
        }
        return true;
    }

    /**
     * @return the contextClass
     */
    public String getContextClass() {
        return contextClass;
    }

    /**
     * Returns the parameter
     * 
     * @param name
     * @return
     */
    public SimulationInputParameter getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Returns a double valued parameter
     * 
     * @param name
     * @return
     */
    public double getParameterDouble(String name) {
        final SimulationInputParameter value = parameters.get(name);
        if (value == null) {
            throw new SimulationException("Cannot find parameter " + name);
        }
        return value.getDouble();
    }

    /**
     * Returning an enumeration by the type passed.
     * 
     * @param <X>
     * @param c
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <X extends Enum> X getParameterEnum(Class<X> c) {
        final SimulationInputParameter value = parameters.get(c.getName());
        if (value == null) {
            throw new SimulationException("Cannot find parameter "
                    + c.getName());
        }
        return (X) value.getEnum();
    }

    /**
     * Returns a double valued parameter
     * 
     * @param name
     * @return
     */
    public int getParameterInt(String name) {
        final SimulationInputParameter value = parameters.get(name);
        if (value == null) {
            throw new SimulationException("Cannot find parameter " + name);
        }
        return value.getInt();
    }

    /**
     * Allows the iteration over the parameters (for external printing
     * inspection etc)
     * 
     * @return
     */
    public Iterator<String> getParameterIterator() {
        return parameters.keySet().iterator();
    }

    /**
     * Returns a String valued parameter
     * 
     * @param name
     * @return
     */
    public String getParameterString(String name) {
        final SimulationInputParameter value = parameters.get(name);
        if (value == null) {
            throw new SimulationException("Cannot find parameter " + name);
        }
        return value.getString();
    }

    /**
     * @return the simulationClass
     */
    public String getSimulationClass() {
        return simulationClass;
    }

    public SimulationControlPanel getSimulationControlPanel() {
        return simulationControlPanel;
    }

    /**
     * @return Returns the startTime.
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * @return Returns the stopTime.
     */
    public double getStopTime() {
        return stopTime;
    }

    /**
     * @return Returns the timeResolution.
     */
    public double getTimeResolution() {
        return timeResolution;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     * 
     * Rewritten to match with the equals
     */
    @Override
    public int hashCode() {
        return parameters.hashCode();
    }

    /**
     * @param contextClass
     *            the contextClass to set
     */
    public void setContextClass(Class<?> context) {
        this.contextClass = context.getCanonicalName();
    }

    /**
     * @param contextClass
     *            the contextClass to set
     */
    public void setContextClass(String contextClass) {
        this.contextClass = contextClass;
    }

    /**
     * Sets an enumeration valued parameter
     * 
     * @param e
     */
    @SuppressWarnings("rawtypes")
    public void setParameter(Enum e) {
        final SimulationInputParameter sim = new SimulationInputParameter(e);
        parameters.put(sim.getName(), sim);
    }

    /**
     * Sets a double valued parameter
     * 
     * @param name
     * @param value
     */
    public void setParameter(String name, double value) {
        final SimulationInputParameter sim = new SimulationInputParameter(name,
                value);
        parameters.put(sim.getName(), sim);
    }

    /**
     * Sets an integer valued parameter
     * 
     * @param name
     * @param value
     */
    public void setParameter(String name, int value) {
        final SimulationInputParameter sim = new SimulationInputParameter(name,
                value);
        parameters.put(sim.getName(), sim);
    }

    /**
     * Sets a String valued parameter
     * 
     * @param name
     * @param value
     */
    public void setParameter(String name, SimulationInputParameter value) {
        parameters.put(name, value);
    }

    /**
     * Sets a String valued parameter
     * 
     * @param name
     * @param value
     */
    public void setParameter(String name, String value) {
        final SimulationInputParameter sim = new SimulationInputParameter(name,
                value);
        parameters.put(sim.getName(), sim);
    }

    /**
     * @param simulationClass
     *            the simulationClass to set
     */
    public void setSimulationClass(Class<?> simulation) {
        this.simulationClass = simulation.getCanonicalName();
    }

    /**
     * @param simulationClass
     *            the simulationClass to set
     */
    public void setSimulationClass(String simulationClass) {
        this.simulationClass = simulationClass;
    }

    public void setSimulationControlPanel(
            SimulationControlPanel simulationControlPanel) {
        this.simulationControlPanel = simulationControlPanel;
    }

    /**
     * @param startTime
     *            The startTime to set.
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    /**
     * @param stopTime
     *            The stopTime to set.
     */
    public void setStopTime(double stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * @param timeResolution
     *            The timeResolution to set.
     */
    public void setTimeResolution(double timeResolution) {
        this.timeResolution = timeResolution;
    }

    /**
     * Stores this SimulationInput in a file
     * 
     * @param fileName
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void store(File file) throws FileNotFoundException, IOException {
        final ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)));
        out.writeObject(this);
        out.flush();
        out.close();
    }

    /**
     * Formatted printing of the object
     */
    @Override
    public String toString() {
        final StringBuffer result = new StringBuffer("Input parameters ");
        result.append("simulation time: [" + startTime + ", " + stopTime + "]");
        final List<String> parlist = new ArrayList<>();
        parlist.addAll(parameters.keySet());
        Collections.sort(parlist);
        for (final String key : parlist) {
            final SimulationInputParameter o = parameters.get(key);
            result.append("\n\t" + o.toString());
        }
        return result.toString();
    }

}
