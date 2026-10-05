package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.io.Serializable;
import java.util.Objects;
import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;
import service.InterestBearing;
import service.Transactable;

public abstract class Account implements Transactable, InterestBearing, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @MaxLength(value = 10, message = "Account number length cannot exceed 10 characters")
    private final String accountNumber;

    @MaxLength(value = 25, message = "Owner name exceeds maximum allowed length")
    private String ownerName;

    @Positive(message = "must be > 0")
    private long balance; // whole rupees

    private boolean active;

    // Transient field: Demonstrates state that is excluded from serialization
    private transient String sessionToken;

    private static long accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Abstract methods to be implemented by subclasses
    @Override
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    // Monthly interest calculation using interestRate() and balance
    public synchronized double monthlyInterest() {
        if (balance <= 0) {
            return 0.0;
        }
        return (balance * (interestRate() / 100.0)) / 12.0;
    }

    // Synchronized deposit method ensuring thread-safe atomic updates
    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive. Provided: " + amount);
        }
        this.balance += amount;
    }

    // Unsynchronized deposit for explicitly demonstrating the race condition (Practical 9 Part B)
    public void depositUnsafe(long amount) {
        if (amount > 0) {
            // Read-Modify-Write non-atomic race window
            long temp = this.balance;
            try {
                // Micro-pause to trigger thread preemption and race condition
                Thread.sleep(0, 50);
            } catch (InterruptedException ignored) {}
            this.balance = temp + amount;
        }
    }

    // Synchronized withdraw method
    @Override
    public synchronized void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException, BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive. Provided: " + amount);
        }
        if (this.balance < amount) {
            long shortfall = amount - this.balance;
            throw new InsufficientFundsException(
                String.format("Withdrawal failed: short by %d (Balance: %d, Attempted: %d)", 
                        shortfall, this.balance, amount),
                shortfall
            );
        }
        this.balance -= amount;
    }

    // Protected helper for subclasses to update balance with synchronization
    protected synchronized void adjustBalance(long delta) {
        this.balance += delta;
    }

    // Reset balance helper for test suites
    public synchronized void setBalance(long balance) {
        this.balance = balance;
    }

    // Transfer method using try-catch-finally and re-throwing BankException
    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new BankException("Destination account cannot be null.");
        }
        System.out.println(String.format("[TRANSFER INITIATED] Transferring Rs. %d from %s to %s...",
                amount, this.getAccountNumber(), to.getAccountNumber()));
        boolean debited = false;
        try {
            this.withdraw(amount);
            debited = true;
            to.deposit(amount);
            System.out.println("[TRANSFER SUCCESS] Transfer completed successfully.");
        } catch (BankException e) {
            if (debited) {
                // Rollback if destination deposit failed
                try {
                    this.deposit(amount);
                } catch (Exception ignored) {}
            }
            System.out.println("[TRANSFER FAILED] " + e.getMessage());
            throw e; // Re-throw BankException
        } finally {
            System.out.println(String.format("[TRANSFER AUDIT] Final Balances -> Sender %s: Rs. %d | Receiver %s: Rs. %d",
                    this.getAccountNumber(), this.getBalance(), to.getAccountNumber(), to.getBalance()));
        }
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    @Override
    public synchronized long getBalance() { return balance; }
    public boolean isActive() { return active; }

    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("%s[No=%s, Owner=%s, Balance=Rs. %d, InterestRate=%.1f%%, Active=%b]",
                getClass().getSimpleName(), accountNumber, ownerName, balance, interestRate(), active);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    public String getStatement() {
        return "----------------------------------------\n" +
               "           ACCOUNT STATEMENT            \n" +
               "----------------------------------------\n" +
               "Account Type   : " + getClass().getSimpleName() + "\n" +
               "Account Number : " + accountNumber + "\n" +
               "Owner Name     : " + ownerName + "\n" +
               "Current Balance: Rs. " + balance + "\n" +
               "Interest Rate  : " + String.format("%.2f%%", interestRate()) + "\n" +
               "Est. Mo. Interest: Rs. " + String.format("%.2f", monthlyInterest()) + "\n" +
               "Est. Yr. Interest: Rs. " + String.format("%.2f", yearlyInterest()) + "\n" +
               "Account Status : " + (active ? "ACTIVE" : "INACTIVE") + "\n" +
               "----------------------------------------";
    }
}
