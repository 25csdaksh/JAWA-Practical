package service;

import exception.BankException;
import model.Account;

public class TransferService {
    private static final long LONG_TRANSFER_THRESHOLD_MS = 50;

    public static void transferDeadlockProne(Account from, Account to, long amount) throws BankException {
        if (from == null || to == null) throw new BankException("Accounts cannot be null");

        synchronized (from) {
            System.out.println(String.format("   [%s] Acquired lock on source %s. Waiting for destination %s...",
                    Thread.currentThread().getName(), from.getAccountNumber(), to.getAccountNumber()));
            try {
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

    public static void transferSafe(Account from, Account to, long amount) throws BankException {
        if (from == null || to == null) throw new BankException("Accounts cannot be null");
        if (from.equals(to)) throw new BankException("Cannot transfer to the same account");

        long startTime = System.currentTimeMillis();

        Account firstLock;
        Account secondLock;

        if (from.compareTo(to) < 0) {
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

        if (duration > LONG_TRANSFER_THRESHOLD_MS) {
            System.out.println(String.format("   [PERFORMANCE WARNING] Long-running transfer detected! Duration: %d ms (Threshold: %d ms)",
                    duration, LONG_TRANSFER_THRESHOLD_MS));
        }
    }
}
