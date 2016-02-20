/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jan 13, 2013
 
   yaestest.ui.plot.testSurfacePlot
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.ui.plot;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import yaes.ui.plot.SurfacePlot;
import yaes.ui.plot.SurfacePlot.SurfaceType;
import yaes.util.DirUtil;

/**
 * 
 * <code>yaestest.ui.plot.testSurfacePlot</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testSurfacePlot {

    public static String outputDirName = "output";

    @Test
    public void test() throws IOException {
        int sizex = 100;
        int sizey = 50;
        double d[][] = new double[sizex][sizey];
        double x[] = new double[sizex];
        double y[] = new double[sizey];
        for (int i = 0; i != sizex; i++) {
            for (int j = 0; j != sizey; j++) {
                d[i][j] = i * i + 4 * j * j;
            }
        }
        for (int i = 0; i != sizex; i++) {
            x[i] = i;
        }
        for (int i = 0; i != sizey; i++) {
            y[i] = i;
        }
        File dir = new File(outputDirName);
        DirUtil.guaranteeDirectory(outputDirName);
        SurfacePlot.createSurfacePlot(SurfaceType.MESH, x, "x", y, "y", d,
                "value", new File(dir, "TestSurfacePlotMesh.m"));
        SurfacePlot.createSurfacePlot(SurfaceType.LIGHTED, x, "x", y, "y", d,
                "value", new File(dir, "TestSurfacePlotLighted.m"));
    }

}
