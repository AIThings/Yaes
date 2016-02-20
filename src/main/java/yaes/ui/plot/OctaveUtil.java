/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Mar 17, 2009
 
   yaes.ui.plot.OctaveUtil
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import java.io.File;
import java.io.FilenameFilter;
import yaes.util.FileWritingUtil;

/**
 * 
 * <code>yaes.ui.plot.OctaveUtil</code>
 * 
 * Various utilities for Octave code generation - similar but not quite the same
 * as matlab
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class OctaveUtil {
    /**
     * Generates octave the basic graph prefix
     * 
     * @param buffer
     */
    public static String generateBasicGraphPrefix(int xsize, int ysize) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("figure1 = figure('Position', [0 0 " + xsize + " "
                + ysize + "]);\n");
        buffer.append("axes1 = axes('Parent',figure1);\n");
        buffer.append("legend('show')\n");
        buffer.append("hold on\n");
        return buffer.toString();
    }

    /**
     * Generates octave text for basic graph properties
     */
    public static String generateBasicGraphProperties(
            AbstractGraphDescription graphDescription) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("hold on\n");
        if (graphDescription.getTitle() != null) {
            buffer.append("title('" + graphDescription.getTitle() + "')\n");
        }
        buffer.append("hold on\n");
        buffer.append("xlabel('"
                + graphDescription.getExperimentPack().getVariableDescription(
                        graphDescription.getXLabel()) + "')\n");
        buffer.append("hold on\n");
        buffer.append("ylabel('"
                + graphDescription.getExperimentPack().getVariableDescription(
                        graphDescription.getYLabel()) + "')\n");
        return buffer.toString();
    }

    /**
     * Generates a file for processing all the figures in a directory and
     * convert them to pdf...
     */
    public static void generateProcessAll(File dir) {
        String outputName = "processAllFiles.m";
        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {
            @Override
			public boolean accept(File dir, String name) {
                return name.endsWith(".m");
            }
        };
        String matlabFiles[] = dir.list(filter);
        StringBuffer buf = new StringBuffer();
        for (String fileName : matlabFiles) {
            String command = fileName.substring(0, fileName.length() - 2);
            if (command.startsWith("process")) {
                continue;
            }
            buf.append(command + ";\n");
            buf.append("print " + command + ".pdf;\n");
            buf.append("print " + command + ".jpg;\n");
            buf.append("close\n");
        }
        FileWritingUtil.writeToTextFile(new File(dir, outputName),
                buf.toString());
    }

}
