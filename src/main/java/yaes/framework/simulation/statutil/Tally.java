/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

import java.io.Serializable;

import umontreal.iro.lecuyer.util.PrintfFormat;

public class Tally extends StatProbe implements Cloneable, Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 6355935766466610976L;
    private int               numObs;
    private double            sumSquares;

    public Tally() {
        init();
    }

    public Tally(String name) {
        this.name = name;
        init();
    }

    public void add(double x) {
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
        if (broadcast) {
            setChanged();
            notifyObservers(new Double(x));
        }
    }

    @Override
    public double average() {
        if (numObs > 0) {
            return sumValue / numObs;
        } else {
            return 0.0D;
        }
    }

    @Override
    public Object clone() {
        Tally o = null;
        try {
            o = (Tally) super.clone();
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
            throw new RuntimeException(
                    "Calling confidenceIntervalStudent with < 2 Observations");
        } else {
            centerAndRadius[0] = average();
            final double t = StudentDist.inverseF(numObs - 1,
                    0.5D * (level + 1.0D));
            centerAndRadius[1] = t * Math.sqrt(variance() / numObs);
            return;
        }
    }

    /**
     * Added by BL Equals function, value based
     * 
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tally)) {
            return false;
        }
        final Tally other = (Tally) o;
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

    public String formatConfidenceIntervalStudent(double level) {
        final PrintfFormat str = new PrintfFormat();
        final double ci[] = new double[2];
        confidenceIntervalStudent(level, ci);
        str.append("  " + 100D * level + "%");
        str.append(" confidence interval for mean: (");
        str.append(10, 3, 2, ci[0] - ci[1]).append(',');
        str.append(10, 3, 2, ci[0] + ci[1]).append(" )\n");
        return str.toString();
    }

    @Override
    public void init() {
        maxValue = -1.0D / 0.0D;
        minValue = 1.0D / 0.0D;
        sumValue = 0.0D;
        sumSquares = 0.0D;
        numObs = 0;
    }

    public int numberObs() {
        return numObs;
    }

    @Override
    public String report() {
        final PrintfFormat str = new PrintfFormat();
        str.append("REPORT on Tally stat. collector ==> " + name);
        str.append("\n         min        max      average    standard dev.  ");
        str.append("num. obs\n");
        str.append(13, 3, 2, minValue);
        str.append(12, 3, 2, maxValue);
        str.append(11, 3, 2, average());
        str.append(13, 3, 2, standardDeviation());
        str.append(13, numObs).append('\n');
        return str.toString();
    }

    public String reportAndConfidenceIntervalStudent(double level) {
        final PrintfFormat str = new PrintfFormat();
        str.append(report());
        str.append(formatConfidenceIntervalStudent(level));
        return str.toString();
    }

    public double standardDeviation() {
        return Math.sqrt(variance());
    }

    public double variance() {
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
}
