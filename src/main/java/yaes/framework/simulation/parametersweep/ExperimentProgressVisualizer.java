/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Sep 28, 2009
 
   yaes.framework.simulation.parametersweep.ExperimentProgressVisualizer
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import yaes.ui.text.TextUiHelper;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.ExperimentProgressVisualizer</code>
 * 
 * The main class for visualizing the progress of an experiment. It can be done
 * with or without access to the experimentpack
 * 
 * TODO: lots of design work here, but for the time being it is simply a
 * toString output and a thread which periodically counts
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ExperimentProgressVisualizer implements Runnable {

    @SuppressWarnings("unused")
    private ExperimentPackage ep;
    private List<File> filesExecuted;
    private List<File> filesExecutedInitial;
    private List<File> filesInput;
    private List<File> filesRemainingToBeExecuted;
    private JFrame frmMain;
    private JTextPane htmlDisplay;
    private JPanel pnlMain;

    private Thread runningThread;
    private boolean stop = false;
    private long timeStarted;

    /**
     * Creates
     * 
     * @param ep
     */
    public ExperimentProgressVisualizer(ExperimentPackage ep) {
        this.ep = ep;
        List<Integer> slice = ep.createFullSliceSpec();
        filesInput =
                ep.getSliceFileNames(slice, ExperimentPackage.EXTENSION_INPUT);
        filesRemainingToBeExecuted =
                ep.getSliceFileNames(slice, ExperimentPackage.EXTENSION_OUTPUT);
        filesExecuted = new ArrayList<>();
        filesExecutedInitial = new ArrayList<>();
        refresh();
        filesExecutedInitial = new ArrayList<>(filesExecuted);
        timeStarted = System.currentTimeMillis();
        runningThread = new Thread(this);
        runningThread.start();
    }

    /**
     * Creates a html display styled with the ExperimentPack.css file
     * 
     * @return
     */
    JTextPane createHtmlDisplay() {
        JTextPane htmlDisplay = new JTextPane();
        htmlDisplay.setContentType("text/html");
        htmlDisplay.setEditable(false);
        // htmlDisplay.setBorder(new EmptyBorder(0, 0, 0, 0));
        htmlDisplay.setBorder(BorderFactory.createLoweredBevelBorder());
        HTMLEditorKit htmlEditorKit =
                (HTMLEditorKit) htmlDisplay.getEditorKit();
        StyleSheet styleSheet = htmlEditorKit.getStyleSheet();
        try {
            URL url = getClass().getResource("resource/ExperimentPack.css");
            // this is to force an error if not read
            url.getContent();
            styleSheet.importStyleSheet(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new Error("This should not happen");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("Could not open the css file!");
        }
        return htmlDisplay;
    }

    public void createPanel() {
        frmMain = new JFrame("Execution progress");
        pnlMain = new JPanel();
        BoxLayout bl = new BoxLayout(pnlMain, BoxLayout.Y_AXIS);
        pnlMain.setLayout(bl);
        pnlMain.setPreferredSize(new Dimension(500, 500));
        frmMain.setContentPane(pnlMain);
        htmlDisplay = createHtmlDisplay();
        pnlMain.add(htmlDisplay);
        frmMain.pack();
        frmMain.setVisible(true);
        frmMain.toFront();
    }

    /**
     * Stops the visualization by stopping the thread and closing down the
     * window
     */
    public void doStop() {
        stop = true;
    }

    /**
     * Generates the display out of the available public information
     * 
     * @return
     */
    public String generateDisplay() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html>");
        // general description of the ExperimentPackage
        buffer.append("General description of ExperimentPackage here<br/>");
        // general state of the simulation
        buffer.append("Simulations: " + filesExecuted.size() + " / "
                + filesInput.size());
        int executedThisUnit =
                filesExecuted.size() - filesExecutedInitial.size();
        if (filesExecutedInitial.size() != 0) {
            buffer.append(" ("
                    + (filesExecuted.size() - filesExecutedInitial.size())
                    + " in this run)");
        }
        buffer.append("<br/>");
        // temporal estimates
        long timeRunning = System.currentTimeMillis() - timeStarted;
        buffer.append("Running since:"
                + TextUiHelper.formatTimeInterval(timeRunning) + "<br/>");
        if (executedThisUnit != 0) {
            long timePerTask = timeRunning / executedThisUnit;
            long timeRemaining =
                    (filesInput.size() - filesExecuted.size()) * timePerTask;
            buffer.append("Remaining time:"
                    + TextUiHelper.formatTimeInterval(timeRemaining) + "<br/>");
        }
        buffer.append("</html>");
        return buffer.toString();
    }

    /**
     * Periodically called to refresh the display
     * 
     */
    public void refresh() {
        List<File> copy = new ArrayList<>(filesRemainingToBeExecuted);
        for (File f : copy) {
            if (f.exists()) {
                filesExecuted.add(f);
                filesRemainingToBeExecuted.remove(f);
            }
        }
        if (htmlDisplay != null) {
            // set the display
            htmlDisplay.setText(generateDisplay());
            htmlDisplay.setCaretPosition(0);
        }
    }

    /**
     * 
     */
    @Override
    public void run() {
        createPanel();
        while (!stop) {
            refresh();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frmMain.setVisible(false);
        frmMain.dispose();
    }
}
