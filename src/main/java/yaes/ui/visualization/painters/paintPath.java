/*
 * Created on Nov 24, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.ui.visualization.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import yaes.ui.visualization.ILayers;
import yaes.ui.visualization.VisualCanvas;
import yaes.ui.visualization.VisualizationProperties;
import yaes.world.physical.location.Location;
import yaes.world.physical.path.Path;

public class paintPath implements IPainter, ILayers {

    private static final float      dash[]            = { 5.0f, 5.0f };
    public static final BasicStroke LINE_THICK_DASHED = new BasicStroke(
                                                              paintPath.width,
                                                              0, 0, 1.0f,
                                                              paintPath.dash,
                                                              0.0f);

    public static final BasicStroke LINE_THIN         = new BasicStroke();
    public static final BasicStroke LINE_THIN_DASHED  = new BasicStroke(1, 0,
                                                              0, 1.0f,
                                                              paintPath.dash,
                                                              0.0f);
    private static final float      width             = (float) 3.0;
    private Color                   color             = Color.BLACK;
    private Stroke                  stroke;

    /**
     * The default constructor
     */
    public paintPath() {
        this.color = Color.black;
        this.stroke = paintPath.LINE_THIN;
    }

    /**
     * Constructor specifying the stroke and color
     */
    public paintPath(Color color, Stroke stroke) {
        this.color = color;
        this.stroke = stroke;
    }

    /**
     * @return Returns the color.
     */
    public Color getColor() {
        return color;
    }

    @Override
    public int getLayer() {
        return ILayers.COMMUNICATION_LINKS_LAYER;
    }

    /**
     * @return Returns the stroke.
     */
    public Stroke getStroke() {
        return stroke;
    }

    /*
     * (non-Javadoc)
     * 
     * @see yaes.ui.visualization.IPainter#paint(java.awt.Graphics2D,
     * java.lang.Object)
     */
    @Override
    public void paint(Graphics2D g, Object o, VisualCanvas panel) {
        final Path plannedPath = (Path) o;
        // TextUi.println("Painting path " + o);
        Location lastLocation = null;
        g.setStroke(stroke);
        g.setColor(color);
        final GeneralPath gp = new GeneralPath();
        synchronized (plannedPath) {
            for (final Location element : plannedPath.getLocationList()) {
                if (lastLocation != null) {
                    Point2D.Double point = new Point2D.Double((float) element
                            .getX(), (float) element.getY());
                    panel.getTheTransform().transform(point, point);
                    gp.lineTo(point.x, point.y);
                } else {
                    lastLocation = element;
                    Point2D.Double point = new Point2D.Double(
                            (float) lastLocation.getX(), (float) lastLocation
                                    .getY());
                    panel.getTheTransform().transform(point, point);
                    gp.moveTo(point.x, point.y);
                }
            }
        }

        g.draw(gp);
    }

    /**
     * @param visualizationProperties
     */
    @Override
    public void registerParameters(
            VisualizationProperties visualizationProperties) {
        // not registering anything, not parameter dependent
    }

    /**
     * @param color
     *            The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @param stroke
     *            The stroke to set.
     */
    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }
}
