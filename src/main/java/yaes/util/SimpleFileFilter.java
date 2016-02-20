/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Aug 5, 2010
 
   yaes.util.SimpleFileFilter
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.util;

/**
 * 
 * <code>yaes.util.SimpleFileFilter</code> 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
import java.io.File;
import java.io.FileFilter;

public class SimpleFileFilter implements FileFilter {

    String   description;
    String[] extensions;

    public SimpleFileFilter(String ext) {
        this(new String[] { ext }, null);
    }

    public SimpleFileFilter(String[] exts, String descr) {
        // Clone and lowercase the extensions
        extensions = new String[exts.length];
        for (int i = exts.length - 1; i >= 0; i--) {
            extensions[i] = exts[i].toLowerCase();
        }
        // Make sure we have a valid (if simplistic) description
        description = descr == null ? exts[0] + " files" : descr;
    }

    @Override
    public boolean accept(File f) {
        // We always allow directories, regardless of their extension
        if (f.isDirectory()) {
            return true;
        }

        // Ok, its a regular file, so check the extension
        String name = f.getName().toLowerCase();
        for (int i = extensions.length - 1; i >= 0; i--) {
            if (name.endsWith(extensions[i])) {
                return true;
            }
        }
        return false;
    }

    public String getDescription() {
        return description;
    }
}
