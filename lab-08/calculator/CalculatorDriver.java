package calculator;

import java.util.Scanner;

public class CalculatorDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   PRACTICAL 8 - PART A1: GUARDED CALCULATOR     ");
        System.out.println("=================================================");

        // Array of test scenarios to demonstrate looped retry and exception handling
        String[][] testInputs = {
            { "abc", "10", "+" },      // Invalid number input
            { "50", "0", "/" },        // Custom DivideByZeroException
            { "25", "5", "?" },        // Invalid operator
            { "100", "4", "/" }        // Valid calculation
        };

        int attempt = 0;
        boolean success = false;

        System.out.println("Processing calculation attempts with robust try-catch-finally:\n");

        for (String[] input : testInputs) {
            attempt++;
            System.out.println(String.format("--- [Attempt #%d] Input: a=\"%s\", b=\"%s\", op=\"%s\" ---",
                    attempt, input[0], input[1], input[2]));
            try {
                // Parsing numbers with separate NumberFormatException handler
                double a = Double.parseDouble(input[0]);
                double b = Double.parseDouble(input[1]);
                char op = input[2].charAt(0);

                double result = Calculator.calculate(a, b, op);
                System.out.println(String.format(" -> [CALCULATION SUCCESS] Result: %.2f %c %.2f = %.2f", a, op, b, result));
                success = true;
                break; // Stop looping on successful calculation
            } catch (NumberFormatException e) {
                System.out.println(" -> [ERROR: INVALID INPUT] Non-numeric value encountered: " + e.getMessage());
            } catch (DivideByZeroException e) {
                System.out.println(" -> [ERROR: ARITHMETIC FAULT] " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(" -> [ERROR: OPERATOR ERROR] " + e.getMessage());
            } finally {
                System.out.println(" -> [FINALLY LOG] Attempt #" + attempt + " audit log recorded at " + java.time.LocalTime.now() + "\n");
            }
        }

        System.out.println("Guarded calculation loop terminated. Status: " + (success ? "SUCCEEDED" : "FAILED"));
        System.out.println("=================================================\n");
    }
}
