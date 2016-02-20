/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Aug 6, 2010
 
   yaes.framework.simulation.AbstractContext
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation;

import java.util.Random;

import yaes.ui.visualization.Visualizer;
import yaes.world.World;

/**
 * 
 * <code>yaes.framework.simulation.AbstractContext</code>
 * 
 * Makes available a collection of functionalities likely to be found in most
 * simulation applications: SimulationInput and Output storage, random
 * generator, world, visualizer
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class AbstractContext implements IContext {

    private static final long  serialVersionUID = 1L;
    protected SimulationInput  sip;
    protected SimulationOutput sop;
    protected World            theWorld;
    protected transient Visualizer       visualizer;
    protected Random           random           = new Random(0);

    /**
     * @param visualizer
     */
    @Override
    public void createVisualRepresentation(Visualizer visualizer) {
        throw new Error("createVisualRepresentation not implemented by default");
    }

    /**
     * Return the random generator
     * 
     * @return the random
     */
    public Random getRandom() {
        return random;
    }

    /**
     * @return
     */
    @Override
    public SimulationInput getSimulationInput() {
        return sip;
    }

    /**
     * @return
     */
    @Override
    public SimulationOutput getSimulationOutput() {
        return sop;
    }

    public Visualizer getVisualizer() {
        return visualizer;
    }

    /**
     * @return
     */
    @Override
    public World getWorld() {
        return theWorld;
    }

    /**
     * @param sip
     * @param sop
     */
    @Override
    public void initialize(SimulationInput sip, SimulationOutput sop) {
        this.sip = sip;
        this.sop = sop;
    }


}
