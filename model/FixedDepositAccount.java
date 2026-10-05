package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.time.LocalDate;
import service.Premium;

public class FixedDepositAccount extends Account implements Premium {
    private static final long serialVersionUID = 1L;

    private final LocalDate maturityDate;
    private boolean matured;

    public FixedDepositAccount(String ownerName, long balance, LocalDate maturityDate) {
        super(ownerName, balance);
        this.maturityDate = maturityDate;
        this.matured = (maturityDate != null && !LocalDate.now().isBefore(maturityDate));
    }

    public FixedDepositAccount(String ownerName, long balance) {
        this(ownerName, balance, LocalDate.now().plusYears(1)); // Default 1 year lock-in
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public synchronized boolean isMatured() {
        return matured || (maturityDate != null && !LocalDate.now().isBefore(maturityDate));
    }

    public synchronized void setMatured(boolean matured) {
        this.matured = matured;
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public synchronized boolean canWithdraw(long amount) {
        if (!isMatured()) {
            return false;
        }
        return getBalance() >= amount;
    }

    @Override
    public synchronized void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException, BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive. Provided: " + amount);
        }
        if (!isMatured()) {
            throw new BankException("Withdrawal failed: Fixed Deposit is locked until maturity date (" + maturityDate + ")");
        }
        if (getBalance() < amount) {
            long shortfall = amount - getBalance();
            throw new InsufficientFundsException(
                String.format("Withdrawal failed: short by %d (Balance: %d, Attempted: %d)", 
                        shortfall, getBalance(), amount),
                shortfall
            );
        }
        adjustBalance(-amount);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [MaturityDate=%s, Matured=%b]", maturityDate, isMatured());
    }
}
