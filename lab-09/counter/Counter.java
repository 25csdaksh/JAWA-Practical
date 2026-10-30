package counter;

public class Counter {
    private int count = 0;

    public void incrementUnsafe() {
        count++;
    }

    public synchronized void incrementSync() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        this.count = 0;
    }
}
