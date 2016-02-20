/*
 * Created on Sep 7, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.map;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import yaes.ui.text.TextUi;
import yaes.world.physical.location.Location;

/**
 * This class implements a map which represents a certain geographical
 * area where every point is either an inaccessible obstacle, or an 
 * area which can be accessed. 
 * 
 * @author Lotzi Boloni
 */
public class SimpleFreeGround implements IMap {
    /**
     * 
     */
    private static final long       serialVersionUID = 3869332629170480516L;
    private boolean                 accessible[][];
    private transient BufferedImage backgroundImage;
    private int                     depth;
    private transient BufferedImage image;
    private String                  mapName          = "nonameSimpleFreeGround";
    private boolean                 safe[][];
    private int                     width;
    private String                  backgroundImageFile;

    /**
     * Recover from reload
     */
    public SimpleFreeGround() {
        loadBackGroundImage(backgroundImageFile);
    }

    /**
     * Recover from reload
     */
    public SimpleFreeGround(String backgroundImageFile) {
        this.backgroundImageFile = backgroundImageFile;
        loadBackGroundImage(backgroundImageFile);
    }

    @Override
    public BufferedImage getBackgroundImage() {
        if ((backgroundImage == null) && (backgroundImageFile != null)) {
            loadBackGroundImage(backgroundImageFile);
        }
        return backgroundImage;
    }

    /**
     * @return
     */
    @Override
    public Image getBufferedImage() {
        return image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.freeground.IFreeGround#getLenght()
     */
    public int getDepth() {
        return depth;
    }

    /*
     * (non-Javadoc)
     * 
     * @see yaes.world.map.IMap#getMapName()
     */
    @Override
    public String getMapName() {
        return mapName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getProperties()
     */
    @Override
    public List<String> getProperties() {
        final ArrayList<String> list = new ArrayList<>();
        list.add(MapConstants.ACCESSIBLE);
        list.add(MapConstants.SAFE);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getPropertyAt(java.lang.String,
     * double, double)
     */
    @Override
    public Object getPropertyAt(String propertyName, double x, double y) {
        final int intX = (int) x;
        final int intY = (int) y;
        if (propertyName.equals(MapConstants.ACCESSIBLE)) {
            return new Boolean(accessible[intY][intX]);
        }
        if (propertyName.equals(MapConstants.SAFE)) {
            return new Boolean(safe[intY][intX]);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * simulation.environment.maps.IMap#getPropertyAtAsBoolean(java.lang.String,
     * simulation.environment.maps.Location)
     */
    @Override
    public boolean getPropertyAtAsBoolean(String propertyName, Location location) {
        final Boolean value = (Boolean) getPropertyAt(propertyName, location
                .getX(), location.getY());
        return value.booleanValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getPropertyClass(java.lang.String)
     */
    @Override
    public Class<?> getPropertyClass(String propertyName) {
        if (propertyName.equals(MapConstants.ACCESSIBLE)) {
            return Boolean.class;
        }
        if (propertyName.equals(MapConstants.SAFE)) {
            return Boolean.class;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getXHigh()
     */
    @Override
    public double getXHigh() {
        return width;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getXLow()
     */
    @Override
    public double getXLow() {
        return 0.0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getXPreferredStep()
     */
    @Override
    public double getXPreferredStep() {
        return 1.0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getYHigh()
     */
    @Override
    public double getYHigh() {
        return depth;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getYLow()
     */
    @Override
    public double getYLow() {
        return 0.0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#getYPreferredStep()
     */
    @Override
    public double getYPreferredStep() {
        return 1.0;
    }

    private void loadBackGroundImage(String imageFileName) {
        TextUi.println("Loading background image:" + imageFileName);
        final File imageFile = new File(imageFileName);
        if (!imageFile.exists()) {
            TextUi.errorPrint("Image file:" + imageFileName
                    + " does not exists");
            throw new Error("Can not load image");
        }
        try {
            backgroundImage = ImageIO.read(new File(imageFileName));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function initializes the internal accessibility structures 
     * from a map. 
     * 
     * @param imageFileName
     */
    public void loadImage(String imageFileName) {
        assert imageFileName != null;
        TextUi.println(imageFileName);
        final File imageFile = new File(imageFileName);
        if (!imageFile.exists()) {
            TextUi.errorPrint("Image file:" + imageFileName
                    + " does not exists");
            throw new Error("Can not load image");
        }
        try {
            image = ImageIO.read(new File(imageFileName));
            // ColorModel cm = image.getColorModel();
            accessible = new boolean[image.getHeight()][image.getWidth()];
            safe = new boolean[image.getHeight()][image.getWidth()];
            width = image.getWidth();
            depth = image.getHeight();
            for (int i = 0; i != image.getHeight(); i++) {
                for (int j = 0; j != image.getWidth(); j++) {
                    final int pixel = image.getRGB(j, i);
                    final Color c = new Color(pixel);
                    final int red = c.getRed();
                    final int blue = c.getBlue();
                    final int green = c.getGreen();
                    // very dark: inaccessible
                    // System.out.println("x =" + j + " y = " + i + "(" + red +
                    // ", " + green + ", " + blue + ")");
                    if ((red < 5) && (green < 5) && (blue < 5)) {
                        accessible[i][j] = false;
                    } else {
                        accessible[i][j] = true;
                    }
                    // very while: safe
                    if ((red > 250) && (green > 250) && (blue > 250)) {
                        safe[i][j] = true;
                    } else {
                        safe[i][j] = false;
                    }
                }
            }
            mapName = imageFileName;
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulation.environment.maps.IMap#setPropertyAt(java.lang.String,
     * double, double, java.lang.Object)
     */
    @Override
    public void setPropertyAt(String propertyName, double x, double y,
            Object value) {
        throw new Error("Not implemented");
    }
}