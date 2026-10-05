package discount.app;

import discount.rule.DiscountRule;
import discount.service.DiscountEngine;
import discount.service.DiscountEngine.DiscountSummary;
import java.util.Scanner;

public class DiscountApp {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   PRACTICAL 6 - PART A3: DYNAMIC DISCOUNT ENGINE ");
        System.out.println("=================================================");

        double[] samplePrices = new double[] { 250.0, 1200.0, 499.0, 850.0, 3200.0 };

        System.out.println("Sample Product Prices: " + java.util.Arrays.toString(samplePrices));
        System.out.println("\nSelect a Discount Rule to Apply:");
        System.out.println("1. Flat 15% Festive Discount (Lambda: p -> p * 0.85)");
        System.out.println("2. Rs. 100 Off for items >= Rs. 500 (Lambda: p -> p >= 500 ? p - 100 : p)");
        System.out.println("3. Mega 30% Clearance on items > Rs. 1000 (Lambda: p -> p > 1000 ? p * 0.70 : p)");
        System.out.println("4. Custom 10% Student Discount (Lambda: p -> p * 0.90)");

        int choice = 1;
        Scanner scanner = new Scanner(System.in);
        if (args.length > 0) {
            try {
                choice = Integer.parseInt(args[0]);
            } catch (Exception ignored) {}
        }

        DiscountRule selectedRule;
        String ruleName;

        switch (choice) {
            case 2 -> {
                selectedRule = p -> (p >= 500 ? Math.max(0, p - 100) : p);
                ruleName = "Rs. 100 Off on items >= Rs. 500";
            }
            case 3 -> {
                selectedRule = p -> (p > 1000 ? p * 0.70 : p);
                ruleName = "Mega 30% Clearance on items > Rs. 1000";
            }
            case 4 -> {
                selectedRule = p -> p * 0.90;
                ruleName = "10% Student Discount";
            }
            case 1 -> {
                selectedRule = p -> p * 0.85;
                ruleName = "Flat 15% Festive Discount";
            }
            default -> {
                selectedRule = p -> p * 0.85;
                ruleName = "Flat 15% Festive Discount (Default)";
            }
        }

        System.out.println("\nApplying Rule: " + ruleName);
        System.out.println("-------------------------------------------------");
        System.out.println(String.format("%-10s | %-16s | %-16s", "Item No", "Original Price", "Discounted Price"));
        System.out.println("-------------------------------------------------");

        for (int i = 0; i < samplePrices.length; i++) {
            double orig = samplePrices[i];
            double disc = selectedRule.apply(orig);
            System.out.println(String.format("%-10d | Rs. %-12.2f | Rs. %-12.2f", (i + 1), orig, disc));
        }

        DiscountSummary summary = DiscountEngine.processPrices(samplePrices, selectedRule);
        System.out.println("-------------------------------------------------");
        System.out.println(String.format("Original Total   : Rs. %.2f", summary.originalTotal()));
        System.out.println(String.format("Discounted Total : Rs. %.2f", summary.discountedTotal()));
        System.out.println(String.format("Total Savings    : Rs. %.2f", summary.totalSavings()));
        System.out.println("=================================================\n");
    }
}
