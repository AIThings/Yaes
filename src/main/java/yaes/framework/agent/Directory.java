/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.agent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import yaes.ui.text.TextUi;
import yaes.world.World;

/**
 * @author Lotzi Boloni
 */
public class Directory implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6075204547745584609L;
    private final HashMap<String, ICommunicatingAgent> agents =
            new HashMap<String, ICommunicatingAgent>();
    private ArrayList<ACLMessage> cycleMessages = null;
    private final HashMap<String, Integer> receivedMessageCounts =
            new HashMap<String, Integer>();
    private final HashMap<String, Integer> sentMessageCounts =
            new HashMap<String, Integer>();
    private int totalMessageCount = 0;
    private final World world;

    public Directory(World world) {
        this.world = world;
    }

    public ArrayList<ACLMessage> activateMessageCollection() {
        cycleMessages = new ArrayList<ACLMessage>();
        return cycleMessages;
    }

    public void addAgent(ICommunicatingAgent agent) {
        agents.put(agent.getName(), agent);
        sentMessageCounts.put(agent.getName(), 0);
        receivedMessageCounts.put(agent.getName(), 0);
        // registers the agent with the default observer
    }

    /**
     * Delivers the message
     * 
     * @param message
     */
    private void deliverMessage(IMessage message) {
        message.setDeliveryTime(world.getTime());
        final ICommunicatingAgent agent = agents.get(message.getDestination());
        if (agent == null) {
            TextUi.errorPrint("Could not find agent:" + message.getDestination()
                    + "\n" + "to deliver the message: " + message);
            throw new Error("Undeliverable message!");
        }
        if (message instanceof ACLMessage) {
            updateMessageCounts((ACLMessage) message);
            if (cycleMessages != null) {
                cycleMessages.add((ACLMessage) message);
            }
        }
        agent.receiveMessage(message);
    }

    public ICommunicatingAgent getAgent(String agentName) {
        if (agents.containsKey(agentName)) {
            return agents.get(agentName);
        }
        return null;
    }

    public List<ICommunicatingAgent> getAllAgents() {
        return new Vector<ICommunicatingAgent>(agents.values());
    }

    public int getReceivedMessageCount(String agentName) {
        return receivedMessageCounts.get(agentName);
    }

    public int getSentMessageCount(String agentName) {
        return sentMessageCounts.get(agentName);
    }

    public int getTotalMessageCount() {
        return totalMessageCount;
    }

    public void removeAgent(ICommunicatingAgent agent) {
        agents.remove(agent.getName());
    }

    /**
     * The act of sending a message
     * 
     * @param message
     */
    public void sendMessage(IMessage message) {
        message.setSendingTime(world.getTime());
        // in this version of the directory, the message is delivered
        // immediately
        deliverMessage(message);
    }

    private void updateMessageCounts(ACLMessage message) {
        totalMessageCount++;
        sentMessageCounts.put(message.getSender(),
                sentMessageCounts.get(message.getSender()) + 1);
        receivedMessageCounts.put(message.getDestination(),
                receivedMessageCounts.get(message.getDestination()) + 1);
    }
}
