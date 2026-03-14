package data;

import java.io.*;
import java.util.*;

public class DataLoader {

    private double[][] features;
    private double[] targets;
    private String[] headers;

    public void loadCSV(String path, String targetName) {
        System.out.println("Loading CSV...");
        List<double[]> featureList = new ArrayList<>();
        List<Double> targetList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("Empty CSV file!");
            }

            headers = headerLine.split(",");
            int targetIdx = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().equalsIgnoreCase(targetName)) {
                    targetIdx = i;
                    break;
                }
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] tokens = line.split(",");

                // Skip rows with missing or extra columns
                if (tokens.length != headers.length) {
                    System.out.println("! Skipping malformed row (expected " + headers.length +
                            " columns but found " + tokens.length + "): " + line);
                    continue;
                }

                double[] feats = new double[headers.length - 1];
                int fi = 0;

                try {
                    for (int i = 0; i < tokens.length; i++) {
                        if (i == targetIdx) continue;
                        String val = tokens[i].trim();

                            val = val.toLowerCase();
                            if (val.equals("yes") || val.equals("furnished")) {
                                feats[fi++] = 1.0;
                            } else if (val.equals("no") || val.equals("unfurnished")) {
                                feats[fi++] = 0.0;
                            } else if ( val.equals("semi-furnished")) {
                                feats[fi++] = 0.5;
                            } else if (val.isEmpty()) {
                                feats[fi++] = 0.0;
                            } else {
                                feats[fi++] = Math.abs(val.hashCode() % 1000);
                            }
                        
                    }

                    double targetVal = Double.parseDouble(tokens[targetIdx].trim());
                    featureList.add(feats);
                    targetList.add(targetVal);
                } catch (Exception e) {
                    System.out.println("! Skipping bad row: " + line+e );
                }
            }

            // Convert lists to arrays
            features = new double[featureList.size()][];
            targets = new double[targetList.size()];

            for (int i = 0; i < featureList.size(); i++) {
                features[i] = featureList.get(i);
                targets[i] = targetList.get(i);
            }

            normalizeFeatures();

            System.out.println("CSV loaded successfully with " + features.length + " valid rows.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void normalizeFeatures() {
        if (features == null || features.length == 0) return;
        int cols = features[0].length;
        int rows = features.length;

        for (int j = 0; j < cols; j++) {
            double sum = 0.0;
            for (int i = 0; i < rows; i++) sum += features[i][j];
            double mean = sum / rows;

            double variance = 0.0;
            for (int i = 0; i < rows; i++) {
                variance += Math.pow(features[i][j] - mean, 2);
            }
            double std = Math.sqrt(variance / rows);
            if (std == 0) std = 1.0;

            for (int i = 0; i < rows; i++) {
                features[i][j] = (features[i][j] - mean) / std;
            }
        }
    }

    public double[][] getFeatures() {
        return features;
    }

    public double[] getTargets() {
        return targets;
    }

    public String[] getHeaders() {
        return headers;
    }
    public void viewData(double[][] X , double[] y,int numRows) {
        if (X == null || y == null) {
            System.out.println("No data loaded yet.");
            return;
        }

        System.out.println("\n=== Data Preview (first " + numRows + " rows) ===");
        for (int i = 0; i < Math.min(numRows, X.length); i++) {
            System.out.print("Row " + (i + 1) + " → [");
            for (int j = 0; j < X[i].length; j++) {
                System.out.printf("%.3f", X[i][j]);
                if (j < X[i].length - 1) System.out.print(", ");
            }
            System.out.printf("] => Target: %.3f%n", y[i]);
        }
        System.out.println("========================================\n");
    }

}
