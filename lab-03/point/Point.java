import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // (b) Override toString() to return “(x, y)”
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // (c) Override equals(Object) to be true when both x and y match
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    // Override hashCode() to agree with equals()
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
