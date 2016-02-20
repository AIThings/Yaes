package yaes.world.physical.map;

import java.io.Serializable;

/**
 * Interface for a field, such as a sensor network etc. Basis for Maps. Many of
 * the simple movement patterns are taking an IField as the parameter
 * 
 * @author Lotzi Boloni
 * 
 */
public interface IField extends Serializable {
    double getXHigh(); // the y coordinate

    double getXLow(); // the lowest x coordinate

    double getYHigh();

    double getYLow();
}
