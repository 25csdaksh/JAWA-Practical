package arraysum;

import java.util.concurrent.atomic.AtomicLong;

public class ArraySumDriver {
    private static final int ARRAY_SIZE = 1_000_000;
    private static final int NUM_THREADS = 4;

    private static long unsafeTotal = 0;
    private static long syncTotal = 0;
    private static final Object lock = new Object();
    private static final AtomicLong atomicTotal = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=================================================");
        System.out.println("   PRACTICAL 9 - PART A3: PARALLEL ARRAY SUM     ");
        System.out.println("=================================================");

        int[] numbers = new int[ARRAY_SIZE];
        long expectedSum = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            numbers[i] = 1; // Array of 1,000,000 ones -> Expected sum = 1,000,000
            expectedSum += numbers[i];
        }

        System.out.println(String.format("Array Size: %,d elements | Number of Threads: %d", ARRAY_SIZE, NUM_THREADS));
        System.out.println(String.format("Expected Exact Sum: %,d\n", expectedSum));

        int chunkSize = ARRAY_SIZE / NUM_THREADS;

        // 1. Unsynchronized Sum (Race Condition)
        unsafeTotal = 0;
        Thread[] unsafeThreads = new Thread[NUM_THREADS];
        long start1 = System.nanoTime();
        for (int i = 0; i < NUM_THREADS; i++) {
            final int startIdx = i * chunkSize;
            final int endIdx = (i == NUM_THREADS - 1) ? ARRAY_SIZE : (i + 1) * chunkSize;
            unsafeThreads[i] = new Thread(() -> {
                for (int j = startIdx; j < endIdx; j++) {
                    unsafeTotal += numbers[j]; // Race condition on shared variable
                }
            });
            unsafeThreads[i].start();
        }
        for (Thread t : unsafeThreads) t.join();
        long time1 = (System.nanoTime() - start1) / 1_000_000;

        System.out.println("1. Unsynchronized Shared Addition:");
        System.out.println(String.format("   Calculated Sum: %,d (Lost: %,d) | Time: %d ms | [WRONG RESULT]",
                unsafeTotal, (expectedSum - unsafeTotal), time1));

        // 2. Fix 1: Synchronized Block on Every Addition
        syncTotal = 0;
        Thread[] syncThreads = new Thread[NUM_THREADS];
        long start2 = System.nanoTime();
        for (int i = 0; i < NUM_THREADS; i++) {
            final int startIdx = i * chunkSize;
            final int endIdx = (i == NUM_THREADS - 1) ? ARRAY_SIZE : (i + 1) * chunkSize;
            syncThreads[i] = new Thread(() -> {
                for (int j = startIdx; j < endIdx; j++) {
                    synchronized (lock) {
                        syncTotal += numbers[j];
                    }
                }
            });
            syncThreads[i].start();
        }
        for (Thread t : syncThreads) t.join();
        long time2 = (System.nanoTime() - start2) / 1_000_000;

        System.out.println("\n2. Fix #1 - Synchronized Block on Shared Lock:");
        System.out.println(String.format("   Calculated Sum: %,d | Time: %d ms | [CORRECT BUT LOCK CONTENTION]",
                syncTotal, time2));

        // 3. Fix 2: Thread-Local Chunk Sums (Fastest & Lock-Free)
        long[] localSums = new long[NUM_THREADS];
        Thread[] localThreads = new Thread[NUM_THREADS];
        long start3 = System.nanoTime();
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadIndex = i;
            final int startIdx = i * chunkSize;
            final int endIdx = (i == NUM_THREADS - 1) ? ARRAY_SIZE : (i + 1) * chunkSize;
            localThreads[i] = new Thread(() -> {
                long chunkSum = 0;
                for (int j = startIdx; j < endIdx; j++) {
                    chunkSum += numbers[j];
                }
                localSums[threadIndex] = chunkSum;
            });
            localThreads[i].start();
        }
        for (Thread t : localThreads) t.join();
        long finalOptimizedSum = 0;
        for (long s : localSums) finalOptimizedSum += s;
        long time3 = (System.nanoTime() - start3) / 1_000_000;

        System.out.println("\n3. Fix #2 - Thread-Local Partial Sums + Final Aggregation:");
        System.out.println(String.format("   Calculated Sum: %,d | Time: %d ms | [CORRECT & MAXIMUM THROUGHPUT]",
                finalOptimizedSum, time3));

        System.out.println("=================================================\n");
    }
}
