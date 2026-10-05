import java.util.Objects;

public abstract class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance; // whole rupees (can become negative for CurrentAccount within overdraft)
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
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    // Supplementary: Monthly interest calculation using interestRate() and balance
    public double monthlyInterest() {
        if (balance <= 0) {
            return 0.0;
        }
        return (balance * (interestRate() / 100.0)) / 12.0;
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
               "Account Status : " + (active ? "ACTIVE" : "INACTIVE") + "\n" +
               "----------------------------------------";
    }
}
