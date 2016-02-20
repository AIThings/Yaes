/*
   This file is part of the Yet Another Extensible Simulator
   Created on: 2004
 
   yaes.world.physical.path.PathTraversal
 
   Copyright (c) 2004-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import yaes.world.physical.location.Location;
import yaes.world.physical.map.IMap;
import yaes.world.physical.pathplanning.cache.PathCache;

/**
 * 
 * 
 * <code>yaes.world.physical.path.PathPlanner</code> A path planner which is
 * using a precalcuated plan cache
 * 
 * @author Majid Ali Khan
 */
public class PathPlanner {
    private static PathPlanner instance;

    public static PathPlanner getInstance() {
        if (PathPlanner.instance == null) {
            PathPlanner.instance = new PathPlanner();
        }
        return PathPlanner.instance;
    }

    protected PathPlanner() {
    }

    // plan for path assuming only white region is accessible
    public PlannedPath planPath(IMap map, Location from, Location to) {
        final PlannedPath path = new PlannedPath(from, to);
        final boolean whiteAccessible = true;
        PathCache.findPath(path, map, whiteAccessible);
        return path;
    }
}
