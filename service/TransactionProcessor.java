package service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import model.Account;

public class TransactionProcessor {
    private final ExecutorService executor;
    private final ConcurrentHashMap<String, AtomicInteger> threadStats = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Account> sharedAccounts = new ConcurrentHashMap<>();

    public TransactionProcessor(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public TransactionProcessor() {
        this(4);
    }

    public TransactionProcessor(int poolSize, ConcurrentHashMap<String, Account> initialAccounts) {
        this(poolSize);
        if (initialAccounts != null) {
            this.sharedAccounts.putAll(initialAccounts);
        }
    }

    public void registerAccount(Account account) {
        if (account != null) {
            sharedAccounts.put(account.getAccountNumber(), account);
        }
    }

    public Account getAccount(String accountNumber) {
        if (accountNumber == null) return null;
        return sharedAccounts.get(accountNumber.trim());
    }

    public ConcurrentHashMap<String, Account> getSharedAccounts() {
        return sharedAccounts;
    }

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

    public void printExecutionStats() {
        System.out.println("\n--- [TRANSACTION PROCESSOR THREAD METRICS] ---");
        threadStats.forEach((tName, count) -> {
            System.out.println(String.format(" - Pool Thread '%s' executed: %d transactions", tName, count.get()));
        });
        System.out.println("----------------------------------------------");
    }
}
