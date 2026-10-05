package model;

import java.util.Objects;
import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;
import service.InterestBearing;
import service.Transactable;

public abstract class Account implements Transactable, InterestBearing {
    @Id
    @MaxLength(value = 10, message = "Account number length cannot exceed 10 characters")
    private final String accountNumber;

    @MaxLength(value = 25, message = "Owner name exceeds maximum allowed length")
    private String ownerName;

    @Positive(message = "must be > 0")
    private long balance; // whole rupees

    private boolean active;

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
    public double monthlyInterest() {
        if (balance <= 0) {
            return 0.0;
        }
        return (balance * (interestRate() / 100.0)) / 12.0;
    }

    @Override
    public void deposit(long amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Deposit amount must be positive. Provided: " + amount);
            return;
        }
        this.balance += amount;
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Withdrawal amount must be positive. Provided: " + amount);
            return false;
        }
        if (canWithdraw(amount)) {
            this.balance -= amount;
            return true;
        }
        System.out.println("[ERROR] Withdrawal not allowed or limit exceeded. Account: " 
                           + this.accountNumber + ", Attempted: " + amount + ", Current Balance: " + this.balance);
        return false;
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    @Override
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static boolean transfer(Account source, Account destination, long amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Transfer amount must be positive.");
            return false;
        }
        
        System.out.println("[TRANSFER] Initiating transfer of Rs. " + amount 
                           + " from " + source.getAccountNumber() + " to " + destination.getAccountNumber());
        
        if (source.withdraw(amount)) {
            destination.deposit(amount);
            System.out.println("[TRANSFER] Transfer successful!");
            return true;
        } else {
            System.out.println("[TRANSFER] Transfer failed.");
            return false;
        }
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
