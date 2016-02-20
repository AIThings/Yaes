/*
 * Created on Oct 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import yaes.ui.text.TextUi;
import yaes.ui.text.TextUiHelper;

/**
 * 
 * This class contains the basic simulation routines
 * 
 * @author Lotzi Boloni
 * 
 */
public class Simulation {
    public static final String SIMULATION_INPUT_FILE_EXTENSION       = "sip";
    public static final String SIMULATION_INPUT_LIST_FILE_EXTENSION  = "sipl";
    public static final String SIMULATION_OUTPUT_FILE_EXTENSION      = "sop";
    public static final String SIMULATION_OUTPUT_LIST_FILE_EXTENSION = "sopl";

    /**
     * Creates an averaging simulation set in which a set of simulations are run
     * repetitions times, and the results are integrated in preparation for
     * creating errorbar type diagrams.
     * 
     * @param repetitions
     * @param sipList
     * @param simulationCode
     * @param contextClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static List<SimulationOutput> averagingSimulationSet(
            int repetitions, List<SimulationInput> sipList,
            Class<? extends ISimulationCode> simulationCode,
            Class<? extends IContext> contextClass)
            throws InstantiationException, IllegalAccessException {
        final long start = System.currentTimeMillis();
        final List<List<SimulationOutput>> collect = new ArrayList<>();
        final int steps = repetitions * sipList.size();
        int currentStep = 0;
        for (int i = 0; i != repetitions; i++) {
            final List<SimulationOutput> sopList = new ArrayList<>();
            int count = 1;
            for (final SimulationInput element : sipList) {
                TextUi.println("\nSimulation step: (" + count++ + " / "
                        + sipList.size() + ") repetition (" + (i + 1) + " / "
                        + repetitions + ")");
                final SimulationOutput sop = Simulation.simulate(element,
                        simulationCode, contextClass);
                currentStep++;
                final long elapsedTime = System.currentTimeMillis() - start;
                final long estimatedTime = steps * elapsedTime / currentStep;
                TextUi.println("Elapsed time: "
                        + TextUiHelper.formatTimeInterval(elapsedTime)
                        + "\nEstimated remaining: "
                        + TextUiHelper.formatTimeInterval(estimatedTime
                                - elapsedTime));
                sopList.add(sop);
            }
            collect.add(sopList);
        }
        TextUi.println("Total simulation time: "
                + TextUiHelper.formatTimeInterval(System.currentTimeMillis()
                        - start));
        return IntegratedSOP.integrateListOfSops(collect);
    }

    /**
     * Cached simulation. The result of the simulation will be put in the result
     * file.
     * 
     * If reuse is true, the simulation result will be read from the (previously
     * saved) result file. If the file does not exist, the simulation will
     * proceed.
     * 
     * @param resultFile
     * @param reuse
     * @param sipList
     * @param simulationCode
     * @param contextClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<SimulationOutput> cachedSimulationSet(File resultFile,
            boolean reuse, List<SimulationInput> sipList,
            Class<? extends ISimulationCode> simulationCode,
            Class<? extends IContext> contextClass)
            throws InstantiationException, IllegalAccessException,
            FileNotFoundException, IOException {
        if (reuse) {
            try {
                final List<SimulationOutput> outputs = SimulationOutput
                        .restoreList(resultFile);
                TextUi.println("Cache file " + resultFile + " loaded.");
                return outputs;
            } catch (final Exception ioex) {
                TextUi.println("Couldn't load cached data, proceeding to simulate");
            }
        }
        final List<SimulationOutput> outputs = Simulation.simulationSet(
                sipList, simulationCode, contextClass);
        SimulationOutput.storeList(outputs, resultFile);
        return outputs;
    }

    /**
     * 
     * Syntactic sugar, setting the system to null
     * 
     * @param sim
     * @param simulationCode
     * @param contextClass
     * @param logDirectory
     *            the directory where to log, null if no logging
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static SimulationOutput simulate(SimulationInput sim,
            Class<? extends ISimulationCode> simulationCode,
            Class<? extends IContext> contextClass)
            throws InstantiationException, IllegalAccessException {
        final IContext context = contextClass.newInstance();
        return Simulation.simulate(sim, simulationCode, context, null);
    }

    /**
     * Syntactic sugar, send the simulation logging to null.
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
        return Simulation.simulate(sim, simulationCode, context, null);
    }

    /**
     * Runs a single simulation run, in text mode, without control.
     * 
     * @param sim
     * @param simulationCode
     * @param context
     * @param logDirectory
     *            the directory where to log, null if no logging
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static SimulationOutput simulate(SimulationInput sim,
            Class<? extends ISimulationCode> simulationCode, IContext context,
            File logDirectory) throws InstantiationException,
            IllegalAccessException {
        TextUi.println("Initializing...");
        final ISimulationCode code = simulationCode.newInstance();
        final SimulationOutput sop = new SimulationOutput(sim, context);
        final long start = System.currentTimeMillis();
        SimulationLog slog = null;
        if (logDirectory != null) {
            slog = new SimulationLog(context, logDirectory);
        }
        TextUi.println("Starting simulation: " + code);
        SimulationStatus status = new SimulationStatus(sim);
        // TextUi.println("Input parameters: " + sim);
        sop.setTimeStart(System.currentTimeMillis());
        code.setup(sim, sop, context);
        for (double time = sim.getStartTime(); time < sim.getStopTime(); time = time
                + sim.getTimeResolution()) {
            status.update(time);
            int nextStep = code.update(time, sim, sop, context);
            if (slog != null) {
                slog.saveStep(time);
            }
            if (nextStep == 0) {
                break;
            }
        }
        TextUi.println("Simulation run done, postprocessing.");
        code.postprocess(sim, sop, context);
        sop.setTimeStop(System.currentTimeMillis());
        TextUi.println("Done. This simulation step took: "
                + TextUiHelper.formatTimeInterval(System.currentTimeMillis()
                        - start));
        TextUi.println("Memory: " + Runtime.getRuntime().freeMemory() / 1024
                + "KB free / " + Runtime.getRuntime().totalMemory() / 1024
                + "KB total");
        return sop;
    }

    /**
     * Performs a set of simulations, described in a list of simulation input
     * parameters.
     * 
     * @param sipList
     * @param simulationCode
     * @param contextClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static List<SimulationOutput> simulationSet(
            List<SimulationInput> sipList,
            Class<? extends ISimulationCode> simulationCode,
            Class<? extends IContext> contextClass)
            throws InstantiationException, IllegalAccessException {
        final long start = System.currentTimeMillis();
        final List<SimulationOutput> sopList = new ArrayList<>();
        int count = 0;
        final int steps = sipList.size();
        for (final SimulationInput element : sipList) {
            TextUi.println("\nSimulation step: (" + (count++ + 1) + " / "
                    + sipList.size() + ")");
            final SimulationOutput sop = Simulation.simulate(element,
                    simulationCode, contextClass);
            sopList.add(sop);
            final long elapsedTime = System.currentTimeMillis() - start;
            final long estimatedTime = steps * elapsedTime / count;
            TextUi.println("Elapsed time: "
                    + TextUiHelper.formatTimeInterval(elapsedTime)
                    + "\nEstimated remaining: "
                    + TextUiHelper.formatTimeInterval(estimatedTime
                            - elapsedTime));
        }
        TextUi.println("Total simulation time: "
                + TextUiHelper.formatTimeInterval(System.currentTimeMillis()
                        - start));
        return sopList;
    }

}
