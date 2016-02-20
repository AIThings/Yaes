/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jun 23, 2009
 
   yaes.ui.format.text.tostrParameterSweep
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.format.text;

import yaes.framework.simulation.parametersweep.ParameterSweep;
import yaes.framework.simulation.parametersweep.ScenarioDistinguisher;
import yaes.ui.format.IDetailLevel;

/**
 * 
 * <code>yaes.ui.format.text.tostrParameterSweep</code> The formatting of the
 * parameter sweep class
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class tostrParameterSweep implements IDetailLevel {

    public static String toStringDetailed(ParameterSweep parameterSweep,
            int detailLevel) {
        switch (detailLevel) {
        case MIN_DETAIL:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case MAX_DETAIL:
            return tostrParameterSweep.toStringMax(parameterSweep);
		default:
			break;
        }
        throw new Error("Invalid detail level");
    }

    /**
     * @param parameterSweep
     * @return
     */
    private static String toStringMax(ParameterSweep parameterSweep) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ParameterSweep " + parameterSweep.getLabel() + " ("
                + parameterSweep.getType() + ")\n");
        if (parameterSweep.getDescription() != null) {
            buffer.append("   // " + parameterSweep.getDescription() + "\n");
        }
        switch (parameterSweep.getType()) {
        case Range:
            buffer.append("   lead variable: "
                    + parameterSweep.getLeadVariable() + "\n");
            break;
        case Repetition:
            buffer.append("   lead variable: "
                    + parameterSweep.getLeadVariable() + "\n");
            break;
        case Discrete: {
            String value = "   items: {";
            for (ScenarioDistinguisher sd : parameterSweep.getList()) {
                value = value + sd.getName() + ", ";
            }
            buffer.append(value.substring(0, value.length() - 2) + "}\n");
            break;
        }
		default:
			break;

        }
        return buffer.toString();
    }

}
