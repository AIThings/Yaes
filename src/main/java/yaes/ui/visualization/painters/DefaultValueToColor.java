/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 27, 2012
 
   yaes.ui.visualization.painters.DefaultValueToColor
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
*/
package yaes.ui.visualization.painters;

import java.awt.Color;

/**
 * 
 * A very simple implementation of the value to color interface
 * 
 * <code>yaes.ui.visualization.painters.DefaultValueToColor</code> 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class DefaultValueToColor implements IValueToColor {

	/**
	 * @return
	 */
	@Override
	public float getTransparency() {
        float transparency = 0.2f;
		return transparency;
	}

	/**
	 * If the value is 50 green, if above magenta
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public Color getColor(double value) {        
        Color color = null;
        if (value <= 50.0) {
            color = Color.white;
        } else {
            color = Color.black;
        }
        return color;
	}

	
	
}
