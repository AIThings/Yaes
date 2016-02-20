package yaes.world.physical.location;

/**
 * 
 * <code>yaes.world.map.IObjectWithLocation</code> Implements an object with a
 * location
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public interface IMoving {
    Location getLocation();
    void setLocation(Location location);
}
