package service;

import exception.BankException;
import model.Account;

public class TransferService {
    private static final long LONG_TRANSFER_THRESHOLD_MS = 50;

    /**
     * Unsafe transfer implementation that acquires locks in arbitrary (from -> to) order.
     * When two threads call transfer concurrently in reverse directions (A->B and B->A),
     * this causes a classic circular wait DEADLOCK.
     */
    public static void transferDeadlockProne(Account from, Account to, long amount) throws BankException {
        if (from == null || to == null) throw new BankException("Accounts cannot be null");
        
        synchronized (from) {
            System.out.println(String.format("   [%s] Acquired lock on source %s. Waiting for destination %s...",
                    Thread.currentThread().getName(), from.getAccountNumber(), to.getAccountNumber()));
            try {
                // Short sleep to expose race window and force deadlock
                Thread.sleep(60);
            } catch (InterruptedException ignored) {}

            synchronized (to) {
                System.out.println(String.format("   [%s] Acquired lock on destination %s. Executing transfer of Rs. %d...",
                        Thread.currentThread().getName(), to.getAccountNumber(), amount));
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    /**
     * Deadlock-free safe transfer method implementing Consistent Global Lock Ordering.
     * Always acquires the lock on the account with the smaller accountNumber first.
     * Includes detection and logging of long-running transfers.
     */
    public static void transferSafe(Account from, Account to, long amount) throws BankException {
        if (from == null || to == null) throw new BankException("Accounts cannot be null");
        if (from.equals(to)) throw new BankException("Cannot transfer to the same account");

        long startTime = System.currentTimeMillis();

        // 1. Consistent Lock Ordering: Always lock lower accountNumber first
        Account firstLock;
        Account secondLock;

        if (from.getAccountNumber().compareTo(to.getAccountNumber()) < 0) {
            firstLock = from;
            secondLock = to;
        } else {
            firstLock = to;
            secondLock = from;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println(String.format("   [SAFE TRANSFER SUCCESS] Rs. %d transferred from %s to %s. (Bal: %s=Rs. %d, %s=Rs. %d)",
                        amount, from.getAccountNumber(), to.getAccountNumber(),
                        from.getAccountNumber(), from.getBalance(), to.getAccountNumber(), to.getBalance()));
            }
        }

        long duration = System.currentTimeMillis() - startTime;
        // Supplementary: Detect and log warning for long-running transfers
        if (duration > LONG_TRANSFER_THRESHOLD_MS) {
            System.out.println(String.format("   [PERFORMANCE WARNING] Long-running transfer detected! Duration: %d ms (Threshold: %d ms)",
                    duration, LONG_TRANSFER_THRESHOLD_MS));
        }
    }
}
