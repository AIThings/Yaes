package yaes.world.physical.environment;

import java.awt.Color;

/**
 * Implements a linear scale of the color to value
 * @author Lotzi Boloni
 *
 */
public class LinearColorToValue implements IColorToValue {

	private double low;
	private double high;
	private double step;
	
	/**
	 * 
	 * @param low
	 * @param high
	 */
	public LinearColorToValue(double low, double high) {
		this.low = low;
		this.high = high;
		this.step = (high - low) / 255.0;
	}
		
	@Override
	public double value(Color color) {
		int avg = (color.getRed() + color.getBlue() + color.getGreen()) / 3;
		avg = 255 - avg;
		double retval = low + step * avg;		
		return retval;
	}

}
