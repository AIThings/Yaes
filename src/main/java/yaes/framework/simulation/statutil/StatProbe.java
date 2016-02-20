/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

import java.io.Serializable;
import java.util.Observable;

public abstract class StatProbe extends Observable implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 8410420271170243212L;
    protected boolean         broadcast;
    protected boolean         collect;
    protected double          maxValue;
    protected double          minValue;
    protected String          name;
    protected double          sumValue;

    public StatProbe() {
        collect = true;
        broadcast = false;
    }

    public abstract double average();

    /**
     * Added by BL Equals function, value based
     * 
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StatProbe)) {
            return false;
        }
        final StatProbe other = (StatProbe) o;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else {
            if (!name.equals(other.name)) {
                return false;
            }
        }
        if (maxValue != other.maxValue) {
            return false;
        }
        if (minValue != other.minValue) {
            return false;
        }
        if (sumValue != other.sumValue) {
            return false;
        }
        if (collect != other.collect) {
            return false;
        }
        if (broadcast != other.broadcast) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    /**
     * Hashcode, made such that it matches with the equals implementation.
     */
    @Override
    public int hashCode() {
        if (name == null) {
            return 0;
        } else {
            return name.hashCode();
        }
    }

    public abstract void init();

    public double max() {
        return maxValue;
    }

    public double min() {
        return minValue;
    }

    public abstract String report();

    public void setBroadcasting(boolean b) {
        broadcast = b;
    }

    public void setCollecting(boolean b) {
        collect = b;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double sum() {
        return sumValue;
    }
}
