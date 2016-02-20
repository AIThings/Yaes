/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.agent;

import java.util.List;

/**
 * @author Lotzi Boloni
 * 
 *         The main interface behind abstract agents which can communicate.
 * 
 */
public interface ICommunicatingAgent extends IAgent {
    public List<IConversation> getConversations();

    public String getName();

    void receiveMessage(IMessage message);
}
