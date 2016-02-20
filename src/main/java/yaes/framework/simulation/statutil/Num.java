/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

public class Num {
    public static final int    DBL_DIG        = 15;

    public static final double DBL_EPSILON    = 2.2204460492503131E-016D;

    public static final int    DBL_MAX_10_EXP = 308;

    public static final int    DBL_MAX_EXP    = 1024;

    public static final int    DBL_MIN_EXP    = -1021;

    public static final double EBASE          = 2.7182818284590451D;

    public static final double ILN2           = 1.4426950408889634D;

    public static final double IRAC2          = 0.70710678118654757D;

    public static final double LN2            = 0.69314718055994529D;

    public static final double MAXINTDOUBLE   = 9007199254740992D;

    public static final double MAXTWOEXP      = 64D;

    public static final double RAC2           = 1.4142135623730951D;

    public static final double TWOEXP[]       = { 1.0D, 2D, 4D, 8D, 16D, 32D,
            64D, 128D, 256D, 512D, 1024D, 2048D, 4096D, 8192D, 16384D, 32768D,
            65536D, 131072D, 262144D, 524288D, 1048576D, 2097152D, 4194304D,
            8388608D, 16777216D, 33554432D, 67108864D, 134217728D, 268435456D,
            536870912D, 1073741824D, 2147483648D, 4294967296D, 8589934592D,
            17179869184D, 34359738368D, 68719476736D, 137438953472D,
            274877906944D, 549755813888D, 1099511627776D, 2199023255552D,
            4398046511104D, 8796093022208D, 17592186044416D, 35184372088832D,
            70368744177664D, 140737488355328D, 281474976710656D,
            562949953421312D, 1125899906842624D, 2251799813685248D,
            4503599627370496D, 9007199254740992D, 18014398509481984D,
            36028797018963968D, 72057594037927936D, 1.4411518807585587E+017D,
            2.8823037615171174E+017D, 5.7646075230342349E+017D,
            1.152921504606847E+018D, 2.305843009213694E+018D,
            4.6116860184273879E+018D, 9.2233720368547758E+018D,
            1.8446744073709552E+019D         };

    public static double besselK025(double x) {
        // int NUMOFCOEF = 7;
        // int DEGOFPOLY = 6;
        final double c[] = { 32177591145D, 2099336339520D, 16281990144000D,
                34611957596160D, 26640289628160D, 7901666082816D, 755914244096D };
        final double b[] = { 75293843625D, 2891283595200D, 18691126272000D,
                36807140966400D, 27348959232000D, 7972533043200D, 755914244096D };
        if (x < 1E-300D) {
            return 4.9406564584124654E-324D;
        }
        if (x >= 0.59999999999999998D) {
            double B = b[6];
            double C = c[6];
            for (int j = 6; j >= 1; j--) {
                B = B * x + b[j - 1];
                C = C * x + c[j - 1];
            }
            final double Res = Math.sqrt(3.1415926535897931D / (2D * x))
                    * Math.exp(-x) * C / B;
            return Res;
        } else {
            final double xx = x * x;
            final double rac = Math.pow(x / 2D, 0.25D);
            double Res = (((xx / 1386D + 0.023809523809523808D) * xx + 0.33333333333333331D)
                    * xx + 1.0D)
                    / (1.225416702465177D * rac);
            final double temp = (((xx / 3510D + 0.011111111111111112D) * xx + 0.20000000000000001D)
                    * xx + 1.0D)
                    * rac / 0.90640247705547705D;
            Res = 3.1415926535897931D * (Res - temp) / 1.4142135623730951D;
            return Res;
        }
    }

