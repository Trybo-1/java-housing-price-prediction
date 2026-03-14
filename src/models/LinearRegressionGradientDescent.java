package models;

public class LinearRegressionGradientDescent {
    private double[] weights;
    private double learningRate;
    private int iterations;

    public LinearRegressionGradientDescent(double learningRate, int iterations) {
        this.learningRate = learningRate;
        this.iterations = iterations;
    }

    public void fit(double[][] X, double[] y) {
        int n = X.length;
        int m = X[0].length;
        weights = new double[m + 1]; // bias + features

        for (int iter = 0; iter < iterations; iter++) {
            double[] gradients = new double[m + 1];
            for (int i = 0; i < n; i++) {
                double pred = predict(X[i]);
                double error = pred - y[i];
                gradients[0] += error;
                for (int j = 0; j < m; j++) {
                    gradients[j + 1] += error * X[i][j];
                }
            }

            for (int j = 0; j < m + 1; j++) {
                weights[j] -= (learningRate / n) * gradients[j];
            }

            if (iter % 100 == 0) {
                double mse = evaluate(X, y);
                System.out.println("Iteration " + iter + " - MSE: " + mse);
            }
        }
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
