package yaes.ui.visualization;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import yaes.ui.simulationcontrol.ISimulationControlPanel;
import yaes.ui.text.TextUi;
import yaes.ui.visualization.painters.IPainter;

/**
 * @author Lotzi Boloni
 * 
 *         A visualization panel. It has a VisualMapPanel object which presents
 *         a 2D visualization and a VisualMapInspector which is a text based
 *         inspector of the objects.
 * 
 * 
 */
public class Visualizer implements HierarchyListener, ISimulationControlPanel {

	/**
	 * Sets the font for all the UI components. Necessary on Ubuntu as the
	 * default font is both ugly and it has linespacing problems
	 * 
	 * setUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.ITALIC,12));
	 * 
	 * 
	 * @param f
	 */
	@SuppressWarnings("rawtypes")
	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		// sets the default font for all Swing components.
		// ex.
		//
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}

	private JFrame frame = null;
	IInspector genericInspector = new GenericInspector();
	private Inspector inspector = null;
	private JPanel panel;

	/**
	 * Shows whether the inspector will be shown. If the inspector is not shown,
	 * it is not updated either.
	 */
	private boolean showInspector = true;
	private JSplitPane splitPane = null;
	/**
	 * Shows whether the inspector will be used. It is set in the constructor.
	 * If it is not used, it cannot be enabled later.
	 * 
	 */
	private boolean useInspector = false;
	/**
	 * Shows whether the inspector is currently updated or not. If we are doing
	 * an uninterrupted running, there is no point updating the inspector.
	 */
	private boolean updatedInspector = false;
	private VisualCanvas visualCanvas = null;

	private VisualizationProperties visualizationProperties = new VisualizationProperties();

	private VisualizationPropertyEditor visualizationPropertyEditor = null;

	private VisualizerStatusBar visualizerStatusBar = null;

	private VisualizerToolBar visualizerToolBar = null;

	/**
	 * Simplified constructor, creates the inspector every time
	 * 
	 * @param width
	 * @param height
	 * @param icon
	 * @param title
	 */
	public Visualizer(int width, int height, String icon, String title) {
		this(width, height, icon, title, true);
	}

	/**
	 * Backwards compatibility
	 * 
	 * @param width
	 * @param height
	 * @param icon
	 * @param title
	 * @param useInspector
	 */
	public Visualizer(int width, int height, String icon, String title,
			boolean useInspector) {
		this(width, height, icon, title, useInspector, false);
	}

	/**
	 * Creates a visual map
	 * 
	 * @param width
	 * @param height
	 * @param icon
	 * @param title
	 * @param withInspector
	 */
	public Visualizer(int width, int height, String icon, String title,
			boolean useInspector, boolean panelOnly) {
		Visualizer.setUIFont(new javax.swing.plaf.FontUIResource("Arial",
				Font.PLAIN | Font.TRUETYPE_FONT, 14));
		this.useInspector = useInspector;
		this.updatedInspector = useInspector;
		this.showInspector = useInspector;
		// JFrame.setDefaultLookAndFeelDecorated(true);
		VisualizationProperties visualizationProperties = new VisualizationProperties();
		visualCanvas = new VisualCanvas(this, visualizationProperties);
		inspector = new Inspector(this);
		setupLayout(showInspector);
		if (!panelOnly) {
			frame = new JFrame();
			if (icon != null) {
				frame.setIconImage(new ImageIcon(icon).getImage());
			}
			frame.getContentPane().add(panel);
			frame.pack();
			// frame.setResizable(false);
			frame.setVisible(true);
			frame.setTitle(title);
			// frame.setLocationRelativeTo(null);
		}
	}

	/**
	 * Adds an object to visual display, and the inspector as well, if the
	 * inspector is there as well.
	 * 
	 * @param o
	 * @param painter
	 */
	public synchronized void addObject(Object o, IPainter painter) {
		painter.registerParameters(visualizationProperties);
		visualCanvas.addObject(o, painter);
		if (useInspector && updatedInspector) {
			inspector.addObject(o, genericInspector);
		}
	}

	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * @return the inspector
	 */
	public Inspector getInspector() {
		return inspector;
	}

	/**
	 * @return
	 */
	@Override
	public JPanel getPanel() {
		return panel;
	}

	public VisualCanvas getVisualCanvas() {
		return visualCanvas;
	}

	/**
	 * @return the visualizationProperties
	 */
	public VisualizationProperties getVisualizationProperties() {
		return visualizationProperties;
	}

	public void hideVisualizationProperties() {
		visualizationPropertyEditor.hideFrame();
		visualizationPropertyEditor = null;
	}

	/**
	 * @param arg0
	 */
	@Override
	public void hierarchyChanged(HierarchyEvent arg0) {
		if (splitPane != null) {
			splitPane.setDividerLocation(0.8);
		}
	}

	/**
	 * @return the updatedInspector
	 */
	public boolean isUpdatedInspector() {
		return updatedInspector;
	}

	/**
	 * Removes all objects from the visualization
	 * 
	 * @param o
	 */
	public synchronized void removeAllObjects() {
		visualCanvas.removeAllObjects();
		if (useInspector && updatedInspector) {
			inspector.removeAllObjects();
		}
	}

	/**
	 * Removes an object from the visualization
	 * 
	 * @param o
	 */
	public synchronized void removeObject(Object o) {
		visualCanvas.removeObject(o);
		if (useInspector && updatedInspector) {
			inspector.removeObject(o);
		}
	}

	/**
	 * Saves an image in the png format
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void saveImage(String fileName) throws IOException {
		final Rectangle rect = panel.getBounds();
		final Image fileImage = panel.createImage(rect.width, rect.height);
		final Graphics g = fileImage.getGraphics();
		// write to the image
		visualCanvas.paintVisualMap((Graphics2D) g);
		// write it out in the format you want
		ImageIO.write((RenderedImage) fileImage, "png", new File(fileName));
		// dispose of the graphics content
		g.dispose();
	}

	public void setDimension(int width, int height) {
		frame.setBounds(0, 0, width, height);
	}

	/**
	 * @param updatedInspector
	 *            the updatedInspector to set
	 */
	public void setUpdatedInspector(boolean updatedInspector) {
		this.updatedInspector = updatedInspector;
	}

	/**
	 * 
	 * Sets up the view with an inspector.
	 * 
	 * @param showInspector
	 */
	private void setupLayout(boolean showInspector) {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		visualizerToolBar = new VisualizerToolBar(this);
		panel.add(visualizerToolBar.getTheToolBar(), BorderLayout.NORTH);
		visualizerStatusBar = new VisualizerStatusBar(this);
		panel.add(visualizerStatusBar.getPanel(), BorderLayout.SOUTH);

		if (showInspector) {
			splitPane = new JSplitPane();
			splitPane.addHierarchyListener(this);
			splitPane.setLeftComponent(visualCanvas.getPanel());
			splitPane.setRightComponent(inspector.getPanel());
			splitPane.setDividerLocation(0.8);
			panel.add(splitPane, BorderLayout.CENTER);
		} else {
			panel.add(visualCanvas.getPanel(), BorderLayout.CENTER);
		}
	}

	/**
	 * @param b
	 */
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

	public void showVisualizationProperties() {
		if (visualizationPropertyEditor == null) {
			visualizationPropertyEditor = new VisualizationPropertyEditor(
					visualizationProperties, this);
		}
		visualizationPropertyEditor.showFrame();
	}

	/**
	 * Updates the visualizer
	 */
	synchronized public void update() {
		if (visualCanvas != null) {
			visualCanvas.update();
		}
		if (visualizerStatusBar != null) {
			visualizerStatusBar.update();
		}
	}

	/**
	 * Call this to update the inspector text, useful if it changes every
	 * timestep.
	 */
	synchronized public void updateInspector() {
		if (useInspector && updatedInspector) {
			try {
				inspector.updateLongText();
			} catch (NullPointerException e) {
				// Nothing selected
				TextUi.println("NPE in updateInspector");
			}
		}
	}
}