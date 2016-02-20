package yaes.framework.agent.communicationmodel;

import yaes.framework.agent.ICommunicatingAgent;
import yaes.world.World;

public abstract class AbstractCommunicationModel {
    private double                communicationLoss;
    private int                   communicationRange;
    protected ICommunicatingAgent theAgent;
    protected World               theWorld;

    public AbstractCommunicationModel() {
    }

    public abstract boolean canDeliverMessage(ICommunicatingAgent agentLoc);

    public double getCommunicationLoss() {
        return communicationLoss;
    }

    public int getCommunicationRange() {
        return communicationRange;
    }

    public ICommunicatingAgent getTheAgent() {
        return theAgent;
    }

    public World getTheWorld() {
        return theWorld;
    }

    public void setCommunicationLoss(double communicationLoss) {
        this.communicationLoss = communicationLoss;
    }

    public void setCommunicationRange(int communicationRange) {
        this.communicationRange = communicationRange;
    }

    public void setTheAgent(ICommunicatingAgent theAgent) {
        this.theAgent = theAgent;
    }

    public void setTheWorld(World theWorld) {
        this.theWorld = theWorld;
    }
}
