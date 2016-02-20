package yaes.ui.visualization.painters;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

import yaes.ui.visualization.VisualCanvas;
import yaes.util.DrawArrow;
import yaes.world.physical.location.Location;

/**
 * A class containing static functions for painting stuff
 * 
 * @author Lotzi Boloni
 * 
 */
public class PainterHelper {

    /**
     * Returns the point transformed according to the transformation of the
     * panel
     * 
     * @param location
     * @param panel
     * @return
     */
    public static Point2D.Double locationToTransformedPoint(Location location,
            VisualCanvas panel) {
        Point2D.Double point = location.asPoint();
        panel.getTheTransform().transform(point, point);
        return point;
    }

    /**
     * Paints an arrow from a location to another location
     * 
     * @param sender
     * @param receiver
     * @param color
     * @param g
     * @param panel
     */
    public static void paintArrow(Location sender, Location receiver,
            Color color, Graphics2D g, VisualCanvas panel) {
        PainterHelper.paintArrow(sender, receiver, color, g, panel, 1.0f);
    }

    /**
     * Paints an arrow from a location to another location
     * 
     * @param sender
     * @param receiver
     * @param color
     * @param g
     * @param panel
     */
    public static void paintArrow(Location sender, Location receiver,
            Color color, Graphics2D g, VisualCanvas panel, double strokeWidth) {
        Point2D.Double senderPoint = PainterHelper.locationToTransformedPoint(
                sender, panel);
        Point2D.Double receiverPoint = PainterHelper
                .locationToTransformedPoint(receiver, panel);
        g.setColor(color);
        DrawArrow.drawArrow(g, (int) senderPoint.x, (int) senderPoint.y,
                (int) receiverPoint.x, (int) receiverPoint.y,
                (float) strokeWidth);
    }

    /**
     * Paints an arrow from a location to another location
     * 
     * @param sender
     * @param receiver
     * @param color
     * @param g
     * @param panel
     */
    public static void paintMiddleArrow(Location sender, Location receiver,
            Color color, Graphics2D g, VisualCanvas panel, double strokeWidth) {
        Point2D.Double senderPoint = PainterHelper.locationToTransformedPoint(
                sender, panel);
        Point2D.Double receiverPoint = PainterHelper
                .locationToTransformedPoint(receiver, panel);
        Point2D.Double middlePoint = new Point2D.Double(
                (senderPoint.x + receiverPoint.x) / 2,
                (senderPoint.y + receiverPoint.y) / 2);
        g.setColor(color);
        DrawArrow.drawArrow(g, (int) senderPoint.x, (int) senderPoint.y,
                (int) middlePoint.x, (int) middlePoint.y, (float) strokeWidth);
        g.drawLine((int) middlePoint.x, (int) middlePoint.y,
                (int) receiverPoint.x, (int) receiverPoint.y);

    }

    /**
     * Paints a HtmlLabel - all the stuff is supposed to be 
     * done in the Html
     * 
     * @param htmlText
     * @param location
     */
    public static Rectangle2D paintHtmlLabel(String htmlText,
            Location location, boolean centered, Graphics2D g, VisualCanvas panel) {
        return paintHtmlLabel(htmlText, location, 0, 0, Color.white, false, 0,
                null, centered, g, panel);
    }

