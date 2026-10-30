public abstract class MediaItem {
    private final String itemId;
    private final String title;

    public MediaItem(String itemId, String title) {
        this.itemId = itemId;
        this.title = title;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public abstract double computeLateFee(int daysLate);

    @Override
    public String toString() {
        return String.format("[%s] \"%s\" (%s)", itemId, title, getClass().getSimpleName());
    }
}
