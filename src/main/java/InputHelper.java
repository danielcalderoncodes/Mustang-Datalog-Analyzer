//decreases risk of someone breaking program with incorrent inputs
import java.util.Scanner;

public class InputHelper {

    public static double readDouble(
            Scanner scanner, String prompt){

        while (true){

            System.out.print(prompt);

            String input = scanner.nextLine();

            try {

                double value = Double.parseDouble(input);

                if (Double.isFinite(value)){
                    return value;
                }

                System.out.println("Please enter a valid number.");

            } catch (NumberFormatException e){

                System.out.println(
                        "Invalid input. Please enter a number.");
            }
        }
    }
}