public class VehicleComparison {
    //variables
    private final Vehicle vehicle1;
    private final Vehicle vehicle2;

    //constructor
    public VehicleComparison(Vehicle vehicle1, Vehicle vehicle2){
        this.vehicle1 = vehicle1;
        this.vehicle2 = vehicle2;
    }

    //methods
    public int comparePeakRpm(){
        int rpm1 = vehicle1.getPeakRpm();
        int rpm2 = vehicle2.getPeakRpm();
        int compare = rpm2-rpm1;
        return compare;
    }

    public double compareAverageRpm(){
        double avg1 = vehicle1.getAverageRpm();
        double avg2 = vehicle2.getAverageRpm();
        double compare = avg2 - avg1;
        return compare;

    }

    public double compareAverageSpeed(){
        double speed1 = vehicle1.getAverageSpeed();
        double speed2 = vehicle2.getAverageSpeed();
        double compare = speed2-speed1;
        return compare;
    }

    public double[] compareRpmDistributions(){
        double[] rpm1 = vehicle1.calculateRpmPercentages();
        double[] rpm2 = vehicle2.calculateRpmPercentages();
        double[] differences = new double[rpm1.length];
        for (int i = 0; i < 5; i++ ) {
            double diff = rpm2[i] - rpm1[i];
            differences[i] = diff;
        }
        return differences;
    }

    public void printRpmDistributionComparison(){
        double[] rpmComparison = compareRpmDistributions();
        String[] categories = {
                "Idle",
                "Low",
                "Mid",
                "High",
                "Redline"

        };

        System.out.println("\n===== RPM Distribution Comparison =====");

        for (int i = 0; i < rpmComparison.length; i++) {
            System.out.printf("%s: %+.1f percentage points%n",
                    categories[i], rpmComparison[i]);
        }
    }

    public void interpretRpmDistribution() {

        double[] differences = compareRpmDistributions();

        double higherRpmDifference = differences[3] + differences[4];
        double lowerRpmDifference = differences[0] + differences[1];

        System.out.println("\n===== RPM Analysis =====");

        if (higherRpmDifference > 0) {
            System.out.println("Session B spent more time in higher RPM ranges.");
        }
        else if (higherRpmDifference < 0) {
            System.out.println("Session A spent more time in higher RPM ranges.");
        }
        else {
            System.out.println("Both sessions spent equal time in higher RPM ranges.");
        }

        if (lowerRpmDifference > 0) {
            System.out.println("Session B spent more time in lower RPM ranges.");
        }
        else if (lowerRpmDifference < 0) {
            System.out.println("Session A spent more time in lower RPM ranges.");
        }
        else {
            System.out.println("Both sessions spent equal time in lower RPM ranges.");
        }
    }
}