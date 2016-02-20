package yaes.ui.simulationcontrol;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import yaes.framework.simulation.SimulationOutput;

/**
 * A panel for the output
 * 
 * <code>yaes.ui.simulationcontrol.PnlSimulationOutput</code>
 * 
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PnlSimulationOutput extends JPanel implements
        ListSelectionListener, ISimulationControlPanel {
    private static final long  serialVersionUID = -807674557920920174L;
    private TmSimulationOutput model;
    private SimulationOutput   so               = null;
    private JTable             table;
    private JTextArea          txtDetail;

    public PnlSimulationOutput() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(BorderFactory.createTitledBorder("Simulation output"));
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(this);
        model = new TmSimulationOutput();
        table.setModel(model);
        add(scrollPane);
        txtDetail = new JTextArea();
        final JScrollPane scrollPane2 = new JScrollPane(txtDetail);
        add(scrollPane2);
    }

    /**
     * @return
     */
    @Override
    public JPanel getPanel() {
        return this;
    }

    public void setSimulationOutput(SimulationOutput so) {
        this.so = so;
        model.setSimulationOutput(so);
    }

    /**
     * As a response to the selection change, it prints the value
     * 
     * @param arg0
     */
    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        int selection = table.getSelectedRow();
        if (selection != -1) {
            String variableName = model.getValueAt(selection, 0).toString();
            txtDetail.setText(so.getRandomVar(variableName).toString());
        } else {
            txtDetail.setText("Nothing selected...");
        }
    }

}
