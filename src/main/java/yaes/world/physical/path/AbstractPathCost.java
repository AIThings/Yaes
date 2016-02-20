/*
   This file is part of the Yet Another Extensible Simulator
   Created on Sep 25, 2004
 
   yaestest.world.physical.path.testPathTraversal
 
   Copyright (c) 2004-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import yaes.framework.algorithm.search.TreeSearchNode;

/**
 * An abstract class which is used by the path planning algorithms to find a
 * shortest path.
 * 
 * <code>yaes.world.map.AbstractPathCost</code>
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public abstract class AbstractPathCost {
    public abstract double cost(Path path);
    public abstract double cost(TreeSearchNode node);
}
