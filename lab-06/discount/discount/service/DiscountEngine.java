package discount.service;

import discount.rule.DiscountRule;

public class DiscountEngine {
    public record DiscountSummary(double originalTotal, double discountedTotal, double totalSavings) {}

    public static DiscountSummary processPrices(double[] prices, DiscountRule rule) {
        double origTotal = 0;
        double discTotal = 0;

        for (double p : prices) {
            origTotal += p;
            discTotal += rule.apply(p);
        }

        double savings = origTotal - discTotal;
        return new DiscountSummary(origTotal, discTotal, savings);
    }
}
