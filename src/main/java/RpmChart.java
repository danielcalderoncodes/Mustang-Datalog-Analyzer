
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.ChartEncoder;
import java.util.function.Function;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RpmChart {

    //abstraction, reduce repetitive code
    public static void displaySensorChart(
            Vehicle vehicle,
            String chartTitle,
            String yAxisTitle,
            String seriesName,
            Function<DataPoint, Double> sensorGetter,
            String fileName
    ) {
        ArrayList<Double> timestamps = new ArrayList<>();
        ArrayList<Double> sensorReadings = new ArrayList<>();

        // Build the chart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(chartTitle)
                .xAxisTitle("Time (seconds)")
                .yAxisTitle(yAxisTitle)
                .build();

        // Extract data from Vehicle
        for (DataPoint point : vehicle.getDataPoints()) {

            timestamps.add(point.getTimeSeconds());
            sensorReadings.add(sensorGetter.apply(point));

        }

        // Add data to chart
        chart.addSeries(seriesName, timestamps, sensorReadings);
        //export photo as png
        try {
            Path exportDirectory = Paths.get("exports");
            Files.createDirectories(exportDirectory);

            Path outputPath = exportDirectory.resolve(fileName);

            ChartEncoder.saveChart(chart, outputPath.toString(), "png");
            System.out.println("Chart saved successfully!");
        } catch (IOException e) {
            System.out.println("Could not save chart: " + e.getMessage());
        }
        // Display chart
        SwingWrapper<XYChart> wrapper = new SwingWrapper<>(chart);

        javax.swing.JFrame frame = wrapper.displayChart();
        frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    }


    public static void displayComparisonSensorChart(
            Vehicle vehicle1,
            Vehicle vehicle2,
            String chartTitle,
            String yAxisTitle,
            String series1Name,
            String series2Name,
            Function<DataPoint, Double> sensorGetter,
            String fileName)
    {
        ArrayList<Double> timestamps1 = new ArrayList<>();
        ArrayList<Double> sensorReadings1 = new ArrayList<>();

        ArrayList<Double> timestamps2 = new ArrayList<>();
        ArrayList<Double> sensorReadings2 = new ArrayList<>();

        // Build the chart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(chartTitle)
                .xAxisTitle("Time (seconds)")
                .yAxisTitle(yAxisTitle)
                .build();

        for (DataPoint point : vehicle1.getDataPoints()) {

            timestamps1.add(point.getTimeSeconds());
            sensorReadings1.add(sensorGetter.apply(point));

        }

        for (DataPoint point : vehicle2.getDataPoints()) {

            timestamps2.add(point.getTimeSeconds());
            sensorReadings2.add(sensorGetter.apply(point));

        }

        chart.addSeries(series1Name, timestamps1, sensorReadings1);
        chart.addSeries(series2Name, timestamps2, sensorReadings2);
        //export photo as png
        try {
            Path exportDirectory = Paths.get("exports");
            Files.createDirectories(exportDirectory);

            Path outputPath = exportDirectory.resolve(fileName);

            ChartEncoder.saveChart(chart, outputPath.toString(), "png");
            System.out.println("Chart saved successfully!");
        } catch (IOException e) {
            System.out.println("Could not save chart: " + e.getMessage());
        }
        // Display chart
        SwingWrapper<XYChart> wrapper = new SwingWrapper<>(chart);

        javax.swing.JFrame frame = wrapper.displayChart();
        frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    }


}

