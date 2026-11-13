public class WotPull {
    private final double  startTime;
    private final double endTime;
    private final double startRpm;
    private final double endRpm;
    private final double peakRpm;
    private final double startSpeed;
    private final double endSpeed;
    private final boolean complete;

    //constructor
    public WotPull(double startTime, double endTime, double startRpm, double endRpm, double peakRpm, double startSpeed, double endSpeed, boolean complete) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startRpm = startRpm;
        this.endRpm = endRpm;
        this.peakRpm = peakRpm;
        this.startSpeed = startSpeed;
        this.endSpeed = endSpeed;
        this.complete = complete;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getStartRpm() {
        return startRpm;
    }

    public double getEndRpm() {
        return endRpm;
    }

    public double getPeakRpm() {
        return peakRpm;
    }

    public double getStartSpeed() {
        return startSpeed;
    }

    public double getEndSpeed() {
        return endSpeed;
    }

    public double getDuration(){
        return endTime-startTime;
    }

    public double getSpeedGain(){
        return endSpeed-startSpeed;
    }

    public double getRpmGain(){
        return endRpm-startRpm;
    }

    public boolean isComplete(){ return complete;}

    public double getAverageAcceleration(){
        if (getDuration() == 0){
            return 0;
        }

        return getSpeedGain() / getDuration();
    }

    public double getRpmGainPerSecond(){
        if (getDuration() == 0){
            return 0;
        }

        return getRpmGain() / getDuration();
    }
}
