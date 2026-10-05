package testrunner;

public class TestRunnerDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("    PRACTICAL 7 - PART A2: MINI TEST RUNNER      ");
        System.out.println("=================================================");

        MiniTestRunner.TestReport report = MiniTestRunner.runTests(TestSuite.class);

        System.out.println("-------------------------------------------------");
        System.out.println("TEST EXECUTION SUMMARY:");
        System.out.println(" Total @Run Methods Found : " + report.totalFound());
        System.out.println(" Total Passed             : " + report.passed());
        System.out.println(" Total Failed             : " + report.failed());
        System.out.println("=================================================\n");
    }
}
