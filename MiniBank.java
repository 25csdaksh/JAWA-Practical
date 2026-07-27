import java.util.Scanner;

record BankInfo(String name, String branch) {
    @Override
    public String toString() {
        return "=================================================\n" +
               "           " + name.toUpperCase() + "\n" +
               "           Branch: " + branch + "\n" +
               "=================================================";
    }
}

public class MiniBank {
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        WORKING_HOURS,
        EXIT
    }

    // Helper method to find an account in the array by account number
    private static Account findAccount(Account[] accounts, int count, String accNo) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountNumber().equalsIgnoreCase(accNo.trim())) {
                return accounts[i];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        BankInfo header = new BankInfo("MiniBank India", "Gujarat University Campus");
        System.out.println(header);

        // Array to hold bank accounts
        Account[] accounts = new Account[100];
        int accountCount = 0;

        // 5. In main, create three Account objects inside an Account[] array, 
        // perform a few deposits and withdrawals, and print each balance.
        System.out.println("\n--- [STARTUP TEST RUN] PRE-LOADING & TESTING 3 ACCOUNTS ---");
        
        // Creating accounts (Daksh Soni: Rs 1000, Prof. Sharma: Rs 500, Alice Smith: Rs 0 default)
        accounts[accountCount++] = new Account("Daksh Soni", 1000);
        accounts[accountCount++] = new Account("Prof. Sharma", 500);
        accounts[accountCount++] = new Account("Alice Smith"); 

        System.out.println("Initial Account List:");
        for (int i = 0; i < accountCount; i++) {
            System.out.println(" - " + accounts[i]);
        }

        System.out.println("\nExecuting transactions:");
        
        // Deposits
        System.out.println(" * Depositing Rs. 500 to Account 1 (" + accounts[0].getOwnerName() + ")");
        accounts[0].deposit(500);

        // Withdrawals (Sufficient balance)
        System.out.println(" * Withdrawing Rs. 200 from Account 2 (" + accounts[1].getOwnerName() + ")");
        accounts[1].withdraw(200);

        // Withdrawals (Insufficient balance - should fail)
        System.out.println(" * Attempting to withdraw Rs. 100 from Account 3 (" + accounts[2].getOwnerName() + ")");
        accounts[2].withdraw(100);

        // Negative value checks (Supplementary)
        System.out.println(" * Attempting negative deposit to Account 1:");
        accounts[0].deposit(-100);

        // Account transfer helper check (Supplementary)
        System.out.println(" * Transferring Rs. 300 from Account 1 to Account 3:");
        Account.transfer(accounts[0], accounts[2], 300);

        System.out.println("\nBalances after transactions:");
        for (int i = 0; i < accountCount; i++) {
            System.out.println(" - Account " + accounts[i].getAccountNumber() 
                               + " (" + accounts[i].getOwnerName() + ") Balance: Rs. " + accounts[i].getBalance());
        }
        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        // Start the interactive console shell
        while (keepRunning) {
            System.out.println("\n----------------- INTERACTIVE MENU -----------------");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer (Local)");
            System.out.println("5. Check Bank Working Hours");
            System.out.println("6. Exit");
            System.out.println("---------------------------------------------");
            System.out.print("Please enter your choice (1-6): ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume trailing newline
            } else {
                scanner.nextLine(); // Clear invalid input from buffer
            }

            MenuOption selectedOption = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.WORKING_HOURS;
                case 6 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 6.");
                continue;
            }

            switch (selectedOption) {
                case OPEN_ACCOUNT -> {
                    System.out.print("Enter owner's name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter opening balance (in Rs.): ");
                    long openingBal = scanner.nextLong();
                    scanner.nextLine(); // Consume newline

                    if (accountCount < accounts.length) {
                        accounts[accountCount] = new Account(name, openingBal);
                        System.out.println("\n[SUCCESS] Account created: " + accounts[accountCount]);
                        accountCount++;
                    } else {
                        System.out.println("\n[ERROR] Bank database full. Cannot open more accounts.");
                    }
                }
                case DEPOSIT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    Account acc = findAccount(accounts, accountCount, accNo);
                    if (acc != null) {
                        System.out.print("Enter amount to deposit: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine(); // Consume newline
                        acc.deposit(amount);
                        System.out.println("[SUCCESS] Updated balance: Rs. " + acc.getBalance());
                    } else {
                        System.out.println("\n[ERROR] Account not found!");
                    }
                }
                case WITHDRAW -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    Account acc = findAccount(accounts, accountCount, accNo);
                    if (acc != null) {
                        System.out.print("Enter amount to withdraw: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine(); // Consume newline
                        if (acc.withdraw(amount)) {
                            System.out.println("[SUCCESS] Withdrawal completed. Remaining balance: Rs. " + acc.getBalance());
                        }
                    } else {
                        System.out.println("\n[ERROR] Account not found!");
                    }
                }
                case TRANSFER -> {
                    System.out.print("Enter Source Account Number (Sender): ");
                    String srcAcc = scanner.nextLine().trim();
                    System.out.print("Enter Destination Account Number (Receiver): ");
                    String destAcc = scanner.nextLine().trim();
                    
                    Account src = findAccount(accounts, accountCount, srcAcc);
                    Account dest = findAccount(accounts, accountCount, destAcc);
                    
                    if (src != null && dest != null) {
                        System.out.print("Enter amount to transfer: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine(); // Consume newline
                        Account.transfer(src, dest, amount);
                    } else {
                        System.out.println("\n[ERROR] One or both account numbers are invalid.");
                    }
                }
                case WORKING_HOURS -> {
                    System.out.println("""
                                      Bank Working Hours:
                                      - Monday to Friday: 09:30 AM - 04:00 PM
                                      - Saturday: 09:30 AM - 01:30 PM (Closed on 2nd and 4th Saturdays)
                                      - Sunday & Public Holidays: CLOSED""");
                }
                case EXIT -> {
                    System.out.println("Thank you for using MiniBank. Goodbye!");
                    keepRunning = false;
                }
            }
        }
        scanner.close();
    }
}
