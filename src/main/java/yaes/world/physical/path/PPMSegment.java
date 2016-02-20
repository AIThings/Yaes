/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 30, 2009
 
   yaes.world.physical.path.PPMSegment
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.path;

import java.io.Serializable;

import yaes.world.physical.location.Location;

public class PPMSegment implements Serializable {
    enum PPMSegmentType {
        PATH_FOLLOW, SET_LOCATION, WAIT_FOR, WAIT_UNTIL
    }

    /**
     * 
     */
    private static final long serialVersionUID = 4946167409630921209L;

    Location                  location;
    PlannedPath               plannedPath;
    double                    speed;
    double                    time             = 0;
    PPMSegmentType            type;
    
    /**
     * To string function
     * @return
     */
    @Override
	public String toString() {
        String retval = "";
        switch(type) {
        case PATH_FOLLOW: {
            retval += "PPMS:PathFollow " + plannedPath + " with speed" + speed;
            break;
        }
        case SET_LOCATION: {
            retval += "PPMS:SetLocation " + location;
            break;
        }
        case WAIT_FOR: {
            retval += "PPMS:WaitFor " + time;
            break;
        }
        case WAIT_UNTIL: {
            retval += "PPMS:WaitUntil " + time;
            break;
        }
		default:
			break;
        }
        return retval;
    }
}