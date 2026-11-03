package bounded;

public class BoundedStatsDemo {

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N val : values) {
            if (val != null) {
                sum += val.doubleValue();
            }
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println(" PRACTICAL 13 - PART A.2: BOUNDED STATS (<N extends Number>)");
        System.out.println("=============================================================");

        Integer[] intScores = {85, 92, 78, 90, 88};
        Double[] dblPrices = {19.99, 45.50, 120.75, 10.00};
        Long[] lngTimestamps = {1000000L, 2000000L, 3000000L, 4000000L};
        Float[] fltTemperatures = {36.5f, 37.2f, 38.0f, 36.8f};

        double intAvg = average(intScores);
        double dblAvg = average(dblPrices);
        double lngAvg = average(lngTimestamps);
        double fltAvg = average(fltTemperatures);

        System.out.printf("Average of Integer[]   : %.2f%n", intAvg);
        System.out.printf("Average of Double[]    : %.2f%n", dblAvg);
        System.out.printf("Average of Long[]      : %.2f%n", lngAvg);
        System.out.printf("Average of Float[]     : %.2f%n", fltAvg);

        System.out.println("\nCompile-time Type Safety Analysis:");
        System.out.println("Why does <N extends Number> refuse non-Number types (like String[] or Account[])?");
        System.out.println("1. Type Restriction: The compiler only allows types that inherit java.lang.Number.");
        System.out.println("2. Method Availability: It guarantees the method .doubleValue() exists on every element.");
        System.out.println("3. Compile-time Failure: Passing String[] results in javac compilation error (incompatible types).");
        System.out.println("=============================================================\n");
    }
}

