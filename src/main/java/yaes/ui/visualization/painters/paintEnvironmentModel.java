package yaes.ui.visualization.painters;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import yaes.ui.visualization.VisualCanvas;
import yaes.ui.visualization.VisualizationProperties;
import yaes.world.physical.environment.EnvironmentModel;

/**
 * Paints the environment model.
 * 
 * The idea here is that it is painting in the background and immediately after
 * that. The background is the original background image of the environment
 * model (if there is any).
 * 
 * The different properties can be turned on and off. They are painted using
 * different colors and different TexturePaints.
 * 
 * Future enhancements: paint a legend and paint a bar at the bottom.
 * 
 * The painting is always done based on the rectangles.
 * 
 * 
 * @author Lotzi Boloni
 * 
 */
public class paintEnvironmentModel implements IPainter {

	/**
	 * For specific properties, it describes which v2c will be used 
	 * to paint them
	 */
	private Map<String, IValueToColor> v2cs = new HashMap<>();
	
	
	/**
	 * Specify for a given property what colors we will use to paint it...
	 * 
	 * @param propertyName
	 * @param v2c
	 */
	public void addV2C(String propertyName, IValueToColor v2c) {
		v2cs.put(propertyName, v2c);
	}
	
	
	@Override
	public int getLayer() {
		return IPainter.BACKGROUND_LAYER;
	}

	@Override
	public void paint(Graphics2D g, Object o, VisualCanvas panel) {
		EnvironmentModel em = (EnvironmentModel) o;
		paintBackground(g, em, panel);
		// paint the properties
		// FIXME: paint different properties with different color schemes
		for (String property : em.getProperties()) {
			// identify what color mapping we are going to use
			IValueToColor v2c = v2cs.get(property);
			if (v2c == null) {
				v2c = new DefaultValueToColor();
			}
			paintProperty(g, em, property, panel, v2c);
		}
	}

	/**
	 * Paints a specific property in form of a grid
	 * 
	 * FIXME: it does not obey the scrolling, only the scale!!!
	 * 
	 * @param g
	 * @param em
	 * @param property
	 * @param panel
	 */
	private void paintProperty(Graphics2D g, EnvironmentModel em,
			String property, VisualCanvas panel, IValueToColor v2c) {
		int dims[] = em.getIndex(em.getXHigh(), em.getYHigh());
		int xgrid = dims[0];
		int ygrid = dims[1];
		final Composite save = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				v2c.getTransparency()));
		double widthCell = panel.getTheTransform().getScaleX()
				* em.getXPreferredStep();
		double heightCell = panel.getTheTransform().getScaleY()
				* em.getYPreferredStep();
		double xLowCell = panel.getTheTransform().getScaleX() * em.getXLow();
		double yLowCell = panel.getTheTransform().getScaleY() * em.getYLow();
		for (int i = 0; i != xgrid; i++) {
			for (int j = 0; j != ygrid; j++) {
				double x = xLowCell + i * widthCell;
				double y = yLowCell + j * heightCell;
				double value = (Double) em.getPropertyAtIndex(property, i, j);
				// translate the value into color and texture
				Color color = v2c.getColor(value);
				// if the v2c returns null, it means we don't need to paint anything
				if (color != null) {
					Rectangle2D.Double cell = new Rectangle2D.Double(x, y,
							widthCell, heightCell);
					g.setColor(color);
					g.fill(cell);
				}
			}
		}
		g.setComposite(save);
	}


    /**
     * Paints the background image.
     * 
     * @param g
     * @param o
     * @param panel
     */
    public void paintBackground(Graphics2D g, EnvironmentModel em,
            VisualCanvas panel) {
        Image theImage = em.getBackgroundImage();
        if (theImage == null) {
//            TextUi.println("Background Error");
            return;
        }
        // the transform of the panel
        AffineTransform panelTransform = panel.getTheTransform();
        // transform which stretches
        AffineTransform stretchTransform = new AffineTransform();
        stretchTransform.scale(
                (em.getXHigh() - em.getXLow()) / theImage.getWidth(null),
                (em.getYHigh() - em.getYLow()) / theImage.getHeight(null));
        stretchTransform.translate(em.getXLow(), em.getYLow());
        stretchTransform.concatenate(panelTransform);
        g.drawImage(theImage, stretchTransform, null);
    }

	@Override
	public void registerParameters(
			VisualizationProperties visualizationProperties) {
		// TODO Auto-generated method stub

	}

}
