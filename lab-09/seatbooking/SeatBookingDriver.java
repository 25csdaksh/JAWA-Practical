package seatbooking;

public class SeatBookingDriver {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=================================================");
        System.out.println("   PRACTICAL 9 - PART A2: SEAT BOOKING RACE      ");
        System.out.println("=================================================");
        System.out.println("Initial Seats Available: 5 | Concurrent Users Attempting: 10\n");

        // 1. Unsynchronized Booking Race (Overselling Demonstration)
        System.out.println("--- [1. Unsynchronized Booking (Check-Then-Act Race Condition)] ---");
        SeatManager unsafeManager = new SeatManager(5);
        Thread[] unsafeUsers = new Thread[10];

        for (int i = 0; i < 10; i++) {
            final String username = "User-" + (i + 1);
            unsafeUsers[i] = new Thread(() -> unsafeManager.bookUnsafe(username));
        }

        for (Thread t : unsafeUsers) t.start();
        for (Thread t : unsafeUsers) t.join();

        System.out.println(String.format("Unsafe Summary -> Total Successful: %d / 5 | Final Seats Left: %d",
                unsafeManager.getSuccessfulBookings(), unsafeManager.getSeatsLeft()));
        if (unsafeManager.getSuccessfulBookings() > 5) {
            System.out.println("[CRITICAL FLAW] OVERSELLING OCCURRED! More tickets sold than available capacity!");
        }

        // 2. Synchronized Booking (Thread-Safe Guarantee)
        System.out.println("\n--- [2. Synchronized Booking (Atomic Mutual Exclusion)] ---");
        SeatManager safeManager = new SeatManager(5);
        Thread[] safeUsers = new Thread[10];

        for (int i = 0; i < 10; i++) {
            final String username = "User-" + (i + 1);
            safeUsers[i] = new Thread(() -> safeManager.bookSync(username));
        }

        for (Thread t : safeUsers) t.start();
        for (Thread t : safeUsers) t.join();

        System.out.println(String.format("Safe Summary   -> Total Successful: %d / 5 | Final Seats Left: %d",
                safeManager.getSuccessfulBookings(), safeManager.getSeatsLeft()));
        if (safeManager.getSuccessfulBookings() == 5 && safeManager.getSeatsLeft() == 0) {
            System.out.println("[PERFECT SUCCESS] Exactly 5 bookings succeeded and remaining 5 were rejected properly.");
        }

        System.out.println("=================================================\n");
    }
}
