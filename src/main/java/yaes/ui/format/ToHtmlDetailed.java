/*
   This file is part of the Golyoscsapagy project
   Created on: Aug 7, 2008
 
   golyoscsapagy.ui.text.ToHtmlDetailed
 
   Copyright (c) 2008 Ladislau Boloni
 */
package yaes.ui.format;

/**
 * 
 * <code>golyoscsapagy.ui.text.ToHtmlDetailed</code>
 * 
 * The interface to be implemented by classes which provide printing to HTML at
 * various levels of detail
 * 
 * @author Ladislau Boloni (lotzi.boloni@gmail.com)
 */

public interface ToHtmlDetailed extends IDetailLevel {
    String toHtml();

    String toHtmlDetailed(int detailLevel);
}
