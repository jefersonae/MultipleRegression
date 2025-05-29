import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;
import java.io.File;
import java.util.List;

public class PlotGenerator {

    public static void plotRegression(List<Double> t, List<Double> f, double[] coeffs, int degree) throws Exception {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries original = new XYSeries("Dados");
        XYSeries fitted = new XYSeries("Regressão Grau " + degree);

        for (int i = 0; i < t.size(); i++) {
            original.add(t.get(i), f.get(i));
        }

        double min = t.stream().min(Double::compare).get();
        double max = t.stream().max(Double::compare).get();
        double step = (max - min) / 100;

        for (double x = min; x <= max; x += step) {
            double y = 0;
            for (int i = 0; i < coeffs.length; i++) {
                y += coeffs[i] * Math.pow(x, i);
            }
            fitted.add(x, y);
        }

        dataset.addSeries(original);
        dataset.addSeries(fitted);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Regressão Polinomial Grau " + degree,
                "Tempo (t)",
                "f(t)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartUtils.saveChartAsPNG(new File("output/regressao_grau_" + degree + ".png"), chart, 800, 600);
    }

    public static void plotR2(List<Double> r2adjList) throws Exception {
        XYSeries series = new XYSeries("R² Ajustado");

        for (int grau = 1; grau <= r2adjList.size(); grau++) {
            series.add(grau, r2adjList.get(grau - 1));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "R² Ajustado por Grau do Polinômio",
                "Grau do Polinômio",
                "R² Ajustado",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartUtils.saveChartAsPNG(new File("output/r2_ajustado.png"), chart, 800, 600);
    }
}