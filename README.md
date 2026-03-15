# Java Housing Price Prediction

A machine learning project implemented in **Java** that predicts housing prices using multiple regression techniques.  
The project compares different models to understand their accuracy and performance when predicting house prices from structured CSV datasets.

---

## Overview

Predicting housing prices is a common machine learning problem that involves estimating property values based on features such as size, location, number of rooms, and other housing attributes.

This project implements three predictive models from scratch in Java:

- **K-Nearest Neighbors (KNN)**
- **Linear Regression using Gradient Descent**
- **Linear Regression using Closed Form (Normal Equation)**

The goal of the project is to compare these approaches and evaluate their effectiveness for predicting housing prices.

---

## Features

- CSV dataset loading and preprocessing
- Implementation of multiple machine learning models
- Model training and prediction
- Comparison of different regression approaches
- Pure Java implementation (no external ML libraries)

---

## Algorithms Implemented

### 1. K-Nearest Neighbors (KNN)
A non-parametric algorithm that predicts the price of a house based on the prices of the **k most similar houses** in the dataset.

### 2. Linear Regression (Gradient Descent)
Uses an iterative optimization algorithm to minimize prediction error by gradually adjusting model weights.

### 3. Linear Regression (Closed Form Solution)
Uses the mathematical **Normal Equation** to compute regression parameters directly without iterative optimization.

---

## Project Structure

```
java-housing-price-prediction
│
├── data/               # Housing datasets (CSV files)
├── src/                # Java source code
│   ├── models/         # Machine learning models
│   ├── utils/          # Utility/helper classes
│   └── main/           # Program entry point
│
├── docs/
│   ├──build.bat        # Script to compile and run the program
│
└── README.md
```

---

## Requirements

- **Java JDK 8 or later**
- Command line / terminal

Check installation:

```
java -version
javac -version
```

---

## Compilation

From the **root project folder**, compile the project:

```
javac -d bin src/**/*.java
```
or run build.bat located in 'docs'.

This compiles all Java source files and places the `.class` files into the `bin` directory.

---

## Running the Program

After compiling:

```
java -cp bin main.Main
```

Or run using the provided batch script:

```
build.bat
```

---

## Example Workflow

1. Load housing dataset from CSV
2. Preprocess the data
3. Train machine learning models
4. Generate predictions
5. Compare the results of each model

---

## Learning Objectives

This project demonstrates:

- Implementation of machine learning algorithms in Java
- Data processing using CSV datasets
- Regression techniques for prediction problems
- Comparing multiple ML models

---

## Future Improvements

Possible enhancements:

- Add evaluation metrics (RMSE, MAE, R²)
- Implement additional machine learning algorithms
- Improve dataset preprocessing
- Add visualization of prediction results
- Create a simple UI for predictions

---

## Author

**Nabeel Vally Omar**  
Computer Science Student

---

## License

This project is open source and available under the MIT License.
