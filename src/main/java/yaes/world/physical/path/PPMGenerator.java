/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Dec 30, 2010
 
   yaes.world.physical.path.PPMGenerator
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import java.awt.geom.Rectangle2D;
import java.util.Random;

import yaes.world.physical.location.Location;

/**
 * 
 * <code>yaes.world.physical.path.PPMGenerator</code>
 * 
 * Programmed path movement generator. Generates several standard movement types
 * (in general, useful for generating intruders
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PPMGenerator {

    /**
     * Follow a path with constant speed
     * 
     * @param area
     * @param time
     * @param speed
     * @param speedStdDev
     * @param waitTime
     * @param waitTimeStdDev
     * @param random
     * @return
     */
    public static ProgrammedPathMovement followPathWithConstantSpeed(
            double speed, double speedStdDev, Random random,
            Location... locations) {
        double realSpeed = PPMGenerator
                .getNextSpeed(random, speed, speedStdDev);
        ProgrammedPathMovement ppm = new ProgrammedPathMovement();
        ppm.addSetLocation(locations[0]);
        PlannedPath path = PathGenerator.createPathFromNodes(locations);
        ppm.addFollowPath(path, realSpeed);
        return ppm;
    }

    /**
     * 
     * A generator of speed, with some guards, trying to prevent very slow
     * occurences
     * 
     * @param random
     * @param speed
     * @param speedStdDev
     * @return
     */
    public static double getNextSpeed(Random random, double speed,
            double speedStdDev) {
        double retval = 0.0;
        while (retval < 0.1 * speed) {
            retval = speed + speedStdDev * random.nextGaussian();
        }
        return speed;
    }

    /**
     * 
     * A generator of the next waiting time, with some guards to prevent
     * negative times
     * 
     * @param random
     * @param waitTime
     * @param waitTimeStdDev
     * @return
     */
    public static double getNextWait(Random random, double waitTime,
            double waitTimeStdDev) {
        double retval = -10.0;
        while (retval < 0.0) {
            retval = waitTime + waitTimeStdDev * random.nextGaussian();
        }
        return waitTime;
    }

    /**
     * 
     * Creates a random waypoint type movement inside the area. The generated
     * path will go for time time
     * 
     * The entity will move with a gaussian random speed, then wait for a
     * gaussian random time etc.
     * 
     * @param area
     * @param time
     * @param speed
     * @param speedStdDev
     * @param waitTime
     * @param waitTimeStdDev
     * @param random
     * @return
     */
    public static ProgrammedPathMovement randomWaypoint(
            Rectangle2D.Double area, double time, double speed,
            double speedStdDev, double waitTime, double waitTimeStdDev,
            Random random) {
        ProgrammedPathMovement retval = new ProgrammedPathMovement();
        double currentTime = 0;
        Location current = PathGenerator.getRandomLocationInArea(random, area);
        retval.addSetLocation(current);
        while (currentTime < time) {
            Location next = PathGenerator.getRandomLocationInArea(random, area);
            PlannedPath path = PathGenerator.createPathFromNodes(current, next);
            double nextSpeed = getNextSpeed(random, speed, speedStdDev);
            retval.addFollowPath(path, nextSpeed);
            current = next;
            currentTime += path.getPathLenght() / nextSpeed;
            double nextWait = getNextWait(random, waitTime, waitTimeStdDev);
            retval.addWaitFor(nextWait);
            currentTime += nextWait;
        }
        return retval;
    }

    
    

    /**
     * 
     * Creates a random waypoint type movement, with the specified length of
     * jumps
     * 
     * The entity will move with a gaussian random speed, then wait for a
     * gaussian random time etc.
     * 
     * @param area
     * @param time
     * @param speed
     * @param speedStdDev
     * @param waitTime
     * @param waitTimeStdDev
     * @param random
     * @return
     */
    public static ProgrammedPathMovement randomWaypointSpecificJumps(
            Rectangle2D.Double area, double time, double speed,
            double speedStdDev, double waitTime, double waitTimeStdDev,
            Random random, double jumpMin, double jumpMax) {
        ProgrammedPathMovement retval = new ProgrammedPathMovement();
        double currentTime = 0;
        Location current = PathGenerator.getRandomLocationInArea(random, area);
        Location next = null;
        retval.addSetLocation(current);
        while (currentTime < time) {
            // chooses a new location which is between jumpMin and jumpMax
            Rectangle2D.Double newarea =
                    new Rectangle2D.Double(current.getX() - jumpMax / 2,
                            current.getY() - jumpMax / 2, jumpMax, jumpMax);
            while (true) {
                next =
                        PathGenerator.getRandomLocationInArea(random, newarea);
                if (next.distanceTo(current) > jumpMin) {
                    break;
                }
            }
            PlannedPath path = PathGenerator.createPathFromNodes(current, next);
            double nextSpeed =
                    PPMGenerator.getNextSpeed(random, speed, speedStdDev);
            retval.addFollowPath(path, nextSpeed);
            current = next;
            currentTime += path.getPathLenght() / nextSpeed;
            double nextWait =
                    PPMGenerator.getNextWait(random, waitTime, waitTimeStdDev);
            retval.addWaitFor(nextWait);
            currentTime += nextWait;
        }
        return retval;
    }

}
