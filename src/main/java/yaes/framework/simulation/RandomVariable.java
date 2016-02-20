package yaes.framework.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import yaes.framework.simulation.statutil.StatProbe;
import yaes.framework.simulation.statutil.StudentDist;
import yaes.ui.format.Formatter;
import yaes.ui.plot.MatlabUtil;

/**
 * This class implements a random variable (almost the same thing as the SSJ
 * tally) but it is serializable and respects the Yaes call conventions.
 */
public class RandomVariable extends StatProbe implements Serializable {
    public enum Probe {
        AVERAGE, CONFINT_HIGH, CONFINT_LOW, CONFINT_RADIUS, COUNT, LASTVALUE,
        MAX, MIN, SUM, TIMESERIES, VALUE, VARIANCE
    }

    static final long              serialVersionUID  = 5130807822371431645L; ;
    private boolean                collectTimeSeries = false;
    private double                 lastValue;
    private int                    numObs;
    private double                 sumSquares;
    private List<TimestampedValue> timeSeries        = null;

    public RandomVariable(String name) {
        this.name = name;
        init();
    }

    /**
     * Required by the statistical probe interface
     */
    @Override
    public double average() {
        return getAvg();
    }

    @Override
    public Object clone() {
        RandomVariable o = null;
        try {
            o = (RandomVariable) super.clone();
        } catch (final CloneNotSupportedException e) {
            System.err.println("Tally can't clone");
        }
        if (name != null) {
            o.name = new String(name);
        }
        return o;
    }

    public void confidenceIntervalStudent(double level,
            double centerAndRadius[]) {
        if (numObs < 2) {
            /*
             * throw new RuntimeException(
             * "Calling confidenceIntervalStudent with < 2 Observations");
             */
            centerAndRadius[0] = average();
            centerAndRadius[1] = 0;
        } else {
            centerAndRadius[0] = average();
            final double t = StudentDist.inverseF(numObs - 1,
                    0.5D * (level + 1.0D));
            centerAndRadius[1] = t * Math.sqrt(getVariance() / numObs);
            return;
        }
    }

    /**
     * Disables the collection of the time series (but leaves the existing ones
     * in place).
     */
    public void disableTimeSeriesCollecting() {
        collectTimeSeries = false;
    }

    /**
     * Enables the time series collecting. The first time it is enabled, it also
     * creates the timestamp list
     */
    public void enableTimeSeriesCollecting() {
        collectTimeSeries = true;
        if (timeSeries == null) {
            timeSeries = new ArrayList<>();
        }
    }

    /**
     * Added by BL Equals function, value based
     * 
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RandomVariable)) {
            return false;
        }
        final RandomVariable other = (RandomVariable) o;
        // fall back to the equals of StatProbe
        if (!super.equals(o)) {
            return false;
        }
        if (numObs != other.numObs) {
            return false;
        }
        if (sumSquares != other.sumSquares) {
            return false;
        }
        return true;
    }

    /**
     * @return Returns the average
     */
    public double getAvg() {
        if (numObs > 0) {
            return sumValue / numObs;
        } else {
            return 0.0D;
        }
    }

    /**
     * Return the 95% confidence interval as center and radius
     * 
     * @return
     */
    public double[] getConfidenceInterval() {
        final double confint[] = new double[2];
        confidenceIntervalStudent(0.95, confint);
        return confint;
    }

    /**
     * Return the confidence interval as center and radius
     * 
     * @return
     */
    public double[] getConfidenceInterval(double percent) {
        final double confint[] = new double[2];
        confidenceIntervalStudent(percent, confint);
        return confint;
    }

    /**
     * Return the number of observations recorder
     * 
     * @return
     */
    public int getCount() {
        return numObs;
    }

    /**
     * @return Returns the max.
     */
    public double getMax() {
        return max();
    }

    /**
     * @return Returns the min.
     */
    public double getMin() {
        return min();
    }

