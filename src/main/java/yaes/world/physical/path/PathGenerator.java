/*
   This file is part of the Yet Another Extensible Simulator
   Created on: 2007
 
   yaes.world.physical.path.PathTraversal
 
   Copyright (c) 2007-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

import yaes.world.physical.location.Location;
import yaes.world.physical.map.MapHelper;

/**
 * 
 * 
 * <code>yaes.world.physical.path.PathGenerator</code>
 * 
 * Generates several types of standard paths.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PathGenerator {

    /**
     * Creates a planned path from a list of nodes, ready to use in a vehicle.
     * It assumes that the current position is the start.
     * 
     * @param locations
     * @return
     */
    public static PlannedPath createPathFromNodes(List<Location> locations) {
        Location beginning = locations.get(0);
        Location end = locations.get(locations.size() - 1);
        PlannedPath path = new PlannedPath(beginning, end);
        for (Location location : locations) {
            path.addLocation(location);
        }
        path.setSource(beginning);
        return path;
    }

    /**
     * Creates a planned path the nodes passed as a parameter
     * 
     * @param locations
     * @return
     */
    public static PlannedPath createPathFromNodes(Location... locations) {
        Location beginning = locations[0];
        Location end = locations[locations.length - 1];
        PlannedPath path = new PlannedPath(beginning, end);
        for (Location location : locations) {
            path.addLocation(location);
        }
        path.setSource(beginning);
        path.setDestination(end);
        return path;
    }

    /**
     * Creates a random waypoint path of (at least) the specified lenght
     * 
     * @param random
     * @param rect
     * @param length
     * @return
     */
    public static PlannedPath createRandomWaypointPathByLength(Random random,
            Rectangle2D rect, double length) {
        final PlannedPath path = new PlannedPath();
        final Location src = PathGenerator
                .getRandomLocationInArea(random, rect);
        path.setSource(src);
        double currentLength = 0;
        Location current = src;
        while (true) {
            final Location nxt = PathGenerator.getRandomLocationInArea(random,
                    rect);
            path.addLocation(nxt);
            currentLength = currentLength + nxt.distanceTo(current);
            current = nxt;
            if (currentLength > length) {
                break;
            }
        }
        final Location dest = path.getLocationAt(path.getPathSize() - 1);
        path.setDestination(dest);
        path.setSource(path.getLocationAt(0));
        return path;
    }

    /**
     * Creates a random waypoint path with the specified number of segments.
     * 
     * @param random
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     * @param segments
     * @return
     */
    public static PlannedPath createRandomWaypointPathBySegments(Random random,
            Rectangle2D rect, int segments) {
        final PlannedPath path = new PlannedPath();
        final Location src = PathGenerator
                .getRandomLocationInArea(random, rect);
        path.setSource(src);
        for (int i = 0; i != segments; i++) {
            final Location nxt = PathGenerator.getRandomLocationInArea(random,
                    rect);
            path.addLocation(nxt);
        }
        final Location dest = path.getLocationAt(path.getPathSize() - 1);
        path.setDestination(dest);
        path.setSource(path.getLocationAt(0));
        return path;
    }

    /**
     * Creates a winding path
     * 
     * @param xStart
     * @param yStart
     * @param xRun
     * @param yRun
     * @param numOfWinds
     * @return
     */
    public static PlannedPath createWindingPath(double xStart, double yStart,
            int xRun, int yRun, int numOfWinds, int repetitions) {
        final PlannedPath path = new PlannedPath();
        final Location src = new Location(xStart, yStart);
        path.setSource(src);
        for (int rep = 0; rep != repetitions; rep++) {
            double x = xStart;
            double y = yStart;
            int xDirection = 0;
            for (int i = 0; i < numOfWinds; i++) {
                // increase or decrease x based on current direction
                for (int j = 0; j < xRun; j++) {
                    path.addLocation(new Location(x, y));
                    if (xDirection == 0) {
                        x += 1;
                    } else {
                        x -= 1;
                    }
                }
                // add y location unless it is the last path line
                if (i < numOfWinds - 1) {
                    for (int j = 0; j < yRun; j++) {
                        path.addLocation(new Location(x, y));
                        y += 1;
                    }
                }
                // Switch x direction
                if (xDirection == 0) {
                    xDirection = 1;
                } else {
                    xDirection = 0;
                }
            }
        }
        final Location dest = path.getLocationAt(path.getPathSize() - 1);
        path.setDestination(dest);
        return path;
    }

    private static Location findClosestNeighbor(Location fromLoc, Location toLoc) {
        Location nextLoc = null, loc = null;
        final int xValue = (int) fromLoc.getRoundedLocation().getX();
        final int yValue = (int) fromLoc.getRoundedLocation().getY();
        double minDistance = Double.MAX_VALUE;
        double currentDistance = 0.0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0) && (j == 0)) {
                    continue;
                }
                loc = new Location(xValue + i, yValue + j);
                currentDistance = MapHelper.distance(loc, toLoc);
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    nextLoc = loc;
                }
            }
        }
        return nextLoc;
    }

    /**
     * This was written by Yi, Majid or Linus - it appears to create
     * intermediary values step by step.
     * 
     * It creates problems, and is unnecessary.
     * 
     * @param startLoc
     * @param endLoc
     * @return
     */
    public static PlannedPath generateLinearPath(Location startLoc,
            Location endLoc) {
        final PlannedPath path = new PlannedPath();
        path.addLocation(startLoc);
        path.setSource(startLoc);
        Location nextLoc = startLoc;
        while (!nextLoc.equals(endLoc)) {
            nextLoc = PathGenerator.findClosestNeighbor(nextLoc, endLoc);
            path.addLocation(nextLoc);
        }
        path.setDestination(endLoc);
        return path;
    }

    public static PlannedPath generateSinosidalPath() {
        double w = 0.0;
        final double dx = 2 * Math.PI / 50.0;
        double tmp = 0.0;
        final double f = 1;
        final double x_init = 20.0;
        final double y_init = 40.0;
        final PlannedPath path = new PlannedPath();
        Location loc = null;
        for (int i = 1; i <= 50; i++) {
            w = i * dx * f;
            tmp = 10 * Math.sin(w);
            // System.out.println(i*dx + "\t\t" + tmp);
            loc = new Location(x_init + i * dx, y_init + tmp);
            path.addLocation(loc);
            if (i == 1) {
                path.setSource(loc);
            }
            if (i == 50) {
                path.setDestination(loc);
            }
        }
        return path;
    }

    /**
     * Creates a zig-zag path???
     * 
     * @return
     */
    public static PlannedPath generateZigZagPath() {
        final PlannedPath path = new PlannedPath();
        final int init_x = 20;
        final int init_y = 20;
        int x = 0;
        Location loc = new Location(init_x, init_y);
        path.setSource(loc);
        for (int i = 1; i < 100; i++) {
            path.addLocation(loc);
            if (i % 2 == 0) {
                x = init_x;
            } else {
                x = init_x + 1;
            }
            loc = new Location(x, init_y + i);
        }
        path.setDestination(loc);
        return path;
    }

    /**
     * Returns a random point in a certain rectangle.
     * 
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     * @return
     */
    public static Location getRandomLocationInArea(Random random,
            Rectangle2D rect) {
        final double xRange = rect.getWidth();
        final double yRange = rect.getHeight();
        final double x = rect.getMinX() + xRange * random.nextDouble();
        final double y = rect.getMinY() + yRange * random.nextDouble();
        return new Location(x, y);
    }
}
