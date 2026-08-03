public class PasswordChecker {
    // Check if password has length >= 8
    public static boolean checkLength(String pw) {
        return pw != null && pw.length() >= 8;
    }

    // Check if password contains an uppercase letter
    public static boolean checkUppercase(String pw) {
        return pw != null && pw.matches(".*[A-Z].*");
    }

    // Check if password contains a digit
    public static boolean checkDigit(String pw) {
        return pw != null && pw.matches(".*[0-9].*");
    }

    // Check if password contains a special character (non-alphanumeric)
    public static boolean checkSpecial(String pw) {
        return pw != null && pw.matches(".*[^a-zA-Z0-9].*");
    }

    // Evaluate strength and return label: Weak (0-1), Medium (2-3), Strong (4)
    public static String strength(String pw) {
        int passedRules = 0;
        if (checkLength(pw)) passedRules++;
        if (checkUppercase(pw)) passedRules++;
        if (checkDigit(pw)) passedRules++;
        if (checkSpecial(pw)) passedRules++;

        if (passedRules <= 1) {
            return "Weak";
        } else if (passedRules <= 3) {
            return "Medium";
        } else {
            return "Strong";
        }
    }
}
