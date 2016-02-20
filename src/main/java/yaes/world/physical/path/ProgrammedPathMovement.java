/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 30, 2009
 
   yaes.world.physical.path.ProgrammedPathMovement
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import yaes.ui.format.Formatter;
import yaes.world.physical.location.Location;
import yaes.world.physical.path.PPMSegment.PPMSegmentType;

/**
 * 
 * <code>yaes.world.physical.path.ProgrammedPathMovement</code>
 * 
 * describes a programmed path movement object
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ProgrammedPathMovement implements Serializable {

    private static final long serialVersionUID = 6046546731888636542L;
    List<PPMSegment>          segments         = new ArrayList<>();

    /**
     * Adds a path following segment
     * 
     * @param plannedPath
     * @param speed
     */
    public PPMSegment addFollowPath(PlannedPath plannedPath, double speed) {
        PPMSegment ppms = new PPMSegment();
        ppms.type = PPMSegmentType.PATH_FOLLOW;
        ppms.plannedPath = plannedPath;
        ppms.speed = speed;
        segments.add(ppms);
        return ppms;
    }

    /**
     * Sets the location to a certain point
     * 
     * @param location
     */
    public PPMSegment addSetLocation(Location location) {
        PPMSegment ppms = new PPMSegment();
        ppms.type = PPMSegmentType.SET_LOCATION;
        ppms.location = location;
        segments.add(ppms);
        return ppms;
    }

    /**
     * Adds a segment which waits for a certain amount of time (time is in the
     * worlds time unit)
     * 
     * @param time
     */
    public PPMSegment addWaitFor(double time) {
        PPMSegment ppms = new PPMSegment();
        ppms.type = PPMSegmentType.WAIT_FOR;
        ppms.time = time;
        segments.add(ppms);
        return ppms;
    }

    /**
     * Adds a segment which waits for a certain amount of time (time is in the
     * worlds time unit)
     * 
     * @param time
     */
    public PPMSegment addWaitUntil(double time) {
        PPMSegment ppms = new PPMSegment();
        ppms.type = PPMSegmentType.WAIT_UNTIL;
        ppms.time = time;
        segments.add(ppms);
        return ppms;
    }
    
    
    @Override
	public String toString() {
        Formatter fmt = new Formatter();
        fmt.add("ProgrammedPathMovement:");
        fmt.indent();
        for(PPMSegment segment: segments) {
            fmt.add(segment.toString());
        }
        return fmt.toString();
    }
}
