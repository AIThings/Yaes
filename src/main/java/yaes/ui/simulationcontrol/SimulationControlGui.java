package yaes.ui.simulationcontrol;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JFrame;

import yaes.framework.simulation.IContext;
import yaes.framework.simulation.ISimulationCode;
import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.SimulationOutput;
import yaes.ui.simulationcontrol.SimulationControlPanel.State;
import yaes.ui.visualization.Visualizer;

/*
 * The simulation control framework.
 */
public class SimulationControlGui extends JFrame {
    public enum Command {
        NoCommand, PostProcess, Run, RunTo, Setup, Step, Stop
    }

    public enum Status {
        AfterPostProcess, BeforePostProcess, BeforeSetup, ReadyToRun, Running,
        Stopped
    }

    private static final long serialVersionUID = -1268363099695354176L;

    /**
     * Runs a single simulation run (with the context specified as a class.
     * 
     * @param sim
     * @param simulationCode
     * @param context
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static SimulationOutput simulate(SimulationInput sim,
            Class<? extends ISimulationCode> simulationCode,
            Class<? extends IContext> contextClass)
            throws InstantiationException, IllegalAccessException {
        final IContext context = contextClass.newInstance();
        return SimulationControlGui.simulate(sim, simulationCode, context);
    };

    /**
     * Runs a single simulation run.
     * 
     * @param sim
     * @param simulationCode
     * @param context
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static SimulationOutput simulate(SimulationInput sim,
            Class<? extends ISimulationCode> simulationCode, IContext context)
            throws InstantiationException, IllegalAccessException {
        final SimulationControlGui theGui = new SimulationControlGui();
        theGui.setVisible(true);
        theGui.runSimulation(sim, simulationCode, context, true);
        return theGui.sop;
    }

    private ISimulationCode              code;
    private Command                      currentCommand;
    private Command                      nextCommand;
    private long                         runningDelay = 0;
    private int                          runTo        = 0;
    private SimulationInput              sin          = null;
    private SimulationOutput             sop          = null;
    private Status                       status;
    private final SimulationControlPanel thePanel;
    public double                        time         = -1;

    /**
     * Creates a simulation control GUI
     */
    public SimulationControlGui() {
        super("YAES Simulation Control");
        Visualizer.setUIFont(new javax.swing.plaf.FontUIResource("Arial",
                Font.PLAIN | Font.TRUETYPE_FONT, 14));
        currentCommand = Command.NoCommand;
        nextCommand = Command.NoCommand;
        status = Status.BeforeSetup;
        thePanel = new SimulationControlPanel(this);
        add(thePanel.getThePanel());
        pack();
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    /**
     * It is used to check for the next command, without blocking the thread.
     * 
     * Returns true if there was a command.
     */
    private boolean checkNextCommand() {
        if (nextCommand == Command.NoCommand) {
            return false;
        }
        currentCommand = nextCommand;
        nextCommand = Command.NoCommand;
        return true;
    }

    /**
     * This function adds a delay if it is necessary to achieve a smooth
     * simulation
     * 
     * @param timeBeginning
     */
    private void delayIfNecessary(long timeBeginning) {
        if (runningDelay == 0) {
            return;
        }
        long timeCurrent = System.currentTimeMillis();
        long timeToDelay = runningDelay - (timeCurrent - timeBeginning);        
        if (timeToDelay >= 0) {
            try {
                Thread.sleep(runningDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thePanel.setSimulationSpeedOK(true);
        } else {
            thePanel.setSimulationSpeedOK(false);
        }

    }

    /**
     * Returns the current running delay
     * 
     * @return
     */
    public long getRunningDelay() {
        return runningDelay;
    }

    public SimulationOutput getSimulationOutput() {
        return sop;
    }

    /**
     * Returns a status string for visualization
     */
    public String getStatusString() {
        if (sin == null) {
            return "" + status;
        }
        String timeString;
        String stopTimeString;
        if (Math.rint(sin.getStopTime()) == sin.getStopTime()) {
            stopTimeString = "" + Math.round(sin.getStopTime());
        } else {
            stopTimeString = String.format("%.3f", sin.getStopTime());
        }
        if (Math.rint(time) == time) {
            timeString = "" + Math.round(time);
        } else {
            timeString = String.format("%.3f", time);
        }
        return status + ":" + timeString + "/" + stopTimeString;

    }

    /**
     * 
     * This function runs the simulation, while waiting for control from the
     * GUI.
     * 
     * @param sim
     * @param simulationCode
     * @param context
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void runSimulation(SimulationInput sin,
            Class<? extends ISimulationCode> simulationCode, IContext context,
            boolean autoSetup) throws InstantiationException,
            IllegalAccessException {
        this.sin = sin;
        sin.setSimulationControlPanel(thePanel);
        if (autoSetup) {
            currentCommand = Command.Setup;
        } else {
            waitForCommand();
        }
        if (currentCommand == Command.Setup) {
            code = simulationCode.newInstance();
            sop = new SimulationOutput(sin, context);
            code.setup(sin, sop, context);
            sop.setTimeStart(System.currentTimeMillis());
            currentCommand = Command.Stop;
            status = Status.ReadyToRun;
            thePanel.setState(State.READY_TO_RUN);
        } else {
            throw new Error("Invalid command " + currentCommand);
        }
        // ready to run, what for a Step or Run to run
        waitForCommand();
        if ((currentCommand == Command.Run) || (currentCommand == Command.Step)
                || (currentCommand == Command.RunTo)) {
            status = Status.Running;
        } else {
            throw new Error("Invalid command " + currentCommand);
        }
        for (time = sin.getStartTime(); time < sin.getStopTime(); time = time
                + sin.getTimeResolution()) {
            long timeBeginning = System.currentTimeMillis();
            if (currentCommand == Command.Stop) {
                waitForCommand();
            }
            // run one step
            if (code.update(time, sin, sop, context) == 0) {
                break;
            }
            if (currentCommand == Command.Step) {
                currentCommand = Command.Stop;
            }
            if (currentCommand == Command.Run) {
                checkNextCommand();
            }
            if (currentCommand == Command.RunTo) {
                if (time > runTo) {
                    currentCommand = Command.Stop;
                    thePanel.setState(SimulationControlPanel.State.READY_TO_RUN);
                } else {
                    checkNextCommand();
                }
            }
            thePanel.update();
            if ((currentCommand == Command.Run)
                    || (currentCommand == Command.RunTo)) {
                delayIfNecessary(timeBeginning);
            }
        }
        code.postprocess(sin, sop, context);
        sop.setTimeStart(System.currentTimeMillis());
    }

    /**
     * This function is used by the panel and other utilities to set the next
     * command.
     * 
     * @param command
     */
    synchronized public void setNextCommand(Command command) {
        nextCommand = command;
        this.notifyAll();
    }

    public void setRunningDelay(long runningDelay) {
        this.runningDelay = runningDelay;
    }

    /**
     * @param runTo
     *            the runTo to set
     */
    public void setRunTo(int runTo) {
        this.runTo = runTo;
    }

    /**
     * Stops the simulation and waits for the next command, by waiting on the
     * thread. It is waken up by the setNextCommand function.
     */
    private synchronized void waitForCommand() {
        while (true) {
            if (nextCommand != Command.NoCommand) {
                currentCommand = nextCommand;
                nextCommand = Command.NoCommand;
                return;
            }
            try {
                this.wait();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
