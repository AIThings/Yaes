/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 30, 2009
 
   yaestest.world.physical.path.testPPMTraversal
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.world.physical.path;

import org.junit.Test;

import yaes.ui.text.TextUi;
import yaes.world.physical.location.Location;
import yaes.world.physical.path.PPMTraversal;
import yaes.world.physical.path.PlannedPath;
import yaes.world.physical.path.ProgrammedPathMovement;

/**
 * 
 * <code>yaestest.world.physical.path.testPPMTraversal</code> tests the
 * Programmed Path Movement traversal
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testPPMTraversal {

    @Test
    public void ppmTraversal() {
        ProgrammedPathMovement ppm = new ProgrammedPathMovement();
        ppm.addSetLocation(new Location(50, 50));

        final PlannedPath path = new PlannedPath();
        path.setSource(new Location(50, 50));
        path.addLocation(new Location(50, 50));
        path.addLocation(new Location(100, 100));
        path.setDestination(new Location(100, 100));
        ppm.addFollowPath(path, 10);

        ppm.addWaitFor(8);

        final PlannedPath path2 = new PlannedPath();
        path2.setSource(new Location(100, 100));
        path2.addLocation(new Location(100, 100));
        path2.addLocation(new Location(80, 80));
        path2.setDestination(new Location(80, 80));
        ppm.addFollowPath(path2, 2);

        ppm.addWaitUntil(100);

        final PlannedPath path3 = new PlannedPath();
        path3.setSource(new Location(80, 80));
        path3.addLocation(new Location(80, 80));
        path3.addLocation(new Location(70, 70));
        path3.setDestination(new Location(70, 70));
        ppm.addFollowPath(path3, 1);

        PPMTraversal ppmtraversal = new PPMTraversal(ppm, 0);

        for (double time = 0; time != 150; time++) {
            Location l = ppmtraversal.getLocation(time);
            TextUi.println(time + "\t\t" + l);
        }

    }

}
