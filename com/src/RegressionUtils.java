import org.apache.commons.math4.legacy.linear.QRDecomposition;
import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.legacy.linear.RealVector;
import org.apache.commons.math4.legacy.linear.ArrayRealVector;
import org.apache.commons.math4.legacy.linear.DecompositionSolver;

import java.util.*;

public class RegressionUtils {
    public static double[] fitPolynomial(List<Double> t, List<Double> f, int degree) {
        int n = t.size();
        double[][] X = new double[n][degree + 1];
        double[] Y = new double[n];

        for (int i = 0; i < n; i++) {
            Y[i] = f.get(i);
            double val = 1.0;
            for (int j = 0; j <= degree; j++) {
                X[i][j] = val;
                val *= t.get(i);
            }
        }

        RealMatrix XMatrix = new Array2DRowRealMatrix(X);
        RealVector YVector = new ArrayRealVector(Y);
        DecompositionSolver solver = new QRDecomposition(XMatrix).getSolver();
        RealVector solution = solver.solve(YVector);

        return solution.toArray();
    }

    public static double rSquaredAdjusted(List<Double> t, List<Double> f, double[] coeffs) {
        int n = t.size();
        int p = coeffs.length - 1;
        double meanY = f.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double ssTot = 0.0;
        double ssRes = 0.0;

        for (int i = 0; i < n; i++) {
            double yi = f.get(i);
            double yPred = 0;
            for (int j = 0; j < coeffs.length; j++) {
                yPred += coeffs[j] * Math.pow(t.get(i), j);
            }
            ssTot += Math.pow(yi - meanY, 2);
            ssRes += Math.pow(yi - yPred, 2);
        }

        double r2 = 1 - (ssRes / ssTot);
        return 1 - (1 - r2) * (n - 1.0) / (n - p - 1.0);
    }
}