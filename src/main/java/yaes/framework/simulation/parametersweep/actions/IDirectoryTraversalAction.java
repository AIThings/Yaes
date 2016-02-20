/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 28, 2009
 
   yaes.framework.simulation.parametersweep.actions.IDirectoryTraversalAction
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep.actions;

import java.io.File;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.actions.IDirectoryTraversalAction</code>
 * This is the function for the directory
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public interface IDirectoryTraversalAction {
    void action(File file);
}
