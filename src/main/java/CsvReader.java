import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {
    public static CsvReadResult readVehicleFromCsv(String filePath, String vehicleName) throws IOException{

        Vehicle vehicle = new Vehicle(vehicleName);

        int validReadings = 0;
        int skippedReadings = 0;

        //MAXIMUM AND MINIMUM VALUES
        int maxPossibleRpm = 8000;
        int maxPossibleMph = 200;
        int maxPossibleThrottle = 100;
        int maxPossibleCoolantTemp = 300;
        int minPossibleRpm = 0;
        int minPossibleMph = 0;
        int minPossibleThrottle = 0;
        int minPossibleCoolantTemp = 0;


        //buffer reader gives access to more methods like "readLine" and essentially "wraps" the standard filereader
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            // Skip the CSV header
            reader.readLine();
            double previousTime = -1;
            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] values = line.split(",");
                if (values.length != 5) {
                    skippedReadings++;
                    continue; //this skips the rest of the current loop interation
                }

                //must use PARSE because the program will read in Strings from file
                try {
                    double time = Double.parseDouble(values[0].trim());
                    int rpm = Integer.parseInt(values[1].trim());
                    double speed = Double.parseDouble(values[2].trim());
                    double throttle = Double.parseDouble(values[3].trim());
                    double coolant = Double.parseDouble(values[4].trim());

                    if (!Double.isFinite(time)
                            || !Double.isFinite(rpm)
                            || !Double.isFinite(speed)
                            || !Double.isFinite(throttle)
                            || !Double.isFinite(coolant)) {

                        skippedReadings++;
                        continue;
                    }

                    if (time < 0 || time <= previousTime || speed > maxPossibleMph || throttle > maxPossibleThrottle || rpm > maxPossibleRpm || coolant > maxPossibleCoolantTemp || rpm < minPossibleRpm || speed < minPossibleMph || throttle < minPossibleThrottle || coolant < minPossibleCoolantTemp) {
                        skippedReadings++;
                    } else {
                        DataPoint point = new DataPoint(
                                time, rpm, speed, throttle, coolant
                        );

                        vehicle.addDataPoint(point);
                        previousTime = time;
                        validReadings++;
                    }
                } catch (NumberFormatException e) {
                    skippedReadings++;
                }
            }
        }
        return new CsvReadResult(vehicle, validReadings, skippedReadings);
    }
}