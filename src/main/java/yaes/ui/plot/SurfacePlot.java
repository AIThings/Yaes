/*
 * Created on Apr 7, 2007
 *
 *
 */
package yaes.ui.plot;

import java.io.File;
import java.io.IOException;

import yaes.util.FileWritingUtil;
import yaes.world.physical.environment.EnvironmentModel;

/**
 * Utilities for drawing surfaces outside the SimulationOutput framework.
 * 
 * <code>yaes.ui.plot.SurfacePlot</code>
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class SurfacePlot {

    public enum SurfaceType {
        MESH, LIGHTED
    };

    /**
     * Creates the MATLAB code FIXME: the labels are not there!!!
     * 
     * @param surfaceType
     * @param x
     * @param xlabel
     * @param y
     * @param ylabel
     * @param z
     * @param zlabel
     * @return
     */
    private static String createSurfaceCommand(SurfaceType surfaceType,
            double[] x, String xlabel, double[] y, String ylabel, double[][] z,
            String zlabel) {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(MatlabUtil.getMatlabVector("x", x));
        buffer.append(MatlabUtil.getMatlabVector("y", y));
        buffer.append(MatlabUtil.getMatlabMatrix("z", z));
        addPlottingCommands(surfaceType, buffer);
        return buffer.toString();
    }
    
    /**
     * Adds the specific plotting commands to a buffer
     * @return
     */
    private static void addPlottingCommands(SurfaceType surfaceType, StringBuffer buffer) {
        switch (surfaceType) {
        case MESH:
            buffer.append("mesh(y,x,z)\n");
            break;
        case LIGHTED:
            buffer.append("surfl(y,x,z);\n");
            buffer.append("colormap('gray');\n");
            buffer.append("shading interp;\n");
            break;
		default:
			break;
        }        
    }
    

    /**
     * Generates a matlab file, which, when executed plots the
     * graph passed in the x,y,z variables
     * 
     * @param x
     * @param y
     * @param z
     * @param destinationFile
     * @throws IOException
     */
    public static void createSurfacePlot(SurfaceType surfaceType, double[] x,
            String xlabel, double[] y, String ylabel, double[][] z,
            String zlabel, File destinationFile) throws IOException {
        String command =
                SurfacePlot.createSurfaceCommand(surfaceType, x, xlabel, y,
                        ylabel, z, zlabel);
        FileWritingUtil.writeToTextFile(destinationFile, command);
    }

    /**
     * Create the surface plot for a specific property in a specific code
     * 
     * @param surfaceType
     */
    private static String createEMSurfaceCommand(SurfaceType surfaceType,
            EnvironmentModel em, String propertyName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(em.getMatlabForm("z", "x", "y", propertyName));
        addPlottingCommands(surfaceType, buffer);
        return buffer.toString();
    }


    /**
     * Generates a matlab file, which, when executed plots the
     * graph passed in the x,y,z variables
     * 
     * @param x
     * @param y
     * @param z
     * @param destinationFile
     * @throws IOException
     */
    public static void createEMSurfacePlot(SurfaceType surfaceType,
            EnvironmentModel em, String propertyName, File destinationFile) throws IOException {
        String command =
                SurfacePlot.createEMSurfaceCommand(surfaceType, em, propertyName);
        FileWritingUtil.writeToTextFile(destinationFile, command);
    }

    
}
