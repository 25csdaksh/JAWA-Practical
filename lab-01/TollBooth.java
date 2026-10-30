import java.util.Scanner;

record Vehicle(String number, String type) {}

public class TollBooth {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int totalToll = 0;
        int bikeCount = 0;
        int carCount = 0;
        int truckCount = 0;

        System.out.println("Toll Booth System initialized.");
        System.out.println("Enter vehicle details (or type 'done' as number to finish).");

        while (true) {
            System.out.print("\nEnter vehicle number (or 'done'): ");
            String number = scanner.next().trim();
            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike, car, truck): ");
            String type = scanner.next().trim().toLowerCase();

            Vehicle vehicle = new Vehicle(number, type);

            int toll;
            try {
                toll = switch (vehicle.type()) {
                    case "bike" -> {
                        bikeCount++;
                        yield 20;
                    }
                    case "car" -> {
                        carCount++;
                        yield 50;
                    }
                    case "truck" -> {
                        truckCount++;
                        yield 150;
                    }
                    default -> throw new IllegalArgumentException("Unknown type: " + vehicle.type());
                };
                totalToll += toll;
                System.out.println("Vehicle recorded: " + vehicle + ", Toll: " + toll);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid vehicle type! Please enter bike, car, or truck.");
            }
        }

        System.out.println("\n=== Toll Booth Summary ===");
        System.out.println("Total toll: " + totalToll);

        String mostFrequent;
        int maxCount = Math.max(bikeCount, Math.max(carCount, truckCount));

        if (maxCount == 0) {
            mostFrequent = "none";
        } else if (maxCount == bikeCount && maxCount == carCount && maxCount == truckCount) {
            mostFrequent = "bike, car, and truck (three-way tie)";
        } else if (maxCount == bikeCount && maxCount == carCount) {
            mostFrequent = "bike and car (tie)";
        } else if (maxCount == bikeCount && maxCount == truckCount) {
            mostFrequent = "bike and truck (tie)";
        } else if (maxCount == carCount && maxCount == truckCount) {
            mostFrequent = "car and truck (tie)";
        } else if (maxCount == bikeCount) {
            mostFrequent = "bike";
        } else if (maxCount == carCount) {
            mostFrequent = "car";
        } else {
            mostFrequent = "truck";
        }

        System.out.println("Most frequent: " + mostFrequent);
        scanner.close();
    }
}
