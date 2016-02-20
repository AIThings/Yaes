/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

public class BetaDist extends ContinuousDistribution {
    private static void backward(double alpha, double beta, double x,
            double I0, int d, int nmax, double I[]) {
        I[0] = I0;
        if (nmax == 0) {
            return;
        }
        final double epsilon = ContinuousDistribution.EPSARRAY[d];
        int nu = 2 * nmax + 5;
        int ntab;
        for (ntab = 64; ntab <= nu; ntab *= 2) {
            ;
        }
        double Rr[] = new double[ntab];
        double Iapprox[] = new double[ntab];
        double Itemp[] = new double[ntab];
        for (int n = 1; n <= nmax; n++) {
            Iapprox[n] = 0.0D;
        }
        for (int n = 0; n <= nmax; n++) {
            Itemp[n] = I[n];
        }
        boolean again;
        do {
            int n = nu;
            double r = 0.0D;
            do {
                r = (n - 1 + alpha + beta)
                        * x
                        / (n + alpha + (n - 1 + alpha + beta) * x - (n + alpha)
                                * r);
                if (n <= nmax) {
                    Rr[n - 1] = r;
                }
            } while (--n >= 1);
            for (n = 0; n < nmax; n++) {
                Itemp[n + 1] = Rr[n] * Itemp[n];
            }
            again = false;
            n = 1;
            do {
                if (n > nmax) {
                    break;
                }
                if (Math.abs((Itemp[n] - Iapprox[n]) / Itemp[n]) > epsilon) {
                    again = true;
                    for (int m = 1; m <= nmax; m++) {
                        Iapprox[m] = Itemp[m];
                    }
                    nu += 5;
                    if (ntab <= nu) {
                        ntab *= 2;
                        double nT[] = new double[ntab];
                        System.arraycopy(Rr, 0, nT, 0, Rr.length);
                        Rr = nT;
                        nT = new double[ntab];
                        System.arraycopy(Iapprox, 0, nT, 0, Iapprox.length);
                        Iapprox = nT;
                        nT = new double[ntab];
                        System.arraycopy(Itemp, 0, nT, 0, Itemp.length);
                        Itemp = nT;
                    }
                    break;
                }
                n++;
            } while (true);
        } while (again);
        for (int n = 0; n <= nmax; n++) {
            I[n] = Itemp[n];
        }
    }

    public static double barF(double alpha, double beta, double a, double b,
            int d, double x) {
        if (a >= b) {
            throw new IllegalArgumentException("a >= b");
        } else {
            return 1.0D - BetaDist.cdf(alpha, beta, d, (x - a) / (b - a));
        }
    }

    public static double barF(double alpha, double beta, int d, double x) {
        return 1.0D - BetaDist.cdf(alpha, beta, d, x);
    }

    private static void beta_alpha_fixed(double alpha, double beta, double x,
            int d, int nmax, double I[]) {
        if ((beta <= 0.0D) || (beta > 1.0D)) {
            throw new IllegalArgumentException("beta not in (0, 1]");
        }
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (nmax < 0) {
            throw new IllegalArgumentException("nmax < 0");
        }
        if ((x == 0.0D) || (x == 1.0D)) {
            for (int n = 0; n <= nmax; n++) {
                I[n] = x;
            }
            return;
        }
        if (x <= 0.5D) {
            BetaDist.isubx_alpha_fixed(alpha, beta, x, d, nmax, I);
        } else {
            BetaDist.isubx_beta_fixed(beta, alpha, 1.0D - x, d, nmax, I);
            for (int n = 0; n <= nmax; n++) {
                I[n] = 1.0D - I[n];
            }
        }
    }

