/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 22, 2008
 
   yaes.world.physical.PhysicalAgent
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is released under the LGPL version 2.
 */
package yaes.world.physical;

import java.util.Set;

import yaes.framework.agent.ACLMessage;
import yaes.framework.agent.IAgent;
import yaes.world.physical.location.IMoving;
import yaes.world.physical.location.INamed;
import yaes.world.physical.location.Location;

/**
 * 
 * <code>yaes.world.physical.PhysicalAgent</code>
 * 
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public abstract class PhysicalAgent implements IAgent, IMoving, INamed {
    private static final long serialVersionUID = 4097026554643599347L;
    private PhysicalWorld     eaWorld;
    private Location          location;
    private String            name;

    public PhysicalAgent(String name, Location location, PhysicalWorld eaWorld) {
        this.name = name;
        this.eaWorld = eaWorld;
        this.location = location;
    }

    /**
     * @return
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the world
     */
    public PhysicalWorld getWorld() {
        return eaWorld;
    }

    /**
     * Returns all the messages received since the function was last called.
     * Cleans the mailbox.
     */
    protected final Set<ACLMessage> receiveMessages() {
        return eaWorld.getMyMessages(this);
    }

    /**
     * @param location
     */
    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Transmits a
     * 
     * @param message
     */
    protected final void transmit(ACLMessage message) {
        eaWorld.sendMessage(message);
    }

}
