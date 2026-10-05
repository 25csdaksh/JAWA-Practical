public class Triangle extends Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        super("Triangle");
        this.base = base;
        this.height = height;
    }

    public double getBase() {
        return base;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }

    @Override
    public String toString() {
        return String.format("Triangle (Base = %.2f, Height = %.2f) -> Area = %.2f", base, height, area());
    }
}
