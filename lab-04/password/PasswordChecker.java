public class PasswordChecker {
    public static boolean checkLength(String pw) {
        return pw != null && pw.length() >= 8;
    }

    public static boolean checkUppercase(String pw) {
        return pw != null && pw.matches(".*[A-Z].*");
    }

    public static boolean checkDigit(String pw) {
        return pw != null && pw.matches(".*[0-9].*");
    }

    public static boolean checkSpecial(String pw) {
        return pw != null && pw.matches(".*[^a-zA-Z0-9].*");
    }

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
