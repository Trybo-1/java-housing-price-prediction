package utils;

/**
 * Simple distance utilities. Currently only Euclidean distance implemented.
 */
public class Distance {
    public static double euclidean(double[] a, double[] b) {
        double s = 0.0;
        for (int i = 0; i < a.length; i++) {
            double d = a[i] - b[i];
            s += d * d;
        }
        return Math.sqrt(s);
    }

    // wrapper for object-style usage
    public double compute(double[] a, double[] b) {
        return euclidean(a, b);
    }
}
