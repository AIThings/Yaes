/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

public class GammaDist extends ContinuousDistribution {
    public static double barF(double alpha, double lambda, int d, double x) {
        return GammaDist.barF(alpha, d, lambda * x);
    }

    public static double barF(double alpha, int d, double x) {
        final double PN[] = new double[6];
        final double ACU = ContinuousDistribution.EPSARRAY[d];
        // double PLIM = 100000000D;
        // double OFLO = 1E+030D;
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (d <= 0) {
            throw new IllegalArgumentException("d <= 0");
        }
        if (x <= 0.0D) {
            return 1.0D;
        }
        if (x >= alpha * 1000D) {
            return 0.0D;
        }
        if (alpha >= 100000000D) {
            return NormalDist.barF01((x - alpha) / Math.sqrt(alpha));
        }
        final double factor = Math.exp(alpha * Math.log(x) - x
                - Num.lnGamma(alpha));
        if ((x <= 1.0D) || (x < alpha)) {
            return 1.0D - GammaDist.cdf(alpha, d, x);
        }
        double A = 1.0D - alpha;
        double B = A + x + 1.0D;
        double TERM = 0.0D;
        PN[0] = 1.0D;
        PN[1] = x;
        PN[2] = x + 1.0D;
        PN[3] = x * B;
        double GIN = PN[2] / PN[3];
        do {
            int i;
            do {
                A++;
                B += 2D;
                TERM++;
                PN[4] = B * PN[2] - A * TERM * PN[0];
                PN[5] = B * PN[3] - A * TERM * PN[1];
                if (PN[5] != 0.0D) {
                    final double RN = PN[4] / PN[5];
                    final double DIF = Math.abs(GIN - RN);
                    if (DIF <= ACU * RN) {
                        return factor * GIN;
                    }
                    GIN = RN;
                }
                for (i = 0; i < 4; i++) {
                    PN[i] = PN[i + 2];
                }
            } while (Math.abs(PN[4]) < 1E+030D);
            i = 0;
            while (i < 4) {
                PN[i] /= 1E+030D;
                i++;
            }
        } while (true);
    }

    public static double cdf(double alpha, double lambda, int d, double x) {
        return GammaDist.cdf(alpha, d, lambda * x);
    }

    public static double cdf(double alpha, int d, double x) {
        // double PLIM = 100000000D;
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (d <= 0) {
            throw new IllegalArgumentException("d <= 0");
        }
        if (x <= 0.0D) {
            return 0.0D;
        }
        if (x >= alpha * 1000D) {
            return 1.0D;
        }
        if (alpha >= 100000000D) {
            return NormalDist.cdf01((x - alpha) / Math.sqrt(alpha));
        }
        final double factor = Math.exp(alpha * Math.log(x) - x
                - Num.lnGamma(alpha));
        final double ACU = ContinuousDistribution.EPSARRAY[d];
        if ((x <= 1.0D) || (x < alpha)) {
            double GIN = 1.0D;
            double TERM = 1.0D;
            double RN = alpha;
            do {
                RN++;
                TERM *= x / RN;
                GIN += TERM;
            } while (TERM >= ACU * GIN);
            GIN = GIN * factor / alpha;
            return GIN;
        } else {
            return 1.0D - GammaDist.barF(alpha, d, x);
        }
    }

    public static double density(double alpha, double lambda, double x) {
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (lambda <= 0.0D) {
            throw new IllegalArgumentException("lambda <= 0");
        }
        if (x <= 0.0D) {
            return 0.0D;
        }
        final double z = alpha * Math.log(lambda * x) - lambda * x
                - Num.lnGamma(alpha);
        if (z > -1000D) {
            return Math.exp(z) / x;
        } else {
            return 0.0D;
        }
    }

    public static double inverseF(double alpha, double lambda, int d, double u) {
        return GammaDist.inverseF(alpha, d, u) / lambda;
    }

