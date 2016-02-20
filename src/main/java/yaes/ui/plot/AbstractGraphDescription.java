/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 18, 2008
 
   yaes.framework.simulation.IGraphDescription
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.plot;

import java.io.File;
import java.io.IOException;

import yaes.framework.simulation.parametersweep.ExperimentPackage;
import yaes.util.FileWritingUtil;

/**
 * <code>yaes.framework.simulation.AbstractGraphDescription</code>
 * 
 * This is the ancestor class to all the classes which describe a graph to be
 * generated in Matlab
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public abstract class AbstractGraphDescription {
    public static final String  lineType[] = { "-", "--", ":", "-." };

    public static final String  sign[]     = { "*", "+", "s", "x", "d", ".",
            "o", "v", "^", "<", ">", "p", "h", };

    protected ExperimentPackage experimentPack;
    private String              title      = "";
    private String              xLabel     = "";
    private String              yLabel     = "";
    private String              zLabel     = "";

    /**
     * Constructor, initialize all the values
     * 
     * @param title
     * @param xLabel
     * @param yLabel
     * @param zLabel
     */
    public AbstractGraphDescription(String title, String xLabel, String yLabel,
            String zLabel, ExperimentPackage experimentPack) {
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.zLabel = zLabel;
        this.experimentPack = experimentPack;
    }

    /**
     * Generates a graph for matlab
     * 
     * @param xsize - the size of the graph
     * @param ysize - the size of the graph
     * @return
     */
    public abstract String generate(int xsize, int ysize);

    /**
     * Generates a graph and writes it to a file
     * 
     * @param file
     * @throws IOException
     */
    public void generate(File file, int xsize, int ysize) {
        String value = generate(xsize, ysize);        
        FileWritingUtil.writeToTextFile(file, value);
    }

    /**
     * @return
     */
    public String generateErrorBarPlot(int xsize, int ysize) {
        return "%%% No errorbar plot generated\n";
    }

    /**
     * Generates a graph and writes it to a file
     * 
     * @param file
     * @throws IOException
     */
    public void generateErrorBarPlot(File file, int xsize, int ysize) {
        String value = generateErrorBarPlot(xsize, ysize);
        FileWritingUtil.writeToTextFile(file, value);
    }

    /**
     * @param xsize - the size of the graph
     * @param ysize - the size of the graph
     * @return
     */
    public abstract String generateOctave(int xsize, int ysize);

    /**
     * Generates a graph and writes it to a file
     * 
     * @param file
     * @throws IOException
     */
    public void generateOctave(File file, int xsize, int ysize) {
        String value = generateOctave(xsize, ysize);
        FileWritingUtil.writeToTextFile(file, value);
    }

    /**
     * @return the experimentPack
     */
    public ExperimentPackage getExperimentPack() {
        return experimentPack;
    }

    /**
     * Generates the linestyle component
     * 
     * @param count
     * @return
     */
    public static String getLineStyle(int count) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(",'LineStyle','"
                + AbstractGraphDescription.lineType[count
                        % AbstractGraphDescription.lineType.length] + "'");
        if (count >= AbstractGraphDescription.lineType.length) {
            buffer.append(",'LineWidth',2");
        }
        return buffer.toString();
    }

    protected String getLineStyleOctave(int count) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(",'" + count + "'");
        return buffer.toString();
    }

    public String getTitle() {
        return title;
    }

    public String getXLabel() {
        return xLabel;
    }

    public String getYLabel() {
        return yLabel;
    }

    /**
     * @return the zLabel
     */
    public String getzLabel() {
        return zLabel;
    }
}
