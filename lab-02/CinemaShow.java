public class CinemaShow {
    // (a) Create class CinemaShow with private fields
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    // (b) Constructor(title, capacity)
    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    // Constructor(title): chain with this(title, 100)
    public CinemaShow(String title) {
        this(title, 100);
    }

    // (c) book(int n): if n <= seatsAvailable, reduce seatsAvailable by n, 
    // add n to totalBooked, return true; else return false unchanged.
    public boolean book(int n) {
        if (n <= this.seatsAvailable) {
            this.seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    // (d) cancel(int n): increase seatsAvailable by n but never above capacity.
    public void cancel(int n) {
        if (this.seatsAvailable + n > this.capacity) {
            this.seatsAvailable = this.capacity;
        } else {
            this.seatsAvailable += n;
        }
    }

    // (e) Add getSeatsAvailable() and static getTotalBooked()
    public int getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public String getTitle() {
        return this.title;
    }

    public static int getTotalBooked() {
        return totalBooked;
    }

    // (f) In main: run a sequence of book and cancel calls, printing...
    public static void main(String[] args) {
        System.out.println("--- Cinema Show Ticket Booking ---");
        
        CinemaShow show = new CinemaShow("Inception", 50);
        System.out.println("Show: " + show.getTitle() + " | Capacity: " + show.getCapacity());
        System.out.println("Initial Seats Available: " + show.getSeatsAvailable());

        // Book 20 seats
        System.out.print("Booking 20 seats: ");
        boolean r1 = show.book(20);
        System.out.println(r1 + " (Remaining: " + show.getSeatsAvailable() + ")");

        // Book 40 seats (should fail because only 30 left)
        System.out.print("Booking 40 seats: ");
        boolean r2 = show.book(40);
        System.out.println(r2 + " (Remaining: " + show.getSeatsAvailable() + ")");

        // Cancel 10 seats
        System.out.println("Cancelling 10 seats...");
        show.cancel(10);
        System.out.println("Remaining seats: " + show.getSeatsAvailable());

        // Book 35 seats (should succeed now, since 30 + 10 = 40 are available)
        System.out.print("Booking 35 seats: ");
        boolean r3 = show.book(35);
        System.out.println(r3 + " (Remaining: " + show.getSeatsAvailable() + ")");

        // Cancel 50 seats (should cap at capacity 50)
        System.out.println("Cancelling 50 seats (exceeding capacity limit)...");
        show.cancel(50);
        System.out.println("Remaining seats (capped): " + show.getSeatsAvailable());

        // Print total booked
        System.out.println("\nTotal seats booked historically: " + CinemaShow.getTotalBooked());
    }
}
