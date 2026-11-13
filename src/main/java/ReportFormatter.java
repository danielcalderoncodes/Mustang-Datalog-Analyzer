public class ReportFormatter {

    public static String formatPercentage(double value){

        if (!Double.isFinite(value)){
            return "N/A";
        }

        return String.format("%+.2f%%", value);
    }

    public static String formatNumber(double value){

        if (!Double.isFinite(value)){
            return "N/A";
        }

        return String.format("%.2f", value);
    }
}
