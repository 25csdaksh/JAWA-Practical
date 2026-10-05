package form;

import java.util.List;

public class FormDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     PRACTICAL 7 - PART A1: FORM VALIDATOR       ");
        System.out.println("=================================================");

        // Test 1: Valid form
        SignupForm validForm = new SignupForm("daksh_soni", "daksh@charusat.edu.in", "SecurePass#2026");
        System.out.println("\n--- [Test 1: Valid SignupForm] ---");
        List<String> errors1 = FormValidator.validate(validForm);
        if (errors1.isEmpty()) {
            System.out.println("[SUCCESS] Form is completely valid! No errors found.");
        } else {
            System.out.println("[FAILED] Found errors: " + errors1);
        }

        // Test 2: Invalid form (Blank fields and exceeding MaxLength)
        SignupForm invalidForm = new SignupForm("super_long_username_exceeding_twelve", "   ", "this_is_an_extremely_long_password_that_exceeds_twenty_characters");
        System.out.println("\n--- [Test 2: Invalid SignupForm (Blank & MaxLength Errors)] ---");
        List<String> errors2 = FormValidator.validate(invalidForm);
        System.out.println("Validation Errors Caught (" + errors2.size() + "):");
        for (String err : errors2) {
            System.out.println(" - " + err);
        }

        System.out.println("=================================================\n");
    }
}
