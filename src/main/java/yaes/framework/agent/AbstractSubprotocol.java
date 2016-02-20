package yaes.framework.agent;

public abstract class AbstractSubprotocol implements ISubprotocol {
    protected AbstractCommunicatingAgent agent;
    private final String                 name;

    public AbstractSubprotocol(String name, AbstractCommunicatingAgent agent) {
        this.name = name;
        this.agent = agent;
    }

    @Override
    public String getName() {
        return name;
    }
}
