import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DataLoader.load("C:/Users/jefer/Downloads/MultipleRegression/com/src/resources/data.txt");

        new File("output").mkdir();  // Garante pasta para salvar os gráficos

        List<Double> r2adjList = new ArrayList<>();

        System.out.printf("%-8s%-70s%s\n", "Grau", "Coeficientes", "R² Ajustado");
        for (int grau = 1; grau <= 10; grau++) {
            double[] coeffs = RegressionUtils.fitPolynomial(DataLoader.t, DataLoader.f, grau);
            double r2adj = RegressionUtils.rSquaredAdjusted(DataLoader.t, DataLoader.f, coeffs);
            r2adjList.add(r2adj);
            System.out.printf("%-8d%-70s%.5f\n", grau, Arrays.toString(coeffs), r2adj);

            PlotGenerator.plotRegression(DataLoader.t, DataLoader.f, coeffs, grau);
        }

        PlotGenerator.plotR2(r2adjList);
    }
}