package threadpool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   PRACTICAL 10 - PART A1: THREAD-POOL RUNNER    ");
        System.out.println("=================================================");

        int poolSize = 3;
        int totalTasks = 10;
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        ConcurrentHashMap<String, AtomicInteger> threadTaskCounts = new ConcurrentHashMap<>();

        System.out.println(String.format("Initialized Fixed Thread Pool with %d worker threads.", poolSize));
        System.out.println(String.format("Submitting %d asynchronous tasks...\n", totalTasks));

        for (int i = 1; i <= totalTasks; i++) {
            final int taskId = i;
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                threadTaskCounts.computeIfAbsent(threadName, k -> new AtomicInteger(0)).incrementAndGet();

                System.out.println(String.format(" -> [TASK-%02d STARTED] on Thread '%s'", taskId, threadName));
                try {
                    // Simulate short work
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(String.format(" <- [TASK-%02d COMPLETED] on Thread '%s'", taskId, threadName));
            });
        }

        // Graceful shutdown and await completion
        System.out.println("\nAll tasks submitted. Shutting down ExecutorService...");
        executor.shutdown();

        try {
            boolean finished = executor.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("Executor terminated successfully? " + finished);
        } catch (InterruptedException e) {
            System.out.println("[ERROR] Thread pool interrupted while waiting: " + e.getMessage());
        }

        System.out.println("\n-------------------------------------------------");
        System.out.println("THREAD REUSE SUMMARY:");
        threadTaskCounts.forEach((threadName, count) -> {
            System.out.println(String.format(" - Thread '%s' executed %d tasks", threadName, count.get()));
        });
        System.out.println("Observation: 3 worker threads efficiently recycled to execute all 10 tasks!");
        System.out.println("=================================================\n");
    }
}
