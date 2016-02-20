/*
 * Created on Nov 8, 2004
 *
 * 
 **/
package yaes.framework.agent;

import java.io.Serializable;

/**
 * @author Lotzi Boloni
 * 
 */
public interface IMessage extends Serializable {

    double getDeliveryTime();

    String getDestination();

    double getSendingTime();

    void setDeliveryTime(double time);

    void setSendingTime(double time);
}
