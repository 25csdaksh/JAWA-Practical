public class ShapeDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     PRACTICAL 5 - PART A1: SHAPE AREAS          ");
        System.out.println("=================================================");

        Shape[] shapes = new Shape[] {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Triangle(3.0, 8.0),
            new Circle(2.5),
            new Rectangle(7.0, 5.0),
            new Triangle(6.0, 10.0)
        };

        double runningTotal = 0.0;
        Shape largestShape = null;
        double maxArea = -1.0;

        System.out.println(String.format("%-4s | %-32s | %-12s | %-12s", "No.", "Shape Details", "Area", "Running Total"));
        System.out.println("-------------------------------------------------------------------------");

        for (int i = 0; i < shapes.length; i++) {
            Shape shape = shapes[i];
            double area = shape.area(); // Dynamic method dispatch (Polymorphism)
            runningTotal += area;

            if (area > maxArea) {
                maxArea = area;
                largestShape = shape;
            }

            System.out.println(String.format("%-4d | %-32s | %-12.2f | %-12.2f", 
                (i + 1), shape.toString(), area, runningTotal));
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.println(String.format("Grand Total Area : %.2f", runningTotal));
        if (largestShape != null) {
            System.out.println(String.format("Largest Shape    : %s (Area = %.2f)", 
                largestShape.getClass().getSimpleName(), maxArea));
        }
        System.out.println("=================================================\n");
    }
}
