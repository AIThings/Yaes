/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.agent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Lotzi Boloni
 * 
 *         A generic implementation of a message.
 * 
 */
public class GenericMessage implements IMessage {
    /**
     * 
     */
    private static final long             serialVersionUID = -7457369713909823063L;
    private double                        deliveryTime;
    private String                        destination;
    private double                        sendingTime;
    private final HashMap<String, Object> values           = new HashMap<String, Object>();

    /**
     * @param destination
     */
    public GenericMessage(String destination) {
        super();
        this.destination = destination;
    }

    @Override
    public double getDeliveryTime() {
        return deliveryTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see yaes.framework.agent.IMessage#getDestination()
     */
    @Override
    public String getDestination() {
        return destination;
    }

    /**
     * Returns a set of the fields of the message
     * 
     * @return
     */
    public Set<String> getFields() {
        return Collections.unmodifiableSet(values.keySet());
    }

    @Override
    public double getSendingTime() {
        return sendingTime;
    }

    public Object getValue(String name) {
        return values.get(name);
    }

    @Override
    public void setDeliveryTime(double deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public void setSendingTime(double sendingTime) {
        this.sendingTime = sendingTime;
    }

    public void setValue(String name, Object o) {
        values.put(name, o);
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("Message to: "
                + getDestination());
        for (final String element : values.keySet()) {
            buffer.append(element + " = " + values.get(element) + "\n");
        }
        return buffer.toString();
    }
}
