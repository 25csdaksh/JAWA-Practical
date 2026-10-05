package service;

import model.Account;

public class AccountWorker implements Runnable {
    public enum Operation {
        DEPOSIT,
        WITHDRAW
    }

    private final Account account;
    private final int times;
    private final long amount;
    private final Operation operation;
    private final boolean unsafeMode;

    public AccountWorker(Account account, int times, long amount, Operation operation, boolean unsafeMode) {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.operation = operation;
        this.unsafeMode = unsafeMode;
    }

    public AccountWorker(Account account, int times, long amount) {
        this(account, times, amount, Operation.DEPOSIT, false);
    }

    public AccountWorker(Account account, int times, long amount, boolean unsafeMode) {
        this(account, times, amount, Operation.DEPOSIT, unsafeMode);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        // Observing thread lifecycle: RUNNING
        // System.out.println(String.format("   [%s] Thread Started. Executing %d %s operations...", 
        //         threadName, times, operation));

        for (int i = 0; i < times; i++) {
            try {
                if (operation == Operation.DEPOSIT) {
                    if (unsafeMode) {
                        account.depositUnsafe(amount);
                    } else {
                        account.deposit(amount);
                    }
                } else {
                    account.withdraw(amount);
                }
            } catch (Exception e) {
                // Ignore business rule exceptions during stress test
            }
        }

        // Thread finishes its execution and enters TERMINATED state
    }
}
