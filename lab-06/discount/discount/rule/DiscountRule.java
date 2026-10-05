package discount.rule;

@FunctionalInterface
public interface DiscountRule {
    double apply(double price);
}
