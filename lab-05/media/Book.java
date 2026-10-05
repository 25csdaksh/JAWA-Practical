public class Book extends MediaItem {
    private final double dailyRate;
    private final int gracePeriodDays;
    private final double maxFeeCap;

    public Book(String itemId, String title, double dailyRate, int gracePeriodDays, double maxFeeCap) {
        super(itemId, title);
        this.dailyRate = dailyRate;
        this.gracePeriodDays = gracePeriodDays;
        this.maxFeeCap = maxFeeCap;
    }

    public Book(String itemId, String title) {
        this(itemId, title, 5.0, 2, 250.0); // Rs. 5/day after 2 days grace, cap Rs. 250
    }

    @Override
    public double computeLateFee(int daysLate) {
        if (daysLate <= gracePeriodDays) {
            return 0.0;
        }
        int chargeableDays = daysLate - gracePeriodDays;
        double calculatedFee = chargeableDays * dailyRate;
        return Math.min(calculatedFee, maxFeeCap);
    }
}
