/*
 * Created on Apr 7, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import yaes.ui.text.TextUi;

public class FileWritingUtil {
    /**
     * A helper function, which very agressively tries to find a place to write
     * the output stream
     * 
     * @param fileName
     * @return
     */
    public static FileOutputStream createOutputStream(File file) {
        final File dir = file.getParentFile();
        if ((dir != null) && !dir.exists()) {
            dir.mkdir();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
        } catch (final FileNotFoundException e) {
            TextUi.println("Can not create file:" + file);
            file = TextUi.inputFile("Enter new file:", false);
            try {
                out = new FileOutputStream(file);
            } catch (final FileNotFoundException e1) {
                throw new Error("This is still not working.");
            }
            e.printStackTrace();
        }
        return out;
    }

    /**
     * Writes the buffer to a Matlab file
     * 
     * When this is done in parallel running, sometimes we are getting exceptions here...
     * this is not a good idea to stop the program.
     * 
     * @param file
     * @param buffer
     * @throws IOException
     */
    public static void writeToTextFile(File file, String text) {
        try {
            final FileOutputStream out =
                    FileWritingUtil.createOutputStream(file);
            final PrintWriter pw = new PrintWriter(out);
            pw.write(text);
            pw.close();
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
            // TextUi.println("One can not recover from this, exiting...");
            //System.exit(1);
        }
    }
}