    /**
     * @return Returns the name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns a single value - but it throws a runtime exception if the value
     * was updated more than one time
     * 
     * @return
     */
    private double getSingleValue() {
        final int count = getCount();
        if (count != 0) {
            throw new RuntimeException("Accessing a multiple times (" + count
                    + ") updated random variable " + name + " as single value!");
        }
        return getAvg();
    }

    /**
     * Returns the standard deviation
     * 
     * @return
     */
    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }

    /**
     * @return Returns the sum.
     */
    public double getSum() {
        return sum();
    }

    public List<TimestampedValue> getTimeSeries() {
        return timeSeries;
    }

    public double getValue(Probe probe) {
        switch (probe) {
        case VALUE:
            return getSingleValue();
        case AVERAGE:
            return getAvg();
        case SUM:
            return getSum();
        case COUNT:
            return getCount();
        case MAX:
            return max();
        case MIN:
            return min();
        case CONFINT_LOW:
            return getConfidenceInterval()[0] - getConfidenceInterval()[1];
        case CONFINT_HIGH:
            return getConfidenceInterval()[0] + getConfidenceInterval()[1];
        case CONFINT_RADIUS:
            return getConfidenceInterval()[1];
        case VARIANCE:
            return getVariance();
        case LASTVALUE:
            return lastValue;
		case TIMESERIES:
			throw new Error("Cannot return the value of a timeseries");
		default:
			break;
        }
        // probably not reachable
        return 0;
    }

    /**
     * Returns the variance on the random variable
     * 
     * @return
     */
    public double getVariance() {
        if (numObs < 2) {
            throw new RuntimeException(
                    "Calling variance with no enough Observations");
        }
        double temp = sumSquares - sumValue * sumValue / numObs;
        temp /= numObs - 1;
        if (temp < 0.0D) {
            return 0.0D;
        } else {
            return temp;
        }
    }

    @Override
    public void init() {
        maxValue = -1.0D / 0.0D;
        minValue = 1.0D / 0.0D;
        sumValue = 0.0D;
        sumSquares = 0.0D;
        numObs = 0;
        lastValue = 0.0D;
    }

    @Override
    public String report() {
        return toString();
    }

    /**
     * Formatted print
     */
    @Override
    public String toString() {
        Formatter fmt = new Formatter();
        String header = name + " (updates: " + getCount() + ")";
        if (collectTimeSeries) {
            header = header + " -collecting series)";
        } else {
            header = header + ")";
        }
        fmt.add(header);
        fmt.indent();
        fmt.add("last: " + Formatter.fmt(getValue(Probe.LASTVALUE)) + " avg: "
                + Formatter.fmt(getAvg()) + " sum:" + Formatter.fmt(getSum()));
        fmt.add("range [" + Formatter.fmt(getMin()) + ".." + Formatter.fmt(getMax())
                + "]");
        if (getCount() > 2) {
            double confint[] = getConfidenceInterval();
            fmt.add("conf int: [" + Formatter.fmt(confint[0] - confint[1]) + ".."
                    + Formatter.fmt(confint[0] + confint[1]) + "]");
        }
        if (collectTimeSeries) {
            List<Double> d = new ArrayList<>();
            for (TimestampedValue value : timeSeries) {
                d.add(value.getValue());
            }
            fmt.add(MatlabUtil.getMatlabVector("series", d));
        }
        return fmt.toString();
    }

    /**
     * Simplified update, assumes a single update per time step
     * 
     * @param x
     */
    public void update(double x) {
        update(x, numObs);
    }

    /**
     * Updates the random variable with a new observation
     * 
     * @param x
     */
    public void update(double x, double time) {
        lastValue = x;
        if (collect) {
            if (x < minValue) {
                minValue = x;
            }
            if (x > maxValue) {
                maxValue = x;
            }
            numObs++;
            sumValue += x;
            sumSquares += x * x;
        }
        // this one updates the time series
        if (collectTimeSeries) {
            timeSeries.add(new TimestampedValue(time, x));
        }
        if (broadcast) {
            setChanged();
            notifyObservers(new Double(x));
        }
    }
}
