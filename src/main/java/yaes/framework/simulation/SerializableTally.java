/**
 * @version 0.1
 * @author Majid Ali Khan
 */
package yaes.framework.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import umontreal.iro.lecuyer.stat.Tally;

/**
 * FIXME: in its current form this is a very expensive
 */
public class SerializableTally implements Serializable {
    static final long          serialVersionUID = -840833639260918900L;
    private transient Tally    tally            = null;
    private final List<Double> values;

    public SerializableTally() {
        values = new ArrayList<>();
    }

    public void add(double value) {
        values.add(new Double(value));
    }

    public double average() {
        if (tally == null) {
            updateTally();
        }
        return tally.average();
    }

    public void confidenceIntervalStudent(double percent, double[] confint) {
        if (tally == null) {
            updateTally();
        }
        tally.confidenceIntervalStudent(percent, confint);
    }

    public double max() {
        if (tally == null) {
            updateTally();
        }
        return tally.max();
    }

    public double min() {
        if (tally == null) {
            updateTally();
        }
        return tally.min();
    }

    public double sum() {
        if (tally == null) {
            updateTally();
        }
        return tally.sum();
    }

    private void updateTally() {
        tally = new Tally();
        for (final Double value : values) {
            tally.add(value.doubleValue());
        }
    }

    public double variance() {
        if (tally == null) {
            updateTally();
        }
        return tally.variance();
    }
}
