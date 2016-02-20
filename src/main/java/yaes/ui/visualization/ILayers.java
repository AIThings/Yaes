/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Apr 13, 2008
 
   yaes.ui.visualization.ILayers
 
   Copyright (c) 2008 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.ui.visualization;

/**
 * 
 * <code>yaes.ui.visualization.ILayers</code> This is an interface for the
 * constants regarding layers. Implement it in painters to get easy access to
 * these constants.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public interface ILayers {
    // these objects are painted on all layers
    public static final int ALL_LAYERS                 = -1;
    // things which happen over the background, eg. spreading of
    // forest fire
    public static final int BACKGROUND_EVENT_LAYER     = 8;
    // things in the background: picture or map
    public static final int BACKGROUND_LAYER           = 9;
    // this is where the communication events will go: they are
    // above the links, but under the objects (eg, flashing an arrow)
    public static final int COMMUNICATION_EVENTS_LAYER = 6;
    // this is where communication links go
    public static final int COMMUNICATION_LINKS_LAYER  = 7;
    // these are paintings which absolutely must go on
    // the foreground
    public static final int FOREGROUND_LAYER           = 0;
    // painting events of the objects
    public static final int OBJECT_EVENT_LAYER         = 4;
    // this is where typical objects would go
    public static final int OBJECT_LAYER               = 5;
}