    private static void beta_beta_fixed(double alpha, double beta, double x,
            int d, int nmax, double I[]) {
        if ((alpha <= 0.0D) || (alpha > 1.0D)) {
            throw new IllegalArgumentException("alpha not in (0, 1]");
        }
        if (beta <= 0.0D) {
            throw new IllegalArgumentException("beta <= 0");
        }
        if (nmax < 0) {
            throw new IllegalArgumentException("nmax < 0");
        }
        if ((x == 0.0D) || (x == 1.0D)) {
            for (int n = 0; n <= nmax; n++) {
                I[n] = x;
            }
            return;
        }
        if (x <= 0.5D) {
            BetaDist.isubx_beta_fixed(alpha, beta, x, d, nmax, I);
        } else {
            BetaDist.isubx_alpha_fixed(beta, alpha, 1.0D - x, d, nmax, I);
            for (int n = 0; n <= nmax; n++) {
                I[n] = 1.0D - I[n];
            }
        }
    }

    private static double beta_g(double x, int d) {
        if (x > 1.3D) {
            return -BetaDist.beta_g(1.0D / x, d);
        }
        if (x < 9.9999999999999995E-021D) {
            return 1.0D;
        }
        if (x < 0.69999999999999996D) {
            return (1.0D - x * x + 2D * x * Math.log(x))
                    / ((1.0D - x) * (1.0D - x));
        }
        if (x == 1.0D) {
            return 0.0D;
        }
        final double epsilon = ContinuousDistribution.EPSARRAY[d];
        final double y = 1.0D - x;
        double sum = 0.0D;
        double term = 1.0D;
        int j = 2;
        double inc;
        do {
            term *= y;
            inc = term / (j * (j + 1));
            sum += inc;
            j++;
        } while (Math.abs(inc / sum) > epsilon);
        return 2D * sum;
    }

    public static double cdf(double alpha, double beta, double a, double b,
            int d, double x) {
        return BetaDist.cdf(alpha, beta, d, (x - a) / (b - a));
    }

    public static double cdf(double alpha, double beta, int d, double x) {
        // double ALPHABETAMAX = 2000D;
        // double ALPHABETALIM = 30D;
        boolean flag = false;
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (beta <= 0.0D) {
            throw new IllegalArgumentException("beta <= 0");
        }
        if (d <= 0) {
            throw new IllegalArgumentException("d <= 0");
        }
        if (x <= 0.0D) {
            return 0.0D;
        }
        if (x >= 1.0D) {
            return 1.0D;
        }
        if (Math.max(alpha, beta) <= 2000D) {
            int n;
            double u;
            double I[];
            if (alpha < beta) {
                n = (int) alpha;
                double alpha0 = alpha - n;
                if (alpha0 <= 0.0D) {
                    alpha0 = 1.0D;
                    n--;
                }
                I = new double[n + 1];
                BetaDist.beta_beta_fixed(alpha0, beta, x, d, n, I);
                u = I[n];
                if (u <= 0.0D) {
                    return 0.0D;
                }
                if (u <= 1.0D) {
                    return u;
                } else {
                    return 1.0D;
                }
            }
            n = (int) beta;
            double beta0 = beta - n;
            if (beta0 <= 0.0D) {
                beta0 = 1.0D;
                n--;
            }
            I = new double[n + 1];
            BetaDist.beta_alpha_fixed(alpha, beta0, x, d, n, I);
            u = I[n];
            if (u <= 0.0D) {
                return 0.0D;
            }
            if (u <= 1.0D) {
                return u;
            } else {
                return 1.0D;
            }
        }
        if (((alpha > 2000D) && (beta < 30D))
                || ((beta > 2000D) && (alpha < 30D))) {
            if (x > 0.5D) {
                return 1.0D - BetaDist.cdf(beta, alpha, d, 1.0D - x);
            }
            double u;
            if (alpha < beta) {
                u = alpha;
                alpha = beta;
                beta = u;
                flag = false;
            } else {
                flag = true;
            }
            u = alpha + 0.5D * beta - 0.5D;
            double temp;
            if (!flag) {
                temp = x / (2D - x);
            } else {
                temp = (1.0D - x) / (1.0D + x);
            }
            double yd = 2D * u * temp;
            final double gam = Math.exp(beta * Math.log(yd) - yd
                    - Num.lnGamma(beta))
                    * (2D * yd * yd - (beta - 1.0D) * yd - (beta * beta - 1.0D))
                    / (24D * u * u);
            if (flag) {
                yd = GammaDist.barF(beta, d, yd);
                return yd - gam;
            } else {
                yd = GammaDist.cdf(beta, d, yd);
                return yd + gam;
            }
        } else {
            final double h1 = alpha + beta - 1.0D;
            final double y = 1.0D - x;
            final double h3 = Math.sqrt((1.0D + y
                    * BetaDist.beta_g((alpha - 0.5D) / (h1 * x), d) + x
                    * BetaDist.beta_g((beta - 0.5D) / (h1 * y), d))
                    / ((h1 + 0.16666666666666666D) * x * y))
                    * ((h1 + 0.33333333333333331D + 0.02D * (1.0D / alpha
                            + 1.0D / beta + 1.0D / (alpha + beta)))
                            * x - alpha + 0.33333333333333331D - 0.02D / alpha - 0.01D / (alpha + beta));
            return NormalDist.cdf01(h3);
        }
    }