    /**
     * Paints a label by creating a JEditorPane
     * 
     * It allows to paint complex labels
     * 
     * @param htmlText
     * @param point
     * @param deltaX
     * @param deltaY
     * @param backgroundColor
     * @param transparent
     * @param g
     * @param panel
     */
    public static Rectangle2D paintHtmlLabel(String htmlText,
            Location location, int deltaX, int deltaY, Color backgroundColor,
            boolean transparent, int borderWidth, Color borderColor,
            boolean centered, Graphics2D g, VisualCanvas panel) {
        // prepare the label in the HTML form
        JEditorPane label = new JEditorPane();
        label.setContentType("text/html");
        label.setText(htmlText);
        label.setEditable(false);
        Dimension size = label.getPreferredSize();
        label.setSize(size);
        if (!transparent) {
            label.setBackground(backgroundColor);
            label.setOpaque(true);
        }
        if (borderWidth != 0.0) {
            // g.setStroke(new BasicStroke(borderWidth));
            label.setBorder(BorderFactory.createLineBorder(borderColor,
                    borderWidth));
        }
        // now paint it
        AffineTransform saveAT = g.getTransform();
        Point2D.Double point = PainterHelper.locationToTransformedPoint(
                location, panel);
        Rectangle2D bounds = label.getBounds();
        if (centered) {
            point.x = point.x - bounds.getWidth() / 2;
            point.y = point.y - bounds.getHeight() / 2;
        }
        g.transform(AffineTransform.getTranslateInstance(point.x + deltaX,
                point.y + deltaY));
        label.paint(g);
        g.setTransform(saveAT);
        return bounds;
    }

    /**
     * Paint icon at location
     * 
     * This shape is intended to be a label, thus it does not follow the zooming
     * 
     * @param sender
     * @param receiver
     * @param color
     * @param g
     * @param panel
     */
    public static void paintImage(Location location, Image image, Graphics2D g,
            VisualCanvas panel) {
        Point2D.Double point = PainterHelper.locationToTransformedPoint(
                location, panel);
        ImageObserver obs = new ImageObserver() {

            @Override
            public boolean imageUpdate(Image arg0, int arg1, int arg2,
                    int arg3, int arg4, int arg5) {
                return false;
            }
        };
        double height = image.getHeight(obs);
        double width = image.getWidth(obs);
        point.x = point.x - width / 2;
        point.y = point.y - height / 2;
        AffineTransform saveAT = g.getTransform();
        // g.transform(AffineTransform.getTranslateInstance(point.x, point.y));
        g.drawImage(image,
                AffineTransform.getTranslateInstance(point.x, point.y), obs);
        g.setTransform(saveAT);
    }

    /**
     * Paints a label manually
     * 
     * @param label
     * @param g
     * @param x
     * @param y
     */
    public static void paintLabel(String label, Point2D.Double point,
            int deltaX, int deltaY, Graphics2D g, VisualCanvas panel) {
        final Font f = g.getFont();
        final int fontSize = 10;
        final Font f2 = f.deriveFont(Font.BOLD, fontSize);
        g.setFont(f2);
        Point2D.Double pointTr = new Point2D.Double();
        panel.getTheTransform().transform(point, pointTr);
        g.drawString(label, (int) pointTr.getX() + deltaX, (int) pointTr.getY()
                + deltaY);
    }

