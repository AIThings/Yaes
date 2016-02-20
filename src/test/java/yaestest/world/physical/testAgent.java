/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 23, 2008
 
   yaestest.world.physical.testAgent
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.world.physical;

import java.util.Random;
import java.util.Set;

import yaes.framework.agent.ACLMessage;
import yaes.world.physical.PhysicalAgent;
import yaes.world.physical.PhysicalWorld;
import yaes.world.physical.location.Location;

/**
 * 
 * <code>yaestest.world.physical.testAgent</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testAgent extends PhysicalAgent {
    private static final long serialVersionUID = -8335035079873596292L;
    private int               dx, dy;

    /**
     * @param name
     * @param location
     * @param eaWorld
     */
    public testAgent(String name, Location location, PhysicalWorld eaWorld) {
        super(name, location, eaWorld);
    }

    /**
	 * 
	 */
    @Override
    public void action() {
        // read my messages
        readMyMessages();
        // update my location by moving
        setLocation(new Location(getLocation().getX() + dx, getLocation()
                .getY()
                + dy));
        // some messaging: pick a destination at random in 0 - 4
        Random rand = new Random();
        String destination = "agent" + rand.nextInt(5);
        ACLMessage message = new ACLMessage(destination, getName(),
                ACLMessage.Performative.INFORM);
        message.setValue("X", new Integer(rand.nextInt(10)));
        message.setValue("Y", new Integer(rand.nextInt(10)));
        transmit(message);
    }

    /**
     * Reads all the messages, gets the X and Y values transmitted and
     * calculates the sum
     * 
     * @return
     */
    private void readMyMessages() {
        dx = 0;
        dy = 0;
        Set<ACLMessage> messages = receiveMessages();
        for (ACLMessage msg : messages) {
            int x = (Integer) msg.getValue("X");
            int y = (Integer) msg.getValue("Y");
            dx = dx + x;
            dy = dy + y;
        }
    }
}
