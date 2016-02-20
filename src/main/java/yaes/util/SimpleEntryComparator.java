/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Jul 7, 2012
 
   yaes.util.SimpleEntryComparator
 
   Copyright (c) 2008-2010 Ladislau Boloni

   This package is licensed under the LGPL version 2.
*/
package yaes.util;

/**
 * 
 * <code>yaes.util.SimpleEntryComparator</code> 
 * @todo describe
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;

public class SimpleEntryComparator<T> implements
        Comparator<SimpleEntry<T, Double>> {
    @Override
    public int compare(SimpleEntry<T, Double> o1, SimpleEntry<T, Double> o2) {
        return o1.getValue().compareTo(o2.getValue());
    }
}