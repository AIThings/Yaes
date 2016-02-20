/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Feb 18, 2009
 
   yaes.framework.simulation.parametersweep.ParameterSweep
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep;

import java.util.ArrayList;
import java.util.List;

import yaes.ui.format.ToStringDetailed;
import yaes.ui.format.text.tostrParameterSweep;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.ParameterSweep</code> A class
 * containing a list of scenario distinguishers and other information for their
 * plotting
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ParameterSweep implements ToStringDetailed {

    public enum ParameterSweepType {
        Discrete, Range, Repetition
    };

    private String                      description;
    private String                      label;
    private String                      leadVariable;
    private List<ScenarioDistinguisher> list;
    private ParameterSweepType          type = ParameterSweepType.Discrete;

    /**
     * Constructor for discrete sweep, no lead variable
     */
    public ParameterSweep(String label) {
        this.label = label;
        this.leadVariable = null;
        list = new ArrayList<>();
    }

    /**
     * @param label
     * @param list
     */
    public ParameterSweep(String label, String leadVariable,
            List<ScenarioDistinguisher> list) {
        this.label = label;
        this.leadVariable = leadVariable;
        this.list = list;
    }

    /**
     * @param sd1
     */
    public void addDistinguisher(ScenarioDistinguisher sd1) {
        list.add(sd1);
    }

    /**
     * @param string
     * @return
     */
    public ScenarioDistinguisher createDistinguisher(String name) {
        ScenarioDistinguisher sd = new ScenarioDistinguisher(name);
        list.add(sd);
        return sd;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the leadVariable
     */
    public String getLeadVariable() {
        return leadVariable;
    }

    /**
     * @return the list
     */
    public List<ScenarioDistinguisher> getList() {
        return list;
    }

    /**
     * @return the type
     */
    public ParameterSweepType getType() {
        return type;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(ParameterSweepType type) {
        this.type = type;
    }

    /**
     * @param detailLevel
     * @return
     */
    @Override
    public String toStringDetailed(int detailLevel) {
        return tostrParameterSweep.toStringDetailed(this, detailLevel);
    }

}
