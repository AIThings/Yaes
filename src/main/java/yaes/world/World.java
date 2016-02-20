/*
 * Created on Sep 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import yaes.framework.agent.Directory;
import yaes.framework.simulation.SimulationOutput;

/**
 * A generic repository of objects.
 * 
 * @author Lotzi Boloni
 * 
 */
/**
 * @author Lotzi Boloni
 * 
 */
public class World extends Observable implements Serializable {
    private static final long serialVersionUID  = 1985777048628473306L;
    private Directory         directory         = new Directory(this);
    private double            endOfTheWorldTime = -1;
    private final Set<Object> objects           = new HashSet<>();
    private SimulationOutput  simulationOutput;
    private double            time;

    /**
     * Creates a world with a specific simulation output
     * 
     * @param so
     */
    public World(SimulationOutput simulationOutput) {
        this.simulationOutput = simulationOutput;
    }

    
    /**
     * Change the simulation output. Normally, this is necessary in situations when 
     * we want to run a different simulation with the same world, (for instance if
     * it was used for caching). 
     * 
     * @param simulationOutput
     */
    public void changeSimulationOutput(SimulationOutput simulationOutput) {
    	this.simulationOutput = simulationOutput;
    }
    
    
    /**
     * Adds an object to the world
     * 
     * @param o
     */
    public void addObject(Object o) {
        objects.add(o);
    }

    /**
     * Returns true if this is the last simulation cycle.
     * 
     * @return
     */
    public boolean endOfTheWorld() {
        return time == endOfTheWorldTime;
    }

    /**
     * @return Returns the directory.
     */
    public Directory getDirectory() {
        return directory;
    }

    public double getEndOfTheWorldTime() {
        return endOfTheWorldTime;
    }

    /**
     * Returns the objects of type c
     * 
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public <X> List<X> getObjectsOfType(Class<X> c) {
        final List<X> retval = new ArrayList<>();
        for (final Object a : objects) {
            if (a.getClass().isAssignableFrom(c)) {
                retval.add((X) a);
            }
        }
        return retval;
    }

    public SimulationOutput getSimulationOutput() {
        return simulationOutput;
    }

    /**
     * @return Returns the time.
     */
    public double getTime() {
        return time;
    }

    /**
     * @param directory
     *            The directory to set.
     */
    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public void setEndOfTheWorldTime(int endOfTheWorldTime) {
        this.endOfTheWorldTime = endOfTheWorldTime;
    }

    /**
     * Sets the time
     * 
     * @param time
     */
    public void setTime(double time) {
        this.time = time;
    }
}
