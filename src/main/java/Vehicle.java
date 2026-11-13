import java.util.ArrayList;
import java.util.Collections;

public class Vehicle {
    private String name;
    private ArrayList<DataPoint> dataPoints;


    //constructor
    public Vehicle(String name) {
        this.name = name;
        this.dataPoints = new ArrayList<>();
    }

    //getter for graph
    public ArrayList<DataPoint> getDataPoints(){
        return this.dataPoints;
    }
    //method to add data points to vehicles arraylist
    public void addDataPoint(DataPoint point){
        dataPoints.add(point);
    }

    //iterate through all points to find highest rpm
    public int getPeakRpm() {
        int peak = 0;
        for(DataPoint point : dataPoints){
            if (point.getRpm() > peak) {
                peak = point.getRpm();
            }
        }
        return peak;
    }

    //calculate average RPM throughout the drive
    public double getAverageRpm() {
        //handle the 0 so no division error
        if (dataPoints.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (DataPoint point : dataPoints) {
            total += point.getRpm();
        }
        return total / dataPoints.size();
    }

    //calculate average speed across all points
    public double getAverageSpeed() {
        //handle the 0 so no division error
        if (dataPoints.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (DataPoint point : dataPoints) {
            total += point.getSpeedMph();
        }

        return total / dataPoints.size();
    }

    //find max coolant temp
    public double getMaxCoolantTemp() {
        double max = 0;

        for (DataPoint point : dataPoints) {
            if (point.getCoolantTempF() > max) {
                max = point.getCoolantTempF();
            }
        }

        return max;
    }
    //total amount of readings/data points
    public int getNumberOfReadings() {
        return dataPoints.size();
    }

    public int countWideOpenThrottleReadings(){
        int num = 0;

        for (DataPoint point : dataPoints) {
            if (point.getThrottlePercent() >= 90) {
                num ++ ;
            }
        }

        return num;
    }

    public double getWideOpenThrottlePercentage() {
        if (this.getNumberOfReadings() == 0) {
            return 0;
        }

        int num = this.countWideOpenThrottleReadings();

        return ((double) num / this.getNumberOfReadings()) * 100;
    }

    public double getMaxThrottle() {
        double max = 0;

        for (DataPoint point : dataPoints) {
            if (point.getThrottlePercent() > max) {
                max = point.getThrottlePercent();
            }
        }

        return max;
    }


    public int[] countRpmReadings() {

        int[] rpmCounts = new int[5];

        for (DataPoint point : dataPoints) {
            if (point.getRpm() >= 0 && point.getRpm() <=999 ){
                rpmCounts[0] ++;
            } else if (point.getRpm() <=2999){
                rpmCounts[1]++;
            } else if (point.getRpm() <=4999){
                rpmCounts[2] ++;
            } else if (point.getRpm() <= 6499){
                rpmCounts[3] ++;
            } else {
                rpmCounts[4]++;
            }
        }

        return rpmCounts;
    }

    public double getHighPercentage() {
        if (this.getNumberOfReadings() == 0) {
            return 0;
        }

        int highReadings = 0;

        for (DataPoint point : dataPoints) {
            if (point.getRpm() > 5000) {
                highReadings++;
            }
        }

        return ((double) highReadings / this.getNumberOfReadings()) * 100;
    }

    public double getTimeAbove5000Rpm(){
            double totalTime = 0;

            for (int i = 0; i < dataPoints.size() - 1; i++) {

                DataPoint current = dataPoints.get(i);
                DataPoint next = dataPoints.get(i + 1);

                double timeDifference =
                        next.getTimeSeconds() - current.getTimeSeconds();

                if (current.getRpm() > 5000) {
                    totalTime += timeDifference;
                }
            }

            return totalTime;
    }

    public double getAverageThrottle(){
        //handle the 0 so no division error
        if (dataPoints.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (DataPoint point : dataPoints) {
            total += point.getThrottlePercent();
        }
        return total / dataPoints.size();
    }

    public double[] calculateRpmPercentages() {

        int[] counts = countRpmReadings();
        double[] percentages = new double[counts.length];

        if (dataPoints.isEmpty()) {
            return percentages;
        }

        for (int i = 0; i < counts.length; i++) {
            percentages[i] =
                    (double) counts[i] / dataPoints.size() * 100;
        }

        return percentages;
    }

    public double getExpectedSamplingInterval() {

        if (dataPoints.size() < 2) {
            return 0;
        }

        ArrayList<Double> intervals = new ArrayList<>();

        // Calculate time differences between consecutive readings
        for (int i = 0; i < dataPoints.size() - 1; i++) {

            double currentTime = dataPoints.get(i).getTimeSeconds();
            double nextTime = dataPoints.get(i + 1).getTimeSeconds();

            intervals.add(nextTime - currentTime);
        }

        // Sort intervals from smallest to largest
        Collections.sort(intervals);

        // Find the median
        int middle = intervals.size() / 2;

        if (intervals.size() % 2 == 0) {
            return (intervals.get(middle - 1) + intervals.get(middle)) / 2.0;
        } else {
            return intervals.get(middle);
        }
    }

    public int getNumberOfMissingReadings() {

        double expectedInterval = getExpectedSamplingInterval();

        if (expectedInterval <= 0) {
            return 0;
        }

        int missingReadings = 0;

        for (int i = 0; i < dataPoints.size() - 1; i++) {

            double currentTime = dataPoints.get(i).getTimeSeconds();
            double nextTime = dataPoints.get(i + 1).getTimeSeconds();

            double actualInterval = nextTime - currentTime;

            if (actualInterval > expectedInterval) {
                missingReadings += (int) Math.round(actualInterval / expectedInterval) - 1;
            }
        }

        return missingReadings;
    }

    public int getNumberOfIrregularIntervals() {

        double expectedInterval = getExpectedSamplingInterval();

        if (expectedInterval <= 0) {
            return 0;
        }

        int irregularIntervals = 0;

        for (int i = 0; i < dataPoints.size() - 1; i++) {

            double currentTime = dataPoints.get(i).getTimeSeconds();
            double nextTime = dataPoints.get(i + 1).getTimeSeconds();

            double actualInterval = nextTime - currentTime;

            double difference = Math.abs(actualInterval - expectedInterval);

            double percentageDifference = difference / expectedInterval;

            if (percentageDifference > 0.10) {
                irregularIntervals++;
            }
        }

        return irregularIntervals;
    }

    public int countWotPulls(){
        return getWotPulls().size();
    }


    public ArrayList<WotPull> getWotPulls(){

        ArrayList<WotPull> pulls = new ArrayList<>();

        boolean inPull = false;
        boolean observedStart = false;

        DataPoint startPoint = null;
        DataPoint endPoint = null;

        double peakRpm = 0;

        double expectedInterval = getExpectedSamplingInterval();

        for (int i = 0; i < dataPoints.size(); i++){

            DataPoint point = dataPoints.get(i);

            boolean largeGap = false;

            if (i > 0){

                DataPoint previous = dataPoints.get(i - 1);

                largeGap = isLargeGap(
                        previous, point, expectedInterval);
            }

            // End the current pull if a large gap occurs
            if (largeGap && inPull){

                WotPull pull = new WotPull(
                        startPoint.getTimeSeconds(),
                        endPoint.getTimeSeconds(),
                        startPoint.getRpm(),
                        endPoint.getRpm(),
                        peakRpm,
                        startPoint.getSpeedMph(),
                        endPoint.getSpeedMph(),
                        false
                );

                if (pull.getDuration() > 0){
                    pulls.add(pull);
                }

                inPull = false;
            }

            if (point.getThrottlePercent() >= 90){

                if (!inPull){

                    inPull = true;

                    startPoint = point;
                    peakRpm = point.getRpm();

                    // A valid start must follow a non-WOT reading
                    // without a large gap
                    observedStart = i > 0 &&
                            !largeGap &&
                            dataPoints.get(i - 1)
                                    .getThrottlePercent() < 90;
                }

                endPoint = point;

                if (point.getRpm() > peakRpm){
                    peakRpm = point.getRpm();
                }

            } else {

                if (inPull){

                    WotPull pull = new WotPull(
                            startPoint.getTimeSeconds(),
                            endPoint.getTimeSeconds(),
                            startPoint.getRpm(),
                            endPoint.getRpm(),
                            peakRpm,
                            startPoint.getSpeedMph(),
                            endPoint.getSpeedMph(),
                            observedStart
                    );

                    if (pull.getDuration() > 0){
                        pulls.add(pull);
                    }

                    inPull = false;
                }
            }
        }

        // Handle a pull still active at the end of the log
        if (inPull){

            WotPull pull = new WotPull(
                    startPoint.getTimeSeconds(),
                    endPoint.getTimeSeconds(),
                    startPoint.getRpm(),
                    endPoint.getRpm(),
                    peakRpm,
                    startPoint.getSpeedMph(),
                    endPoint.getSpeedMph(),
                    false
            );

            if (pull.getDuration() > 0){
                pulls.add(pull);
            }
        }

        return pulls;
    }
//interpolation
    private double interpolateTime(
        DataPoint point1,
        DataPoint point2,
        double targetSpeed){

        double speed1 = point1.getSpeedMph();
        double speed2 = point2.getSpeedMph();

        double time1 = point1.getTimeSeconds();
        double time2 = point2.getTimeSeconds();

        if (speed1 == speed2){
        return Double.NaN;
        }

        double speedInterval =
            (targetSpeed - speed1) / (speed2 - speed1);

        return time1 + speedInterval * (time2 - time1);
    }

    public ArrayList<AccelerationRun> getAccelerationRuns(
            double startSpeed, double endSpeed){

        double expectedInterval = getExpectedSamplingInterval();

        ArrayList<AccelerationRun> runs = new ArrayList<>();

        boolean inRun = false;
        double startTime = 0;

        if (startSpeed < 0 || endSpeed <= startSpeed){
            return runs;
        }

        for (int i = 1; i < dataPoints.size(); i++){

            DataPoint previous = dataPoints.get(i - 1);
            DataPoint current = dataPoints.get(i);

            if (isLargeGap(previous, current, expectedInterval)){

                inRun = false;
                continue;
            }

            double previousSpeed = previous.getSpeedMph();
            double currentSpeed = current.getSpeedMph();

            // Cancel the run if throttle drops below WOT
            if (previous.getThrottlePercent() < 90 ||
                    current.getThrottlePercent() < 90){

                inRun = false;
                continue;
            }

            // Detect the beginning of a run
            if (!inRun &&
                    previousSpeed <= startSpeed &&
                    currentSpeed >= startSpeed &&
                    currentSpeed > previousSpeed){

                inRun = true;

                startTime = interpolateTime(
                        previous, current, startSpeed);
            }

            // Detect the end of a run
            if (inRun &&
                    previousSpeed < endSpeed &&
                    currentSpeed >= endSpeed){

                double endTime = interpolateTime(
                        previous, current, endSpeed);

                AccelerationRun run = new AccelerationRun(
                        startSpeed,
                        endSpeed,
                        startTime,
                        endTime
                );

                runs.add(run);

                inRun = false;
            }

            // Cancel if speed drops below the starting speed
            if (inRun && currentSpeed < startSpeed){
                inRun = false;
            }
        }

        return runs;
    }


    private boolean isLargeGap(DataPoint previous,
                               DataPoint current,
                               double expectedInterval){

        if (expectedInterval <= 0){
            return false;
        }

        double actualInterval =
                current.getTimeSeconds() -
                        previous.getTimeSeconds();

        return actualInterval > expectedInterval * 2;
    }

    //name of vehicle
    public String getName() {
        return name;
    }
}
