/*
   This file is part of the Golyoscsapagy project
   Created on: Nov 25, 2008
 
   golyoscsapagy.ui.text.IDetailLevel
 
   Copyright (c) 2008 Ladislau Boloni
 */
package yaes.ui.format;

/**
 * 
 * the ancestor class to all the classes which deal with detail levels in a way
 * or another
 * 
 * @author Ladislau Boloni (lotzi.boloni@gmail.com)
 */

public interface IDetailLevel {
    public static final int DTL_HUMAN_ONLY = 3;
    public static final int DTL_HUMAN_REC  = 2;

    public static final int DTL_NAME_ONLY  = 1;
    public static final int MAX_DETAIL     = 10;
    public static final int MIN_DETAIL     = 0;
}