    public static double density(double alpha, double beta, double x) {
        return BetaDist.density(alpha, beta, 0.0D, 1.0D, x);
    }

    public static double density(double alpha, double beta, double a, double b,
            double x) {
        if (a >= b) {
            throw new IllegalArgumentException("a >= b");
        }
        if ((x <= a) || (x >= b)) {
            return 0.0D;
        } else {
            final double factor = Math.exp(Num.lnGamma(alpha + beta))
                    / (Math.exp(Num.lnGamma(alpha)) * Math.exp(Num
                            .lnGamma(beta)))
                    / Math.pow(b - a, alpha + beta - 1.0D);
            return factor * Math.pow(x - a, alpha - 1.0D)
                    * Math.pow(b - x, beta - 1.0D);
        }
    }

    private static void forward(double alpha, double beta, double x, double I0,
            double I1, int nmax, double I[]) {
        I[0] = I0;
        if (nmax > 0) {
            I[1] = I1;
        }
        for (int n = 1; n < nmax; n++) {
            I[n + 1] = (1.0D + (n - 1 + alpha + beta) * (1.0D - x) / (n + beta))
                    * I[n]
                    - (n - 1 + alpha + beta)
                    * (1.0D - x)
                    * I[n - 1]
                    / (n + beta);
        }
    }

    public static double inverseF(double alpha, double beta, double a,
            double b, int d, double u) {
        if (a >= b) {
            throw new IllegalArgumentException("a >= b");
        } else {
            return a + (b - a) * BetaDist.inverseF(alpha, beta, d, u);
        }
    }

