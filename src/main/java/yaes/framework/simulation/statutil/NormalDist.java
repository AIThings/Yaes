/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

public class NormalDist extends ContinuousDistribution {
    private static final double InvNormal2P1[] = { 16.030495584406623D,
            -90.784959262960328D, 186.44914861620987D, -169.00142734642381D,
            65.454662847944874D, -8.6421301158724777D, 0.17605878213905901D };

    private static final double InvNormal2P2[] = { -0.015238926344072612D,
            0.34445569241361251D, -2.9344398672542478D, 11.763505705217828D,
            -22.655292823101103D, 19.12133439658033D, -5.478927619598319D,
            0.237516689024448D                };

    private static final double InvNormal2P3[] = { 5.6451977709864484E-005D,
            0.0053504147487893018D, 0.12969550099727353D, 1.0426158549298266D,
            2.8302677901754492D, 2.6255672879448073D, 2.0789742630174919D,
            0.7271880623155681D, 0.066816807711804996D, -0.017791004575111759D,
            0.0022419563223346345D            };

    private static final double InvNormal2Q1[] = { 14.780647071513831D,
            -91.374167024260316D, 210.15790486205319D, -222.10254121855132D,
            107.60453916055124D, -20.601073032826545D, 1.0D };

    private static final double InvNormal2Q2[] = { -0.010846516960205995D,
            0.26106288858430787D, -2.406831810439376D, 10.695129973387015D,
            -23.71671552159658D, 24.640158943917285D, -10.01437634978307D, 1.0D };

    private static final double InvNormal2Q3[] = { 5.6451699862760653E-005D,
            0.0053505587067930651D, 0.12986615416911648D, 1.0542932232626492D,
            3.0379331173522206D, 3.7631168536405029D, 3.8782858277042012D,
            2.0372431817412178D, 1.0D         };

    // private static final int COEFFMAX = 24;
    private static double       NORMAL2_A[]    = { 0.61014308192320044D,
            -0.4348412727125775D, 0.17635119364360549D, -0.060710795609249413D,
            0.017712068995694115D, -0.0043211193855672942D,
            0.00085421667688709865D, -0.00012715509060916275D,
            1.1248167243671189E-005D, 3.1306388542182096E-007D,
            -2.70988068537762E-007D, 3.0737622701407687E-008D,
            2.5156203848176228E-009D, -1.0289299213203192E-009D,
            2.9944052119949941E-011D, 2.6051789687266936E-011D,
            -2.6348399241719693E-012D, -6.4340450989063649E-013D,
            1.1245740180166345E-013D, 1.7281533389986097E-014D,
            -4.264101694942375E-015D, -5.4537197788019098E-016D,
            1.5869760776167101E-016D, 2.0899837844334001E-017D,
            -5.900526869409E-018D, -9.4189338755399998E-019D,
            2.1497735646999999E-019D, 4.6660985008000001E-020D,
            -7.2430118620000002E-021D, -2.387966824E-021D,
            1.9117753499999999E-022D, 1.20482568E-022D,
            -6.7237700000000001E-025D, -5.7479969999999999E-024D,
            -4.2849300000000001E-025D, 2.4485600000000001E-025D, 4.3793E-026D,
            -8.1509999999999998E-027D, -3.0890000000000001E-027D,
            9.3000000000000002E-029D, 1.74E-028D, 1.6000000000000001E-029D,
            -8.0000000000000007E-030D, -2.0000000000000002E-030D };

    public static double barF(double mu, double sigma, double x) {
        if (sigma <= 0.0D) {
            throw new IllegalArgumentException("sigma <= 0");
        } else {
            return NormalDist.barF01((x - mu) / sigma);
        }
    }

    public static double barF01(double x) {
        final double A[] = { 0.61014308192320044D, -0.4348412727125775D,
                0.17635119364360549D, -0.060710795609249413D,
                0.017712068995694115D, -0.0043211193855672942D,
                0.00085421667688709865D, -0.00012715509060916275D,
                1.1248167243671189E-005D, 3.1306388542182096E-007D,
                -2.70988068537762E-007D, 3.0737622701407687E-008D,
                2.5156203848176228E-009D, -1.0289299213203192E-009D,
                2.9944052119949941E-011D, 2.6051789687266936E-011D,
                -2.6348399241719693E-012D, -6.4340450989063649E-013D,
                1.1245740180166345E-013D, 1.7281533389986097E-014D,
                -4.2641016949424002E-015D, -5.4537197787999998E-016D,
                1.5869760776000001E-016D, 2.0899837799999999E-017D,
                -5.9000000000000002E-018D };
        // double KK = 5.3033008588991066D;
        if (x >= 1000D) {
            return 0.0D;
        }
        if (x <= -1000D) {
            return 1.0D;
        }
        int neg;
        if (x >= 0.0D) {
            neg = 0;
        } else {
            neg = 1;
            x = -x;
        }
        final double t = (x - 5.3033008588991066D) / (x + 5.3033008588991066D);
        double y = Num.evalCheby(A, 24, t);
        y = y * Math.exp(-x * x / 2D) / 2D;
        if (neg == 1) {
            return 1.0D - y;
        } else {
            return y;
        }
    }

