package counter;

public class Counter {
    private int count = 0;

    // Unsynchronized increment: subject to lost updates in multi-threaded environment
    public void incrementUnsafe() {
        count++; // Not atomic: read -> modify -> write
    }

    // Synchronized increment: thread-safe, ensures mutual exclusion
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
