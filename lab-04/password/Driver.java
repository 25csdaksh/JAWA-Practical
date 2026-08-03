public class Driver {
    public static void main(String[] args) {
        String[] passwords = {
            "abc",
            "Abcd1234!",
            "weak1",
            "Medium12",
            "NoSpecial123",
            "SPECIAL!",
            "12345678",
            "Ab1!"
        };

        System.out.println("=== PASSWORD STRENGTH CHECKER TEST ===");
        for (String pw : passwords) {
            boolean length = PasswordChecker.checkLength(pw);
            boolean upper = PasswordChecker.checkUppercase(pw);
            boolean digit = PasswordChecker.checkDigit(pw);
            boolean special = PasswordChecker.checkSpecial(pw);
            String label = PasswordChecker.strength(pw);

            System.out.printf("Password: \"%s\"\n", pw);
            System.out.printf(" - Rule 1 (Length >= 8)      : %s\n", length ? "PASS" : "FAIL");
            System.out.printf(" - Rule 2 (Contains Upper)   : %s\n", upper ? "PASS" : "FAIL");
            System.out.printf(" - Rule 3 (Contains Digit)   : %s\n", digit ? "PASS" : "FAIL");
            System.out.printf(" - Rule 4 (Contains Special) : %s\n", special ? "PASS" : "FAIL");
            System.out.printf(" - Final Strength Label      : %s\n\n", label);
        }
    }
}
