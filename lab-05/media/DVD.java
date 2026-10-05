public class DVD extends MediaItem {
    private final double dailyRate;

    public DVD(String itemId, String title, double dailyRate) {
        super(itemId, title);
        this.dailyRate = dailyRate;
    }

    public DVD(String itemId, String title) {
        this(itemId, title, 15.0); // DVDs have no grace period and Rs. 15/day
    }

    @Override
    public double computeLateFee(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return daysLate * dailyRate;
    }
}
