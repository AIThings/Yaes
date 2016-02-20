package yaestest.world.physical.environment;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import yaes.ui.text.TextUi;
import yaes.ui.visualization.Visualizer;
import yaes.ui.visualization.painters.paintEnvironmentModel;
import yaes.util.ClassResourceHelper;
import yaes.world.physical.environment.EnvironmentModel;
import yaes.world.physical.environment.LinearColorToValue;

public class testEnvironmentModel {

    /**
     * Tests the manual loading and saving
     */
    @Test
    public void testManualSetting() {
        EnvironmentModel em =
                new EnvironmentModel("TheModel", 0, 0, 100, 100, 10, 10);
        em.createProperty("interest");
        TextUi.println(em);
        em.setPropertyAt("interest", 5, 5, 1.0);
        TextUi.println("interest(5,5) = " + em.getPropertyAt("interest", 5, 5));
        TextUi.println("interest(1,1) = " + em.getPropertyAt("interest", 1, 1));
        TextUi.println("interest(100,100) = "
                + em.getPropertyAt("interest", 100, 100));
        TextUi.println("interest(1000,1000) = "
                + em.getPropertyAt("interest", 1000, 1000));
    }

    /**
     * Tests the loading from an image
     */
    @Test
    public void testLoadFromImage() {
        URL url = this.getClass().getResource("/ThreeBlackPatches.png");
        File file = new File(url.getFile());

        // File file = ClassResourceHelper.getResourceFile(this,
        // "ThreeBlackPatches.png");
        EnvironmentModel em =
                new EnvironmentModel("TheModel", 0, 0, 1000, 1000, 10, 10);
        em.createProperty("interest");
        LinearColorToValue lctv = new LinearColorToValue(0, 100);
        em.loadDataFromImage("interest", file, lctv);
        for (double x = 0; x < 1000; x += 50) {
            double val = (double) em.getPropertyAt("interest", x, 500);
            TextUi.println("interest(" + x + ", 500) = " + val);
        }
    }

    /**
     * Tests the visualization
     */
    @Test
    public void testEMVisualization() {
        URL url = this.getClass().getResource("/ThreeBlackPatches.png");
        File file = new File(url.getFile());

        // File file = ClassResourceHelper.getResourceFile(this,
        // "ThreeBlackPatches.png");
        EnvironmentModel em =
                new EnvironmentModel("TheModel", 0, 0, 1000, 1000, 10, 10);
        em.createProperty("interest");
        LinearColorToValue lctv = new LinearColorToValue(0, 100);
        em.loadDataFromImage("interest", file, lctv);
        Visualizer vis = new Visualizer(800, 800, null, "Test");
        vis.addObject(em, new paintEnvironmentModel());
        vis.setVisible(true);
        TextUi.confirm("Exit?", true);
    }

    /**
     * Tests the visualization
     * 
     * @throws IOException
     */
    @Test
    public void testEMVisualizationLipari() throws IOException {
        URL url = this.getClass().getResource("/Lipari-patches.png");
        File fileLipariPatches = new File(url.getFile());

        url = this.getClass().getResource("/Lipari.png");
        File fileBackground = new File(url.getFile());
/*        File fileLipariPatches =
                ClassResourceHelper.getResourceFile(this, "Lipari-patches.jpg");
        File fileBackground =
                ClassResourceHelper.getResourceFile(this, "Lipari.png");
*/        EnvironmentModel em =
                new EnvironmentModel("TheModel", 0, 0, 654, 587, 1, 1);
        em.loadBackgroundImage(fileBackground);
        em.createProperty("interest");
        LinearColorToValue lctv = new LinearColorToValue(0, 100);
        em.loadDataFromImage("interest", fileLipariPatches, lctv);
        Visualizer vis = new Visualizer(800, 800, null, "Test");
        vis.addObject(em, new paintEnvironmentModel());
        vis.setVisible(true);
        TextUi.confirm("Exit?", true);
    }

}
