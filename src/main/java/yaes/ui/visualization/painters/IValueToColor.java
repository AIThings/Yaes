/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 27, 2012
 
   yaes.ui.visualization.painters.IValueToColor
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
*/
package yaes.ui.visualization.painters;

import java.awt.Color;

/**
 * 
 * This interface allows the specification of how a specific value 
 * (for instance in the Environment) will be mapped to a color 
 * (either a degree or a false color or something) and a transparency
 * value
 * 
 * <code>yaes.ui.visualization.painters.IValueToColor</code> 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public interface IValueToColor {

	float getTransparency();
	Color getColor(double value);
	
}
