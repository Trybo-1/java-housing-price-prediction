package models;

import java.util.PriorityQueue;
import java.util.Comparator;

public class KNN {
    private int k;

    public KNN(int k) {
        this.k = k;
    }

    public double predict(double[][] X_train, double[] y_train, double[] x_query) {
        PriorityQueue<double[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[0]));

        for (int i = 0; i < X_train.length; i++) {
            double dist = euclidean(X_train[i], x_query);
            pq.add(new double[]{dist, y_train[i]});
        }

        double sum = 0;
        for (int i = 0; i < k && !pq.isEmpty(); i++) {
            sum += pq.poll()[1];
        }
        return sum / k;
    }

    private double euclidean(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    public double evaluate(double[][] X_train, double[] y_train, double[][] X_test, double[] y_test) {
        double mse = 0.0;
        for (int i = 0; i < X_test.length; i++) {
            double pred = predict(X_train, y_train, X_test[i]);
            mse += Math.pow(pred - y_test[i], 2);
        }
        return mse / X_test.length;
    }
}
