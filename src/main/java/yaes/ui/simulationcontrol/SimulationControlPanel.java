package yaes.ui.simulationcontrol;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This is the whole thing.
 * 
 * Todo: split it into components, such that we can make a single tabbed GUI.
 * 
 * <code>yaes.ui.simulationcontrol.SimulationControlPanel</code>
 * 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class SimulationControlPanel implements ActionListener, ChangeListener {
    public enum State {
        FINISHED, INITIAL, POSTPROCESSED, READY_TO_RUN, RUNNING
    };

    private final JButton              btnPostProcess;
    private final JButton              btnRun;
    private final JButton              btnRunTo;
    // buttons
    private final JButton              btnSetup;
    private final JButton              btnStep;
    private final JButton              btnStop;
    private JLabel                     lblOneStepIs;
    private final JPanel               pnlButtons;
    private PnlSimulationOutput        pnlOutput;
    private JSlider                    sldDelay;
    private State                      state            = State.INITIAL;
    private JTabbedPane                tbPanelComponents;
    private final JFormattedTextField  tfRunTo;
    //
    private final JTextField           tfStatus;
    private final SimulationControlGui theGui;
    private final JPanel               thePanel;

    public SimulationControlPanel(SimulationControlGui theGui) {
        this.theGui = theGui;
        thePanel = new JPanel();
        thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
        // button panel
        pnlButtons = new JPanel();
        pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
        thePanel.add(pnlButtons);
        btnSetup = addButton("Setup");
        btnStep = addButton("Step");
        btnRun = addButton("Run");
        btnRunTo = addButton("Run to:");
        tfRunTo = new JFormattedTextField(new Integer(0));
        Dimension prefSize = tfRunTo.getPreferredSize();
        tfRunTo.setMinimumSize(new Dimension(100, 0));
        tfRunTo.setPreferredSize(new Dimension(100, prefSize.height));
        tfRunTo.setMaximumSize(new Dimension(100, prefSize.height));
        pnlButtons.add(tfRunTo);
        pnlButtons
                .add(Box.createHorizontalStrut(UiHelper.INTERBUTTON_DISTANCE));
        btnStop = addButton("Stop");
        btnPostProcess = addButton("Postprocess");
        tfStatus = UiHelper.createDisplayField(15);
        pnlButtons.add(tfStatus);
        pnlButtons.add(createSimulationSpeedPanel());
        state = State.INITIAL;
        setButtonBasedOnState();
        // tabs
        tbPanelComponents = new JTabbedPane();
        thePanel.add(tbPanelComponents);
        pnlOutput = new PnlSimulationOutput();
        addTab("Output", pnlOutput);
    }

    /**
     * Handles the buttons
     * 
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnSetup) {
            theGui.setNextCommand(SimulationControlGui.Command.Setup);
            state = State.READY_TO_RUN;
            setButtonBasedOnState();
            return;
        }
        if (event.getSource() == btnStep) {
            theGui.setNextCommand(SimulationControlGui.Command.Step);
            state = State.READY_TO_RUN;
            setButtonBasedOnState();
            return;
        }
        if (event.getSource() == btnRun) {
            theGui.setNextCommand(SimulationControlGui.Command.Run);
            state = State.RUNNING;
            setButtonBasedOnState();
            return;
        }
        if (event.getSource() == btnRunTo) {
            theGui.setNextCommand(SimulationControlGui.Command.RunTo);
            theGui.setRunTo((Integer) tfRunTo.getValue());
            state = State.RUNNING;
            setButtonBasedOnState();
            return;
        }
        if (event.getSource() == btnStop) {
            theGui.setNextCommand(SimulationControlGui.Command.Stop);
            state = State.READY_TO_RUN;
            setButtonBasedOnState();
            return;
        }
        if (event.getSource() == btnPostProcess) {
            theGui.setNextCommand(SimulationControlGui.Command.PostProcess);
            state = State.POSTPROCESSED;
            setButtonBasedOnState();
            return;
        }
    }

    /**
     * Adds a button to the button panel
     * 
     * @param text
     * @return
     */
    private JButton addButton(String text) {
        final JButton button = new JButton(text);
        pnlButtons.add(button);
        // pnlButtons
        // .add(Box.createHorizontalStrut(UiHelper.INTERBUTTON_DISTANCE));
        button.addActionListener(this);
        button.setEnabled(false);
        return button;
    }

    public void addTab(String tabName, ISimulationControlPanel iscp) {
        tbPanelComponents.add(tabName, iscp.getPanel());
        tbPanelComponents.setSelectedComponent(iscp.getPanel());
    }

    /**
     * Simulation speed panel
     * 
     * @return
     */
    private JPanel createSimulationSpeedPanel() {
        JPanel pnlSlider = new JPanel();
        pnlSlider.setLayout(new BoxLayout(pnlSlider, BoxLayout.X_AXIS));
        lblOneStepIs = new JLabel("Sec/step:");
        pnlSlider.add(lblOneStepIs);
        sldDelay = new JSlider(SwingConstants.HORIZONTAL, 0, 5, (int) theGui
                .getRunningDelay());
        sldDelay.addChangeListener(this);
        Dictionary<Integer, JComponent> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("Max"));
        labelTable.put(1, new JLabel("0.01"));
        labelTable.put(2, new JLabel("0.03"));
        labelTable.put(3, new JLabel("0.1"));
        labelTable.put(4, new JLabel("0.3"));
        labelTable.put(5, new JLabel("1"));
        sldDelay.setLabelTable(labelTable);
        sldDelay.setPaintLabels(true);
        pnlSlider.add(sldDelay);
        return pnlSlider;
    }

    /**
     * @return the thePanel
     */
    public JPanel getThePanel() {
        return thePanel;
    }

    /**
     * Turns the buttons on and off.
     * 
     */
    private void setButtonBasedOnState() {
        tfStatus.setText(theGui.getStatusString());
        if (theGui.getSimulationOutput() != null) {
            pnlOutput.setSimulationOutput(theGui.getSimulationOutput());
        }
        switch (state) {
        case INITIAL:
            btnSetup.setEnabled(true);
            btnStep.setEnabled(false);
            btnRun.setEnabled(false);
            btnRunTo.setEnabled(false);
            btnStop.setEnabled(false);
            btnPostProcess.setEnabled(false);
            break;
        case READY_TO_RUN:
            btnSetup.setEnabled(false);
            btnStep.setEnabled(true);
            btnRun.setEnabled(true);
            btnRunTo.setEnabled(true);
            btnStop.setEnabled(false);
            btnPostProcess.setEnabled(false);
            break;
        case RUNNING:
            btnSetup.setEnabled(false);
            btnStep.setEnabled(false);
            btnRun.setEnabled(false);
            btnRunTo.setEnabled(false);
            btnStop.setEnabled(true);
            btnPostProcess.setEnabled(false);
            break;
        case FINISHED:
            btnSetup.setEnabled(false);
            btnStep.setEnabled(false);
            btnRun.setEnabled(false);
            btnRunTo.setEnabled(false);
            btnStop.setEnabled(false);
            btnPostProcess.setEnabled(true);
            break;
        case POSTPROCESSED:
            btnSetup.setEnabled(false);
            btnStep.setEnabled(false);
            btnRun.setEnabled(false);
            btnRunTo.setEnabled(false);
            btnStop.setEnabled(false);
            btnPostProcess.setEnabled(false);
            break;
		default:
			break;
        }
    }

    /**
     * If the simulation speed is too high... turns the one step is to red!
     * 
     * @param isOk
     */
    public void setSimulationSpeedOK(boolean isOk) {
        if (isOk) {
            lblOneStepIs.setForeground(Color.black);
        } else {
            lblOneStepIs.setForeground(Color.red);
        }
    }

    /**
     * Sets the button based on the state
     * 
     * @param state
     */
    public void setState(State state) {
        this.state = state;
        setButtonBasedOnState();
    }

    /**
     * Handles events - for the time being, it is the slider for the simulation
     * speed
     * 
     * @param arg0
     */
    @Override
    public void stateChanged(ChangeEvent arg0) {
        if (arg0.getSource() == sldDelay) {
            if (!sldDelay.getValueIsAdjusting()) {
                int val = sldDelay.getValue();
                switch (val) {
                case 0:
                    theGui.setRunningDelay(0);
                    break;
                case 1:
                    theGui.setRunningDelay(10);
                    break;
                case 2:
                    theGui.setRunningDelay(33);
                    break;
                case 3:
                    theGui.setRunningDelay(100);
                    break;
                case 4:
                    theGui.setRunningDelay(333);
                    break;
                case 5:
                    theGui.setRunningDelay(1000);
                    break;
				default:
					break;
                }
            }
        }
    }

    /**
     * Updates the status button
     */
    public void update() {
        tfStatus.setText(theGui.getStatusString());
    }
}
