package service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import model.Account;

/**
 * Multi-threaded Transaction Processor engine.
 * Practical 12 Requirement 5: Uses a thread-safe ConcurrentHashMap for shared accounts.
 */
public class TransactionProcessor {
    private final ExecutorService executor;
    private final ConcurrentHashMap<String, AtomicInteger> threadStats = new ConcurrentHashMap<>();

    // Practical 12 Requirement 5: Thread-safe ConcurrentHashMap for shared accounts
    private final ConcurrentHashMap<String, Account> sharedAccounts = new ConcurrentHashMap<>();

    public TransactionProcessor(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public TransactionProcessor() {
        this(4); // Default 4 threads in fixed pool
    }

    public TransactionProcessor(int poolSize, ConcurrentHashMap<String, Account> initialAccounts) {
        this(poolSize);
        if (initialAccounts != null) {
            this.sharedAccounts.putAll(initialAccounts);
        }
    }

    /**
     * Registers an account into the thread-safe ConcurrentHashMap.
     */
    public void registerAccount(Account account) {
        if (account != null) {
            sharedAccounts.put(account.getAccountNumber(), account);
        }
    }

    /**
     * Retrieves an account safely from the ConcurrentHashMap.
     */
    public Account getAccount(String accountNumber) {
        if (accountNumber == null) return null;
        return sharedAccounts.get(accountNumber.trim());
    }

    /**
     * Returns the thread-safe ConcurrentHashMap of shared accounts.
     */
    public ConcurrentHashMap<String, Account> getSharedAccounts() {
        return sharedAccounts;
    }

    /**
     * Asynchronously submits a deposit transaction onto a shared account.
     */
    public void submitDeposit(String accountNumber, long amount) {
        submit(() -> {
            Account acc = getAccount(accountNumber);
            if (acc != null) {
                try {
                    acc.deposit(amount);
                } catch (Exception e) {
                    System.out.println("[DEPOSIT ERROR] " + e.getMessage());
                }
            } else {
                System.out.println("[ERROR] Account not found in ConcurrentHashMap: " + accountNumber);
            }
        });
    }

    /**
     * Asynchronously submits a withdrawal transaction onto a shared account.
     */
    public void submitWithdraw(String accountNumber, long amount) {
        submit(() -> {
            Account acc = getAccount(accountNumber);
            if (acc != null) {
                try {
                    acc.withdraw(amount);
                } catch (Exception e) {
                    System.out.println("[WITHDRAW ERROR] " + e.getMessage());
                }
            } else {
                System.out.println("[ERROR] Account not found in ConcurrentHashMap: " + accountNumber);
            }
        });
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

    // Prints how many tasks each pool thread executed
    public void printExecutionStats() {
        System.out.println("\n--- [TRANSACTION PROCESSOR THREAD METRICS] ---");
        threadStats.forEach((tName, count) -> {
            System.out.println(String.format(" - Pool Thread '%s' executed: %d transactions", tName, count.get()));
        });
        System.out.println("----------------------------------------------");
    }
}