    public static double[][] calcMatStirling(int m, int n) {
        final double M[][] = new double[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                M[i][j] = 0.0D;
            }
        }
        M[0][0] = 1.0D;
        for (int j = 1; j <= n; j++) {
            M[0][j] = 0.0D;
            int k;
            if (j <= m) {
                k = j - 1;
                M[j][j] = 1.0D;
            } else {
                k = m;
            }
            for (int i = 1; i <= k; i++) {
                M[i][j] = i * M[i][j - 1] + M[i - 1][j - 1];
            }
        }
        return M;
    }

    public static double combination(int n, int s) {
        // int SLIM = 15;
        if ((s == 0) || (s == n)) {
            return 1.0D;
        }
        if (s < 0) {
            System.err.println("combination:   s < 0");
        }
        if (s > n) {
            System.err.println("combination:   s > n");
        }
        if (s > n / 2) {
            s = n - s;
        }
        if (s <= 15) {
            double Res = 1.0D;
            final int Diff = n - s;
            for (int i = 1; i <= s; i++) {
                Res *= (double) (Diff + i) / (double) i;
            }
            return Res;
        } else {
            final double Res = Num.lnFactorial(n) - Num.lnFactorial(s)
                    - Num.lnFactorial(n - s);
            return Math.exp(Res);
        }
    }

    public static double evalCheby(double S[], int N, double x) {
        double b2 = 0.0D;
        if (Math.abs(x) > 1.0D) {
            System.err
                    .println("Chebychev polynomial evaluated at x outside [-1, 1]");
        }
        final double xx = 2D * x;
        double b0 = 0.0D;
        double b1 = 0.0D;
        for (int j = N; j >= 0; j--) {
            b2 = b1;
            b1 = b0;
            b0 = xx * b1 - b2 + S[j];
        }
        return (b0 - b2) / 2D;
    }

    public static double factorial(int n) {
        double T = 1.0D;
        for (int j = 1; j <= n; j++) {
            T *= j;
        }
        return T;
    }

    public static double lnFactorial(int n) {
        // int NLIM = 14;
        if (n < 0) {
            throw new IllegalArgumentException("LnFactorialle: n negative");
        }
        if ((n == 0) || (n == 1)) {
            return 0.0D;
        }
        if (n <= 14) {
            long z = 1L;
            long x = 1L;
            for (int i = 2; i <= n; i++) {
                x++;
                z *= x;
            }
            return Math.log(z);
        } else {
            final double x = n + 1;
            final double y = 1.0D / (x * x);
            double z = ((-(0.00059523809523799999D * y) + 0.00079365007936510001D)
                    * y - 0.0027777777777778D)
                    * y + 0.083333333333332996D;
            z = (x - 0.5D) * Math.log(x) - x + 0.91893853320467001D + z / x;
            return z;
        }
    }

    public static double lnGamma(double x) {
        // double XLIMBIG = 4503599627370496D;
        // double XLIM1 = 18D;
        // int N = 15;
        final double DK2 = Math.log(Math.sqrt(6.2831853071795862D));
        // double DK1 = 0.95741869905106269D;
        final double C[] = { 0.52854303698223459D, 0.54987644612141406D,
                0.020739800616136651D, -0.00056916770421543844D,
                2.3245872104001691E-005D, -1.13060758570393E-006D,
                6.0656530989480001E-008D, -3.4628435777000001E-009D,
                2.0624998805999999E-010D, -1.266351116E-011D,
                7.9531006999999997E-013D, -5.0820769999999998E-014D,
                3.29187E-015D, -2.1556E-016D, 1.4240000000000001E-017D,
                -9.4999999999999995E-019D };
        if (x <= 0.0D) {
            throw new IllegalArgumentException("lnGamma");
        }
        double y;
        double z;
        if (x > 18D) {
            if (x > 4503599627370496D) {
                y = 0.0D;
            } else {
                y = 1.0D / (x * x);
            }
            z = ((-(0.00059523809523799999D * y) + 0.00079365007936510001D) * y - 0.0027777777777778D)
                    * y + 0.083333333333332996D;
            z = (x - 0.5D) * Math.log(x) - x + DK2 + z / x;
            return z;
        }
        if (x > 4D) {
            final int k = (int) x;
            z = x - k;
            y = 1.0D;
            for (int i = 3; i < k; i++) {
                y *= z + i;
            }
            y = Math.log(y);
        } else {
            if (x <= 0.0D) {
                return 1.7976931348623157E+308D;
            }
            if (x < 3D) {
                final int k = (int) x;
                z = x - k;
                y = 1.0D;
                for (int i = 2; i >= k; i--) {
                    y *= z + i;
                }
                y = -Math.log(y);
            } else {
                z = x - 3D;
                y = 0.0D;
            }
        }
        z = Num.evalCheby(C, 15, 2D * z - 1.0D);
        return z + 0.95741869905106269D + y;
    }

    public static double log2(double x) {
        return 1.4426950408889634D * Math.log(x);
    }

    public static double multMod(double a, double s, double c, double m) {
        // double DEUX53 = 9007199254740992D;
        // double DEUX17 = 131072D;
        // double UNDEUX17 = 7.62939453125E-006D;
        double V = a * s + c;
        int k;
        if ((V >= 9007199254740992D) || (-V >= 9007199254740992D)) {
            k = (int) (a * 7.62939453125E-006D);
            a -= k * 131072D;
            V = k * s;
            k = (int) (V / m);
            V -= k * m;
            V = V * 131072D + a * s + c;
        }
        k = (int) (V / m);
        V -= k * m;
        return V;
    }

    public static int multMod(int a, int s, int c, int m) {
        long x = a;
        final long m1 = m;
        x = (x * s + c) % m1;
        return (int) x;
    }

    public static long multMod(long a, long s, long c, long m) {
        // long H = 0x80000000L;
        long a0;
        long p;
        if (a < 0x80000000L) {
            a0 = a;
            p = 0L;
        } else {
            long a1 = a / 0x80000000L;
            a0 = a - 0x80000000L * a1;
            final long qh = m / 0x80000000L;
            final long rh = m - 0x80000000L * qh;
            long k;
            if (a1 >= 0x80000000L) {
                a1 -= 0x80000000L;
                k = s / qh;
                for (p = 0x80000000L * (s - k * qh) - k * rh; p < 0L; p += m) {
                    ;
                }
            } else {
                p = 0L;
            }
            if (a1 != 0L) {
                final long q = m / a1;
                k = s / q;
                p -= k * (m - a1 * q);
                if (p > 0L) {
                    p -= m;
                }
                for (p += a1 * (s - k * q); p < 0L; p += m) {
                    ;
                }
            }
            k = p / qh;
            for (p = 0x80000000L * (p - k * qh) - k * rh; p < 0L; p += m) {
                ;
            }
        }
        if (a0 != 0L) {
            final long q = m / a0;
            final long k = s / q;
            p -= k * (m - a0 * q);
            if (p > 0L) {
                p -= m;
            }
            for (p += a0 * (s - k * q); p < 0L; p += m) {
                ;
            }
        }
        p = p - m + c;
        if (p < 0L) {
            p += m;
        }
        return p;
    }

    public static double volumeSphere(double p, int t) {
        // double EPS = 4.4408920985006262E-016D;
        final int pLR = (int) p;
        final double kLR = t;
        if (p < 0.0D) {
            throw new IllegalArgumentException("volumeSphere:   p < 0");
        }
        if (Math.abs(p - pLR) <= 4.4408920985006262E-016D) {
            switch (pLR) {
            case 0: // '\0'
                return Num.TWOEXP[t];
            case 1: // '\001'
                return Num.TWOEXP[t] / Num.factorial(t);
            case 2: // '\002'
                if (t % 2 == 0) {
                    return Math.pow(3.1415926535897931D, kLR / 2D)
                            / Num.factorial(t / 2);
                } else {
                    final int s = (t + 1) / 2;
                    return Math.pow(3.1415926535897931D, s - 1.0D)
                            * Num.factorial(s) * Num.TWOEXP[2 * s]
                            / Num.factorial(2 * s);
                }
			default:
				break;
            }
        }
        final double Vol = kLR
                * (0.69314718055994529D + Num.lnGamma(1.0D + 1.0D / p))
                - Num.lnGamma(1.0D + kLR / p);
        return Math.exp(Vol);
    }

    private Num() {
    }
}
