package yaes.ui.simulationcontrol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import yaes.framework.simulation.RandomVariable;
import yaes.framework.simulation.SimulationOutput;

/**
 * A table model attached to the simulation output
 * 
 * @author Lotzi Boloni
 * 
 */
public class TmSimulationOutput extends AbstractTableModel implements Observer {
    private static final long serialVersionUID = -1496655700314442693L;
    List<String>              names            = new ArrayList<>();
    private SimulationOutput  so;

    public TmSimulationOutput() {
        this.so = null;
    }

    /**
     * Returns the number of columns
     */
    @Override
    public int getColumnCount() {
        return 3;
    }

    /**
     * The names of the columns
     */
    @Override
    public String getColumnName(int column) {
        switch (column) {
        case 0:
            return "Name";
        case 1:
            return "Average";
        case 2:
            return "Update count";
        default:
            return "--- unknown ---";
        }
    }

    /**
     * Count of the rows
     */
    @Override
    public int getRowCount() {
        if (so == null) {
            return 0;
        }
        if (names.size() == so.getVariableNames().size()) {
            return names.size();
        }
        // there was an addition
        names = new ArrayList<>(so.getVariableNames());
        Collections.sort(names);
        return names.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        final String variableName = names.get(row);
        switch (column) {
        case 0:
            return variableName;
        case 1:
            return so.getValue(variableName, RandomVariable.Probe.AVERAGE);
        case 2:
            return so.getValue(variableName, RandomVariable.Probe.COUNT);
		default:
			break;
        }
        return null;
    }

    public void setSimulationOutput(SimulationOutput so) {
        this.so = so;
        so.addObserver(this);
        fireTableDataChanged();
    }

    @Override
    public void update(Observable observable, Object arg1) {
        if (so == observable) {
            fireTableDataChanged();
        }
    }
}
