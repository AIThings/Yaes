/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jan 13, 2013
 
   yaestest.ui.plot.testMatlabUtil
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
*/
package yaestest.ui.plot;

import org.junit.Test;

import yaes.ui.plot.MatlabUtil;
import yaes.ui.text.TextUi;

/**
 * 
 * <code>yaestest.ui.plot.testMatlabUtil</code> 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testMatlabUtil {

    @Test
    public void testGetMatlabMatrix() {
        int sizex = 100;
        int sizey = 50;
        double d[][] = new double[sizex][sizey];
        for(int i = 0; i != sizex; i++) {
            for(int j = 0; j != sizey; j++) {
                d[i][j] = i * i + 4 * j * j;
            }
        }
        String tmp = MatlabUtil.getMatlabMatrix("var", d);
        TextUi.println(tmp);
    }
    
}
