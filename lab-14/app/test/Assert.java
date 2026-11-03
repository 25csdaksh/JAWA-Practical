package app.test;

import java.util.Objects;

public class Assert {

    @FunctionalInterface
    public interface Executable {
        void execute() throws Throwable;
    }

    public static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(String.format("Expected [%s] but found [%s]", expected, actual));
        }
    }

    public static void assertEquals(double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError(String.format("Expected [%f] but found [%f] (delta=%f)", expected, actual, delta));
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Assertion failed: expected true but was false.");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Assertion failed: expected false but was true.");
        }
    }

    public static void assertNotNull(Object obj) {
        if (obj == null) {
            throw new AssertionError("Assertion failed: expected non-null object.");
        }
    }

    public static void assertNull(Object obj) {
        if (obj != null) {
            throw new AssertionError("Assertion failed: expected null object but was " + obj);
        }
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable) {
        try {
            executable.execute();
        } catch (Throwable actual) {
            if (expectedType.isInstance(actual)) {
                return expectedType.cast(actual);
            }
            throw new AssertionError(String.format("Expected exception [%s] but caught [%s]",
                    expectedType.getName(), actual.getClass().getName()), actual);
        }
        throw new AssertionError("Expected exception [" + expectedType.getName() + "] was not thrown.");
    }
}
