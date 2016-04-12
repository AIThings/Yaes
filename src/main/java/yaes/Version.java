package yaes;

import java.io.FileOutputStream;
import java.util.Properties;

public class Version {
    public static final String PROJECTNAME    = "YAES: Yet Another Extensible Simulator";
    /**
     * Description of the Field
     */
    public static String       VERSION_DATE   = "April 12, 2016";
    /**
     * Description of the Field
     */
    public static int          VERSION_MAJOR  = 0;
    /**
     * Description of the Field
     */
    public static int          VERSION_MICRO  = 80;
    /**
     * Description of the Field
     */
    public static int          VERSION_MINOR  = 9;
    // Use: Development, Alpha, Beta, Release
    /**
     * Description of the Field
     */
    public static String       VERSION_STATUS = "Development";

    /**
     * Creates a property file for the use of the ant generator
     * 
     * @param args
     *            The command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Created the version property:"
                    + Version.versionNumberString());
            final Properties property = new Properties();
            property.setProperty("version", Version.versionNumberString());
            final FileOutputStream fos = new FileOutputStream(
                    "version.properties");
            property.store(fos, "Yaes");
            fos.close();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description of the Method
     * 
     * @return Description of the Return Value
     */
    public static int versionNumber() {
        return 10000 * Version.VERSION_MAJOR + 100 * Version.VERSION_MINOR
                + Version.VERSION_MICRO;
    }

    /**
     * Description of the Method
     * 
     * @return Description of the Return Value
     */
    public static String versionNumberString() {
        return "" + Version.VERSION_MAJOR + "." + Version.VERSION_MINOR + "."
                + Version.VERSION_MICRO;
    }

    /**
     * Description of the Method
     * 
     * @return Description of the Return Value
     */
    public static String versionString() {
        return Version.PROJECTNAME + " " + Version.VERSION_MAJOR + "."
                + Version.VERSION_MINOR + "." + Version.VERSION_MICRO + " ("
                + Version.VERSION_DATE + ")";
    }

    public static String versionStringNoDate() {
        return Version.PROJECTNAME + " " + Version.VERSION_MAJOR + "."
                + Version.VERSION_MINOR + "." + Version.VERSION_MICRO;
    }
}