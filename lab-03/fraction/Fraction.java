import java.util.Objects;

public class Fraction {
    private int num;
    private int den;

    // Helper method to calculate Greatest Common Divisor (GCD)
    private static int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    // (b) Constructor reduces to lowest terms
    public Fraction(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        
        // Handle negative signs cleanly
        if (den < 0) {
            num = -num;
            den = -den;
        }

        int g = gcd(num, den);
        this.num = num / g;
        this.den = den / g;
    }

    // (c) Override toString() to return num + “/” + den
    @Override
    public String toString() {
        return num + "/" + den;
    }

    // (d) Override equals()/hashCode() using the reduced num and den
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fraction fraction = (Fraction) obj;
        return num == fraction.num && den == fraction.den;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}
