public class AudioBook extends MediaItem {
    private final double dailyRate;

    public AudioBook(String itemId, String title, double dailyRate) {
        super(itemId, title);
        this.dailyRate = dailyRate;
    }

    public AudioBook(String itemId, String title) {
        this(itemId, title, 8.0); // Audio books have Rs. 8/day rate
    }

    @Override
    public double computeLateFee(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return daysLate * dailyRate;
    }
}
