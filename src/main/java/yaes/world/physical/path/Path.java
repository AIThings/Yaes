/*
   This file is part of the Yet Another Extensible Simulator
   Created on Sep 25, 2004
 
   yaestest.world.physical.path.testPathTraversal
 
   Copyright (c) 2004-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import yaes.world.physical.location.Location;

/**
 * <code>yaes.world.physical.path.Path</code> The implementation of a locations.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class Path implements Serializable {
    private static final long      serialVersionUID = 3616733768275211312L;
    protected final List<Location> locations;

    public Path() {
        locations = new ArrayList<>();
    }

    /**
     * Adds a location to the end of the locations
     * 
     * @param location
     */
    public synchronized void addLocation(Location location) {
        locations.add(location);
    }

    public boolean containsLocation(Location loc) {
        return locations.contains(loc);
    }

    public boolean containsLocationValue(Location loc) {
        for (final Location location : locations) {
            if (location.equals(loc)) {
                return true;
            }
        }
        return false;
    }

    public Location getLocationAt(int index) {
        return locations.get(index);
    }

    /**
     * Returns the locations as a list of locations
     * 
     * @return
     */
    public List<Location> getLocationList() {
        return locations;
    }

    public Location getNextLocation(Location loc, int index) {
        int locIndex = -1;
        locIndex = indexOfLocation(loc);
        if ((locIndex >= 0) && (locIndex + index < getPathSize())
                && (locIndex + index > 0)) {
            return getLocationAt(locIndex + index);
        }
        return null;
    }

    public int getPathSize() {
        return locations.size();
    }

    /**
     * Returns a random location on the locations
     * 
     * @param random
     *            the random generator to be used
     * @return
     */
    public Location getRandomLocation(Random random) {
        final int pathSize = locations.size() - 1;
        if (pathSize < 0) {
            return null;
        }
        final int randomLocation = random.nextInt(pathSize);
        return getLocationAt(randomLocation);
    }

    public int indexOfLocation(Location loc) {
        return locations.indexOf(loc);
    }

    @Override
    public String toString() {
        String temp = "";
        for (final Location element : getLocationList()) {
            temp += element;
        }
        return temp;
    }
}
