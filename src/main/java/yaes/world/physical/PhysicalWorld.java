/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 22, 2008
 
   yaes.world.physical.PhysicalWorld
 
   Copyright (c) 2008 Ladislau Boloni

   This package is released under the LGPL version 2 licence.
 */
package yaes.world.physical;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import yaes.framework.agent.ACLMessage;
import yaes.framework.simulation.SimulationOutput;
import yaes.world.World;
import yaes.world.physical.location.NamedLocation;
import yaes.world.physical.map.IField;
import yaes.world.physical.map.IMap;

/**
 * 
 * <code>yaes.world.physical.PhysicalWorld</code>
 * 
 * <p>
 * This is a world which has
 * 
 * <ul>
 * <li>a map
 * <li>a set of embodied agents
 * <li>manages the communication among the embodied agents.
 * </ul>
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PhysicalWorld extends World {
    /**
     * 
     */
    private static final long                    serialVersionUID      = 3727206552954627076L;
    private Set<PhysicalAgent>                   embodiedAgents        = new HashSet<>();
    private IField                               field                 = null;
    private final Map<String, Set<ACLMessage>>   mailBoxes             = new HashMap<>();
    private IMap                                 map;
    private final List<ACLMessage>               messageCache          = new ArrayList<>();
    private final HashMap<String, NamedLocation> namedLocations        = new HashMap<>();
    private Calendar                             physicalTimeStart;
    private int                                  time                  = 0;
    private int                                  timeScaleMilliseconds = 1000;

    public PhysicalWorld(IField field, SimulationOutput sop) {
        super(sop);
        this.field = field;
        physicalTimeStart = Calendar.getInstance();
    }

    /**
     * Adds an embodied agent and creates it mailbox
     * 
     * @param node
     */
    public void addEmbodiedAgent(PhysicalAgent agent) {
        // verify if the agent is already there
        Set<ACLMessage> mailBox = mailBoxes.get(agent.getName());
        if (mailBox != null) {
            throw new Error("An agent with the name " + agent.getName()
                    + " already exists in this world");
        }
        embodiedAgents.add(agent);
        mailBoxes.put(agent.getName(), new HashSet<ACLMessage>());
    }

    public void addNamedLocation(NamedLocation nl) {
        namedLocations.put(nl.getName(), nl);
    }

    /**
     * Returns the agents in this world as an unmodifiable set. The reason why
     * this one is unmodifiable is because one should add agents only through
     * the addEmbodiedAgent function.
     * 
     * @return
     */
    public Set<PhysicalAgent> getAgents() {
        return Collections.unmodifiableSet(embodiedAgents);
    }

    /**
     * @return the field
     */
    public IField getField() {
        return field;
    }

    /**
     * @return Returns the map.
     */
    public IMap getMap() {
        return map;
    }

    /**
     * @param abstractEmbodiedAgent
     */
    public Set<ACLMessage> getMyMessages(PhysicalAgent embodiedAgent) {
        Set<ACLMessage> messages = new HashSet<>();
        Set<ACLMessage> mailBox = mailBoxes.get(embodiedAgent.getName());
        assert mailBox != null;
        messages.addAll(mailBox);
        mailBox.clear();
        return messages;
    }

    public NamedLocation getNamedLocation(String name) {
        return namedLocations.get(name);
    }

    /**
     * Returns the physical time
     * 
     * @return
     */
    public Calendar getPhysicalTime() {
        Calendar physicalTime = (Calendar) physicalTimeStart.clone();
        physicalTime.add(Calendar.MILLISECOND, time * timeScaleMilliseconds);
        return physicalTime;
    }

    /**
     * Returns the time scale, which is the ratio between the time counter and
     * the physical time in milliseconds
     * 
     * @return
     */
    public int getTimeScaleMilliseconds() {
        return timeScaleMilliseconds;
    }

    /**
     * Deliver all the pending messages
     */
    public void messageFlush() {
        for (final ACLMessage message : messageCache) {
            String destination = message.getDestination();
            if (destination.equals(ACLMessage.BROADCAST)) {
                // deliver to all, except the sender
                String sender = message.getSender();
                for (String agentName : mailBoxes.keySet()) {
                    if (agentName.equals(sender)) {
                        continue;
                    }
                    Set<ACLMessage> mailBox = mailBoxes.get(agentName);
                    mailBox.add(message);
                }
            } else {
                Set<ACLMessage> mailBox = mailBoxes.get(destination);
                if (mailBox == null) {
                    throw new Error("Can not find the destination agent "
                            + destination + " for message" + message);
                }
                mailBox.add(message);
            }
        }
        messageCache.clear();
    }

    /**
     * @param message
     */
    public void sendMessage(ACLMessage message) {
        messageCache.add(message);
    }

    /**
     * @param map
     *            The map to set.
     */
    public void setMap(IMap map) {
        this.map = map;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Sets the time scale, which is the ratio between the time counter and the
     * physical time in milliseconds.
     * 
     * Setting it to 1000 makes one timestep be one second.
     * 
     * @return
     */
    public void setTimeScaleMilliseconds(int timeScaleMilliseconds) {
        this.timeScaleMilliseconds = timeScaleMilliseconds;
    }

    /**
     * Performs the messaging and updates all the agents
     * 
     * @param time
     */
    public void update(int time) {
        this.time = time;
        for (PhysicalAgent agent : embodiedAgents) {
            agent.action();
        }
        messageFlush();
    }
}
