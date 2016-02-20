package yaes.world.physical.environment;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import yaes.ui.format.Formatter;
import yaes.ui.plot.MatlabUtil;
import yaes.ui.text.TextUi;
import yaes.world.physical.location.Location;
import yaes.world.physical.map.IMap;

/**
 * A general purpose environment model which uses a grid based model of values.
 * 
 * @author Lotzi Boloni
 * 
 */
public class EnvironmentModel implements IMap {

    /**
     * @param mapName
     * @param xlow
     * @param ylow
     * @param xhigh
     * @param yhigh
     */
    public EnvironmentModel(String mapName, double xlow, double ylow,
            double xhigh, double yhigh, double xPreferredStep,
            double yPreferredStep) {
        super();
        this.mapName = mapName;
        this.xhigh = xhigh;
        this.xlow = xlow;
        this.yhigh = yhigh;
        this.ylow = ylow;
        this.xPreferredStep = xPreferredStep;
        this.yPreferredStep = yPreferredStep;
    }

    /**
     * Called at the de-serialization
     */
    public EnvironmentModel() {

    }

    private static final long serialVersionUID = -3268661009397297974L;
    /**
     * The name of the map
     */
    private String mapName;
    /**
     * The highest x coordinate (in meters)
     */
    private double xhigh;
    /**
     * The lowest x coordinate (in meters)
     */
    private double xlow;
    /**
     * The highest y coordinate (in meters)
     */
    private double yhigh;
    /**
     * The lowests y coordinate (in meters)
     */
    private double ylow;
    /**
     * Background image - transient - because it can not make it serializable
     * anyhow
     */
    private transient BufferedImage backgroundImage;
    /**
     * Background image filename
     */
    private File backgroundImageFile;
    /**
     * The data stores
     */
    private Map<String, double[][]> data = new HashMap<>();
    /**
     * The grid size on the x coordinate
     */
    private double xPreferredStep = 1.0;
    /**
     * The grid size on the y coordinate
     */
    private double yPreferredStep = 1.0;

    /**
     * Utility function, to set a property to a
     * 
     * @return
     */

    @Override
    public double getXHigh() {
        return xhigh;
    }

    @Override
    public double getXLow() {
        return xlow;
    }

    @Override
    public double getYHigh() {
        return yhigh;
    }

    @Override
    public double getYLow() {
        return ylow;
    }

    @Override
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    public Image getBufferedImage() {
        return null;
    }

    @Override
    public String getMapName() {
        return mapName;
    }

    @Override
    public List<String> getProperties() {
        List<String> retval = new ArrayList<>();
        retval.addAll(data.keySet());
        return retval;
    }

    @Override
    public Object getPropertyAt(String propertyName, double x, double y) {
        // check if it is in the range
        double val[][] = data.get(propertyName);
        if (val == null) {
            throw new Error("Could not find property: " + propertyName);
        }
        if (x < xlow || x > xhigh || y < ylow || y > yhigh) {
            return -1.0;
        }
        int[] indexes = getIndex(x, y);
        return val[indexes[0]][indexes[1]];
    }

    /**
     * Direct access to a cell - do this if you know what you doing (eg. in the
     * painter)
     * 
     * @param propertyName
     * @param i
     * @param j
     * @return
     */
    public Object getPropertyAtIndex(String propertyName, int i, int j) {
        double val[][] = data.get(propertyName);
        return val[i][j];
    }

    @Override
    public boolean
            getPropertyAtAsBoolean(String propertyName, Location location) {
        throw new Error("EnvironmentModel does not support boolean properties");
    }

    /**
     * Returns the property name.
     */
    @Override
    public Class<?> getPropertyClass(String propertyName) {
        if (getProperties().contains(propertyName)) {
            return Double.class;
        }
        return null;
    }

    /**
     * Returns the preferred step on the x coordinate. In the environment model
     * this is the grid size of the internal representation
     */
    @Override
    public double getXPreferredStep() {
        return xPreferredStep;
    }

    /**
     * Returns the preferred step on the y coordinate. In the environment model
     * this is the grid size of the internal representation
     */
    @Override
    public double getYPreferredStep() {
        return yPreferredStep;
    }

    /**
     * Sets a property. If x and y fall outside the area, quietly does not do
     * anything
     * 
     * @param propertyName
     * @param x
     * @param y
     * @param value
     */
    @Override
    public void setPropertyAt(String propertyName, double x, double y,
            Object value) {
        double val[][] = data.get(propertyName);
        int[] indexes = getIndex(x, y);
        // if it is outside the area, it returns false
        if (indexes == null) {
            return;
        }
        val[indexes[0]][indexes[1]] = (Double) value;
        return;
    }

