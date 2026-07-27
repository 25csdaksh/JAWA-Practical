public class FractionDriver {
    public static void main(String[] args) {
        System.out.println("Creating fractions 1/2, 2/4, and 3/6:");
        
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);

        System.out.println("f1 (1, 2) prints: " + f1);
        System.out.println("f2 (2, 4) prints: " + f2);
        System.out.println("f3 (3, 6) prints: " + f3);

        System.out.println("\nEquality checks using equals():");
        System.out.println("f1 equals f2? " + f1.equals(f2));
        System.out.println("f2 equals f3? " + f2.equals(f3));
        System.out.println("f1 equals f3? " + f1.equals(f3));

        if (f1.equals(f2) && f2.equals(f3)) {
            System.out.println("\n[CONFIRMED] Equivalent fractions are reduced and equal.");
        } else {
            System.out.println("\n[ERROR] Equality check failed.");
        }
    }
}
