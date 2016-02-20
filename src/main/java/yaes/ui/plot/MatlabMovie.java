/*
   This file is part of the Yet Another Extensible Simulator
 
   Created on: August 1, 2008
 
   yaestest.ui.plot.testTimeSeriesPlot
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Creates a movie of matlab graph sequences
 * 
 * <code>yaes.ui.plot.MatlabMovie</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class MatlabMovie {

    private int    count = 0;
    @SuppressWarnings("unused")
    private File   directory;
    private String fileRoot;

    public MatlabMovie(String fileRoot, File directory) {
        this.fileRoot = fileRoot;
        this.directory = directory;
    }

    /**
     * 
     * Adds a movie scene. The passed parameter is the matlab code which
     * generates the graph
     * 
     * @param matlabGenerator
     * @throws IOException
     */
    public void addMovieScene(String matlabGenerator) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(matlabGenerator);
        String fileName = String.format("%s_%04d.jpg", fileRoot, count);
        buffer.append("print(gcf, '-djpeg', '" + fileName + "')\n");
        buffer.append("close\n");
        String matlabFileName = String.format("%s_%04d.m", fileRoot, count);
        FileWriter fw = new FileWriter(matlabFileName);
        fw.append(buffer.toString());
        fw.close();
        count++;
    }

    /**
     * Creates the matlab file which calls the other matlab files etc.
     * 
     * @throws IOException
     */
    public void createGenerator() throws IOException {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i != count; i++) {
            String matlabCommand = String.format("%s_%04d", fileRoot, i);
            buffer.append(matlabCommand + ";\n");
        }
        String matlabFileName = fileRoot + ".m";
        FileWriter fw = new FileWriter(matlabFileName);
        fw.append(buffer.toString());
        fw.close();
        // now for the batch file
        StringBuffer mencoderCommand = new StringBuffer();
        mencoderCommand
                .append("\"\\program files\\mplayer\\mencoder.exe\" \"mf://"
                        + fileRoot + "_*.jpg\"");
        mencoderCommand
                .append("-mf fps=10 -ovc lavc -lavcopts vcodec=msmpeg4v2:vbitrate=800");
        mencoderCommand.append("-o " + fileRoot + ".avi");
        String mencoderFileName = fileRoot + "_generate.bat";
        fw = new FileWriter(mencoderFileName);
        fw.append(mencoderCommand.toString());
        fw.close();
    }

}
