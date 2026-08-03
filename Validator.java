import java.util.regex.Pattern;

public class Validator {
    // Mobile: starts with 6-9, followed by 9 digits
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");
    
    // Email: typical email structure
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    
    // PAN: 5 letters, 4 digits, 1 letter (case-insensitive)
    private static final Pattern PAN_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$", Pattern.CASE_INSENSITIVE);
    
    // IFSC: 4 letters, 0, 6 alphanumeric characters (case-insensitive)
    private static final Pattern IFSC_PATTERN = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$", Pattern.CASE_INSENSITIVE);
    
    // Amount: positive whole numbers (starts with 1-9, followed by any digits)
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^[1-9][0-9]*$");

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPan(String pan) {
        return pan != null && PAN_PATTERN.matcher(pan).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC_PATTERN.matcher(ifsc).matches();
    }

    public static boolean isValidAmount(String amount) {
        return amount != null && AMOUNT_PATTERN.matcher(amount).matches();
    }
}
