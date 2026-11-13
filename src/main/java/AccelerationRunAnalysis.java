import java.util.ArrayList;

public class AccelerationRunAnalysis {

    private final ArrayList<AccelerationRun> runs;

    public AccelerationRunAnalysis(
            Vehicle vehicle,
            double startSpeed,
            double endSpeed){

        this.runs = vehicle.getAccelerationRuns(
                startSpeed, endSpeed);
    }

    public ArrayList<AccelerationRun> getRuns(){
        return new ArrayList<>(runs);
    }

    public int getNumberOfRuns(){
        return runs.size();
    }

    public AccelerationRun getFastestRun(){

        AccelerationRun fastestRun = null;

        for (AccelerationRun run : runs){

            if (fastestRun == null ||
                    run.getDuration() < fastestRun.getDuration()){

                fastestRun = run;
            }
        }

        return fastestRun;
    }
}
