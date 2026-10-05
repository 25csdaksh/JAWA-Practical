package model;

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
    public String toString() {
        return super.toString() + String.format(" [MinBalance=Rs. %d]", minBalance);
    }
}