    public static double cdf(double mu, double sigma, double x) {
        if (sigma <= 0.0D) {
            throw new IllegalArgumentException("sigma <= 0");
        } else {
            return NormalDist.cdf01((x - mu) / sigma);
        }
    }

    public static double cdf01(double x) {
        if (x <= -1000D) {
            return 0.0D;
        }
        if (x >= 1000D) {
            return 1.0D;
        }
        x = -x / 1.4142135623730951D;
        double r;
        if (x < 0.0D) {
            x = -x;
            final double t = (x - 3.75D) / (x + 3.75D);
            r = 1.0D - 0.5D * Math.exp(-x * x)
                    * Num.evalCheby(NormalDist.NORMAL2_A, 24, t);
        } else {
            final double t = (x - 3.75D) / (x + 3.75D);
            r = 0.5D * Math.exp(-x * x)
                    * Num.evalCheby(NormalDist.NORMAL2_A, 24, t);
        }
        return r;
    }

    public static double density(double mu, double sigma, double x) {
        if (sigma <= 0.0D) {
            throw new IllegalArgumentException("sigma <= 0");
        } else {
            double diff = x - mu;
            return Math.exp(-diff * diff / (2D * sigma * sigma))
                    / (Math.sqrt(6.2831853071795862D) * sigma);
        }
    }

    public static double inverseF(double mu, double sigma, double u) {
        if (sigma <= 0.0D) {
            throw new IllegalArgumentException("sigma <= 0");
        } else {
            return mu + sigma * NormalDist.inverseF01(u);
        }
    }

    public static double inverseF01(double u) {
        if (u < 0.0D) {
            throw new IllegalArgumentException("u < 0");
        }
        if (u > 1.0D) {
            throw new IllegalArgumentException("u > 1");
        }
        if (u > 0.99999999999999978D) {
            return 8.1999999999999993D;
        }
        if (u < 2.2204460492503131E-016D) {
            return -8.1999999999999993D;
        }
        u = 2D * u - 1.0D;
        boolean negatif;
        if (u < 0.0D) {
            u = -u;
            negatif = true;
        } else {
            negatif = false;
        }
        double z;
        if (u <= 0.75D) {
            final double v = u * u - 0.5625D;
            double denom;
            double numer = denom = 0.0D;
            for (int i = 6; i >= 0; i--) {
                numer = numer * v + NormalDist.InvNormal2P1[i];
                denom = denom * v + NormalDist.InvNormal2Q1[i];
            }
            z = u * numer / denom;
        } else if (u <= 0.9375D) {
            final double v = u * u - 0.87890625D;
            double denom;
            double numer = denom = 0.0D;
            for (int i = 7; i >= 0; i--) {
                numer = numer * v + NormalDist.InvNormal2P2[i];
                denom = denom * v + NormalDist.InvNormal2Q2[i];
            }
            z = u * numer / denom;
        } else {
            final double v = 1.0D / Math.sqrt(-Math.log(1.0D - u));
            double denom;
            double numer = denom = 0.0D;
            for (int i = 10; i >= 0; i--) {
                numer = numer * v + NormalDist.InvNormal2P3[i];
            }
            for (int i = 8; i >= 0; i--) {
                denom = denom * v + NormalDist.InvNormal2Q3[i];
            }
            z = 1.0D / v * numer / denom;
        }
        if (negatif) {
            return -z * 1.4142135623730951D;
        } else {
            return z * 1.4142135623730951D;
        }
    }

    protected double mu;

    protected double sigma;

    public NormalDist() {
        setParams(0.0D, 1.0D);
    }

    public NormalDist(double mu, double sigma) {
        setParams(mu, sigma);
    }

    @Override
    public double barF(double x) {
        return NormalDist.barF01((x - mu) / sigma);
    }

    @Override
    public double cdf(double x) {
        return NormalDist.cdf01((x - mu) / sigma);
    }

    @Override
    public double density(double x) {
        double diff = x - mu;
        return Math.exp(-diff * diff / (2D * sigma * sigma))
                / (Math.sqrt(6.2831853071795862D) * sigma);
    }

    public double getMu() {
        return mu;
    }

    public double getSigma() {
        return sigma;
    }

    @Override
    public double inverseF(double u) {
        return mu + sigma * NormalDist.inverseF01(u);
    }

    public void setParams(double mu, double sigma) {
        if (sigma <= 0.0D) {
            throw new IllegalArgumentException("sigma <= 0");
        } else {
            this.mu = mu;
            this.sigma = sigma;
            return;
        }
    }
}
