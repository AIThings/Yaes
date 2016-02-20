/*
 * Created on Nov 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.world.physical.map;

import yaes.world.physical.location.Location;

/**
 * @author lboloni
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface IAccessibilityChecker {
    public boolean isAccessible(IMap map, Location location);
}
