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

        // Pre-load accounts for demonstration
        accounts[accountCount++] = new Account("Daksh Soni", 1000);
        accounts[accountCount++] = new Account("Prof. Sharma", 500);
        accounts[accountCount++] = new Account("Alice Smith");

        System.out.println("\n--- [PRACTICAL 3 DEMONSTRATION & TEST RUN] ---");

        // 1. In main, print accounts using toString()
        System.out.println("Printing accounts using toString():");
        for (int i = 0; i < accountCount; i++) {
            System.out.println(" - " + accounts[i].toString());
        }

        // 2. Compare two Account objects with equals()
        System.out.println("\nComparing Account 1 (AC0001) and Account 2 (AC0002) using equals():");
        boolean isEqual = accounts[0].equals(accounts[1]);
        System.out.println("AC0001 equals AC0002? " + isEqual);
        System.out.println("Comparing AC0001 with itself using equals():");
        System.out.println("AC0001 equals AC0001? " + accounts[0].equals(accounts[0]));

        // 3. Use instanceof to check an object’s type
        System.out.println("\nChecking object type using instanceof:");
        Object testObj = accounts[0];
        if (testObj instanceof Account) {
            System.out.println("testObj is indeed an instance of Account class.");
        }
        if (testObj instanceof Object) {
            System.out.println("testObj is also an instance of Object class.");
        }

        // 4. Test Customer Address nested class and clone()
        System.out.println("\nTesting Customer Address and deep cloning:");
        Customer.Address addr = new Customer.Address("101 University Road", "Ahmedabad", "380009");
        Customer originalCustomer = new Customer("Daksh Soni", "daksh@charusat.edu.in", "9876543210", addr);
        Customer clonedCustomer = originalCustomer.clone();

        System.out.println("Original Customer: " + originalCustomer);
        System.out.println("Cloned Customer:   " + clonedCustomer);
        System.out.println("Are references equal? (original == cloned) -> " + (originalCustomer == clonedCustomer));
        System.out.println("Are addresses shared reference? (original.addr == cloned.addr) -> " 
                           + (originalCustomer.getAddress() == clonedCustomer.getAddress()));

        // Supplementary: Format and print Statement (Practical 3 version)
        System.out.println("\nPrinting multi-line Account Statement (Practical 3 getStatement()):");
        System.out.println(accounts[0].getStatement());

        System.out.println("-----------------------------------------------------------");

        System.out.println("\n--- [PRACTICAL 4 DEMONSTRATION & TEST RUN] ---");

        // 1. Test Validator
        System.out.println("Testing Mobile Number Validator:");
        System.out.println(" - 9876543210 (Correct): " + Validator.isValidMobile("9876543210"));
        System.out.println(" - 5876543210 (Wrong  ): " + Validator.isValidMobile("5876543210"));

        System.out.println("\nTesting Email Validator:");
        System.out.println(" - daksh@charusat.edu.in (Correct): " + Validator.isValidEmail("daksh@charusat.edu.in"));
        System.out.println(" - daksh.charusat.edu.in (Wrong  ): " + Validator.isValidEmail("daksh.charusat.edu.in"));

        System.out.println("\nTesting PAN Validator:");
        System.out.println(" - ABCDE1234F (Correct): " + Validator.isValidPan("ABCDE1234F"));
        System.out.println(" - ABC1234F   (Wrong  ): " + Validator.isValidPan("ABC1234F"));

        System.out.println("\nTesting IFSC Validator:");
        System.out.println(" - BARB0GUJARA (Correct): " + Validator.isValidIfsc("BARB0GUJARA"));
        System.out.println(" - BARB1GUJARA (Wrong  ): " + Validator.isValidIfsc("BARB1GUJARA"));

        System.out.println("\nTesting Positive Amount Validator (Supplementary):");
        System.out.println(" - 500 (Correct): " + Validator.isValidAmount("500"));
        System.out.println(" - -50 (Wrong  ): " + Validator.isValidAmount("-50"));
        System.out.println(" - 0   (Wrong  ): " + Validator.isValidAmount("0"));

        // 2. Test Parser
        System.out.println("\nTesting Command Parser:");
        String commandLine = "DEPOSIT AC0001 500";
        System.out.println("Parsing line: \"" + commandLine + "\"");
        try {
            Command cmd = CommandParser.parse(commandLine);
            System.out.println("Parsed Command Parts:");
            System.out.println(" - Type: " + cmd.type());
            System.out.println(" - Account Number: " + cmd.accountNumber());
            System.out.println(" - Amount: " + cmd.amount());
        } catch (Exception e) {
            System.out.println("Error parsing command: " + e.getMessage());
        }

        System.out.println("\nTesting Command Parser Error Handling (Wrong number of parts):");
        String wrongCommandLine = "WITHDRAW AC0001";
        System.out.println("Parsing line: \"" + wrongCommandLine + "\"");
        try {
            CommandParser.parse(wrongCommandLine);
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Exception Caught: " + e.getMessage());
        }

        // 3. Test StatementFormatter
        System.out.println("\nPrinting Statement via StatementFormatter:");
        System.out.println(StatementFormatter.buildStatement(accounts[0]));

        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

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
                scanner.nextLine(); // Consume newline
            } else {
                scanner.nextLine(); // Clear buffer
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
                        System.out.println("\n[ERROR] Bank database full.");
                    }
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
