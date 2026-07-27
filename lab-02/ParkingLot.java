public class ParkingLot {
    // (a) Create class ParkingLot with private fields
    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    // Constructor to initialize capacities
    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
        this.twoWheelers = 0;
        this.fourWheelers = 0;
    }

    // (b) park(String type): for “two”, if twoWheelers < twoCap increment and add 20 to revenue;
    // for “four”, if fourWheelers < fourCap increment and add 40; otherwise print “Full” and reject.
    public boolean park(String type) {
        if (type.equalsIgnoreCase("two")) {
            if (this.twoWheelers < this.twoCap) {
                this.twoWheelers++;
                revenue += 20;
                System.out.println("Parked two-wheeler. Spot allocated.");
                return true;
            } else {
                System.out.println("Full: Two-wheeler parking section is at capacity.");
                return false;
            }
        } else if (type.equalsIgnoreCase("four")) {
            if (this.fourWheelers < this.fourCap) {
                this.fourWheelers++;
                revenue += 40;
                System.out.println("Parked four-wheeler. Spot allocated.");
                return true;
            } else {
                System.out.println("Full: Four-wheeler parking section is at capacity.");
                return false;
            }
        } else {
            System.out.println("Error: Unknown vehicle type: " + type);
            return false;
        }
    }

    // (c) leave(String type): decrement the matching count but never below 0.
    public void leave(String type) {
        if (type.equalsIgnoreCase("two")) {
            if (this.twoWheelers > 0) {
                this.twoWheelers--;
                System.out.println("Two-wheeler left the parking lot.");
            } else {
                System.out.println("Alert: No two-wheelers currently parked.");
            }
        } else if (type.equalsIgnoreCase("four")) {
            if (this.fourWheelers > 0) {
                this.fourWheelers--;
                System.out.println("Four-wheeler left the parking lot.");
            } else {
                System.out.println("Alert: No four-wheelers currently parked.");
            }
        } else {
            System.out.println("Error: Unknown vehicle type: " + type);
        }
    }

    public int getTwoWheelers() {
        return this.twoWheelers;
    }

    public int getFourWheelers() {
        return this.fourWheelers;
    }

    public static long getRevenue() {
        return revenue;
    }

    // (d) In main: simulate a sequence of park/leave events...
    public static void main(String[] args) {
        System.out.println("--- Parking Lot Simulator (Capacities: 2 Two-Wheelers, 1 Four-Wheeler) ---");
        ParkingLot lot = new ParkingLot(2, 1);

        // Try to park two-wheelers
        System.out.println("\n--- Testing Two-Wheeler Parking ---");
        lot.park("two");
        lot.park("two");
        lot.park("two"); // Should fail - Full

        // Try to park four-wheelers
        System.out.println("\n--- Testing Four-Wheeler Parking ---");
        lot.park("four");
        lot.park("four"); // Should fail - Full

        // Vehicles leaving
        System.out.println("\n--- Testing Vehicles Leaving ---");
        lot.leave("two");
        lot.park("two"); // Should succeed now

        lot.leave("four");
        lot.leave("four"); // Underflow check

        // Final occupancy and revenue
        System.out.println("\n=================================");
        System.out.println("        PARKING LOT REPORT       ");
        System.out.println("=================================");
        System.out.println("Two-Wheelers Parked: " + lot.getTwoWheelers());
        System.out.println("Four-Wheelers Parked: " + lot.getFourWheelers());
        System.out.println("Total Revenue: Rs. " + ParkingLot.getRevenue());
    }
}