    public static double inverseF(double alpha, int d, double u) {
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (d <= 0) {
            throw new IllegalArgumentException("d <= 0");
        }
        if ((u > 1.0D) || (u < 0.0D)) {
            throw new IllegalArgumentException("u not in [0,1]");
        }
        if (u <= 0.0D) {
            return 0.0D;
        }
        if (u >= 1.0D) {
            throw new ArithmeticException(
                    "inverse function cannot be positive infinity");
        }
        u = 1.0D - u;
        // double MACHEP = 1.1102230246251565E-016D;
        // double MAXLOG = 709.78271289338397D;
        // double MINLOG = -708.39641853226408D;
        // double MAXNUM = 1.7976931348623157E+308D;
        double x0 = 1.7976931348623157E+308D;
        double yl = 0.0D;
        double x1 = 0.0D;
        double yh = 1.0D;
        final double dithresh = 5.5511151231257827E-016D;
        double z = 1.0D / (9D * alpha);
        double y = 1.0D - z - NormalDist.inverseF01(u) * Math.sqrt(z);
        double x = alpha * y * y * y;
        double lgm = Num.lnGamma(alpha);
        boolean ihalve = false;
        label0: do {
            int i;
            if (ihalve) {
                ihalve = false;
                z = 0.0625D;
                if (x0 == 1.7976931348623157E+308D) {
                    if (x <= 0.0D) {
                        x = 1.0D;
                    }
                    do {
                        if (x0 != 1.7976931348623157E+308D) {
                            break;
                        }
                        x = (1.0D + z) * x;
                        y = GammaDist.barF(alpha, d, x);
                        if (y < u) {
                            x0 = x;
                            yl = y;
                            break;
                        }
                        z += z;
                    } while (true);
                }
                z = 0.5D;
                int dir = 0;
                for (i = 0; i < 400; i++) {
                    x = x1 + z * (x0 - x1);
                    y = GammaDist.barF(alpha, d, x);
                    lgm = (x0 - x1) / (x1 + x0);
                    if (Math.abs(lgm) < dithresh) {
                        break;
                    }
                    lgm = (y - u) / u;
                    if ((Math.abs(lgm) < dithresh) || (x <= 0.0D)) {
                        break;
                    }
                    if (y >= u) {
                        x1 = x;
                        yh = y;
                        if (dir < 0) {
                            dir = 0;
                            z = 0.5D;
                        } else if (dir > 1) {
                            z = 0.5D * z + 0.5D;
                        } else {
                            z = (u - yl) / (yh - yl);
                        }
                        dir++;
                        continue;
                    }
                    x0 = x;
                    yl = y;
                    if (dir > 0) {
                        dir = 0;
                        z = 0.5D;
                    } else if (dir < -1) {
                        z = 0.5D * z;
                    } else {
                        z = (u - yl) / (yh - yl);
                    }
                    dir--;
                }
                if (x == 0.0D) {
                    System.err.println("GammaDist.inverseF: underflow");
                }
                return x;
            }
            i = 0;
            do {
                if (i >= 13) {
                    break;
                }
                if ((x > x0) || (x < x1)) {
                    ihalve = true;
                    continue label0;
                }
                y = GammaDist.barF(alpha, d, x);
                if ((y < yl) || (y > yh)) {
                    ihalve = true;
                    continue label0;
                }
                if (y < u) {
                    x0 = x;
                    yl = y;
                } else {
                    x1 = x;
                    yh = y;
                }
                z = (alpha - 1.0D) * Math.log(x) - x - lgm;
                if (z < -709.78271289338397D) {
                    ihalve = true;
                    continue label0;
                }
                z = -Math.exp(z);
                z = (y - u) / z;
                if (Math.abs(z / x) < 1.1102230246251565E-016D) {
                    return x;
                }
                x -= z;
                i++;
            } while (true);
            return x;
        } while (true);
    }

    private double alpha;

    private double lambda;

    private double logFactor;

    public GammaDist(double alpha) {
        setParams(alpha, 1.0D, decPrec);
    }

    public GammaDist(double alpha, double lambda) {
        setParams(alpha, lambda, decPrec);
    }

    public GammaDist(double alpha, double lambda, int d) {
        setParams(alpha, lambda, d);
    }

    @Override
    public double cdf(double x) {
        return GammaDist.cdf(alpha, lambda, decPrec, x);
    }

    @Override
    public double density(double x) {
        if (x <= 0.0D) {
            return 0.0D;
        }
        final double z = logFactor + (alpha - 1.0D) * Math.log(x) - lambda * x;
        if (z > -1000D) {
            return Math.exp(z);
        } else {
            return 0.0D;
        }
    }

    public double getAlpha() {
        return alpha;
    }

    public double getLambda() {
        return lambda;
    }

    @Override
    public double inverseF(double u) {
        return GammaDist.inverseF(alpha, decPrec, u) / lambda;
    }

    public void setParams(double alpha, double lambda, int d) {
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (lambda <= 0.0D) {
            throw new IllegalArgumentException("lambda <= 0");
        } else {
            this.alpha = alpha;
            this.lambda = lambda;
            decPrec = d;
            logFactor = alpha * Math.log(lambda) - Num.lnGamma(alpha);
            xa0 = 0.0D;
            return;
        }
    }
}
