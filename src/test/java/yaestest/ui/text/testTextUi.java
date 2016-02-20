/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Aug 19, 2010
 
   yaestest.ui.text.testTextUi
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaestest.ui.text;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import yaes.ui.text.TextUi;

/**
 * 
 * <code>yaestest.ui.text.testTextUi</code>
 * 
 * 
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class testTextUi {

    @Test
    public void testMenuNumerical() {
        List<String> items = new ArrayList<>();
        items.add("First");
        items.add("Second");
        items.add("Third");
        items.add("Fourth");
        int retval = TextUi.menu(items, 1, "choose one:");
        TextUi.println("Returned value:" + retval);
        TextUi.println("Returned value index:" + items.get(retval));
    }

    @Test
    public void testMenuStringBased() {
        List<String> items = new ArrayList<>();
        items.add("First");
        items.add("Second");
        items.add("Third");
        items.add("Fourth");
        String retval = TextUi.menu(items, "Second", "choose one:");
        TextUi.println("Returned value:" + retval);
    }

}