    public static double inverseF(double alpha, double beta, int d, double u) {
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (beta <= 0.0D) {
            throw new IllegalArgumentException("beta <= 0");
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
            return 1.0D;
        }
        // double MACHEP = 1.1102230246251565E-016D;
        // double MAXLOG = 709.78271289338397D;
        // double MINLOG = -708.39641853226408D;
        // double MAXNUM = 1.7976931348623157E+308D;
        boolean ihalve = false;
        boolean newt = false;
        double p = 0.0D;
        double q = 0.0D;
        double y0 = 0.0D;
        double z = 0.0D;
        double y = 0.0D;
        double x = 0.0D;
        double lgm = 0.0D;
        double yp = 0.0D;
        double di = 0.0D;
        double dithresh = 0.0D;
        double xt = 0.0D;
        double x0 = 0.0D;
        double yl = 0.0D;
        double x1 = 1.0D;
        double yh = 1.0D;
        boolean nflg = false;
        boolean rflg = false;
        if ((alpha <= 1.0D) || (beta <= 1.0D)) {
            dithresh = 9.9999999999999995E-007D;
            rflg = false;
            p = alpha;
            q = beta;
            y0 = u;
            x = p / (p + q);
            y = BetaDist.cdf(p, q, d, x);
            ihalve = true;
        } else {
            dithresh = 0.0001D;
        }
        label0: do {
            if (ihalve) {
                ihalve = false;
                int dir = 0;
                di = 0.5D;
                for (int i = 0; i < 100; i++) {
                    if (i != 0) {
                        x = x0 + di * (x1 - x0);
                        if (x == 1.0D) {
                            x = 0.99999999999999989D;
                        }
                        if (x == 0.0D) {
                            di = 0.5D;
                            x = x0 + di * (x1 - x0);
                            if (x == 0.0D) {
                                System.err
                                        .println("BetaDist.inverseF: underflow");
                                return 0.0D;
                            }
                        }
                        y = BetaDist.cdf(p, q, d, x);
                        yp = (x1 - x0) / (x1 + x0);
                        if (Math.abs(yp) < dithresh) {
                            newt = true;
                            continue label0;
                        }
                        yp = (y - y0) / y0;
                        if (Math.abs(yp) < dithresh) {
                            newt = true;
                            continue label0;
                        }
                    }
                    if (y < y0) {
                        x0 = x;
                        yl = y;
                        if (dir < 0) {
                            dir = 0;
                            di = 0.5D;
                        } else if (dir > 3) {
                            di = 1.0D - (1.0D - di) * (1.0D - di);
                        } else if (dir > 1) {
                            di = 0.5D * di + 0.5D;
                        } else {
                            di = (y0 - y) / (yh - yl);
                        }
                        dir++;
                        if (x0 <= 0.75D) {
                            continue;
                        }
                        if (rflg) {
                            rflg = false;
                            p = alpha;
                            q = beta;
                            y0 = u;
                        } else {
                            rflg = true;
                            p = beta;
                            q = alpha;
                            y0 = 1.0D - u;
                        }
                        x = 1.0D - x;
                        y = BetaDist.cdf(p, q, d, x);
                        x0 = 0.0D;
                        yl = 0.0D;
                        x1 = 1.0D;
                        yh = 1.0D;
                        ihalve = true;
                        continue label0;
                    }
                    x1 = x;
                    if (rflg && (x1 < 1.1102230246251565E-016D)) {
                        x = 0.0D;
                        break label0;
                    }
                    yh = y;
                    if (dir > 0) {
                        dir = 0;
                        di = 0.5D;
                    } else if (dir < -3) {
                        di *= di;
                    } else if (dir < -1) {
                        di = 0.5D * di;
                    } else {
                        di = (y - y0) / (yh - yl);
                    }
                    dir--;
                }
                if (x0 >= 1.0D) {
                    x = 0.99999999999999989D;
                    break;
                }
                if (x <= 0.0D) {
                    System.err.println("BetaDist.inverseF: underflow");
                    return 0.0D;
                }
                newt = true;
            }
            if (newt) {
                newt = false;
                if (nflg) {
                    break;
                }
                nflg = true;
                lgm = Num.lnGamma(p + q) - Num.lnGamma(p) - Num.lnGamma(q);
                for (int i = 0; i < 8; i++) {
                    if (i != 0) {
                        y = BetaDist.cdf(p, q, d, x);
                    }
                    if (y < yl) {
                        x = x0;
                        y = yl;
                    } else if (y > yh) {
                        x = x1;
                        y = yh;
                    } else if (y < y0) {
                        x0 = x;
                        yl = y;
                    } else {
                        x1 = x;
                        yh = y;
                    }
                    if ((x >= 1.0D) || (x <= 0.0D)) {
                        break;
                    }
                    z = (p - 1.0D) * Math.log(x) + (q - 1.0D)
                            * Math.log(1.0D - x) + lgm;
                    if (z < -708.39641853226408D) {
                        break label0;
                    }
                    if (z > 709.78271289338397D) {
                        break;
                    }
                    z = Math.exp(z);
                    z = (y - y0) / z;
                    xt = x - z;
                    if (xt <= x0) {
                        y = (x - x0) / (x1 - x0);
                        xt = x0 + 0.5D * y * (x - x0);
                        if (xt <= 0.0D) {
                            break;
                        }
                    }
                    if (xt >= x1) {
                        y = (x1 - x) / (x1 - x0);
                        xt = x1 - 0.5D * y * (x1 - x);
                        if (xt >= 1.0D) {
                            break;
                        }
                    }
                    x = xt;
                    if (Math.abs(z / x) < 1.4210854715202004E-014D) {
                        break label0;
                    }
                }
                dithresh = 2.8421709430404007E-014D;
                ihalve = true;
            } else {
                yp = -NormalDist.inverseF01(u);
                if (u > 0.5D) {
                    rflg = true;
                    p = beta;
                    q = alpha;
                    y0 = 1.0D - u;
                    yp = -yp;
                } else {
                    rflg = false;
                    p = alpha;
                    q = beta;
                    y0 = u;
                }
                lgm = (yp * yp - 3D) / 6D;
                x = 2D / (1.0D / (2D * p - 1.0D) + 1.0D / (2D * q - 1.0D));
                z = yp * Math.sqrt(x + lgm) / x
                        - (1.0D / (2D * q - 1.0D) - 1.0D / (2D * p - 1.0D))
                        * (lgm + 0.83333333333333337D - 2D / (3D * x));
                z = 2D * z;
                if (z < -708.39641853226408D) {
                    x = 1.0D;
                    System.err.println("BetaDist.inverseF: underflow");
                    return 0.0D;
                }
                x = p / (p + q * Math.exp(z));
                y = BetaDist.cdf(p, q, d, x);
                yp = (y - y0) / y0;
                if (Math.abs(yp) < 0.20000000000000001D) {
                    newt = true;
                } else {
                    ihalve = true;
                }
            }
        } while (true);
        if (rflg) {
            if (x <= 1.1102230246251565E-016D) {
                x = 0.99999999999999989D;
            } else {
                x = 1.0D - x;
            }
        }
        return x;
    }

