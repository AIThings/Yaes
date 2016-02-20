/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

public interface Distribution {
    public abstract double barF(double d);

    public abstract double cdf(double d);

    public abstract double inverseF(double d);
}
