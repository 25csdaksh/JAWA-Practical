package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import service.Premium;

public class SavingsAccount extends Account implements Premium {
    private final long minBalance;

    public SavingsAccount(String ownerName, long balance, long minBalance) {
        super(ownerName, balance);
        this.minBalance = minBalance;
    }

    public SavingsAccount(String ownerName, long balance) {
        this(ownerName, balance, 500); // Default minimum balance: Rs. 500
    }

    public long getMinBalance() {
        return minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= minBalance;
    }

    @Override
    public void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException, BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive. Provided: " + amount);
        }
        if ((getBalance() - amount) < minBalance) {
            long shortfall = (minBalance + amount) - getBalance();
            throw new InsufficientFundsException(
                String.format("Withdrawal failed: short by %d (Requires maintaining min balance Rs. %d)", 
                        shortfall, minBalance),
                shortfall
            );
        }
        adjustBalance(-amount);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [MinBalance=Rs. %d]", minBalance);
    }
}
