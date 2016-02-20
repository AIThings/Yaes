package yaes.world.physical.path;

import yaes.world.physical.location.Location;

/**
 * 
 * <code>yaes.world.map.PlannedPath</code>
 * 
 * A path which has a source and a destination - you start with this one and
 * then fill it in by a path planner algorithm.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class PlannedPath extends Path {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Location          destination;
    private Location          source;

    public PlannedPath() {
        super();
    }

    /**
     * @param source
     * @param destination
     */
    public PlannedPath(Location source, Location destination) {
        super();
        this.source = source;
        this.destination = destination;
    }

    /**
     * @return Returns the destination.
     */
    public Location getDestination() {
        return destination;
    }

    /**
     * Returns the lenght of a path (by iterating through)
     * 
     * @return
     */
    public double getPathLenght() {
        double length = source.distanceTo(locations.get(0));
        for (int i = 0; i != locations.size() - 1; i++) {
            length = length + locations.get(i).distanceTo(locations.get(i + 1));
        }
        length = length
                + locations.get(locations.size() - 1).distanceTo(destination);
        return length;
    }

    /**
     * @return Returns the source.
     */
    public Location getSource() {
        return source;
    }

    /**
     * @param destination
     *            The destination to set.
     */
    public void setDestination(Location destination) {
        this.destination = destination;
    }

    /**
     * @param source
     *            The source to set.
     */
    public void setSource(Location source) {
        this.source = source;
    }
}