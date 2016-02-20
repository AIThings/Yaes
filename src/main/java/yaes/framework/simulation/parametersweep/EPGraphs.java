/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Oct 28, 2009
 
   yaes.framework.simulation.parametersweep.EPGraphs
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import yaes.framework.simulation.RandomVariable;
import yaes.framework.simulation.RandomVariable.Probe;
import yaes.framework.simulation.SimulationOutput;
import yaes.ui.plot.BarGraphDescription;
import yaes.ui.plot.Plot3DDescription;
import yaes.ui.plot.Plot3DDescription.Plot3DType;
import yaes.ui.plot.PlotDescription;
import yaes.ui.plot.PlotLineDescription;
import yaes.ui.plot.PlotSurfaceDescription;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.EPGraphs</code> Contains the
 * functions for the generation of graphs from the results of experiment packs
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class EPGraphs {
	/**
	 * 
	 * Generates a 3D graph from an NN stack
	 * 
	 * @param variable
	 * @param plotTitle
	 * @param fileBase
	 */
	public static void generate3DGraph(ExperimentPackage ep,
			ISOSource outputsource, String variable, String plotTitle,
			String outputBase) {
		ParameterSweep sweepDiscrete = ep.getParameterSweeps().get(0);
		ParameterSweep sweepRangeX = ep.getParameterSweeps().get(1);
		ParameterSweep sweepRangeY = ep.getParameterSweeps().get(2);
		Plot3DDescription pd = new Plot3DDescription(plotTitle,
				sweepRangeX.getLeadVariable(), sweepRangeY.getLeadVariable(),
				variable, ep);
		for (ScenarioDistinguisher sdDiscrete : sweepDiscrete.getList()) {
			File currentTopDir = new File(ep.getDirectory(),
					sdDiscrete.getName());
			List<List<SimulationOutput>> outputs = outputsource
					.getTwoLevelsOfSOs(currentTopDir, sweepRangeX, sweepRangeY);
			PlotSurfaceDescription psd = new PlotSurfaceDescription(outputs,
					sdDiscrete.getName(), sweepRangeX.getLeadVariable(),
					Probe.LASTVALUE, sweepRangeY.getLeadVariable(),
					Probe.LASTVALUE, variable, Probe.AVERAGE);
			pd.addPlotSurface(psd);
		}
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase, true);
		pd.setType(Plot3DType.SURF);
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase + "_surf", true);
		// generate the other two graphs as well
		if (sweepDiscrete.getList().size() == 1) {
			pd.setType(Plot3DType.SURFL);
			ep.generateAll(pd, ep.getGraphDirectory(), outputBase + "_surfl",
					true);
		}
	}

	/**
	 * @param variable
	 * @param plotTitle
	 * @param fileBase
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void generateAveraged3DGraph(ExperimentPackage ep,
			ISOSource outputsource, String variable, String plotTitle,
			String outputBase) {
		ParameterSweep sweepDiscrete = ep.getParameterSweeps().get(0);
		ParameterSweep sweepRangeX = ep.getParameterSweeps().get(1);
		ParameterSweep sweepRangeY = ep.getParameterSweeps().get(2);
		ParameterSweep sweepRepetition = ep.getParameterSweeps().get(3);
		Plot3DDescription pd = new Plot3DDescription(plotTitle,
				sweepRangeX.getLeadVariable(), sweepRangeY.getLeadVariable(),
				variable, ep);
		for (ScenarioDistinguisher sdDiscrete : sweepDiscrete.getList()) {
			File currentTopDir = new File(ep.getDirectory(),
					sdDiscrete.getName());
			List<List<SimulationOutput>> outputs = outputsource
					.getTwoLevelsOfSOsIntegrated(currentTopDir, sweepRangeX,
							sweepRangeY, sweepRepetition);
			PlotSurfaceDescription psd = new PlotSurfaceDescription(outputs,
					sdDiscrete.getName(), sweepRangeX.getLeadVariable(),
					Probe.LASTVALUE, sweepRangeY.getLeadVariable(),
					Probe.LASTVALUE, variable, Probe.AVERAGE);
			pd.addPlotSurface(psd);
		}
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase, true);
		pd.setType(Plot3DType.SURF);
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase + "_surf", true);
		// generate the other two graphs as well
		if (sweepDiscrete.getList().size() == 1) {
			pd.setType(Plot3DType.SURFL);
			ep.generateAll(pd, ep.getGraphDirectory(), outputBase + "_surfl",
					true);
		}
	}

	/**
	 * 
	 * Creates a bar graphs (from a D stack)
	 * 
	 * @param variable
	 * @param plotTitle
	 * @param fileBase
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void generateBarGraph(ExperimentPackage ep,
			ISOSource outputsource, String variable, String plotTitle,
			String outputBase) {
		ParameterSweep sweep = ep.getParameterSweeps().get(0);
		List<SimulationOutput> outputs = outputsource.getOneLevelOfSOs(
				ep.getDirectory(), sweep);
		final BarGraphDescription bgd = new BarGraphDescription(plotTitle,
				variable, RandomVariable.Probe.AVERAGE, ep, outputs);
		ep.generateAll(bgd, ep.getGraphDirectory(), outputBase, false);
	}

	/**
	 * @param variable
	 * @param plotTitle
	 * @param outputFile
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void generateMultipleAveragedPlotlineGraph(
			ExperimentPackage ep, ISOSource outputsource, String variable,
			String plotTitle, String outputBase) {
		ParameterSweep sweepDiscrete = ep.getParameterSweeps().get(0); // discrete
		// values
		ParameterSweep sweepRange = ep.getParameterSweeps().get(1); // the range
		ParameterSweep sweepRepetition = ep.getParameterSweeps().get(2); // the
		// repetion
		List<List<SimulationOutput>> outputsList = outputsource
				.getTwoLevelsOfSOsIntegrated(ep.getDirectory(), sweepDiscrete,
						sweepRange, sweepRepetition);
		final PlotDescription pd = new PlotDescription(plotTitle,
				sweepRange.getLeadVariable(), variable, ep);
		for (int i = 0; i != sweepDiscrete.getList().size(); i++) {
			pd.addPlotLine(new PlotLineDescription(
					sweepRange.getLeadVariable(), variable, sweepDiscrete
							.getList().get(i).getName(), outputsList.get(i)));
		}
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase, true);
	}

	/**
	 * Generates a regular line plot from a DN stack
	 * 
	 * @param variable
	 * @param plotTitle
	 * @param outputFile
	 */
	public static void generateMultiplePlotlineGraph(ExperimentPackage ep,
			ISOSource outputsource, String variable, String plotTitle,
			String outputBase) {
		ParameterSweep sweepDiscrete = ep.getParameterSweeps().get(0); // the
		// range
		ParameterSweep sweepRange = ep.getParameterSweeps().get(1); // the range
		List<List<SimulationOutput>> outputsList = outputsource
				.getTwoLevelsOfSOs(ep.getDirectory(), sweepDiscrete, sweepRange);
		final PlotDescription pd = new PlotDescription(plotTitle,
				sweepRange.getLeadVariable(), variable, ep);
		for (int i = 0; i != sweepDiscrete.getList().size(); i++) {
			pd.addPlotLine(new PlotLineDescription(
					sweepRange.getLeadVariable(), variable, sweepDiscrete
							.getList().get(i).getName(), outputsList.get(i)));
		}
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase, false);
	}

	/**
	 * Generates one level of graph from an N stack
	 * 
	 * @param variable
	 * @param outputFile
	 * @return
	 */
	public static void generateOnePlotlineGraph(ExperimentPackage ep,
			ISOSource outputsource, String variable, String plotTitle,
			String outputBase) {
		ParameterSweep sweep = ep.getParameterSweeps().get(0);
		List<SimulationOutput> outputs = outputsource.getOneLevelOfSOs(
				ep.getDirectory(), sweep);
		final PlotDescription pd = new PlotDescription(plotTitle,
				sweep.getLeadVariable(), variable, ep);
		pd.addPlotLine(new PlotLineDescription(sweep.getLeadVariable(),
				variable, variable, outputs));
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase, false);
	}

	/**
	 * 
	 * Generates a 3D graph from an NN stack
	 * 
	 * @param variable
	 * @param plotTitle
	 * @param fileBase
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void generateSingle3DGraph(ExperimentPackage ep,
			ISOSource outputsource, String variable, String plotTitle,
			String outputBase) {
		ParameterSweep sweepRangeX = ep.getParameterSweeps().get(0);
		ParameterSweep sweepRangeY = ep.getParameterSweeps().get(1);
		Plot3DDescription pd = new Plot3DDescription(plotTitle,
				sweepRangeX.getLeadVariable(), sweepRangeY.getLeadVariable(),
				variable, ep);
		List<List<SimulationOutput>> outputs = outputsource.getTwoLevelsOfSOs(
				ep.getDirectory(), sweepRangeX, sweepRangeY);
		PlotSurfaceDescription psd = new PlotSurfaceDescription(outputs, null,
				sweepRangeX.getLeadVariable(), Probe.LASTVALUE,
				sweepRangeY.getLeadVariable(), Probe.LASTVALUE, variable,
				Probe.AVERAGE);
		pd.addPlotSurface(psd);
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase, true);
		pd.setType(Plot3DType.SURF);
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase + "_surf", true);
		pd.setType(Plot3DType.SURFL);
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase + "_surfl", true);
	}

	/**
	 * @param variable
	 * @param plotTitle
	 * @param fileBase
	 */
	public static void generateSingleAveraged3DGraph(ExperimentPackage ep,
			ISOSource outputsource, String variable, String plotTitle,
			String outputBase) {
		ParameterSweep sweepRangeX = ep.getParameterSweeps().get(0);
		ParameterSweep sweepRangeY = ep.getParameterSweeps().get(1);
		ParameterSweep sweepRepetition = ep.getParameterSweeps().get(2);
		Plot3DDescription pd = new Plot3DDescription(plotTitle,
				sweepRangeX.getLeadVariable(), sweepRangeY.getLeadVariable(),
				variable, ep);
		List<List<SimulationOutput>> outputs = outputsource
				.getTwoLevelsOfSOsIntegrated(ep.getDirectory(), sweepRangeX,
						sweepRangeY, sweepRepetition);
		PlotSurfaceDescription psd = new PlotSurfaceDescription(outputs, "",
				sweepRangeX.getLeadVariable(), Probe.LASTVALUE,
				sweepRangeY.getLeadVariable(), Probe.LASTVALUE, variable,
				Probe.AVERAGE);
		pd.addPlotSurface(psd);
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase, true);
		pd.setType(Plot3DType.SURF);
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase + "_surf", true);
		pd.setType(Plot3DType.SURFL);
		ep.generateAll(pd, ep.getGraphDirectory(), outputBase + "_surfl", true);
	}

	/**
	 * Takes a DN experiment package.
	 * 
	 * For each discrete variable, generates a new plot based on the underlying
	 * N-type level.
	 * 
	 * Each plot has multiple variables, specified in the variables list.
	 * 
	 * @param ep
	 *            - the experiment package which is assumed to be a DN type
	 * @param variables
	 *            - the list of the variables to be plotted
	 * @param plotTitle
	 * @param yLabel
	 *            - the label displayed on the y axis for all the plots
	 * @param outputBase
	 *            - the prefix of all the plots to be generated, the name of the
	 *            discrete variable will be added to it.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void generateMultiVariableGraphs(ExperimentPackage ep,
			List<String> variables, String plotTitle, String yLabel,
			String outputBase) throws FileNotFoundException, IOException,
			ClassNotFoundException {
		ISOSource outputsource = ep.getSoSource();
		ParameterSweep sweepDiscrete = ep.getParameterSweeps().get(0);
		ParameterSweep sweepRange = ep.getParameterSweeps().get(1);
		List<List<SimulationOutput>> outputsList = outputsource
				.getTwoLevelsOfSOs(ep.getDirectory(), sweepDiscrete, sweepRange);
		for (int i = 0; i != sweepDiscrete.getList().size(); i++) {
			String discreteName = sweepDiscrete.getList().get(i).getName();
			final PlotDescription pd = new PlotDescription(plotTitle + " "
					+ discreteName, sweepRange.getLeadVariable(), yLabel, ep);
			for (String variable : variables) {
				pd.addPlotLine(new PlotLineDescription(sweepRange
						.getLeadVariable(), variable, ep
						.getVariableDescription(variable), outputsList.get(i)));
			}
			ep.generateAll(pd, ep.getGraphDirectory(), outputBase
					+ discreteName, false);
		}
	}

	/**
	 * 
	 * Takes a DNR experiment package. 
	 * 
	 * Generates one plot for each discrete variable. In each plot, it plots the 
	 * variables specified in the variable list.
	 * 
	 * @param variable
	 * @param plotTitle
	 * @param outputFile
	 */
	public static void generateMultiVariableAveragedGraphs(
			ExperimentPackage ep, List<String> variables, String plotTitle, String yLabel,
			String outputBase) {
		ISOSource outputsource = ep.getSoSource();
		ParameterSweep sweepDiscrete = ep.getParameterSweeps().get(0); // discrete
		// values
		ParameterSweep sweepRange = ep.getParameterSweeps().get(1); // the range
		ParameterSweep sweepRepetition = ep.getParameterSweeps().get(2); // the
		// repetion
		List<List<SimulationOutput>> outputsList = outputsource
				.getTwoLevelsOfSOsIntegrated(ep.getDirectory(), sweepDiscrete,
						sweepRange, sweepRepetition);
		for (int i = 0; i != sweepDiscrete.getList().size(); i++) {
			String discreteName = sweepDiscrete.getList().get(i).getName();
			final PlotDescription pd = new PlotDescription(plotTitle + " " + discreteName,
					sweepRange.getLeadVariable(), yLabel, ep);
			for (String variable : variables) {
				pd.addPlotLine(new PlotLineDescription(sweepRange
						.getLeadVariable(), variable, ep.getVariableDescription(variable), outputsList.get(i)));
			}
			ep.generateAll(pd, ep.getGraphDirectory(), outputBase + discreteName, true);
		}
	}

}
