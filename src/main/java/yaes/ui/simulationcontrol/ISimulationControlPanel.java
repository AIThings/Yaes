/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Apr 22, 2010
 
   yaes.ui.simulationcontrol.ISimulationControlPanel
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.simulationcontrol;

import javax.swing.JPanel;

/**
 * 
 * <code>yaes.ui.simulationcontrol.ISimulationControlPanel</code>
 * 
 * The interface to be implemented by the simulation control panels
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public interface ISimulationControlPanel {
    JPanel getPanel();
}
