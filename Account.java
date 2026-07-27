public class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance; // whole rupees
    private boolean active;

    private static long accountCounter = 0;

    // Private static method to generate account number, e.g., "AC0001"
    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    // Constructor taking ownerName and opening balance
    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        // Set opening balance; reject negative starting balance (set to 0)
        this.balance = Math.max(0, balance);
        this.active = true;
    }

    // Constructor taking only ownerName, chaining to the first constructor
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Public method deposit
    public void deposit(long amount) {
        // Supplementary: Reject a deposit of a negative/zero amount
        if (amount <= 0) {
            System.out.println("[ERROR] Deposit amount must be positive. Provided: " + amount);
            return;
        }
        this.balance += amount;
    }

    // Public method withdraw
    public boolean withdraw(long amount) {
        // Supplementary: Reject a withdrawal of a negative/zero amount
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
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    // Setters (only for ownerName and active, NOT for balance)
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Supplementary: Add a helper that moves an amount from one Account to another using withdraw and deposit
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
            System.out.println("[TRANSFER] Transfer failed due to insufficient funds or validation errors.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Account[No=" + accountNumber + ", Owner=" + ownerName 
               + ", Balance=Rs. " + balance + ", Active=" + active + "]";
    }
}
