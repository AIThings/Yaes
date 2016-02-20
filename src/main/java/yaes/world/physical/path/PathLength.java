/*
   This file is part of the Yet Another Extensible Simulator
   Created on Sep 25, 2004
 
   yaes.world.physical.path.PathTraversal
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */package yaes.world.physical.path;

import yaes.framework.algorithm.search.TreeSearchNode;
import yaes.world.physical.location.Location;
import yaes.world.physical.map.MapHelper;

/**
 * 
 * 
 * <code>yaes.world.physical.path.PathLength</code>
 * 
 * An implementation of the AbstractPathCost which is based on the length of the
 * path
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PathLength extends AbstractPathCost {
    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.environment.maps.AbstractPathCost#cost(simulation.environment
     * .maps.Path)
     */
    @Override
    public double cost(Path path) {
        Location previous = null;
        double cost = 0.0;
        for (final Location currentLocation : path.getLocationList()) {
            if (previous != null) {
                cost += MapHelper.distance(currentLocation, previous);
            }
            previous = currentLocation;
        }
        // cost = path.getLocationList().size();
        return cost;
    }

    public double cost(Path path, Location startLoc, Location endLoc) {
        Location previous = null;
        double cost = 0.0;
        boolean startCost = false;
        for (Location currentLocation : path.getLocationList()) {
            if (!startCost && (previous != null)
                    && startLoc.locationWithIn(previous, currentLocation)) {
                startCost = true;
            }
            if (startCost) {
                cost += MapHelper.distance(currentLocation, previous);
            }
            if ((previous != null)
                    && endLoc.locationWithIn(previous, currentLocation)) {
                startCost = false;
                break;
            }
            previous = currentLocation;
        }
        // cost = path.getLocationList().size();
        return cost;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.environment.maps.AbstractPathCost#cost(simulation.algorithms
     * .treesearch.TreeSearchNode)
     */
    @Override
    public double cost(TreeSearchNode node) {
        TreeSearchNode currentNode = node;
        Location previous = null;
        double cost = 0.0;
        while (true) {
            final Location currentLocation = (Location) currentNode.getObject();
            if (previous != null) {
                cost += MapHelper.distance(currentLocation, previous);
            }
            previous = currentLocation;
            currentNode = currentNode.getParent();
            if (currentNode == null) {
                return cost;
            }
        }
    }
}
