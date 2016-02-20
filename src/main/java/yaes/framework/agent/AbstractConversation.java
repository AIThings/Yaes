package yaes.framework.agent;

public abstract class AbstractConversation implements IConversation {
    String                     conversationId;
    AbstractCommunicatingAgent theAgent;

    /**
     * This is the constructor used at the side which initiates the conversation
     * 
     * @param theAgent
     */
    public AbstractConversation(AbstractCommunicatingAgent theAgent) {
        this.theAgent = theAgent;
        conversationId = null;
        theAgent.registerConversation(this);
    }

    public AbstractConversation(AbstractCommunicatingAgent theAgent,
            ACLMessage message) {
        this.theAgent = theAgent;
        conversationId = message.getConversation();
        theAgent.registerConversation(this);
    }

    protected abstract boolean action();

    @Override
    public void doAllActions() {
        while (action()) {
            ;
        }
    }

    @Override
    public AbstractCommunicatingAgent getAgent() {
        return theAgent;
    }

    @Override
    public String getConversationId() {
        return conversationId;
    }

    public String getObservableIdentifier() {
        return theAgent.getObservableIdentifier() + "conversation";
    }

    public String getObservableParentIdentifier() {
        return theAgent.getObservableIdentifier();
    }

    @Override
    public ACLMessage nextMessage() {
        return theAgent.getACLMessageFromConversation(conversationId);
    }

    /*
     * public void send(ACLMessage message) { // establish the conversation at
     * the first send. if (conversationId == null) { conversationId =
     * message.getConversation(); } else {
     * message.setConversation(conversationId); } theAgent.send(message); }
     */
}
