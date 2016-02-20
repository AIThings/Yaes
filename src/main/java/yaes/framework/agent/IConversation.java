/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.agent;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public interface IConversation {
    void doAllActions();

    AbstractCommunicatingAgent getAgent();

    String getConversationId();

    ISubprotocol getSubprotocol();

    boolean isFinished();

    ACLMessage nextMessage();

    void send(ACLMessage message);
}
