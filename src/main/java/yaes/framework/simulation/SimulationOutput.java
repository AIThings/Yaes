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
import java.util.Observable;
import java.util.Set;

import yaes.ui.format.Formatter;
import yaes.ui.text.TextUi;

/**
 * @author Lotzi Boloni
 * 
 *         This class contains the output of a simulation.
 * 
 */
public class SimulationOutput extends Observable implements Serializable {
    public static final String INTEGRATED_COUNT = "IntegratedFromCount";
    public static final String REAL_DATA        = "SimulationOutputRealData";
    static final long          serialVersionUID = 1134568210844627276L;

    /**
     * Creates a SimulationOutput object from a file
     * 
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static SimulationOutput restore(File file) {
        SimulationOutput simOut = null;
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(file)));
            simOut = (SimulationOutput) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            TextUi.println("Cannot recover from this...");
            System.exit(1);            
        }
        return simOut;
    }

    /**
     * Creates a list of simulationOutput objects from a file
     * 
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static List<SimulationOutput> restoreList(File file)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        List<SimulationOutput> simOut = null;
        final ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(file)));
        simOut = (List<SimulationOutput>) in.readObject();
        in.close();
        return simOut;
    }

    /**
     * Stores this list of SimulationOutputs in a file
     * 
     * @param list
     * @param fileName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void storeList(List<SimulationOutput> list, File file)
            throws FileNotFoundException, IOException {
        final ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)));
        out.writeObject(list);
        out.flush();
        out.close();
    }

    private transient IContext                    theContext;

    /**
     * The time the simulation was started (System.milliseconds)
     */
    private long                                  timeStart;
    /**
     * The time the simulation was end (System.milliseconds)
     */
    private long                                  timeStop;
    private final HashMap<String, RandomVariable> variables = new HashMap<>();

    /**
     * Creates an empty SimulationOutput.
     */
    public SimulationOutput() {
        update(SimulationOutput.REAL_DATA, 1.0);
        update(SimulationOutput.INTEGRATED_COUNT, 1.0);
    }

    /**
     * Creates the SimulationOutput based on the variables from a
     * SimulationInput
     * 
     * @param sim
     * @param context
     */
    public SimulationOutput(SimulationInput sim, IContext context) {
        update(SimulationOutput.REAL_DATA, 1.0);
        update(SimulationOutput.INTEGRATED_COUNT, 1.0);
        if (sim == null) { // this is the case of the integratedSOP
            return;
        }
        // copying the input into the output
        for (final Iterator<String> it = sim.getParameterIterator(); it
                .hasNext();) {
            final String key = it.next();
            final SimulationInputParameter sip = sim.getParameter(key);
            switch (sip.getType()) {
            case T_DOUBLE:
                update(key, sip.getDouble());
                break;
            case T_INT:
                update(key, sip.getInt());
                break;
            case T_ENUM:
            case T_STRING:
            case T_BYTE_ARRAY:
                // FIXME: these are not yet transferred.
                continue;
			default:
				break;
            }
        }
        this.theContext = context;
    }

    /**
     * Creates a random variable, it also specifies whether it will have time
     * series or not
     * 
     * @param name
     * @param enableTimeSeries
     *            - enables the time series collecting, normally false
     * @return
     */
    public RandomVariable createVariable(String name, boolean enableTimeSeries) {
        final RandomVariable var = new RandomVariable(name);
        if (enableTimeSeries) {
            var.enableTimeSeriesCollecting();
        }
        variables.put(name, var);
        return var;
    }

    /**
     * Returns an iterator to the parameter names
     * 
     * @return
     */
    public Iterator<String> getParameterIterator() {
        return variables.keySet().iterator();
    }

    /**
     * Returns the random variable with the specified name
     * 
     * @param name
     * @return
     */
    public RandomVariable getRandomVar(String name) {
        final RandomVariable var = variables.get(name);
        if (var == null) {
            throw new Error("Random variable:" + name + " not found!");
        }
        return var;
    }

    /**
     * Gets the length of the simulation
     * 
     * @return the lenght of the simulation in duration
     */
    public long getSimulationDuration() {
        return this.timeStop - this.timeStart;
    }

    /**
     * @return Returns the theContext.
     */
    public IContext getTheContext() {
        return theContext;
    }

    /**
     * @return the timeStart
     */
    public long getTimeStart() {
        return timeStart;
    }

    /**
     * @return the timeStop
     */
    public long getTimeStop() {
        return timeStop;
    }

    /**
     * Gets the value of a certain type from a random variable.
     * 
     * @param name
     * @param type
     * @return
     */
    public double getValue(String name, RandomVariable.Probe type) {
        final RandomVariable var = variables.get(name);
        if (var == null) {
            // throw new Error("Can not find variable:" + name);
            TextUi.println("This variable was accessed without being set:"
                    + name + " Asummed 0.");
            update(name, 0);
            return 0.0;
        }
        return var.getValue(type);
    }

    /**
     * Returns a set of variable names
     */
    public Set<String> getVariableNames() {
        return variables.keySet();
    }

    /**
     * Sets a random variable
     * 
     * @param name
     * @param value
     */
    public void set(String name, double value) {
        RandomVariable var = variables.get(name);
        if (var == null) {
            var = createVariable(name, false);
        } else {
            var.init();
        }
        var.update(value);
        notifyObservers();
    }

    /**
     * @param timeStart
     *            the timeStart to set
     */
    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * @param timeStop
     *            the timeStop to set
     */
    public void setTimeStop(long timeStop) {
        this.timeStop = timeStop;
    }

    /**
     * Stores this SimulationOutput in a file
     * 
     * @param fileName
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void store(String file) throws FileNotFoundException, IOException {
        final ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)));
        out.writeObject(this);
        out.flush();
        out.close();
    }

    @Override
    public String toString() {
        Formatter fmt = new Formatter();
        fmt.add("SimulationOutput");
        fmt.indent();
        final List<String> vars = new ArrayList<>(variables.keySet());
        Collections.sort(vars);
        for (final String key : vars) {
            final RandomVariable rv = getRandomVar(key);
            fmt.add(rv);
        }
        return fmt.toString();
    }

    /**
     * Updates a random variable.
     * 
     * @param name
     * @param value
     */
    public void update(String name, double value) {
        RandomVariable var = variables.get(name);
        if (var == null) {
            var = createVariable(name, false);
        }
        var.update(value);
        notifyObservers();
    }

    
    /**
     * Updates two random variables: one based on the name provided, and
     * the other one a postfixed one with the identifier provided as second
     * 
     * @param name
     * @param value
     */
    public void updateDual(String name, String id, double value) {
        update(name, value);
        update(name + "_" + id, value);
        notifyObservers();
    }

}
