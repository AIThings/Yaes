/*
 * Created on Mar 7, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.agent;

/**
 * @author Lotzi Boloni
 * 
 *         A generic implementation for an interagent communication message -
 *         approximately on the FIPA ACL style
 * 
 */
public class ACLMessage extends GenericMessage {

    /**
     * The FIPA ACL performatives: see
     * http://www.fipa.org/specs/fipa00037/SC00037J.html
     * 
     */
    public enum Performative {
        ACCEPT_PROPOSAL, AGREE, CALL_FOR_PROPOSAL, CANCEL, CONFIRM, DISCONFIRM,
        FAILURE, INFORM, INFORM_IF, INFORM_REF, NOT_UNDERSTOOD, PROPAGATE,
        PROPOSE, PROXY, QUERY_IF, QUERY_REF, REFUSE, REJECT_PROPOSAL, REQUEST,
        REQUEST_WHEN, REQUEST_WHENEVER, SUBSCRIBE
    }

    public static final String BROADCAST        = "*";                  ;

    private static int         counter          = 0;

    private static final long  serialVersionUID = 4960127770214045898L;
    private Object             content;
    private String             conversation;
    private final Performative performative;
    private final String       sender;
    private String             subprotocol;
    private String             subprotocolMessage;

    public ACLMessage(String sender, Performative performative) {
        super(null);
        this.sender = sender;
        this.performative = performative;
        ACLMessage.counter++;
        this.conversation = "Conversation" + ACLMessage.counter;
    }

    /**
     * Creates a new message, identical to the old one but with a new sender and
     * destination - for forwarding purposes
     * 
     * @param destination
     * @param sender
     * @param otherMessage
     */
    public ACLMessage(String destination, String sender, ACLMessage otherMessage) {
        super(destination);
        this.sender = sender;
        this.performative = otherMessage.performative;
        ACLMessage.counter++;
        this.conversation = "Conversation" + ACLMessage.counter;
        this.subprotocol = otherMessage.subprotocol;
        this.subprotocolMessage = otherMessage.subprotocolMessage;
        this.content = otherMessage.content;
        for (String keys : otherMessage.getFields()) {
            setValue(keys, otherMessage.getValue(keys));
        }
    }

    /**
     * @param destination
     */
    public ACLMessage(String destination, String sender,
            Performative performative) {
        super(destination);
        this.sender = sender;
        this.performative = performative;
        ACLMessage.counter++;
        this.conversation = "Conversation" + ACLMessage.counter;
    }

    /**
     * @return Returns the content.
     */
    public Object getContent() {
        return content;
    }

    /**
     * @return Returns the conversation.
     */
    public String getConversation() {
        return conversation;
    }

    /**
     * @return Returns the performative.
     */
    public Performative getPerformative() {
        return performative;
    }

    /**
     * @return Returns the sender.
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return Returns the subprotocol.
     */
    public String getSubprotocol() {
        return subprotocol;
    }

    /**
     * @return Returns the subprotocolMessage.
     */
    public String getSubprotocolMessage() {
        return subprotocolMessage;
    }

    /**
     * @param content
     *            The content to set.
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * @param conversation
     *            The conversation to set.
     */
    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    /**
     * @param subprotocol
     *            The subprotocol to set.
     */
    public void setSubprotocol(String subprotocol) {
        this.subprotocol = subprotocol;
    }

    /**
     * @param subprotocolMessage
     *            The subprotocolMessage to set.
     */
    public void setSubprotocolMessage(String subprotocolMessage) {
        this.subprotocolMessage = subprotocolMessage;
    }

    @Override
    public String toString() {
        final StringBuffer temp = new StringBuffer("<");
        temp.append(performative);
        temp.append("\n\tsender: " + sender);
        temp.append("\n\tdestination: " + getDestination());
        if (subprotocol != null) {
            temp.append("\n\tsubprotocol: " + subprotocol);
        }
        if (subprotocolMessage != null) {
            temp.append("\n\tsubprotocolMessage: " + subprotocolMessage);
        }
        temp.append("\n");
        for (final String element : getFields()) {
            temp.append("\t" + element + " = " + getValue(element) + "\n");
        }
        temp.append("\n>");
        return temp.toString();
    }
}
