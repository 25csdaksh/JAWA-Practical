public class PointDriver {
    public static void main(String[] args) {
        Point[] points = new Point[] {
            new Point(1, 1),
            new Point(2, 2),
            new Point(1, 1), // Repeat 1
            new Point(3, 3),
            new Point(2, 2)  // Repeat 2
        };

        System.out.println("Points Array:");
        for (Point p : points) {
            System.out.println(" - " + p);
        }

        int distinctCount = 0;
        for (int i = 0; i < points.length; i++) {
            boolean alreadyAppeared = false;
            for (int j = 0; j < i; j++) {
                if (points[i].equals(points[j])) {
                    alreadyAppeared = true;
                    break;
                }
            }
            if (!alreadyAppeared) {
                distinctCount++;
            }
        }

        System.out.println("\nDistinct: " + distinctCount);
    }
}
