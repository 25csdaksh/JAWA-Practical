package util;

import java.util.regex.Pattern;

public class Validator {
    // 10 digits starting with 6, 7, 8, or 9
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");

    // Standard email regex format
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    // PAN: 5 uppercase letters, 4 digits, 1 uppercase letter
    private static final Pattern PAN_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    // IFSC: 4 uppercase letters, 0, 6 letters/digits
    private static final Pattern IFSC_PATTERN = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    // Positive integer amounts
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^[1-9][0-9]*$");

    public static boolean isValidMobile(String mobile) {
        if (mobile == null) return false;
        return MOBILE_PATTERN.matcher(mobile.trim()).matches();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPan(String pan) {
        if (pan == null) return false;
        return PAN_PATTERN.matcher(pan.trim()).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        if (ifsc == null) return false;
        return IFSC_PATTERN.matcher(ifsc.trim()).matches();
    }

    public static boolean isValidAmount(String amountStr) {
        if (amountStr == null) return false;
        return AMOUNT_PATTERN.matcher(amountStr.trim()).matches();
    }
}
