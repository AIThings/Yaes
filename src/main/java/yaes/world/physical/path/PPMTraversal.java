/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 30, 2009
 
   yaes.world.physical.path.PPMTraversal
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import java.io.Serializable;

import yaes.world.physical.location.Location;
import yaes.world.physical.path.PPMSegment.PPMSegmentType;

/**
 * 
 * <code>yaes.world.physical.path.PPMTraversal</code> A traversal of the
 * programmed path
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PPMTraversal implements Serializable {

    /**
     * 
     */
    private static final long      serialVersionUID = -2610805104848435369L;
    private Location               currentLocation;
    private PathTraversal          currentPathTraversal;
    private PPMSegment             currentSegment;
    /**
     * @return the currentSegment
     */
    public PPMSegment getCurrentSegment() {
        return currentSegment;
    }

    private int                    currentSegmentNo = 0;
    private double                 currentTime;
    private boolean                finished         = false;
    private ProgrammedPathMovement ppm;
    private double                 timeShift        = 0;
    private double                 waitStart;

    public PPMTraversal(ProgrammedPathMovement ppm, double timeShift) {
        this.ppm = ppm;
        this.timeShift = timeShift;
        currentSegment = ppm.segments.get(currentSegmentNo);
        if (currentSegment.type == PPMSegmentType.SET_LOCATION) {
            currentLocation = currentSegment.location;
            advance();
        }

    }

    /**
     * Advances in the description
     */
    private void advance() {
        if (finished) {
            return;
        }
        if (currentSegmentNo == ppm.segments.size() - 1) {
            finished = true;
            return;
        }
        currentSegmentNo = currentSegmentNo + 1;
        currentSegment = ppm.segments.get(currentSegmentNo);
        switch (currentSegment.type) {
        case SET_LOCATION:
            // nothing here
            break;
        case WAIT_FOR:
            waitStart = currentTime;
            break;
        case WAIT_UNTIL:
            break;
        case PATH_FOLLOW:
            currentPathTraversal = new PathTraversal(currentSegment.plannedPath);
			break;
		default:
			break;

        }
    }

    /**
     * 
     * Returns the location on a certain path at a certain time
     * 
     * @param time
     * @return
     */
    public Location getLocation(double time) {
        assert time >= currentTime;
        if (time == currentTime) {
            return currentLocation;
        }
        double timePassed = time - currentTime;
        // iterate because over a longer time sequence one can
        // have many segments
        while (true) {
            if (finished) {
                return currentLocation;
            }
            switch (currentSegment.type) {
            case SET_LOCATION:
                currentLocation = currentSegment.location;
                advance();
                break;
            case WAIT_FOR:
                if (time > waitStart + currentSegment.time) {
                    currentTime = waitStart + currentSegment.time;
                    advance();
                    break;
                } else {
                    currentTime = time;
                    return currentLocation;
                }
            case WAIT_UNTIL:
                if (time > currentSegment.time + timeShift) {
                    currentTime = currentSegment.time + timeShift;
                    advance();
                    break;
                } else {
                    currentTime = time;
                    return currentLocation;
                }
            case PATH_FOLLOW: {
                double distanceTraversed = currentSegment.speed * timePassed;
                double distanceLeft = currentSegment.plannedPath
                        .getPathLenght()
                        - currentPathTraversal.getTraversedDistance();
                if (distanceTraversed < distanceLeft) {
                    currentTime = time;
                    currentLocation = currentPathTraversal
                            .travel(distanceTraversed);
                    return currentLocation;
                } else {
                    currentLocation = currentPathTraversal
                            .travel(distanceTraversed);
                    currentTime = currentTime + distanceLeft
                            / distanceTraversed * timePassed;
                    advance();
                    break;
                }
            }
			default:
				break;

            }
        }
    }
}
