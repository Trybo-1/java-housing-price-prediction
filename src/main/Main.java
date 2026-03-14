package main;

import data.DataLoader;
import models.KNN;
import models.LinearRegressionClosedForm;
import models.LinearRegressionGradientDescent;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try {

            String csvPath = "data/housing.csv";
            String targetName = "price";

            // Load data
            DataLoader loader = new DataLoader();
            loader.loadCSV(csvPath, targetName);
            double[][] X = loader.getFeatures();
            double[] y = loader.getTargets();

            if (X.length == 0) {
                System.out.println("X! No valid rows loaded.");
                return;
            }
            
            loader.viewData(X, y, X.length);

            // Split into training/testing sets (80/20)
            int n = X.length;
            int trainSize = (int) (0.8 * n);
            int testSize = n - trainSize;

            double[][] X_train = new double[trainSize][X[0].length];
            double[] y_train = new double[trainSize];
            double[][] X_test = new double[testSize][X[0].length];
            double[] y_test = new double[testSize];

            // Random split
            Random rand = new Random(42);
            int[] indices = new int[n];
            for (int i = 0; i < n; i++) indices[i] = i;
            for (int i = 0; i < n; i++) {
                int j = rand.nextInt(n);
                int temp = indices[i];
                indices[i] = indices[j];
                indices[j] = temp;
            }

            for (int i = 0; i < trainSize; i++) {
                X_train[i] = X[indices[i]];
                y_train[i] = y[indices[i]];
            }
            for (int i = 0; i < testSize; i++) {
                X_test[i] = X[indices[trainSize + i]];
                y_test[i] = y[indices[trainSize + i]];
            }

            // Train Linear Regression (Closed Form) 
            System.out.println("\n=== Linear Regression (Closed Form) ===");
            LinearRegressionClosedForm lrClosed = new LinearRegressionClosedForm();
            lrClosed.fit(X_train, y_train);
            double mseClosed = lrClosed.evaluate(X_test, y_test);
            System.out.println("Test MSE (Closed Form): " + mseClosed);

            // Train Linear Regression (Gradient Descent)
            System.out.println("\n=== Linear Regression (Gradient Descent) ===");
            LinearRegressionGradientDescent lrGD = new LinearRegressionGradientDescent(0.01, 1000);
            lrGD.fit(X_train, y_train);
            double mseGD = lrGD.evaluate(X_test, y_test);
            System.out.println("Test MSE (Gradient Descent): " + mseGD);

            // KNN with different K values
            System.out.println("\n=== KNN Regression ===");
            int[] kValues = {1, 3, 5, 7};
            for (int k : kValues) {
                KNN knn = new KNN(k);
                double mseKNN = knn.evaluate(X_train, y_train, X_test, y_test);
                System.out.println("K = " + k + " -> Test MSE: " + mseKNN);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
