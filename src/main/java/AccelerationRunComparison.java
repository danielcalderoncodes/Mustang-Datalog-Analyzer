public class AccelerationRunComparison {

    private final AccelerationRun run1;
    private final AccelerationRun run2;

    public AccelerationRunComparison(
            AccelerationRun run1,
            AccelerationRun run2){

        this.run1 = run1;
        this.run2 = run2;
    }
    public double getDurationDifference(){
        return run2.getDuration() - run1.getDuration();
    }
    public double getPercentImprovement(){

        if (run1.getDuration() == 0){
            return Double.NaN;
        }

        return ((run1.getDuration() - run2.getDuration())
                / run1.getDuration()) * 100;
    }

    public AccelerationRun getFasterRun(){

        if (run1.getDuration() <= run2.getDuration()){
            return run1;
        }

        return run2;
    }
}
