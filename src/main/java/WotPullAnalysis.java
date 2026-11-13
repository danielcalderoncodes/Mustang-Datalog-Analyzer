import java.util.ArrayList;

public class WotPullAnalysis {

    private final ArrayList<WotPull> pulls;

    public WotPullAnalysis(Vehicle vehicle){
        this.pulls = vehicle.getWotPulls();
    }

    public ArrayList<WotPull> getPulls(){
        return new ArrayList<>(pulls);
    }

    public int getNumberOfPulls(){
        return pulls.size();
    }

    public WotPull getFastestPull(){

        WotPull fastestPull = null;

        for (WotPull pull : pulls){

            if (!pull.isComplete()){
                continue;
            }

            if (fastestPull == null ||
                    pull.getAverageAcceleration() >
                            fastestPull.getAverageAcceleration()){

                fastestPull = pull;
            }
        }

        return fastestPull;
    }

    public WotPull getLongestPull(){

        WotPull longestPull = null;

        for (WotPull pull : pulls){
            if (!pull.isComplete()){
                continue;
            }
            if (longestPull == null || pull.getDuration() > longestPull.getDuration()){
                longestPull = pull;
            }

        }

        return longestPull;
    }

    public WotPull getHighestRpmPull(){

        WotPull highRPMPull = null;

        for (WotPull pull : pulls){
            if (!pull.isComplete()){
                continue;
            }
            if (highRPMPull == null || pull.getPeakRpm() > highRPMPull.getPeakRpm()){
                highRPMPull = pull;
            }

        }

        return highRPMPull;
    }

    public double getAveragePullAcceleration(){

        int complete = 0;
        double totalAcceleration = 0;

        for (WotPull pull : pulls){

            if (!pull.isComplete()){
                continue;
            }

            totalAcceleration += pull.getAverageAcceleration();
            complete++;
        }

        if (complete == 0){
            return 0;
        }

        return totalAcceleration / complete;
    }

    public ArrayList<WotPull> getCompletePulls(){

        ArrayList<WotPull> completePulls = new ArrayList<>();

        for (WotPull pull : pulls){

            if (pull.isComplete()){
                completePulls.add(pull);
            }
        }

        return completePulls;
    }

    public int getNumberOfCompletePulls(){
        return getCompletePulls().size();
    }

    public int getNumberOfIncompletePulls(){
        return pulls.size() - getNumberOfCompletePulls();
    }
}