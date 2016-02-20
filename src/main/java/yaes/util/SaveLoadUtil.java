package yaes.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import yaes.ui.text.TextUi;

public class SaveLoadUtil<KB> {

    public SaveLoadUtil() {

    }

    /**
     * Loads the knowledgebase from a file
     * 
     * @param file
     * @return the KB if successful, null if not
     */
    @SuppressWarnings("unchecked")
    public KB load(final File file) {
        KB theKB = null;
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream(file)))) {
            theKB = (KB) in.readObject();
            in.close();
        } catch (final FileNotFoundException e) {
            TextUi.errorPrint("FileNotFoundException while loading the KB from file: "
                    + file.getAbsolutePath());
            return null;
        } catch (final IOException e) {
            TextUi.errorPrint("IOException while loading the KB from file: "
                    + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        } catch (final ClassNotFoundException e) {
            TextUi.errorPrint("ClassNotFoundException while loading the KB from file: "
                    + file.getAbsolutePath());
            return null;
        } 
        
        return theKB;

    }

    /**
     * Saves the knowledgebase to a file.
     * 
     * @param theKB
     * @param file
     * @return true if successful, false if not
     */
    public boolean save(final KB theKB, final File file) {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(file)));
            out.writeObject(theKB);
            out.flush();
            out.close();
        } catch (final FileNotFoundException e) {
            TextUi.errorPrint("FileNotFoundException while saving the KB to file: "
                    + file.getAbsolutePath());
            return false;
        } catch (final IOException e) {
            TextUi.errorPrint("IOException while saving the KB to file: "
                    + file.getAbsolutePath());
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
