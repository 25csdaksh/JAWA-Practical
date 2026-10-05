package service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionProcessor {
    private final ExecutorService executor;
    private final ConcurrentHashMap<String, AtomicInteger> threadStats = new ConcurrentHashMap<>();

    public TransactionProcessor(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public TransactionProcessor() {
        this(4); // Default 4 threads in fixed pool
    }

    public void submit(Runnable task) {
        executor.execute(() -> {
            String threadName = Thread.currentThread().getName();
            threadStats.computeIfAbsent(threadName, k -> new AtomicInteger(0)).incrementAndGet();
            try {
                task.run();
            } catch (Exception e) {
                System.out.println("[TASK ERROR] Exception during task execution: " + e.getMessage());
            }
        });
    }

    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Supplementary: prints how many tasks each pool thread executed
    public void printExecutionStats() {
        System.out.println("\n--- [TRANSACTION PROCESSOR THREAD METRICS] ---");
        threadStats.forEach((tName, count) -> {
            System.out.println(String.format(" - Pool Thread '%s' executed: %d transactions", tName, count.get()));
        });
        System.out.println("----------------------------------------------");
    }
}
