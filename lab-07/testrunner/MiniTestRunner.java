package testrunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MiniTestRunner {
    public record TestReport(int totalFound, int passed, int failed) {}

    public static TestReport runTests(Class<?> testClass) {
        System.out.println("Running tests for: " + testClass.getSimpleName());
        System.out.println("-------------------------------------------------");

        int totalFound = 0;
        int passed = 0;
        int failed = 0;

        try {
            Object instance = testClass.getDeclaredConstructor().newInstance();
            Method[] methods = testClass.getDeclaredMethods();

            for (Method method : methods) {
                // Check if method is annotated with @Run
                if (method.isAnnotationPresent(Run.class)) {
                    totalFound++;
                    Run annotation = method.getAnnotation(Run.class);
                    String desc = annotation.description().isEmpty() ? method.getName() : annotation.description();
                    
                    System.out.println(String.format("[RUNNING] %s() - %s", method.getName(), desc));
                    method.setAccessible(true);
                    try {
                        method.invoke(instance); // Execute the test method
                        System.out.println("   ==> [PASSED]\n");
                        passed++;
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        System.out.println("   ==> [FAILED] Cause: " + cause.getClass().getSimpleName() + " - " + cause.getMessage() + "\n");
                        failed++;
                    } catch (Exception e) {
                        System.out.println("   ==> [FAILED] Invocation error: " + e.getMessage() + "\n");
                        failed++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[FATAL] Could not instantiate test class: " + e.getMessage());
        }

        return new TestReport(totalFound, passed, failed);
    }
}
