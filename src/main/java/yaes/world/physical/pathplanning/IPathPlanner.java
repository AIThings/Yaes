/*
 * Created on Sep 9, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.world.physical.pathplanning;

import yaes.world.physical.map.IMap;
import yaes.world.physical.path.PlannedPath;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public interface IPathPlanner {
    boolean planPath(PlannedPath path, IMap map);
}
