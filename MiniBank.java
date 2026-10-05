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
        VIEW_STATEMENT,
        WORKING_HOURS,
        EXIT
    }

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

        Account[] accounts = new Account[100];
        int accountCount = 0;

        // Pre-load accounts with different concrete subclasses
        accounts[accountCount++] = new SavingsAccount("Daksh Soni", 15000, 1000);
        accounts[accountCount++] = new CurrentAccount("Prof. Sharma", 25000, 10000);
        accounts[accountCount++] = new FixedDepositAccount("Alice Smith", 50000);

        System.out.println("\n--- [PRACTICAL 5 DEMONSTRATION: INHERITANCE & POLYMORPHISM] ---");

        // 1. Loop through Account[] array calling interestRate() on each (Polymorphism)
        System.out.println("\nPolymorphic Interest Rate & Account Inspection Loop:");
        System.out.println(String.format("%-8s | %-16s | %-20s | %-14s | %-16s | %-30s", 
            "Acc No", "Owner", "Account Type", "Balance", "Interest Rate", "Special Subtype Attribute"));
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < accountCount; i++) {
            Account acc = accounts[i];
            double rate = acc.interestRate(); // Dynamic method dispatch (Polymorphism)
            double monthlyInt = acc.monthlyInterest();

            // 2. Use Pattern Matching instanceof to handle each subtype specially
            String subtypeDetail;
            if (acc instanceof SavingsAccount sa) {
                subtypeDetail = "Min Balance Req: Rs. " + sa.getMinBalance();
            } else if (acc instanceof CurrentAccount ca) {
                subtypeDetail = "Overdraft Limit: Rs. " + ca.getOverdraftLimit();
            } else if (acc instanceof FixedDepositAccount fda) {
                subtypeDetail = "Matures: " + fda.getMaturityDate() + " (Locked=" + !fda.isMatured() + ")";
            } else {
                subtypeDetail = "Generic Account";
            }

            System.out.println(String.format("%-8s | %-16s | %-20s | Rs. %-10d | %-14s | %-30s",
                acc.getAccountNumber(), acc.getOwnerName(), acc.getClass().getSimpleName(), 
                acc.getBalance(), String.format("%.1f%% (Rs. %.2f/mo)", rate, monthlyInt), subtypeDetail));
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        // 3. Testing Polymorphic Withdrawal & Business Rules
        System.out.println("\nTesting Polymorphic Withdrawal Rules across Account Types:");

        // Test Savings Account minimum balance protection
        System.out.println("\n1. Testing SavingsAccount (AC0001) - Balance: Rs. 15,000 | MinBalance: Rs. 1,000");
        System.out.println("Attempting withdrawal of Rs. 14,500 (would leave Rs. 500 < minBalance):");
        accounts[0].withdraw(14500); // Should fail
        System.out.println("Attempting withdrawal of Rs. 10,000 (leaves Rs. 5,000 >= minBalance):");
        accounts[0].withdraw(10000); // Should succeed

        // Test Current Account overdraft limit
        System.out.println("\n2. Testing CurrentAccount (AC0002) - Balance: Rs. 25,000 | Overdraft Limit: Rs. 10,000");
        System.out.println("Attempting withdrawal of Rs. 30,000 (leaves balance Rs. -5,000 within overdraft):");
        accounts[1].withdraw(30000); // Should succeed
        System.out.println("Attempting additional withdrawal of Rs. 10,000 (would exceed overdraft limit):");
        accounts[1].withdraw(10000); // Should fail

        // Test Fixed Deposit Account lock-in and maturity
        System.out.println("\n3. Testing FixedDepositAccount (AC0003) - Balance: Rs. 50,000 | Locked");
        System.out.println("Attempting premature withdrawal of Rs. 10,000 on locked FD:");
        accounts[2].withdraw(10000); // Should fail
        System.out.println("Simulating maturity for FixedDepositAccount (AC0003)...");
        if (accounts[2] instanceof FixedDepositAccount fda) {
            fda.setMatured(true);
            System.out.println("Attempting withdrawal of Rs. 20,000 post-maturity:");
            fda.withdraw(20000); // Should succeed
        }

        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n----------------- INTERACTIVE MENU -----------------");
            System.out.println("1. Open Account (Savings / Current / Fixed Deposit)");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer (Local)");
            System.out.println("5. View Account Statement");
            System.out.println("6. Check Bank Working Hours");
            System.out.println("7. Exit");
            System.out.println("----------------------------------------------------");
            System.out.print("Please enter your choice (1-7): ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                scanner.nextLine(); // Clear buffer
            }

            MenuOption selectedOption = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.VIEW_STATEMENT;
                case 6 -> MenuOption.WORKING_HOURS;
                case 7 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 7.");
                continue;
            }

            switch (selectedOption) {
                case OPEN_ACCOUNT -> {
                    if (accountCount >= accounts.length) {
                        System.out.println("\n[ERROR] Bank database is full.");
                        break;
                    }
                    System.out.println("\nSelect Account Type:");
                    System.out.println("1. Savings Account (4.0% Interest, Min Balance)");
                    System.out.println("2. Current Account (0.0% Interest, Overdraft Facility)");
                    System.out.println("3. Fixed Deposit Account (7.0% Interest, Locked Deposit)");
                    System.out.print("Choice (1-3): ");
                    
                    int accTypeChoice = 1;
                    if (scanner.hasNextInt()) {
                        accTypeChoice = scanner.nextInt();
                        scanner.nextLine();
                    } else {
                        scanner.nextLine();
                    }

                    System.out.print("Enter owner's name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter opening balance (in Rs.): ");
                    long openingBal = scanner.nextLong();
                    scanner.nextLine();

                    Account newAcc = null;
                    switch (accTypeChoice) {
                        case 1 -> {
                            System.out.print("Enter minimum balance requirement (e.g. 500): ");
                            long minBal = scanner.nextLong();
                            scanner.nextLine();
                            newAcc = new SavingsAccount(name, openingBal, minBal);
                        }
                        case 2 -> {
                            System.out.print("Enter overdraft limit (e.g. 10000): ");
                            long odLimit = scanner.nextLong();
                            scanner.nextLine();
                            newAcc = new CurrentAccount(name, openingBal, odLimit);
                        }
                        case 3 -> {
                            newAcc = new FixedDepositAccount(name, openingBal);
                        }
                        default -> {
                            System.out.println("Invalid type selected. Defaulting to Savings Account.");
                            newAcc = new SavingsAccount(name, openingBal);
                        }
                    }

                    accounts[accountCount++] = newAcc;
                    System.out.println("\n[SUCCESS] Account successfully opened:\n" + newAcc);
                }
                case DEPOSIT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    Account acc = findAccount(accounts, accountCount, accNo);
                    if (acc != null) {
                        System.out.print("Enter amount to deposit: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();
                        acc.deposit(amount);
                        System.out.println("[SUCCESS] Updated details: " + acc);
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
                        scanner.nextLine();
                        if (acc.withdraw(amount)) {
                            System.out.println("[SUCCESS] Updated details: " + acc);
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
                        scanner.nextLine();
                        Account.transfer(src, dest, amount);
                    } else {
                        System.out.println("\n[ERROR] One or both account numbers are invalid.");
                    }
                }
                case VIEW_STATEMENT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    Account acc = findAccount(accounts, accountCount, accNo);
                    if (acc != null) {
                        System.out.println("\n" + acc.getStatement());
                    } else {
                        System.out.println("\n[ERROR] Account not found!");
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
