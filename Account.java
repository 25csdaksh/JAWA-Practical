import java.util.Objects;

public class Account {
    private final String accountNumber;
    private String ownerName;
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
        this.balance = Math.max(0, balance);
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Deposit amount must be positive. Provided: " + amount);
            return;
        }
        this.balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Withdrawal amount must be positive. Provided: " + amount);
            return false;
        }
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        System.out.println("[ERROR] Insufficient balance for withdrawal. Account: " 
                           + this.accountNumber + ", Attempted: " + amount + ", Balance: " + this.balance);
        return false;
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
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

    // (1) In Account, override the toString() method (showing whether the account is active)
    @Override
    public String toString() {
        return "Account[No=" + accountNumber + ", Owner=" + ownerName 
               + ", Balance=Rs. " + balance + ", Active=" + active + "]";
    }

    // (2) In Account, override equals(Object o) and hashCode() by accountNumber
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

    // Supplementary: Add a method that returns a formatted, multi-line statement string for an account
    public String getStatement() {
        return "----------------------------------------\n" +
               "           ACCOUNT STATEMENT            \n" +
               "----------------------------------------\n" +
               "Account Number : " + accountNumber + "\n" +
               "Owner Name     : " + ownerName + "\n" +
               "Current Balance: Rs. " + balance + "\n" +
               "Account Status : " + (active ? "ACTIVE" : "INACTIVE") + "\n" +
               "----------------------------------------";
    }
}
