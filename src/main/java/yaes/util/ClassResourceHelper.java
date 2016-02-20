/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 3, 2012
 
   yaes.util.ClassResourceHelper
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
*/
package yaes.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import yaes.ui.text.TextUi;

/**
 * 
 * <code>yaes.util.ClassResourceHelper</code> 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class ClassResourceHelper {
    /**
     * Returns a string
     * 
     * @param resource
     * @return
     */
    public static String getResourceContent(Object object, String resource) {
        URL testURL = object.getClass().getResource(resource);
        String testFile = null;
        try {
            testFile = new URI(testURL.toString()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            // should not normally happen
        }
        File file = new File(testFile);
        if (!file.exists()) {
            throw new Error("File: " + testFile + " does not exist");
        }
        return testFile;
    }

    /**
     * Returns a string
     * 
     * @param resource
     * @return
     * @throws URISyntaxException
     */
    @SuppressWarnings("rawtypes")
    public static File getResourceFile(Object object, String resource) {
        URL urlL = null;
        if (object instanceof Class) {
            urlL = ((Class) object).getResource(resource);
        } else {
            urlL = object.getClass().getResource(resource);
        }
        if (urlL == null) {
            TextUi.errorPrint("Could not find resource:" + resource);
            throw new Error("Could not find resource:" + resource);
        }
        String testFile = null;
        try {
            testFile = new URI(urlL.toString()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File file = new File(testFile);
        return file;
    }

}
