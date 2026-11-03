package test;

import java.lang.reflect.Method;

public class MiniBankTestRunner {

    public static int runTests(Class<?> testClass) {
        System.out.println("=============================================================");
        System.out.println(" RUNNING TEST SUITE: " + testClass.getSimpleName());
        System.out.println("=============================================================");

        int passed = 0;
        int failed = 0;

        try {
            Object instance = testClass.getDeclaredConstructor().newInstance();
            Method[] methods = testClass.getDeclaredMethods();

            for (Method m : methods) {
                if (m.isAnnotationPresent(Test.class)) {
                    Test annotation = m.getAnnotation(Test.class);
                    String desc = annotation.value().isEmpty() ? m.getName() : annotation.value();
                    try {
                        m.invoke(instance);
                        System.out.println(" [PASS] " + m.getName() + " - " + desc);
                        passed++;
                    } catch (Throwable t) {
                        Throwable cause = t.getCause() != null ? t.getCause() : t;
                        System.out.println(" [FAIL] " + m.getName() + " - " + desc + ": " + cause.getMessage());
                        failed++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Could not instantiate test suite: " + e.getMessage());
            return 1;
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println(String.format("Suite Results: %d Passed, %d Failed (Total: %d)",
                passed, failed, passed + failed));
        System.out.println("=============================================================\n");
        return failed == 0 ? 0 : 1;
    }

    public static void main(String[] args) {
        int result = runTests(MiniBankTest.class);
        if (result != 0) {
            System.exit(result);
        }
    }
}
