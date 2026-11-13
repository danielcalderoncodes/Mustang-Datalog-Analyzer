
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            System.out.println("\n===== Vehicle Datalog Analyzer =====");
            System.out.println("1. Analyze a single log");
            System.out.println("2. Compare two sessions");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    analyzeSingleLog(scanner);
                    break;

                case "2":
                    compareSessions(scanner);
                    break;

                case "3":
                    running = false;
                    System.out.println("Exiting analyzer...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    public static void analyzeSingleLog(Scanner scanner) {

        System.out.print("Enter session name: ");
        String name = scanner.nextLine();
        System.out.print("Enter CSV file path: ");
        String filePath = scanner.nextLine();

        try {
            CsvReadResult result =
                    CsvReader.readVehicleFromCsv(filePath, name);

            Vehicle vehicle = result.getVehicle();

            System.out.println("\n===== Analysis Results =====");
            System.out.println("Vehicle: " + vehicle.getName());
            System.out.println("Valid readings: " + result.getValidReadings());
            System.out.println("Skipped readings: " + result.getSkippedReadings());

            System.out.println("Total readings: " + vehicle.getNumberOfReadings());
            System.out.println("Peak RPM: " + vehicle.getPeakRpm());
            System.out.println("Average RPM: " + vehicle.getAverageRpm());
            System.out.println("Average speed: " + vehicle.getAverageSpeed());
            System.out.println("Maximum coolant temperature: "
                    + vehicle.getMaxCoolantTemp() + " F");
            System.out.println("Wide-open throttle events: "
                    + vehicle.countWideOpenThrottleReadings());
            chartMenu(vehicle, result);

        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public static void compareSessions(Scanner scanner) {

        System.out.print("Enter first session name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter first CSV file path: ");
        String firstPath = scanner.nextLine();

        System.out.print("Enter second session name: ");
        String secondName = scanner.nextLine();
        System.out.print("Enter second CSV file path: ");
        String secondPath = scanner.nextLine();

        try {
            CsvReadResult firstResult =
                    CsvReader.readVehicleFromCsv(firstPath, firstName);

            CsvReadResult secondResult =
                    CsvReader.readVehicleFromCsv(secondPath, secondName);

            Vehicle firstVehicle = firstResult.getVehicle();
            Vehicle secondVehicle = secondResult.getVehicle();

            VehicleComparison comparison =
                    new VehicleComparison(firstVehicle, secondVehicle);

            System.out.println("\n===== Session Comparison =====");

            System.out.println("First session: " + firstVehicle.getName());
            System.out.println("Second session: " + secondVehicle.getName());

            System.out.println("\n===== File Quality =====");

            System.out.println("First session valid readings: "
                    + firstResult.getValidReadings());
            System.out.println("First session skipped readings: "
                    + firstResult.getSkippedReadings());

            System.out.println("Second session valid readings: "
                    + secondResult.getValidReadings());
            System.out.println("Second session skipped readings: "
                    + secondResult.getSkippedReadings());

            System.out.println("\n===== Performance Differences =====");

            System.out.println("Peak RPM difference: "
                    + comparison.comparePeakRpm());

            System.out.println("Average RPM difference: "
                    + comparison.compareAverageRpm());

            System.out.println("Average speed difference: "
                    + comparison.compareAverageSpeed());


            System.out.println("\nPositive differences mean the second session was higher.");

            comparisonChartMenu(firstVehicle, secondVehicle);

        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public static void chartMenu(Vehicle vehicle, CsvReadResult result) {

        boolean running = true;

        while (running) {

            System.out.println("\n===== DATA VISUALIZATION =====");
            System.out.println("1. RPM vs. Time");
            System.out.println("2. Coolant Temperature vs. Time");
            System.out.println("3. Throttle Position vs. Time");
            System.out.println("4. Display All Charts");
            System.out.println("5. Display Performance Report");
            System.out.println("6. View Data Quality Report");
            System.out.println("7. WOT Pull Report");
            System.out.println("8. Acceleration Analysis");
            System.out.println("9. Return to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    RpmChart.displaySensorChart(
                            vehicle,
                            "Engine RPM vs. Time",
                            "RPM",
                            "Engine RPM",
                            point -> (double) point.getRpm(),
                            "Engine_RPM"
                    );
                    break;

                case "2":
                    RpmChart.displaySensorChart(
                            vehicle,
                            "Coolant Temperature vs. Time",
                            "Temperature (F)",
                            "Coolant Temperature",
                            point -> (double) point.getCoolantTempF(),
                            "Coolant_Temps"
                    );
                    break;

                case "3":
                    RpmChart.displaySensorChart(
                            vehicle,
                            "Throttle Position vs. Time",
                            "Throttle (%)",
                            "Throttle Position",
                            point -> (double) point.getThrottlePercent(),
                            "Throttle_Position"
                    );
                    break;
                case "4":
                    RpmChart.displaySensorChart(
                            vehicle,
                            "Engine RPM vs. Time",
                            "RPM",
                            "Engine RPM",
                            point -> (double) point.getRpm(),
                            "Engine_RPM"
                    );
                    RpmChart.displaySensorChart(
                            vehicle,
                            "Coolant Temperature vs. Time",
                            "Temperature (F)",
                            "Coolant Temperature",
                            point -> (double) point.getCoolantTempF(),
                            "Coolant_Temps"
                    );
                    RpmChart.displaySensorChart(
                            vehicle,
                            "Throttle Position vs. Time",
                            "Throttle (%)",
                            "Throttle Position",
                            point -> (double) point.getThrottlePercent(),
                            "Throttle_Position"
                    );
                    break;
                case "5":
                    displayPerformanceReport(vehicle);
                    break;

                case "6":
                    displayDataQualityReport(vehicle, result);
                    break;

                case "7":
                    displayWotPullReport(vehicle);
                    break;
                case "8":
                    displayAccelerationReport(vehicle);
                    break;
                case "9":
                    running = false;
                    break;


                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void comparisonChartMenu(Vehicle vehicle1, Vehicle vehicle2) {
        boolean running = true;

        while (running) {

            System.out.println("\n===== COMPARISON VISUALIZATIONS =====");
            System.out.println("1. Before vs. After RPM");
            System.out.println("2. Before vs. After Coolant Temperature");
            System.out.println("3. Before vs After Throttle Position");
            System.out.println("4. Display All Charts");
            System.out.println("5. WOT Comparison Report");
            System.out.println("6. Acceleration Comparison");
            System.out.println("7. Return to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    RpmChart.displayComparisonSensorChart(
                            vehicle1, vehicle2,
                            "RPM Comparison", "RPM",
                            vehicle1.getName(), vehicle2.getName(),
                            point -> (double) point.getRpm(),
                            "Before_Tune_vs_After_Tune_RPM"
                    );
                    break;

                case "2":
                    RpmChart.displayComparisonSensorChart(
                            vehicle1, vehicle2,
                            "Coolant Temperature Comparison", "Temperature (F)",
                            vehicle1.getName(), vehicle2.getName(),
                            point -> (double) point.getCoolantTempF(),
                            "Before_Tune_vs_After_Tune_Coolant"
                    );
                    break;

                case "3":
                    RpmChart.displayComparisonSensorChart(
                            vehicle1, vehicle2,
                            "Throttle Comparison", "Throttle (%)",
                            vehicle1.getName(), vehicle2.getName(),
                            point -> (double) point.getThrottlePercent(),
                            "Before_Tune_vs_After_Tune_Throttle"
                    );
                    break;
                case "4":
                    RpmChart.displayComparisonSensorChart(
                            vehicle1, vehicle2,
                            "RPM Comparison", "RPM",
                            vehicle1.getName(), vehicle2.getName(),
                            point -> (double) point.getRpm(),
                            "Before_Tune_vs_After_Tune_RPM"
                    );
                    RpmChart.displayComparisonSensorChart(
                            vehicle1, vehicle2,
                            "Coolant Temperature Comparison", "Temperature (F)",
                            vehicle1.getName(), vehicle2.getName(),
                            point -> (double) point.getCoolantTempF(),
                            "Before_Tune_vs_After_Tune_Coolant"
                    );
                    RpmChart.displayComparisonSensorChart(
                            vehicle1, vehicle2,
                            "Throttle Comparison", "Throttle (%)",
                            vehicle1.getName(), vehicle2.getName(),
                            point -> (double) point.getThrottlePercent(),
                            "Before_Tune_vs_After_Tune_Throttle"
                    );
                    break;
                case "5":
                    displayWotComparisonReport(vehicle1,vehicle2);
                    break;
                case "6":
                    displayAccelerationComparisonReport(vehicle1,vehicle2);
                    break;
                case "7":
                    running = false;
                    break;


                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void displayPerformanceReport(Vehicle vehicle) {

        System.out.println("\n========== PERFORMANCE REPORT ==========");
        System.out.println("Vehicle: " + vehicle.getName());
        System.out.println("----------------------------------------");

        System.out.printf("%-28s %,d%n", "Peak RPM:", vehicle.getPeakRpm());
        System.out.printf("%-28s %.2f%n", "Average RPM:", vehicle.getAverageRpm());
        System.out.printf("%-28s %.2f MPH%n", "Average Speed:", vehicle.getAverageSpeed());
        System.out.printf("%-28s %.2f °F%n", "Max Coolant Temperature:", vehicle.getMaxCoolantTemp());

        System.out.println("----------------------------------------");

        System.out.printf("%-28s %.2f%%%n", "Wide Open Throttle:", vehicle.getWideOpenThrottlePercentage());
        System.out.println("Total WOT Pulls: " + vehicle.countWotPulls());
        System.out.printf("%-28s %.2f%%%n", "High RPM Readings:", vehicle.getHighPercentage());
        System.out.printf("%-28s %.2f seconds%n", "Time Above 5,000 RPM:", vehicle.getTimeAbove5000Rpm());
        System.out.printf("%-28s %.2f%%%n", "Average Throttle:", vehicle.getAverageThrottle());

        System.out.println("========================================\n");
    }
    private static void displayDataQualityReport(Vehicle vehicle, CsvReadResult result) {

        System.out.println("\n========== DATA QUALITY REPORT ==========");
        System.out.println("Vehicle: " + vehicle.getName());
        System.out.println("-----------------------------------------");

        System.out.printf("%-30s %d%n", "Valid Readings:", result.getValidReadings());
        System.out.printf("%-30s %d%n", "Skipped Readings:", result.getSkippedReadings());
        System.out.printf("%-30s %.2f seconds%n", "Expected Sampling Interval:", vehicle.getExpectedSamplingInterval());
        System.out.printf("%-30s %d%n", "Estimated Missing Readings:", vehicle.getNumberOfMissingReadings());
        System.out.printf("%-30s %d%n", "Irregular Intervals:", vehicle.getNumberOfIrregularIntervals());

        System.out.println("=========================================\n");
    }

    public static void displayWotPullReport(Vehicle vehicle){

        WotPullAnalysis analysis = new WotPullAnalysis(vehicle);

        ArrayList<WotPull> pulls = analysis.getPulls();

        System.out.println("\n--- WOT Pull Report ---");

        System.out.println("Vehicle: " + vehicle.getName());

        System.out.println("Total Pulls: " +
                analysis.getNumberOfPulls());

        System.out.println("Complete Pulls: " +
                analysis.getNumberOfCompletePulls());

        System.out.println("Incomplete Pulls: " +
                analysis.getNumberOfIncompletePulls());

        if (pulls.isEmpty()){
            System.out.println("No WOT pulls detected.");
            return;
        }

        // Display each individual pull
        for (int i = 0; i < pulls.size(); i++){

            WotPull pull = pulls.get(i);

            System.out.println("\n--- Pull #" + (i + 1) + " ---");

            System.out.println("Status: " +
                    (pull.isComplete() ? "Complete" : "Incomplete"));

            System.out.printf("Start Time: %.2f seconds%n",
                    pull.getStartTime());

            System.out.printf("End Time: %.2f seconds%n",
                    pull.getEndTime());

            System.out.printf("Duration: %.2f seconds%n",
                    pull.getDuration());

            System.out.printf("Start RPM: %.0f RPM%n",
                    pull.getStartRpm());

            System.out.printf("End RPM: %.0f RPM%n",
                    pull.getEndRpm());

            System.out.printf("Peak RPM: %.0f RPM%n",
                    pull.getPeakRpm());

            System.out.printf("RPM Gain: %.0f RPM%n",
                    pull.getRpmGain());

            System.out.printf("Start Speed: %.2f mph%n",
                    pull.getStartSpeed());

            System.out.printf("End Speed: %.2f mph%n",
                    pull.getEndSpeed());

            System.out.printf("Speed Gain: %.2f mph%n",
                    pull.getSpeedGain());

            System.out.printf("Average Acceleration: %.2f mph/s%n",
                    pull.getAverageAcceleration());

            System.out.printf("RPM Gain Per Second: %.2f RPM/s%n",
                    pull.getRpmGainPerSecond());
        }

        // Summary statistics
        System.out.println("\n--- WOT Pull Summary ---");

        if (analysis.getNumberOfCompletePulls() == 0){

            System.out.println(
                    "No complete WOT pulls available for analysis.");

            return;
        }

        WotPull fastest = analysis.getFastestPull();
        WotPull longest = analysis.getLongestPull();
        WotPull highestRpm = analysis.getHighestRpmPull();

        System.out.printf("Fastest Pull: %.2f mph/s%n",
                fastest.getAverageAcceleration());

        System.out.printf("Longest Pull: %.2f seconds%n",
                longest.getDuration());

        System.out.printf("Highest RPM Pull: %.0f RPM%n",
                highestRpm.getPeakRpm());

        System.out.printf("Average Pull Acceleration: %.2f mph/s%n",
                analysis.getAveragePullAcceleration());
    }

    public static void displayWotComparisonReport(
            Vehicle vehicle1, Vehicle vehicle2){

        WotPullAnalysis analysis1 = new WotPullAnalysis(vehicle1);
        WotPullAnalysis analysis2 = new WotPullAnalysis(vehicle2);

        WotPull fastest1 = analysis1.getFastestPull();
        WotPull fastest2 = analysis2.getFastestPull();

        System.out.println("\n--- WOT Comparison Report ---");

        if (fastest1 == null || fastest2 == null){
            System.out.println(
                    "Both sessions must have at least one complete WOT pull.");
            return;
        }

        WotPullComparison comparison =
                new WotPullComparison(fastest1, fastest2);

        System.out.println("Session 1: " + vehicle1.getName());
        System.out.println("Session 2: " + vehicle2.getName());

        System.out.println("\n--- Average Acceleration ---");

        System.out.printf("%s: %.2f mph/s%n",
                vehicle1.getName(),
                fastest1.getAverageAcceleration());

        System.out.printf("%s: %.2f mph/s%n",
                vehicle2.getName(),
                fastest2.getAverageAcceleration());

        System.out.printf("Difference: %+.2f mph/s%n",
                comparison.compareAcceleration());

        System.out.println("Percentage Change: " +
                ReportFormatter.formatPercentage(
                        comparison.getAccelerationPercentChange()));


        System.out.println("\n--- Peak RPM ---");

        System.out.printf("%s: %.0f RPM%n",
                vehicle1.getName(), fastest1.getPeakRpm());

        System.out.printf("%s: %.0f RPM%n",
                vehicle2.getName(), fastest2.getPeakRpm());

        System.out.printf("Difference: %+.0f RPM%n",
                comparison.comparePeakRpm());

        System.out.println("Percentage Change: " +
                ReportFormatter.formatPercentage(
                        comparison.getAccelerationPercentChange()));


        System.out.println("\n--- Speed Gain ---");

        System.out.printf("%s: %.2f mph%n",
                vehicle1.getName(), fastest1.getSpeedGain());

        System.out.printf("%s: %.2f mph%n",
                vehicle2.getName(), fastest2.getSpeedGain());

        System.out.printf("Difference: %+.2f mph%n",
                comparison.compareSpeedGain());

        System.out.println("Percentage Change: " +
                ReportFormatter.formatPercentage(
                        comparison.getAccelerationPercentChange()));


        System.out.println("\n--- Pull Duration ---");

        System.out.printf("%s: %.2f seconds%n",
                vehicle1.getName(), fastest1.getDuration());

        System.out.printf("%s: %.2f seconds%n",
                vehicle2.getName(), fastest2.getDuration());

        System.out.printf("Difference: %+.2f seconds%n",
                comparison.compareDuration());

        System.out.println("Percentage Change: " +
                ReportFormatter.formatPercentage(
                        comparison.getAccelerationPercentChange()));
    }

    public static void displayAccelerationReport(Vehicle vehicle){

        System.out.println("\n--- Acceleration Analysis ---");

        double startSpeed = InputHelper.readDouble(
                scanner, "Enter starting speed (mph): ");

        double endSpeed = InputHelper.readDouble(
                scanner, "Enter ending speed (mph): ");


        if (startSpeed < 0 || endSpeed <= startSpeed){
            System.out.println("Invalid speed range.");
            return;
        }

        AccelerationRunAnalysis analysis =
                new AccelerationRunAnalysis(
                        vehicle, startSpeed, endSpeed);

        ArrayList<AccelerationRun> runs = analysis.getRuns();

        System.out.printf("%n--- %.0f-%.0f MPH Report ---%n",
                startSpeed, endSpeed);

        System.out.println("Vehicle: " + vehicle.getName());
        System.out.println("Total Runs: " + analysis.getNumberOfRuns());

        if (runs.isEmpty()){
            System.out.println("No qualifying acceleration runs found.");
            return;
        }

        for (int i = 0; i < runs.size(); i++){

            AccelerationRun run = runs.get(i);

            System.out.println("\nRun #" + (i + 1));

            System.out.printf("Start Time: %.2f seconds%n",
                    run.getStartTime());

            System.out.printf("End Time: %.2f seconds%n",
                    run.getEndTime());

            System.out.printf("Duration: %.2f seconds%n",
                    run.getDuration());
        }

        AccelerationRun fastest = analysis.getFastestRun();

        System.out.println("\n--- Fastest Run ---");

        System.out.printf("%.0f-%.0f MPH: %.2f seconds%n",
                startSpeed, endSpeed, fastest.getDuration());
    }
    public static void displayAccelerationComparisonReport(
            Vehicle vehicle1, Vehicle vehicle2){

        System.out.println("\n--- Acceleration Comparison ---");

        double startSpeed = InputHelper.readDouble(
                scanner, "Enter starting speed (mph): ");

        double endSpeed = InputHelper.readDouble(
                scanner, "Enter ending speed (mph): ");


        if (startSpeed < 0 || endSpeed <= startSpeed){
            System.out.println("Invalid speed range.");
            return;
        }

        AccelerationRunAnalysis analysis1 =
                new AccelerationRunAnalysis(
                        vehicle1, startSpeed, endSpeed);

        AccelerationRunAnalysis analysis2 =
                new AccelerationRunAnalysis(
                        vehicle2, startSpeed, endSpeed);

        AccelerationRun fastest1 = analysis1.getFastestRun();

        AccelerationRun fastest2 = analysis2.getFastestRun();

        if (fastest1 == null || fastest2 == null){
            System.out.println(
                    "Both sessions must have a qualifying acceleration run.");
            return;
        }

        AccelerationRunComparison comparison =
                new AccelerationRunComparison(fastest1, fastest2);

        System.out.printf("%n--- %.0f-%.0f MPH Comparison ---%n",
                startSpeed, endSpeed);

        System.out.printf("%s: %.2f seconds%n",
                vehicle1.getName(),
                fastest1.getDuration());

        System.out.printf("%s: %.2f seconds%n",
                vehicle2.getName(),
                fastest2.getDuration());

        System.out.printf("Duration Difference: %+.2f seconds%n",
                comparison.getDurationDifference());

        System.out.println("Percentage Improvement: " +
                ReportFormatter.formatPercentage(
                        comparison.getPercentImprovement()));

        System.out.println("\n--- Result ---");

        if (Math.abs(comparison.getDurationDifference()) < 0.000001){

            System.out.println("Both sessions tied.");

        } else if (comparison.getFasterRun() == fastest1){

            System.out.println(vehicle1.getName() + " was faster.");

        } else {

            System.out.println(vehicle2.getName() + " was faster.");
        }
    }

}

