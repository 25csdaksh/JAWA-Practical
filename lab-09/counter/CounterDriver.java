package counter;

public class CounterDriver {
    private static final int NUM_THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 10000;
    private static final int EXPECTED_TOTAL = NUM_THREADS * INCREMENTS_PER_THREAD;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=================================================");
        System.out.println("      PRACTICAL 9 - PART A1: COUNTER RACE        ");
        System.out.println("=================================================");
        System.out.println("Configuration: " + NUM_THREADS + " threads x " + INCREMENTS_PER_THREAD + " increments each");
        System.out.println("Expected Final Count: " + EXPECTED_TOTAL);
        System.out.println("-------------------------------------------------");

        // 1. Unsynchronized Counter Test (Race Condition Demonstration)
        Counter unsafeCounter = new Counter();
        Thread[] unsafeThreads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    unsafeCounter.incrementUnsafe();
                }
            }, "Unsafe-Worker-" + (i + 1));
        }

        System.out.println("\n--- [1. Running Unsynchronized Counter (Race Condition)] ---");
        for (Thread t : unsafeThreads) t.start();
        for (Thread t : unsafeThreads) t.join(); // Wait for all threads to complete

        int unsafeResult = unsafeCounter.getCount();
        System.out.println("Actual Result   : " + unsafeResult);
        System.out.println("Discrepancy     : " + (EXPECTED_TOTAL - unsafeResult) + " lost increments!");
        System.out.println("Conclusion      : " + (unsafeResult < EXPECTED_TOTAL ? "[RACE CONDITION DETECTED]" : "[PASS]"));

        // 2. Synchronized Counter Test (Thread-Safe Synchronization)
        Counter safeCounter = new Counter();
        Thread[] safeThreads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    safeCounter.incrementSync();
                }
            }, "Sync-Worker-" + (i + 1));
        }

        System.out.println("\n--- [2. Running Synchronized Counter (Mutual Exclusion)] ---");
        for (Thread t : safeThreads) t.start();
        for (Thread t : safeThreads) t.join();

        int safeResult = safeCounter.getCount();
        System.out.println("Actual Result   : " + safeResult);
        System.out.println("Discrepancy     : " + (EXPECTED_TOTAL - safeResult) + " lost increments");
        System.out.println("Conclusion      : " + (safeResult == EXPECTED_TOTAL ? "[THREAD-SAFE EXACT MATCH]" : "[FAILED]"));

        System.out.println("=================================================\n");
    }
}
