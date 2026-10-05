public abstract class Shape {
    private final String name;

    public Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Abstract method to be implemented by all concrete shapes
    public abstract double area();

    @Override
    public String toString() {
        return String.format("%s [Area = %.2f]", name, area());
    }
}
