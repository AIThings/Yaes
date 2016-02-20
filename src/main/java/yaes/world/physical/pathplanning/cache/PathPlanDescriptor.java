/*
 * Created on Nov 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.world.physical.pathplanning.cache;

import java.io.Serializable;

import yaes.world.physical.location.Location;

/**
 * @author Lotzi Boloni
 * 
 */
public class PathPlanDescriptor implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -6488627509811861983L;

    private Location          destination;

    private String            mapName;

    private Location          source;

    boolean                   whiteAccessible;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PathPlanDescriptor)) {
            return false;
        }
        final PathPlanDescriptor other = (PathPlanDescriptor) o;
        return mapName.equals(other.mapName) && source.equals(other.source)
                && destination.equals(other.destination)
                && (whiteAccessible == other.whiteAccessible);
    }

    /**
     * @return Returns the destination.
     */
    public Location getDestination() {
        return destination;
    }

    /**
     * @return Returns the mapName.
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * @return Returns the source.
     */
    public Location getSource() {
        return source;
    }

    @Override
    public int hashCode() {
        return source.hashCode() + destination.hashCode();
    }

    /**
     * @return Returns the whiteAccessible.
     */
    public boolean isWhiteAccessible() {
        return whiteAccessible;
    }

    /**
     * @param destination
     *            The destination to set.
     */
    public void setDestination(Location destination) {
        this.destination = destination;
    }

    /**
     * @param mapName
     *            The mapName to set.
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * @param source
     *            The source to set.
     */
    public void setSource(Location source) {
        this.source = source;
    }

    /**
     * @param whiteAccessible
     *            The whiteAccessible to set.
     */
    public void setWhiteAccessible(boolean whiteAccessible) {
        this.whiteAccessible = whiteAccessible;
    }
}