    private static void isubx_alpha_fixed(double alpha, double beta, double x,
            int d, int nmax, double I[]) {
        if ((beta <= 0.0D) || (beta > 1.0D)) {
            throw new IllegalArgumentException("beta not in (0, 1] ");
        }
        final int m = (int) alpha;
        final double s = alpha - m;
        int mmax;
        double alpha0;
        if (s > 0.0D) {
            alpha0 = s;
            mmax = m;
        } else {
            alpha0 = s + 1.0D;
            mmax = m - 1;
        }
        final double I0 = BetaDist.isubx_alphabeta_small(alpha0, beta, x, d);
        final double I1 = BetaDist.isubx_alphabeta_small(alpha0, beta + 1.0D,
                x, d);
        final double Ialpha[] = new double[mmax + 1];
        BetaDist.backward(alpha0, beta, x, I0, d, mmax, Ialpha);
        final double Ibeta0 = Ialpha[mmax];
        BetaDist.backward(alpha0, beta + 1.0D, x, I1, d, mmax, Ialpha);
        final double Ibeta1 = Ialpha[mmax];
        BetaDist.forward(alpha, beta, x, Ibeta0, Ibeta1, nmax, I);
    }

    private static double isubx_alphabeta_small(double alpha, double beta,
            double x, int d) {
        int k = 0;
        if ((alpha <= 0.0D) || (alpha > 1.0D)) {
            throw new IllegalArgumentException("alpha not in (0, 1] ");
        }
        if ((beta <= 0.0D) || (beta > 2D)) {
            throw new IllegalArgumentException("beta not in (0, 2] ");
        }
        final double epsilon = ContinuousDistribution.EPSARRAY[d];
        double u = Math.pow(x, alpha);
        double s = u / alpha;
        double v;
        do {
            u = (k + 1 - beta) * x * u / (k + 1);
            v = u / (k + 1 + alpha);
            s += v;
            k++;
        } while (Math.abs(v) / s > epsilon);
        v = Num.lnGamma(alpha + beta) - Num.lnGamma(alpha) - Num.lnGamma(beta);
        return s * Math.exp(v);
    }

