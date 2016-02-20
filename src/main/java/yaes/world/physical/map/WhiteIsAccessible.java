package yaes.world.physical.map;

import yaes.world.physical.location.Location;

/**
 * @author lboloni
 * 
 */
public class WhiteIsAccessible implements IAccessibilityChecker {
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
        } else {
            if (map.getPropertyAtAsBoolean(MapConstants.SAFE, location) == false) {
                return false;
            }
        }
        return true;
    }
}
