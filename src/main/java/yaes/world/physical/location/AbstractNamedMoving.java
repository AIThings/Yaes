/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 27, 2009
 
   yaes.world.physical.location.AbstractNamedMoving
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.world.physical.location;

/**
 * 
 * <code>yaes.world.physical.location.AbstractNamedMoving</code> the abstract
 * class for all the objects which have a name and can move.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class AbstractNamedMoving implements INamedMoving {

    private static final long serialVersionUID = -5740415613108372998L;
    private Location          location;
    private String            name;

    public AbstractNamedMoving(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    /**
     * @return
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param location
     */
    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

}
