
public class CsvReadResult {

    private Vehicle vehicle;
    private int validReadings;
    private int skippedReadings;

        public CsvReadResult(Vehicle vehicle, int validReadings, int skippedReadings) {
            this.vehicle = vehicle;
            this.validReadings = validReadings;
            this.skippedReadings = skippedReadings;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public int getValidReadings() {
            return validReadings;
        }

        public int getSkippedReadings() {
            return skippedReadings;
        }
    }
