package containers;

public class GenericContainersDemo {

    public static class Box<T> {
        private T value;

        public Box() {}

        public Box(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }

        public boolean isEmpty() {
            return value == null;
        }

        @Override
        public String toString() {
            return "Box[" + value + "]";
        }
    }

    public static class Pair<A, B> {
        private A first;
        private B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return first;
        }

        public void setFirst(A first) {
            this.first = first;
        }

        public B getSecond() {
            return second;
        }

        public void setSecond(B second) {
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    public static <T> T getFirst(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println(" PRACTICAL 13 - PART A.1: GENERIC CONTAINERS & METHODS");
        System.out.println("=============================================================");

        Box<Integer> intBox = new Box<>(100);
        Box<String> strBox = new Box<>("Java Generics");
        Box<Double> dblBox = new Box<>(99.95);

        int intVal = intBox.get();
        String strVal = strBox.get();
        double dblVal = dblBox.get();

        System.out.println("Integer Box value (no cast needed): " + intVal);
        System.out.println("String Box value (no cast needed): " + strVal);
        System.out.println("Double Box value (no cast needed): " + dblVal);

        intBox.set(250);
        System.out.println("Updated Integer Box: " + intBox);

        Pair<String, Integer> studentAge = new Pair<>("Daksh", 20);
        Pair<Integer, String> httpStatus = new Pair<>(404, "Not Found");
        Pair<String, Double> productPrice = new Pair<>("Laptop", 75000.50);

        System.out.println("\nPair 1 (String, Integer): " + studentAge.getFirst() + " -> " + studentAge.getSecond());
        System.out.println("Pair 2 (Integer, String): Code " + httpStatus.getFirst() + " (" + httpStatus.getSecond() + ")");
        System.out.println("Pair 3 (String, Double): " + productPrice);

        String[] names = {"Alice", "Bob", "Charlie"};
        Integer[] numbers = {10, 20, 30, 40};
        Double[] rates = {3.5, 4.0, 7.5};
        String[] emptyArray = {};

        String firstName = getFirst(names);
        Integer firstNumber = getFirst(numbers);
        Double firstRate = getFirst(rates);
        String firstEmpty = getFirst(emptyArray);

        System.out.println("\nGeneric Method getFirst() results:");
        System.out.println("getFirst(names)   -> " + firstName);
        System.out.println("getFirst(numbers) -> " + firstNumber);
        System.out.println("getFirst(rates)   -> " + firstRate);
        System.out.println("getFirst(empty)   -> " + firstEmpty);
        System.out.println("=============================================================\n");
    }
}
