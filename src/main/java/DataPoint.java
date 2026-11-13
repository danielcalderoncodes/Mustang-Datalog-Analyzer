public class DataPoint {
    //variables
    private double timeSeconds;
    private int rpm;
    private double speedMph;
    private double throttlePercent;
    private double coolantTempF;

    //constructor
    public DataPoint(double timeSeconds, int rpm, double speedMph, double throttlePercent, double coolantTempF){
        this.timeSeconds = timeSeconds;
        this.rpm = rpm;
        this.speedMph = speedMph;
        this.throttlePercent = throttlePercent;
        this.coolantTempF = coolantTempF;
    }

    //getters
    public double getTimeSeconds() {
        return timeSeconds;
    }

    public int getRpm() {
        return rpm;
    }

    public double getSpeedMph() {
        return speedMph;
    }

    public double getThrottlePercent() {
        return throttlePercent;
    }

    public double getCoolantTempF() {
        return coolantTempF;
    }

}
