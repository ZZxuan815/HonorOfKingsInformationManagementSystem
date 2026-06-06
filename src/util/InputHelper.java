package util;

import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        return readInt(prompt, "Invalid input. Please enter a valid integer.");
    }

    public static int readInt(String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static int readIntRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt, "Invalid input. Please enter a valid integer between " + min + " and " + max + ".");
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
}
