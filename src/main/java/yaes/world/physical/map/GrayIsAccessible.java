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
public class GrayIsAccessible implements IAccessibilityChecker {
    /*
     * (non-Javadoc)
     * 
     * @see yaes.world.map.IAccessibilityChecker#isAccessible(yaes.framework
     * .world.map.IMap, yaes.world.map.Location)
     */
    @Override
    public boolean isAccessible(IMap map, Location location) {
        if (map.getPropertyAtAsBoolean(MapConstants.ACCESSIBLE, location) == false) {
            return false;
        }
        return true;
    }
}
