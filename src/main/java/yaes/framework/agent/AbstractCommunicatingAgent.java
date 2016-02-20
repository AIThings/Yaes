/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.agent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import yaes.framework.agent.communicationmodel.AbstractCommunicationModel;
import yaes.ui.text.TextUi;
import yaes.world.World;

/**
 * An implementation of an abstract communicating agent which has an
 * implementation of an Mailbox
 * 
 * @author Lotzi Boloni
 * 
 */
public abstract class AbstractCommunicatingAgent extends Observable implements
        ICommunicatingAgent {

    private static final long          serialVersionUID   = -2621396292727724386L;
    private AbstractCommunicationModel communicationModel = null;
    private final List<IConversation>  conversations      = new ArrayList<IConversation>();
    private final String               name;
    protected List<IMessage>           receivedMessages   = new ArrayList<IMessage>();
    protected World                    world;

    /**
     * @param name
     */
    public AbstractCommunicatingAgent(String name, World world) {
        super();
        this.name = name;
        this.world = world;
        // for agent inspection
    }

    // broadcast messages to all agents using communication model
    public void broadcast(IMessage message) {
        final List<ICommunicatingAgent> listOfAgents = world.getDirectory()
                .getAllAgents();
        for (final ICommunicatingAgent agent : listOfAgents) {
            if ((agent != null) && communicationModel.canDeliverMessage(agent)) {
                world.getDirectory().sendMessage(message);
            } else {
                TextUi.println(getName()
                        + ": broadcast(message) --> communication model can not deliver message to: "
                        + agent.getName());
            }
        }
    }

    public void cleanConversations() {
        for (final Iterator<IConversation> it = conversations.iterator(); it
                .hasNext();) {
            if (it.next().isFinished()) {
                it.remove();
            }
        }
    }

    public ACLMessage getACLMessage() {
        for (IMessage element : receivedMessages) {
            if (element instanceof ACLMessage) {
                receivedMessages.remove(element);
                return (ACLMessage) element;
            }
        }
        return null;
    }

    /**
     * Returns from the queue an extended message which is part of a
     * conversation or null if no message arrived.
     * 
     * @param id
     * @return
     */
    public ACLMessage getACLMessageFromConversation(String id) {
        for (final IMessage element : receivedMessages) {
            if (element instanceof ACLMessage) {
                final ACLMessage msg = (ACLMessage) element;
                if (id.equals(msg.getConversation())) {
                    receivedMessages.remove(element);
                    return msg;
                }
            }
        }
        return null;
    }

    public AbstractCommunicationModel getCommunicationModel() {
        return communicationModel;
    }

    /**
     * @return list of active converations
     */
    @Override
    public List<IConversation> getConversations() {
        return conversations;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getObservableIdentifier() {
        return getName();
    }

    public IMessage getReceivedMessage() {
        if (receivedMessages.isEmpty()) {
            return null;
        }
        final IMessage message = receivedMessages.iterator().next();
        receivedMessages.remove(message);
        return message;
    }

    public World getWorld() {
        return world;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * yaes.framework.agent.ICommunicatingAgent#receiveMessage(yaes.framework
     * .agent.IMessage)
     */
    @Override
    public void receiveMessage(IMessage message) {
        receivedMessages.add(message);
    }

    public void registerConversation(IConversation conversation) {
        conversations.add(conversation);
    }

    /*
     * public void send(IMessage message) { final ICommunicatingAgent agent =
     * world.getDirectory().getAgent( message.getDestination()); if ((agent !=
     * null) && ((communicationModel == null) || communicationModel
     * .canDeliverMessage(agent))) { world.getDirectory().sendMessage(message);
     * } else { TextUi .println(getName() +
     * ": send(message) --> communication model can not deliver message to: " +
     * agent.getName()); } }
     */

    public void setCommunicationModel(
            AbstractCommunicationModel communicationModel) {
        this.communicationModel = communicationModel;
    }
}
