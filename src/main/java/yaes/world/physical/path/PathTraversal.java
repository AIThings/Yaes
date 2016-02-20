/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 27, 2009
 
   yaes.world.physical.path.PathTraversal
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import java.io.Serializable;

import yaes.world.physical.location.Location;
import yaes.world.physical.map.MapHelper;

/**
 * 
 * <code>yaes.world.physical.path.PathTraversal</code>
 * 
 * Implements support for traversing a path
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PathTraversal implements Serializable {
    private static final long serialVersionUID  = 828164596783437299L;
    private Location          currentLocation;
    private int               lastCheckPoint;
    private boolean           moreToGo;
    private PlannedPath       path;
    private double            traversedDistance = 0;

    /**
     * Creates a path travelsal from a path
     * 
     * @param path
     */
    public PathTraversal(PlannedPath path) {
        this.path = path;
        this.currentLocation = path.getSource();
        lastCheckPoint = path.indexOfLocation(currentLocation);
        moreToGo = true;
    }

    /**
     * @return the currentLocation
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * @return the path
     */
    public PlannedPath getPath() {
        return path;
    }

    /**
     * @return the traversedDistance
     */
    public double getTraversedDistance() {
        return traversedDistance;
    }

    /**
     * @return the moreToGo
     */
    public boolean moreToGo() {
        return moreToGo;
    }

    /**
     * Return the location obtained after traveling distanceToTravel on the
     * path.
     * 
     * @param distanceToTravel
     * @return the new location
     */
    public Location travel(double distanceToTravel) {
        Location previousLocation = currentLocation;
        double dx = 0.0, dy = 0.0;
        double adjustedX = 0.0, adjustedY = 0.0;
        double distance = 0.0;
        double currentDistance = 0.0;
        traversedDistance = traversedDistance + distanceToTravel;
        // Starting from last check point, go through list of locations and
        // compute distance from current location to that location
        for (int i = lastCheckPoint; i < path.getPathSize(); i++) {
            currentLocation = path.getLocationAt(i);
            currentDistance = MapHelper.distance(previousLocation,
                    currentLocation);
            distance += currentDistance;
            // If distance to a location is larger than distance to travel then
            // mark this location as next check point and use adjusted x,y
            // values to determine a middle location
            // that is exact distance to be travelled away from current location
            if (distance >= distanceToTravel) {
                lastCheckPoint = i;
                dx = currentLocation.getX() - previousLocation.getX();
                dy = currentLocation.getY() - previousLocation.getY();
                adjustedX = dx
                        * (currentDistance - distance + distanceToTravel)
                        / currentDistance;
                adjustedY = dy
                        * (currentDistance - distance + distanceToTravel)
                        / currentDistance;
                currentLocation = new Location(previousLocation.getX()
                        + adjustedX, previousLocation.getY() + adjustedY);

                return currentLocation;
            } else if (i == path.getPathSize() - 1) {
                traversedDistance = path.getPathLenght();
                moreToGo = false;
                lastCheckPoint = i;
                return currentLocation;
            }
            previousLocation = currentLocation;
        }
        throw new Error("We should have never gotten here!!!");
    }

}
