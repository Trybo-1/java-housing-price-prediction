package models;

public class LinearRegressionClosedForm {
    private double[] weights;

    public void fit(double[][] X, double[] y) {
        int n = X.length;
        int m = X[0].length;
        double[][] Xb = new double[n][m + 1];

        // Add bias column
        for (int i = 0; i < n; i++) {
            Xb[i][0] = 1.0;
            for (int j = 0; j < m; j++) {
                Xb[i][j + 1] = X[i][j];
            }
        }

        // Compute (X^T X)
        double[][] XtX = new double[m + 1][m + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                double sum = 0;
                for (int k = 0; k < n; k++) sum += Xb[k][i] * Xb[k][j];
                XtX[i][j] = sum;
            }
        }

        // Compute (X^T y)
        double[] Xty = new double[m + 1];
        for (int i = 0; i < m + 1; i++) {
            double sum = 0;
            for (int k = 0; k < n; k++) sum += Xb[k][i] * y[k];
            Xty[i] = sum;
        }

        // Solve XtX * w = Xty 
        weights = gaussian(XtX, Xty);
    }

    private double[] gaussian(double[][] A, double[] b) {
        int n = b.length;
        for (int i = 0; i < b.length; i++) {
            // Pivot
            double max = Math.abs(A[i][i]);
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(A[k][i]) > max) {
                    max = Math.abs(A[k][i]);
                    maxRow = k;
                }
            }
            // Swap
            double[] temp = A[i];
            A[i] = A[maxRow];
            A[maxRow] = temp;

            double t = b[i];
            b[i] = b[maxRow];
            b[maxRow] = t;

            // Eliminate
            for (int k = i + 1; k < n; k++) {
                double factor = A[k][i] / A[i][i];
                b[k] -= factor * b[i];
                for (int j = i; j < n; j++) {
                    A[k][j] -= factor * A[i][j];
                }
            }
        }

        // Back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = b[i];
            for (int j = i + 1; j < n; j++) {
                sum -= A[i][j] * x[j];
            }
            x[i] = sum / A[i][i];
        }
        return x;
    }

    public double predict(double[] x) {
        double result = weights[0];
        for (int i = 0; i < x.length; i++) result += weights[i + 1] * x[i];
        return result;
    }

    public double evaluate(double[][] X, double[] y) {
        double mse = 0.0;
        for (int i = 0; i < X.length; i++) {
            double pred = predict(X[i]);
            mse += Math.pow(pred - y[i], 2);
        }
        return mse / X.length;
    }
}
