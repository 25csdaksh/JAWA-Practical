package seatbooking;

public class SeatManager {
    private int seatsLeft;
    private int successfulBookings = 0;

    public SeatManager(int initialSeats) {
        this.seatsLeft = initialSeats;
    }

    public boolean bookUnsafe(String user) {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}

            seatsLeft--;
            successfulBookings++;
            System.out.println(String.format("   [%s] Unsafe booking SUCCESS! Seats left: %d", user, seatsLeft));
            return true;
        }
        System.out.println(String.format("   [%s] Unsafe booking REJECTED! Housefull.", user));
        return false;
    }

    public synchronized boolean bookSync(String user) {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}

            seatsLeft--;
            successfulBookings++;
            System.out.println(String.format("   [%s] Synchronized booking SUCCESS! Seats left: %d", user, seatsLeft));
            return true;
        }
        System.out.println(String.format("   [%s] Synchronized booking REJECTED! Housefull.", user));
        return false;
    }

    public int getSeatsLeft() { return seatsLeft; }
    public int getSuccessfulBookings() { return successfulBookings; }
}
