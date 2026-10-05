package testrunner;

public class TestSuite {
    @Run(description = "Verify addition operation")
    public void testAddition() {
        int result = 10 + 20;
        if (result != 30) throw new AssertionError("Expected 30 but got " + result);
        System.out.println("   -> Addition test passed: 10 + 20 == 30");
    }

    @Run(description = "Verify string concatenation")
    public void testStringConcat() {
        String s = "Hello" + " " + "World";
        if (!s.equals("Hello World")) throw new AssertionError("String mismatch");
        System.out.println("   -> String test passed: 'Hello World'");
    }

    @Run(description = "Intentional failure test case")
    public void testDivisionByZero() {
        System.out.println("   -> Testing division arithmetic...");
        int error = 10 / 0; // Throws ArithmeticException
    }

    // Regular helper method NOT marked with @Run (Should NOT be executed by runner)
    public void helperMethod() {
        System.out.println("   -> [WARNING] helperMethod should not be run!");
    }

    @Run(description = "Verify array length")
    public void testArrayLength() {
        int[] arr = new int[5];
        if (arr.length != 5) throw new AssertionError("Array length error");
        System.out.println("   -> Array test passed: length is 5");
    }
}
