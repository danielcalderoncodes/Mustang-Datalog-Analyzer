public class WotPullComparison {

    private final WotPull pull1;
    private final WotPull pull2;

    public WotPullComparison(WotPull pull1, WotPull pull2){
        this.pull1 = pull1;
        this.pull2 = pull2;
    }

    public double compareAcceleration(){
    return pull2.getAverageAcceleration() - pull1.getAverageAcceleration();
    }

    public double comparePeakRpm(){
    return pull2.getPeakRpm() - pull1.getPeakRpm();
    }

    public double compareSpeedGain(){
    return pull2.getSpeedGain() - pull1.getSpeedGain();
    }

    public double compareDuration(){
    return pull2.getDuration()-pull1.getDuration();
    }

    private double calculatePercentChange(double original, double newValue){
        if (original == 0){
            return Double.NaN;
        }

        return ((newValue - original) / original) * 100;
    }

    public double getAccelerationPercentChange(){
        return calculatePercentChange(
                pull1.getAverageAcceleration(),
                pull2.getAverageAcceleration()
        );
    }

    public double getPeakRpmPercentChange(){
        return calculatePercentChange(
                pull1.getPeakRpm(),
                pull2.getPeakRpm()
        );
    }

    public double getSpeedGainPercentChange(){
        return calculatePercentChange(
                pull1.getSpeedGain(),
                pull2.getSpeedGain()
        );
    }

    public double getDurationPercentChange(){
        return calculatePercentChange(
                pull1.getDuration(),
                pull2.getDuration()
        );
    }
}

