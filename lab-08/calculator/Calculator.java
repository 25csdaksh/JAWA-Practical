package calculator;

public class Calculator {
    public static double calculate(double a, double b, char op) throws DivideByZeroException, IllegalArgumentException {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0.0) {
                    throw new DivideByZeroException("Cannot divide " + a + " by zero.");
                }
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unsupported arithmetic operator: '" + op + "'");
        };
    }
}
