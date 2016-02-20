/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jun 22, 2009
 
   yaes.framework.simulation.parametersweep.ExperimentPackage
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yaes.framework.simulation.SimulationInput;
import yaes.framework.simulation.SimulationOutput;
import yaes.framework.simulation.parametersweep.actions.IDirectoryTraversalAction;
import yaes.framework.simulation.parametersweep.actions.dtaCleanup;
import yaes.framework.simulation.parametersweep.actions.dtaCount;
import yaes.framework.simulation.parametersweep.actions.dtaProbabilisticRun;
import yaes.framework.simulation.parametersweep.actions.dtaRun;
import yaes.ui.plot.AbstractGraphDescription;
import yaes.ui.plot.TimeSeriesLineDescription;
import yaes.ui.plot.TimeSeriesPlot;
import yaes.ui.text.TextUi;
import yaes.util.FileWritingUtil;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.ExperimentPackage</code>
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ExperimentPackage {

	public static final File dataYaes = new File("../../../Data");
	public static final File dataYaesApps = new File("../../../../Data");

	public static final String EXTENSION_INPUT = ".inp";
	public static final String EXTENSION_LOCK = ".lock";
	public static final String EXTENSION_OUTPUT = ".out";
	public static final String EXTENSION_TXT = ".txt";
	private File directory;
	private FileFilter filterDirectories = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};
	private FileFilter filterInput = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(
					ExperimentPackage.EXTENSION_INPUT);
		}
	};

	private File graphDirectory;
	private SimulationInput model;;
	private List<ParameterSweep> parameterSweeps = new ArrayList<>();
	private ISOSource soSource = new FullExecutionSOSource();
	private Map<String, String> variableDescriptions = new HashMap<>();

	/**
	 * If set to true, generate the graphs for Octave
	 */
	private boolean generateForOctave = false;

	/**
	 * @return the generateForOctave
	 */
	public boolean isGenerateForOctave() {
		return generateForOctave;
	}

	/**
	 * @param generateForOctave
	 *            the generateForOctave to set
	 */
	public void setGenerateForOctave(boolean generateForOctave) {
		this.generateForOctave = generateForOctave;
	}

	/**
	 * The size of the generated graphs - x coordinate
	 */
	private int graphSizeX = 400;

	/**
	 * @return the graphSizeX
	 */
	public int getGraphSizeX() {
		return graphSizeX;
	}

	/**
	 * @param graphSizeX
	 *            the graphSizeX to set
	 */
	public void setGraphSizeX(int graphSizeX) {
		this.graphSizeX = graphSizeX;
	}

	/**
	 * @return the graphSizeY
	 */
	public int getGraphSizeY() {
		return graphSizeY;
	}

	/**
	 * @param graphSizeY
	 *            the graphSizeY to set
	 */
	public void setGraphSizeY(int graphSizeY) {
		this.graphSizeY = graphSizeY;
	}

	/**
	 * The size of the generated graphs - y coordinate
	 */
	private int graphSizeY = 250;

	/**
	 * Constructor
	 * 
	 * @param directory
	 *            the directory where the working files, inputs, outputs etc go.
	 * @param graphDirectory
	 *            the directory where the graphs are going to be created.
	 */
	public ExperimentPackage(File directory, File graphDirectory) {
		this.directory = directory;
		this.graphDirectory = graphDirectory;
	}

	public void addParameterSweep(ParameterSweep sweep) {
		parameterSweeps.add(sweep);
	}

	/**
	 * Cleans up the files to run with a new stuff
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws FileNotFoundException
	 */
	public void cleanUp() throws FileNotFoundException, InstantiationException,
			IllegalAccessException, IOException, ClassNotFoundException {
		dtaCleanup dta = new dtaCleanup(false);
		visitDirectory(directory, dta);
	}

	/**
	 * Creates a slice specification which covers the whole area
	 * 
	 * @return
	 */
	public List<Integer> createFullSliceSpec() {
		List<Integer> sliceSpec = new ArrayList<>();
		for (int i = 0; i != parameterSweeps.size(); i++) {
			sliceSpec.add(-1);
		}
		return sliceSpec;
	}

	/**
	 * Counts the execution status by traversing the directory
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws FileNotFoundException
	 */
	public void doCount() throws FileNotFoundException, InstantiationException,
			IllegalAccessException, IOException, ClassNotFoundException {
		dtaCount action = new dtaCount();
		visitDirectory(directory, action);

	}

	/**
	 * Helper function, for a plot description
	 * 
	 * @param pd
	 * @param outputDirectory
	 * @param graphDir
	 * @param errorBars
	 * @throws IOException
	 */
	public void generateAll(AbstractGraphDescription pd, File outputDirectory,
			String graphDir, boolean errorBars) {
		if (!isGenerateForOctave()) {
			File matlabFile = new File(outputDirectory, graphDir + ".m");
			pd.generate(matlabFile, graphSizeX, graphSizeY);
		} else {
			File octaveFile = new File(outputDirectory, graphDir + ".m");
			pd.generateOctave(octaveFile, graphSizeX, graphSizeY);
		}
		if (errorBars) {
			File errorBarFile = new File(outputDirectory, graphDir + "_eb.m");
			pd.generateErrorBarPlot(errorBarFile, graphSizeX, graphSizeY);
		}
		TextUi.println("Generated graph: " + graphDir + " in directory "
				+ outputDirectory);
	}

	/**
	 * Generates a time series graph for a simple set of discrete variables
	 * 
	 * @param variable
	 * @param plotTitle
	 * @param timmeLabel
	 * @param fileBase
	 */
	private void generateDiscreteTimeSeriesGraph(String variable,
			String plotTitle, String timeLabel, String outputBase) {
		TimeSeriesPlot tsPlot = new TimeSeriesPlot(plotTitle, timeLabel,
				plotTitle + "-y", this);
		ParameterSweep sweep = parameterSweeps.get(0);
		for (ScenarioDistinguisher sd : sweep.getList()) {
			File f = new File(directory, sd.getName()
					+ ExperimentPackage.EXTENSION_OUTPUT);
			SimulationOutput out = SimulationOutput.restore(f);
			TimeSeriesLineDescription tsld = new TimeSeriesLineDescription(
					variable, sd.getName(), out);
			tsPlot.addTimeSeriesLineDescription(tsld);
		}
		generateAll(tsPlot, graphDirectory, outputBase, false);
	}

	/**
	 * General purpose graph generation. It knows what kind of graphs should
	 * generate
	 * 
	 * @param variable
	 * @param plotTitle
	 * @param fileBase
	 */
	public void generateGraph(String variable, String plotTitle, String fileBase) {
		// bar graph
		if (parameterSweeps.size() == 1
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Discrete)) {
			EPGraphs.generateBarGraph(this, soSource, variable, plotTitle,
					fileBase);
			return;
		}
		// one plot line graph
		if (parameterSweeps.size() == 1
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)) {
			EPGraphs.generateOnePlotlineGraph(this, soSource, variable,
					plotTitle, fileBase);
			return;
		}
		// multiple plots line graph: discrete + range
		if (parameterSweeps.size() == 2
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Discrete)
				&& parameterSweeps.get(1).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)) {
			EPGraphs.generateMultiplePlotlineGraph(this, soSource, variable,
					plotTitle, fileBase);
			return;
		}
		// single 3D graph: range + range
		if (parameterSweeps.size() == 2
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)
				&& parameterSweeps.get(1).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)) {
			EPGraphs.generateSingle3DGraph(this, soSource, variable, plotTitle,
					fileBase);
			return;
		}
		// multiple averaged plots line graph: discrete + range + repetition
		if (parameterSweeps.size() == 3
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Discrete)
				&& parameterSweeps.get(1).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)
				&& parameterSweeps.get(2).getType()
						.equals(ParameterSweep.ParameterSweepType.Repetition)) {
			EPGraphs.generateMultipleAveragedPlotlineGraph(this, soSource,
					variable, plotTitle, fileBase);
			return;
		}
		// 3d without averaging
		if (parameterSweeps.size() == 3
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Discrete)
				&& parameterSweeps.get(1).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)
				&& parameterSweeps.get(2).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)) {
			EPGraphs.generate3DGraph(this, soSource, variable, plotTitle,
					fileBase);
			return;
		}
		// single 3D with averaging
		if (parameterSweeps.size() == 3
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)
				&& parameterSweeps.get(1).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)
				&& parameterSweeps.get(2).getType()
						.equals(ParameterSweep.ParameterSweepType.Repetition)) {
			EPGraphs.generateSingleAveraged3DGraph(this, soSource, variable,
					plotTitle, fileBase);
			return;
		}
		// 3d with averaging
		if (parameterSweeps.size() == 4
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Discrete)
				&& parameterSweeps.get(1).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)
				&& parameterSweeps.get(2).getType()
						.equals(ParameterSweep.ParameterSweepType.Range)
				&& parameterSweeps.get(3).getType()
						.equals(ParameterSweep.ParameterSweepType.Repetition)) {
			EPGraphs.generateAveraged3DGraph(this, soSource, variable,
					plotTitle, fileBase);
			return;
		}

		throw new Error(
				"generate graph for this configuration of parameter sweeps is not "
						+ "implemented yet");
	}

	/**
	 * Time series graph generation
	 * 
	 * @param variable
	 * @param plotTitle
	 *            the title of the plot
	 * @param timeLabel
	 *            the label of the time component of the plot
	 * 
	 */
	public void generateTimeSeriesGraph(String variable, String plotTitle,
			String timeLabel, String fileBase) {
		// first resolve the file
		// one plot line graph
		if (parameterSweeps.size() == 1
				&& parameterSweeps.get(0).getType()
						.equals(ParameterSweep.ParameterSweepType.Discrete)) {
			generateDiscreteTimeSeriesGraph(variable, plotTitle, timeLabel,
					fileBase);
			return;
		}

		throw new Error(
				"generate time series graph for this configuration of parameter sweeps is not "
						+ "implemented yet");
	}

	/**
	 * @return the directory
	 */
	public File getDirectory() {
		return directory;
	}

	/**
	 * Recursive helper function
	 * 
	 * @param level
	 * @param dir
	 * @param sliceSpec
	 * @param files
	 * @param extension
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void getFileNamesHelper(int level, File dir,
			List<Integer> sliceSpec, List<File> files, String extension) {
		int posInSweeps = parameterSweeps.size() - level - 1;
		List<ScenarioDistinguisher> list = parameterSweeps.get(posInSweeps)
				.getList();
		int marker = sliceSpec.get(posInSweeps);
		for (int i = 0; i < list.size(); i++) {
			if (marker != -1 && marker != i) {
				continue;
			}
			ScenarioDistinguisher sd = list.get(i);
			if (level == 0) {
				// at the last level, insert the files
				File f = new File(dir, sd.getName() + extension);
				files.add(f);
			} else {
				// at the other levels, create the directory and descend
				File f = new File(dir, sd.getName());
				getFileNamesHelper(level - 1, f, sliceSpec, files, extension);
			}
		}
	}

	/**
	 * @return the outputDirectory
	 */
	public File getGraphDirectory() {
		return graphDirectory;
	}

	public SimulationInput getModel() {
		return model;
	}

	/**
	 * @return the parameterSweeps
	 */
	public List<ParameterSweep> getParameterSweeps() {
		return parameterSweeps;
	}

	/**
	 * Returns the names of the files for a specific slice of the space
	 * 
	 * @param sliceSpec
	 * @param extension
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<File> getSliceFileNames(List<Integer> sliceSpec,
			String extension) {
		List<File> files = new ArrayList<>();
		getFileNamesHelper(parameterSweeps.size() - 1, getDirectory(),
				sliceSpec, files, extension);
		return files;
	}

	/**
	 * @return the soSource
	 */
	public ISOSource getSoSource() {
		return soSource;
	}

	/**
	 * Describe a variable. If no description exists, the description is the
	 * name.
	 * 
	 * @param description
	 */
	public String getVariableDescription(String name) {
		String retval = variableDescriptions.get(name);
		if (retval != null) {
			return retval;
		}
		return name;
	}

	/**
	 * Initializes the system, creates the directories, writes out the
	 * SimulationOutputs
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void initialize() {
		directory.mkdirs();
		initialize(parameterSweeps.size() - 1, directory, model);
	}

	/**
	 * Recursive function to initialize all the directories
	 * 
	 * @param level
	 * @param dir
	 * @param model
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void initialize(int level, File dir, SimulationInput model) {
		for (ScenarioDistinguisher sd : parameterSweeps.get(
				parameterSweeps.size() - level - 1).getList()) {
			SimulationInput sip = new SimulationInput(model);
			sd.apply(sip);
			if (level == 0) {
				// at the last level, create the files
				File f = new File(dir, sd.getName()
						+ ExperimentPackage.EXTENSION_INPUT);
				try {
					sip.store(f);
				} catch (IOException ioex) {
					ioex.printStackTrace();
					TextUi.println("One cannot recover from this, exiting...");
					System.exit(1);
				}
				File f3txt = new File(dir, sd.getName()
						+ ExperimentPackage.EXTENSION_TXT);
				FileWritingUtil.writeToTextFile(f3txt, sip.toString());
			} else {
				// at the other levels, create the directory and descend
				File f = new File(dir, sd.getName());
				f.mkdirs();
				initialize(level - 1, f, sip);
			}
		}
	}

	/**
	 * Performs one step of probabilistic run
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws FileNotFoundException
	 */
	public void probabilisticRun(double probability)
			throws FileNotFoundException, InstantiationException,
			IllegalAccessException, IOException, ClassNotFoundException {
		dtaCleanup dta = new dtaCleanup(true);
		visitDirectory(directory, dta);
		dtaCount dtaCount = new dtaCount();
		visitDirectory(directory, dtaCount);
		ExperimentProgressVisualizer epv = new ExperimentProgressVisualizer(
				this);
		dtaProbabilisticRun dtaRun = new dtaProbabilisticRun(probability);
		dtaRun.setCountRunsTotal(dtaCount.getCountRunsTotal());
		visitDirectory(directory, dtaRun);
		epv.doStop();
	}

	/**
	 * Compatibility: cleans up the locks
	 * 
	 * @param cleanupLocks
	 */
	public void run() {
		run(true);
	}

	/**
	 * Runs the simulation from scratch.
	 */
	public void run(boolean cleanupLocks) {
		// clean up the locks if required
		if (cleanupLocks) {
			dtaCleanup dta = new dtaCleanup(true);
			visitDirectory(directory, dta);
		}
		dtaCount dtaCount = new dtaCount();
		visitDirectory(directory, dtaCount);
		ExperimentProgressVisualizer epv = new ExperimentProgressVisualizer(
				this);
		dtaRun dtaRun = new dtaRun();
		dtaRun.setCountRunsTotal(dtaCount.getCountRunsTotal());
		visitDirectory(directory, dtaRun);
		epv.doStop();
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(SimulationInput model) {
		this.model = model;
	}

	/**
	 * @param soSource
	 *            the soSource to set
	 */
	public void setSoSource(ISOSource soSource) {
		this.soSource = soSource;
	}

	/**
	 * Describe a variable
	 * 
	 * @param name
	 * @param description
	 */
	public void setVariableDescription(String name, String description) {
		variableDescriptions.put(name, description);
	}

	/**
	 * @param directory2
	 */
	void visitDirectory(File currentDir, IDirectoryTraversalAction action) {
		File directories[] = currentDir.listFiles(filterDirectories);
		if (directories == null) {
			return;
		}
		for (int i = 0; i != directories.length; i++) {
			visitDirectory(directories[i], action);
		}
		File inputFiles[] = currentDir.listFiles(filterInput);
		for (int i = 0; i != inputFiles.length; i++) {
			action.action(inputFiles[i]);
		}
	}

}
