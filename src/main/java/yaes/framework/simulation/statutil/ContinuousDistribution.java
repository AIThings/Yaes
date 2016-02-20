/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

public abstract class ContinuousDistribution implements Distribution {
    protected static final double EPSARRAY[] = { 0.5D, 0.050000000000000003D,
            0.0050000000000000001D, 0.00050000000000000001D,
            5.0000000000000002E-005D, 5.0000000000000004E-006D,
            4.9999999999999998E-007D, 4.9999999999999998E-008D,
            5.0000000000000001E-009D, 5.0000000000000003E-010D,
            5.0000000000000002E-011D, 4.9999999999999997E-012D,
            4.9999999999999999E-013D, 5.0000000000000002E-014D, 5E-015D,
            5.0000000000000004E-016D, 4.9999999999999999E-017D,
            5.0000000000000004E-018D, 5.0000000000000004E-019D,
            4.9999999999999999E-020D, 4.9999999999999997E-021D,
            4.9999999999999995E-022D, 5.0000000000000002E-023D,
            4.9999999999999998E-024D, 4.9999999999999996E-025D,
            5.0000000000000002E-026D, 5.0000000000000002E-027D,
            5.0000000000000002E-028D, 4.9999999999999999E-029D,
            4.9999999999999997E-030D, 5.0000000000000004E-031D,
            5.0000000000000004E-032D, 5.0000000000000003E-033D,
            5.0000000000000003E-034D, 4.9999999999999996E-035D, 5E-036D };

    protected static final double XBIG       = 1000D;

    protected static final double XINF       = 1.7976931348623157E+308D;

    public int                    decPrec;

    protected double              xa0;
    protected double              xb0;

    public ContinuousDistribution() {
        decPrec = 15;
        xa0 = -8D;
        xb0 = 8D;
    }

    @Override
    public double barF(double x) {
        return 1.0D - cdf(x);
    }

    public abstract double density(double d);

    // private int getDecPrec() {
    // return decPrec;
    // }
    @Override
    public double inverseF(double u) {
        // int MAXITER = 100;
        final double EPSILON = ContinuousDistribution.EPSARRAY[decPrec];
        // double XLIM = 8.9884656743115785E+307D;
        double x = 0.0D;
        // boolean detail = false;
        if ((u > 1.0D) || (u < 0.0D)) {
            throw new IllegalArgumentException("u not in [0, 1]");
        }
        if (decPrec > 15) {
            throw new IllegalArgumentException("decPrec too large");
        }
        if (decPrec <= 0) {
            throw new IllegalArgumentException("decPrec <= 0");
        }
        if (u <= 0.0D) {
            x = -1.7976931348623157E+308D;
            return x;
        }
        if (u >= 1.0D) {
            x = 1.7976931348623157E+308D;
            return x;
        }
        double xb = xb0;
        double xa = xa0;
        double yb = cdf(xb);
        double ya = cdf(xa);
        if (yb < ya) {
            throw new IllegalArgumentException("F is decreasing");
        }
        for (; (yb < u) && (xb < 8.9884656743115785E+307D); yb = cdf(xb)) {
            xa = xb;
            ya = yb;
            xb *= 2D;
        }
        for (; (ya > u) && (xa > -8.9884656743115785E+307D); ya = cdf(xa)) {
            xb = xa;
            yb = ya;
            xa *= 2D;
        }
        yb -= u;
        ya -= u;
        boolean fini = false;
        int i = 0;
        do {
            if (fini) {
                break;
            }
            x = (xa + xb) / 2D;
            final double y = cdf(x) - u;
            if ((Math.abs(y) <= EPSILON)
                    || (Math.abs((xb - xa) / (x + 2.2204460492503131E-016D)) <= EPSILON)) {
                fini = true;
            } else if (y * ya < 0.0D) {
                xb = x;
            } else {
                xa = x;
            }
            if (++i > 100) {
                fini = true;
            }
        } while (true);
        return x;
    }
}
