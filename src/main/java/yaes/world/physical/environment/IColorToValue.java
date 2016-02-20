package yaes.world.physical.environment;

import java.awt.Color;

/**
 * An interface for translation functions which translate a color pixel into 
 * double values
 * 
 * @author Lotzi Boloni
 *
 */
public interface IColorToValue {

	double value(Color color);
	
}
