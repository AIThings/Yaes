/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 27, 2009
 
   yaestest.world.physical.path.testPathTraversal
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.world.physical.path;

import org.junit.Test;

import yaes.ui.text.TextUi;
import yaes.world.physical.location.Location;
import yaes.world.physical.path.PathTraversal;
import yaes.world.physical.path.PlannedPath;

/**
 * 
 * <code>yaestest.world.physical.path.testPathTraversal</code> unit test for the
 * PathTraversal object
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testPathTraversal {

    private PlannedPath createCustomPath() {
        // (27.0, 271.0)(28.0, 270.0)(29.0, 269.0)(30.0, 268.0)(31.0, 267.0)
        final PlannedPath path = new PlannedPath();
        path.setSource(new Location(27.0, 271.0));
        path.addLocation(new Location(27.0, 271.0));
        path.addLocation(new Location(28.0, 270.0));
        path.addLocation(new Location(29.0, 269.0));
        path.addLocation(new Location(30.0, 268.0));
        path.setDestination(new Location(30.0, 268.0));
        return path;
    }

    @Test
    public void testTraversal() {
        PlannedPath path = createCustomPath();
        PathTraversal traversal = new PathTraversal(path);
        TextUi.println(traversal.getCurrentLocation());
        while (traversal.moreToGo()) {
            TextUi.println(traversal.travel(1));
        }
        TextUi.println("We are at the destination");
        TextUi.println(traversal.travel(1));
        TextUi.println(traversal.travel(1));
        TextUi.println(traversal.travel(1));
    }

}