    private static void isubx_beta_fixed(double alpha, double beta, double x,
            int d, int nmax, double I[]) {
        double Ibeta1 = 0.0D;
        if ((alpha <= 0.0D) || (alpha > 1.0D)) {
            throw new IllegalArgumentException("alpha not in (0, 1] ");
        }
        final int m = (int) beta;
        final double s = beta - m;
        int mmax;
        double beta0;
        if (s > 0.0D) {
            beta0 = s;
            mmax = m;
        } else {
            beta0 = s + 1.0D;
            mmax = m - 1;
        }
        final double Ibeta0 = BetaDist
                .isubx_alphabeta_small(alpha, beta0, x, d);
        if (mmax > 0) {
            Ibeta1 = BetaDist.isubx_alphabeta_small(alpha, beta0 + 1.0D, x, d);
        }
        final double Ibeta[] = new double[mmax + 1];
        BetaDist.forward(alpha, beta0, x, Ibeta0, Ibeta1, mmax, Ibeta);
        BetaDist.backward(alpha, beta, x, Ibeta[mmax], d, nmax, I);
    }

    protected double a;

    protected double alpha;

    protected double b;

    protected double beta;

    protected double bminusa;

    protected double factor;

    public BetaDist(double alpha, double beta) {
        setParams(alpha, beta, 0.0D, 1.0D, decPrec);
    }

    public BetaDist(double alpha, double beta, double a, double b) {
        setParams(alpha, beta, a, b, decPrec);
    }

    public BetaDist(double alpha, double beta, double a, double b, int d) {
        setParams(alpha, beta, a, b, d);
    }

    public BetaDist(double alpha, double beta, int d) {
        setParams(alpha, beta, 0.0D, 1.0D, d);
    }

    @Override
    public double cdf(double x) {
        if (a >= b) {
            throw new IllegalArgumentException("a >= b");
        } else {
            return BetaDist.cdf(alpha, beta, decPrec, (x - a) / bminusa);
        }
    }

    @Override
    public double density(double x) {
        if ((x <= a) || (x >= b)) {
            return 0.0D;
        } else {
            return factor * Math.pow(x - a, alpha - 1.0D)
                    * Math.pow(b - x, beta - 1.0D);
        }
    }

    public double getA() {
        return a;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getB() {
        return b;
    }

    public double getBeta() {
        return beta;
    }

    @Override
    public double inverseF(double u) {
        return a + (b - a) * BetaDist.inverseF(alpha, beta, decPrec, u);
    }

    public void setParams(double alpha, double beta, double a, double b, int d) {
        if (alpha <= 0.0D) {
            throw new IllegalArgumentException("alpha <= 0");
        }
        if (beta <= 0.0D) {
            throw new IllegalArgumentException("beta <= 0");
        }
        if (a >= b) {
            throw new IllegalArgumentException("a >= b");
        } else {
            this.alpha = alpha;
            this.beta = beta;
            decPrec = d;
            xa0 = this.a = a;
            xb0 = this.b = b;
            bminusa = b - a;
            factor = Math.exp(Num.lnGamma(alpha + beta) - Num.lnGamma(alpha)
                    - Num.lnGamma(beta))
                    / Math.pow(bminusa, alpha + beta - 1.0D);
            return;
        }
    }
}
