package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class CurrentAccount extends Account {
    private final long overdraftLimit;

    public CurrentAccount(String ownerName, long balance, long overdraftLimit) {
        super(ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public CurrentAccount(String ownerName, long balance) {
        this(ownerName, balance, 10000); // Default overdraft limit: Rs. 10,000
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= -overdraftLimit;
    }

    @Override
    public void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException, BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive. Provided: " + amount);
        }
        if ((getBalance() - amount) < -overdraftLimit) {
            long shortfall = amount - (getBalance() + overdraftLimit);
            throw new InsufficientFundsException(
                String.format("Withdrawal failed: exceeds overdraft limit by %d (Overdraft Limit: Rs. %d)", 
                        shortfall, overdraftLimit),
                shortfall
            );
        }
        adjustBalance(-amount);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [OverdraftLimit=Rs. %d]", overdraftLimit);
    }
}