    /**
     * Returns the indeces in the grids where the value for the specific
     * location must be looked up
     * 
     * @param x
     * @param y
     * @return
     */
    public int[] getIndex(double x, double y) {
        int xindex = 0;
        int yindex = 0;
        int[] retval = new int[2];
        // check the input parameters
        if (x < xlow) {
            return null;
        }
        if (x > xhigh) {
            return null;
        }
        if (y < ylow) {
            return null;
        }
        if (y > yhigh) {
            return null;
        }
        // calculate the grid transformation
        xindex = (int) ((x - xlow) / xPreferredStep);
        yindex = (int) ((y - ylow) / yPreferredStep);
        retval[0] = xindex;
        retval[1] = yindex;
        return retval;
    }

    /**
     * Creates a property
     */
    public void createProperty(String propertyName) {
        // the size of the property is the index of xHigh and yHigh
        int[] dims = getIndex(xhigh, yhigh);
        double[][] propertyData = new double[dims[0] + 1][dims[1] + 1];
        data.put(propertyName, propertyData);
    }

    /**
     * Loads the background image from the specified file. It also keeps the
     * file for later use.
     * 
     * @param backgroundImageFile
     * @throws IOException
     */
    public void loadBackgroundImage(File backgroundImageFile)
            throws IOException {
        this.backgroundImageFile = backgroundImageFile;
        if (!backgroundImageFile.exists()) {
            TextUi.errorPrint("Background image file " + backgroundImageFile
                    + " does not exist!");
            return;
        }
        backgroundImage = ImageIO.read(backgroundImageFile);
        if (backgroundImage == null) {
            TextUi.errorPrint("Could not load the background image from "
                    + backgroundImageFile);
            return;
        }
    }

    /**
     * Loads the data from an image file
     * 
     * @param propertyName
     * @param fileName
     */
    public void loadDataFromImage(String propertyName, File imageFile,
            IColorToValue c2v) {
        double val[][] = data.get(propertyName);
        if (!imageFile.exists()) {
            TextUi.errorPrint("Image file:" + imageFile.toString()
                    + " does not exists");
            throw new Error("Can not load image");
        }
        int[] dims = getIndex(xhigh, yhigh);
        int xgrid = dims[0];
        int ygrid = dims[1];
        try {
            BufferedImage image = ImageIO.read(imageFile);
            for (int i = 0; i != xgrid; i++) {
                for (int j = 0; j != ygrid; j++) {
                    int pixel = image.getRGB(i, j);
                    Color c = new Color(pixel);
                    // TextUi.println(c);
                    double value = c2v.value(c);
                    val[i][j] = value;
                    // TextUi.println(c + " value " + value);
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Overriding the de-serialization - the problem here is that the
     * backgroundImage is transitive, so if it was there we need to recreate it
     * here
     * 
     * @param ois
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        // if we need to, recreate the background here
        if (backgroundImageFile != null) {
            backgroundImage = ImageIO.read(backgroundImageFile);
        }
    }

    public void setPropertyToValue(String propertyName, double value) {
        double[][] val = data.get(propertyName);
        for (int i = 0; i < val.length; i++) {
            Arrays.fill(val[i], value);
        }
    }

    /**
     * Generates matlab code which stores a specific property in form of 
     * a data matrix and the x and y matrices. 
     * 
     * To be used in plotting and other postprocessing
     * 
     * @param varNameData 
     * @param varNameX
     * @param varNameY
     * @param propertyName
     * @return
     */
    public String getMatlabForm(String varNameData, String varNameX,
            String varNameY, String propertyName) {
        StringBuffer buf = new StringBuffer();
        double values[][] = data.get(propertyName);
        buf.append(varNameX + " = 1:" + values.length + "\n");
        buf.append(varNameY + " = 1:" + values[0].length + "\n");
        buf.append(MatlabUtil.getMatlabMatrix(varNameData, values) + "\n");
        return buf.toString();
    }

    /**
     * The toString function
     */
    @Override
	public String toString() {
        Formatter fmt = new Formatter();
        fmt.add("EnvironmentalModel:" + mapName);
        fmt.indent();
        fmt.is("xLow", xlow);
        fmt.is("xHigh", xhigh);
        fmt.is("yLow", ylow);
        fmt.is("yHigh", yhigh);
        fmt.is("xPreferredStep", xPreferredStep);
        fmt.is("yPreferredStep", yPreferredStep);
        fmt.add("List of properties: (" + getProperties().size() + ")");
        fmt.indent();
        for (String name : getProperties()) {
            fmt.add(name);
        }
        return fmt.toString();
    }

}
