package yaes.ui.visualization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import yaes.ui.format.IDetailLevel;

/**
 * This will normally appear on the right side of the visual map and allows the
 * inspection of the individual elements.
 * 
 * For the time being: a list on the top, a text area in the bottom.
 * 
 * @author Lotzi Boloni
 * 
 */
public class Inspector implements ListSelectionListener, IDetailLevel,
		ActionListener {

	private JButton guiButtonRefresh;
	Object currentlySelected;
	private Map<Object, IInspector> inspectors;
	@SuppressWarnings("rawtypes")
	private JList guiListObjects;
	@SuppressWarnings("rawtypes")
	private DefaultListModel guiListModelObjects;
	private List<Object> objects;
	private Observer observer = new Observer() {

		@Override
		public void update(Observable arg0, Object arg1) {
			if (arg0 == currentlySelected) {
				updateLongText();
			}
		}
	};
	private JPanel panel;
	private Visualizer parent;
	private JSlider slDetail;
	private JTextArea textArea;

	/**
	 * Create the panel
	 * 
	 * @param parent
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Inspector(Visualizer parent) {
		this.parent = parent;
		// initialize the data structures
		objects = new ArrayList<>();
		inspectors = new HashMap<>();
		// visual setup
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder("Object inspector"));
		//
		// this panel has a button for the Html and a slider which
		// determines what detail level is going to be visualized at the bottom
		//
		JPanel pnlDetail = new JPanel();
		pnlDetail.setLayout(new BoxLayout(pnlDetail, BoxLayout.X_AXIS));
		guiButtonRefresh = new JButton("Refresh");
		guiButtonRefresh.addActionListener(this);
		pnlDetail.add(guiButtonRefresh);
		slDetail = new JSlider();
		slDetail.setMinimum(IDetailLevel.MIN_DETAIL);
		slDetail.setMaximum(IDetailLevel.MAX_DETAIL);
		pnlDetail.add(slDetail);
		panel.add(pnlDetail);

		// the list of the objects
		guiListObjects = new JList();
		guiListObjects.addListSelectionListener(this);
		guiListModelObjects = new DefaultListModel();
		guiListObjects.setModel(guiListModelObjects);
		final JScrollPane scrlList = new JScrollPane(guiListObjects);
		panel.add(scrlList);
		textArea = new JTextArea(20, 20);
		textArea.setEditable(false);
		final JScrollPane scrTextArea = new JScrollPane(textArea);
		panel.add(scrTextArea);
	}

	/**
	 * Called whenever some kind of event happens
	 * 
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(guiButtonRefresh)) {
			guiListObjects.setModel(guiListModelObjects);
			updateLongText();
		}
	}

	/**
	 * Adds an object to the inspector -if it is an observable object,
	 * 
	 * @param o
	 */
	@SuppressWarnings("unchecked")
	public void addObject(Object o, IInspector inspector) {
		guiListModelObjects.addElement(inspector.getLineDescriptor(o));
		objects.add(o);
		inspectors.put(o, inspector);
		if (o instanceof Observable) {
			((Observable) o).addObserver(observer);
		}
	}

	/**
	 * @return the currentlySelected
	 */
	public Object getCurrentlySelected() {
		return currentlySelected;
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
     * 
     */
	public void removeAllObjects() {
		// objects.clear();
		// inspectors.clear();
		while (!objects.isEmpty()) {
			Object o = objects.get(0);
			removeObject(o);
		}
	}

	/**
	 * Removes the element from the inspector
	 * 
	 * FIXME: this is not quite the right way here, because the inspector line
	 * desciptor might have changed
	 * 
	 * @param o
	 */
	public void removeObject(Object o) {
		if (o instanceof Observable) {
			((Observable) o).deleteObserver(observer);
		}
		IInspector inspector = inspectors.get(o);
		if (inspector == null) {
			return;
		}
		guiListModelObjects.removeElement(inspector.getLineDescriptor(o));
		objects.remove(o);
	}

	/**
	 * Updates the detail description
	 */
	public void updateLongText() {
		if (currentlySelected == null) {
			currentlySelected = objects.get(0);
		}
		final IInspector inspector = inspectors.get(currentlySelected);
		textArea.setText(inspector.getLongDescriptor(currentlySelected,
				slDetail.getValue()));
		textArea.setCaretPosition(0);
		// update the visualizer as well
		parent.getVisualCanvas().update();
	}

	/**
	 * The list selection changed. Change the value in the area.
	 * 
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			textArea.setText("");
			return;
		}
		final int first = guiListObjects.getMinSelectionIndex();
		final int second = guiListObjects.getMaxSelectionIndex();
		if (first != second) {
			textArea.setText("Multiple objects selected");
			return;
		}
		if (first != -1) {
			currentlySelected = objects.get(first);
			updateLongText();
		} else {
			// nothing here, will be probably overwritten
		}
	}
}
