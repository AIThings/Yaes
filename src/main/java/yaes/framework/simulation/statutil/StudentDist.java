/**
 * This class was adapted from Pierre L'Ecuyer's 
 * excellent statistical package SSJ 
 * 
 * http://www.iro.umontreal.ca/~lecuyer/
 * 
 */
package yaes.framework.simulation.statutil;

public class StudentDist extends ContinuousDistribution {
    // private static final int STUDENT_N1 = 20;
    // private static final double STUDENT_X1 = 8.0099999999999998D;
    // private static final int STUDENT_KMAX = 200;
    // private static final double STUDENT_EPS = 4.9999999999999999E-017D;
    private static double W[] = new double[5];

    public static double barF(int n, double x) {
        return 1.0D - StudentDist.cdf(n, x);
    }

    public static double cdf(int n, double x) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        if (n == 1) {
            return 0.5D + Math.atan(x) / 3.1415926535897931D;
        }
        double z;
        if (n == 2) {
            z = 1.0D + x * x / 2D;
            return 0.5D + x * Math.sqrt(z) / (2D * z * 1.4142135623730951D);
        }
        if (x <= -1000D) {
            return 0.0D;
        }
        if (x >= 1000D) {
            return 1.0D;
        }
        double b;
        double y;
        int k;
        if ((n <= 20) && (x <= 8.0099999999999998D)) {
            b = 1.0D + x * x / n;
            y = x / Math.sqrt(n);
            z = 1.0D;
            for (k = n - 2; k >= 2; k -= 2) {
                z = 1.0D + z * (k - 1.0D) / (k * b);
            }
            if (n % 2 == 0) {
                return (1.0D + z * y / Math.sqrt(b)) / 2D;
            } else {
                return 0.5D + (Math.atan(y) + z * y / b) / 3.1415926535897931D;
            }
        }
        double z2;
        if (x < 8.0099999999999998D) {
            final double a = n - 0.5D;
            b = 48D * a * a;
            z2 = a * Math.log(1.0D + x * x / n);
            z = Math.sqrt(z2);
            y = (((((64D * z2 + 788D) * z2 + 9801D) * z2 + 89775D) * z2 + 543375D)
                    * z2 + 1788885D)
                    * z / (210D * b * b * b);
            y -= (((4D * z2 + 33D) * z2 + 240D) * z2 + 855D) * z
                    / (10D * b * b);
            y += z + (z2 + 3D) * z / b;
            if (x >= 0.0D) {
                return NormalDist.barF01(-y);
            } else {
                return NormalDist.barF01(y);
            }
        }
        b = 1.0D + x * x / n;
        y = Num.lnGamma((n + 1) / 2D) - Num.lnGamma(n / 2D);
        y = Math.exp(y);
        y *= 1.0D / (Math.sqrt(3.1415926535897931D * n) * Math.pow(b,
                (n + 1) / 2D));
        y *= 2D * Math.sqrt(n * b);
        z = y / n;
        k = 2;
        double prec;
        z2 = prec = 10D;
        for (; (k < 200) && (prec > 4.9999999999999999E-017D); k += 2) {
            y *= (k - 1) / (k * b);
            z += y / (n + k);
            prec = Math.abs(z - z2);
            z2 = z;
        }
        if (k >= 200) {
            System.err.println("student: k >= STUDENT_KMAX");
        }
        if (x >= 0.0D) {
            return 1.0D - z / 2D;
        } else {
            return z / 2D;
        }
    }

    public static double cdf2(int n, int d, double x) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        if (d <= 0) {
            throw new IllegalArgumentException("student2:   d <= 0");
        }
        if (x >= 0.0D) {
            return 0.5D * (1.0D + BetaDist.cdf(0.5D, 0.5D * n, d, x * x
                    / (n + x * x)));
        } else {
            return 0.5D * BetaDist.cdf(0.5D * n, 0.5D, d, n / (n + x * x));
        }
    }

    public static double density(int n, double x) {
        final double factor = Math.exp(Num.lnGamma((n + 1) / 2D)
                - Num.lnGamma(n / 2D))
                / Math.sqrt(n * 3.1415926535897931D);
        return factor * Math.pow(1.0D + x * x / n, -(n + 1.0D) / 2D);
    }

    public static double inverseF(int n, double u) {
        // double DWARF = 1.0000000000000001E-031D;
        // double PIOVR2 = 1.5707963268D;
        if (n < 1) {
            throw new IllegalArgumentException("Calling student with n < 1");
        }
        if ((u > 1.0D) || (u < 0.0D)) {
            throw new IllegalArgumentException(
                    "Calling student with u < 0 or u > 1");
        }
        if (n == 1) {
            final double arg = 3.1415926535897931D * (1.0D - u);
            if (Math.sin(arg) > 1.0000000000000001E-031D) {
                return Math.cos(arg) / Math.sin(arg);
            } else {
                return Math.cos(arg) / 1.0000000000000001E-031D;
            }
        }
        double T;
        if (n == 2) {
            if (u <= 0.5D) {
                T = 2D * u;
            } else {
                T = 2D * (1.0D - u);
            }
            if (T <= 1.0000000000000001E-031D) {
                T = 1.0000000000000001E-031D;
            }
            double vtemp = Math.sqrt(2D / (T * (2D - T)) - 2D);
            if (u <= 0.5D) {
                return -vtemp;
            } else {
                return vtemp;
            }
        }
        if ((u <= 1.0000000000000001E-031D)
                || (1.0D - u <= 1.0000000000000001E-031D)) {
            T = 9.9999999999999996E+030D;
            if (u < 0.5D) {
                return -T;
            } else {
                return T;
            }
        }
        final double ux2 = u * 2D;
        double p2tail;
        if (ux2 < 2D - ux2) {
            p2tail = ux2;
        } else {
            p2tail = 2D - ux2;
        }
        StudentDist.W[2] = 1.0D / (n - 0.5D);
        StudentDist.W[1] = 48D / (StudentDist.W[2] * StudentDist.W[2]);
        StudentDist.W[3] = ((20700D * StudentDist.W[2] / StudentDist.W[1] - 98D)
                * StudentDist.W[2] - 16D)
                * StudentDist.W[2] + 96.359999999999999D;
        StudentDist.W[4] = ((94.5D / (StudentDist.W[1] + StudentDist.W[3]) - 3D)
                / StudentDist.W[1] + 1.0D)
                * Math.sqrt(StudentDist.W[2] * 1.5707963268D) * n;
        double X = StudentDist.W[4] * p2tail;
        double C = StudentDist.W[3];
        double Y = Math.pow(X, 2D / n);
        if (Y <= 0.050000000000000003D + StudentDist.W[2]) {
            double Y2 = (n + 6D) / (n + Y) - 0.088999999999999996D
                    * StudentDist.W[4] - 0.82199999999999995D;
            Y2 = Y2 * (n + 2D) * 3D;
            Y2 = (1.0D / Y2 + 0.5D / (n + 4D)) * Y - 1.0D;
            Y2 = Y2 * (n + 1.0D) / (n + 2D);
            Y = Y2 + 1.0D / Y;
        } else {
            X = NormalDist.inverseF01(p2tail * 0.5D);
            Y = X * X;
            if (n < 5D) {
                C += 0.29999999999999999D * (n - 4.5D)
                        * (X + 0.59999999999999998D);
            }
            C = (((0.050000000000000003D * StudentDist.W[4] * X - 5D) * X - 7D)
                    * X - 2D)
                    * X + StudentDist.W[1] + C;
            Y = (((((0.40000000000000002D * Y + 6.2999999999999998D) * Y + 36D)
                    * Y + 94.5D)
                    / C - Y - 3D)
                    / StudentDist.W[1] + 1.0D)
                    * X;
            Y = StudentDist.W[2] * Y * Y;
            if (Y <= 0.002D) {
                Y = 0.5D * Y * Y + Y;
            } else {
                Y = Math.exp(Y) - 1.0D;
            }
        }
        T = Math.sqrt(n * Y);
        if (u < 0.5D) {
            return -T;
        } else {
            return T;
        }
    }

    private double factor;

    private int    n;

    public StudentDist(int n) {
        setN(n);
    }

    @Override
    public double cdf(double x) {
        return StudentDist.cdf(n, x);
    }

    @Override
    public double density(double x) {
        return factor * Math.pow(1.0D + x * x / n, -(n + 1.0D) / 2D);
    }

    public int getN() {
        return n;
    }

    @Override
    public double inverseF(double u) {
        return StudentDist.inverseF(n, u);
    }

    public void setN(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        } else {
            this.n = n;
            factor = Math.exp(Num.lnGamma((n + 1) / 2D) - Num.lnGamma(n / 2D))
                    / Math.sqrt(n * 3.1415926535897931D);
            return;
        }
    }
}
