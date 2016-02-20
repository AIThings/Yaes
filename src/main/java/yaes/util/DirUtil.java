/*
 * Created on Apr 4, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.util;

import java.io.File;

import yaes.ui.text.TextUi;

public class DirUtil {
    /**
     * Guarantees that a given directory exists or exits!!!
     * 
     * @param dir
     * @return
     */
    public static File guaranteeDirectory(String dir) {
        final File outputDir = new File(dir);
        if (!outputDir.isDirectory()) {
            TextUi.errorPrint("Output directory: " + outputDir
                    + " does not exist!");
            if (!outputDir.mkdirs()) {
                TextUi.errorPrint("Creation of output directory: " + outputDir
                        + " unsuccessful.");
                System.exit(1);
            } else {
                return outputDir;
            }
        }
        return outputDir;
    }
}
