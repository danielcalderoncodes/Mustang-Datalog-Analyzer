public class AccelerationRun {

    private final double startSpeed;
    private final double endSpeed;
    private final double startTime;
    private final double endTime;

    public AccelerationRun(double startSpeed,
                           double endSpeed,
                           double startTime,
                           double endTime){

        this.startSpeed = startSpeed;
        this.endSpeed = endSpeed;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public double getStartSpeed(){
        return startSpeed;
    }

    public double getEndSpeed(){
        return endSpeed;
    }

    public double getStartTime(){
        return startTime;
    }

    public double getEndTime(){
        return endTime;
    }

    public double getDuration(){
        return endTime - startTime;
    }
}