    /**
     * Paints a circular range, such as the transmission range, or sensing
     * range. It follows the zooming
     * 
     * @param location
     * @param range
     * @param drawBorder
     * @param borderColor
     * @param fill
     * @param fillColor
     * @param transparency
     * @param g
     * @param panel
     */
    public static void paintRange(Location location, double range,
            Color borderColor, Color fillColor, float transparency,
            Graphics2D g, VisualCanvas panel) {
        double transformedRange = panel.getTheTransform().getScaleX() * range;
        Point2D.Double center = PainterHelper.locationToTransformedPoint(
                location, panel);
        if (borderColor != null) {
            g.setColor(borderColor);
            g.drawOval((int) (center.x - range), (int) (center.y - range),
                    (int) (2 * range), (int) (2 * range));
        }
        if (fillColor != null) {
            g.setColor(fillColor);
            final Composite save = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    transparency));
            g.fillOval((int) (center.x - transformedRange),
                    (int) (center.y - transformedRange),
                    (int) (2 * transformedRange), (int) (2 * transformedRange));
            g.setComposite(save);
        }
    }

    /**
     * 
     * Paints a specific rectangle which follows the zooming
     * 
     * @param rectangle
     * @param drawBorder
     * @param borderColor
     * @param drawFill
     * @param fillColor
     * @param transparency
     * @param g
     * @param panel
     */
    public static void paintRectangle(Rectangle2D.Double rectangle,
            boolean drawBorder, Color borderColor, boolean drawFill,
            Color fillColor, float transparency, Graphics2D g,
            VisualCanvas panel) {
        Point2D.Double startPoint = new Point2D.Double(rectangle.getMinX(),
                rectangle.getMinY());
        panel.getTheTransform().transform(startPoint, startPoint);
        double width = panel.getTheTransform().getScaleX()
                * rectangle.getWidth();
        double height = panel.getTheTransform().getScaleY()
                * rectangle.getHeight();
        Rectangle2D.Double newRect = new Rectangle2D.Double(startPoint.x,
                startPoint.y, width, height);
        if (drawFill) {
            g.setColor(fillColor);
            final Composite save = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    transparency));
            g.fill(newRect);
            g.setComposite(save);
        }
        if (drawBorder) {
            g.setColor(borderColor);
            g.draw(newRect);
        }
    }

    /**
     * Compatibility function, for transparency 1
     * 
     * @param rectangle
     * @param drawBorder
     * @param borderColor
     * @param drawFill
     * @param fillColor
     * @param transparency
     * @param g
     * @param panel
     */
    public static void paintRectangle(Rectangle2D.Double rectangle,
            boolean drawBorder, Color borderColor, boolean drawFill,
            Color fillColor, Graphics2D g, VisualCanvas panel) {
        PainterHelper.paintRectangle(rectangle, drawBorder, borderColor,
                drawFill, fillColor, (float) 1.0, g, panel);
    }

    /**
     * Paints a rectangle at a specific location
     * 
     * @param location
     * @param size
     * @param drawBorder
     * @param borderColor
     * @param drawFill
     * @param fillColor
     * @param g
     * @param panel
     */
    public static void paintRectangleAtLocation(Location location, int size,
            Color borderColor, Color fillColor, Graphics2D g, VisualCanvas panel) {
        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, size, size);
        PainterHelper.paintShapeAtLocation(location, rectangle, borderColor,
                fillColor, g, panel);
    }

    /**
     * Paints a shape at a certain location. The shape will be positioned with
     * its center at the location.
     * 
     * This shape is intended to be a label, thus it does not follow the zooming
     * 
     * @param location
     * @param s
     * @param drawBorder
     * @param borderColor
     * @param drawFill
     * @param fillColor
     * @param g
     * @param panel
     */
    public static void paintShape(Shape s, PaintSpec pspec, Graphics2D g,
            VisualCanvas panel) {
        Shape ts = panel.getTheTransform().createTransformedShape(s);
        if (pspec.doFill()) {
            pspec.applyFill(g);
            g.fill(ts);
        }
        if (pspec.doBorder()) {
            pspec.applyBorder(g);
            g.draw(ts);
        }
    }

    /**
     * Paints a shape at a certain location. The shape will be positioned with
     * its center at the location.
     * 
     * This shape is intended to be a label, thus it does not follow the zooming
     * 
     * @param location
     * @param s
     * @param drawBorder
     * @param borderColor
     * @param drawFill
     * @param fillColor
     * @param g
     * @param panel
     */
    public static void paintShapeAtLocation(Location location, Shape s,
            Color borderColor, Color fillColor, Graphics2D g, VisualCanvas panel) {
        Point2D.Double point = PainterHelper.locationToTransformedPoint(
                location, panel);
        Rectangle2D bounds = s.getBounds2D();
        point.x = point.x - bounds.getWidth() / 2;
        point.y = point.y - bounds.getHeight() / 2;
        AffineTransform saveAT = g.getTransform();
        g.transform(AffineTransform.getTranslateInstance(point.x, point.y));
        if (fillColor != null) {
            g.setColor(fillColor);
            g.fill(s);
        }
        if (borderColor != null) {
            g.setStroke(new BasicStroke());
            g.setColor(borderColor);
            g.draw(s);
        }
        g.setTransform(saveAT);
    }

}
