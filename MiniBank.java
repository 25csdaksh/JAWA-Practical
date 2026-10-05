import java.util.Scanner;
import model.*;
import service.*;
import util.*;
import static util.Validator.*; // Static import for Validator methods

public class MiniBank {
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        VIEW_STATEMENT,
        VERIFY_CREDENTIALS,
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

        // Pre-load accounts (Demonstrating model package & Premium marker interface)
        accounts[accountCount++] = new SavingsAccount("Daksh Soni", 25000, 1000);
        accounts[accountCount++] = new CurrentAccount("Prof. Sharma", 40000, 15000);
        accounts[accountCount++] = new FixedDepositAccount("Alice Smith", 100000);

        System.out.println("\n--- [PRACTICAL 6 DEMONSTRATION: INTERFACES, PACKAGES & LAMBDAS] ---");

        // 1. Functional Interface WithdrawRule: Anonymous Class vs Lambda Expression
        System.out.println("\n1. Testing WithdrawRule Functional Interface (Anonymous Class vs Lambda):");

        // (a) Implemented as an Anonymous Class (Rule: Max Rs. 20,000 per transaction & check account capability)
        WithdrawRule anonymousNightLimitRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount <= 20000 && account.canWithdraw(amount);
            }
        };

        // (b) Implemented as a Lambda Expression (Rule: Max Rs. 50,000 ATM limit & check account capability)
        WithdrawRule lambdaAtmLimitRule = (account, amount) -> (amount <= 50000 && account.canWithdraw(amount));

        Account testSavings = accounts[0];
        long testAmt1 = 15000;
        long testAmt2 = 25000;

        System.out.println("Test Account: " + testSavings.getAccountNumber() + " | Balance: Rs. " + testSavings.getBalance());
        System.out.println(String.format(" - Attempt Rs. %d -> Anonymous Class Rule (Max 20k) Allowed? %b",
                testAmt1, anonymousNightLimitRule.allow(testSavings, testAmt1)));
        System.out.println(String.format(" - Attempt Rs. %d -> Anonymous Class Rule (Max 20k) Allowed? %b",
                testAmt2, anonymousNightLimitRule.allow(testSavings, testAmt2)));
        System.out.println(String.format(" - Attempt Rs. %d -> Lambda Expression Rule (Max 50k) Allowed? %b",
                testAmt2, lambdaAtmLimitRule.allow(testSavings, testAmt2)));

        // 2. Default Methods in InterestBearing interface
        System.out.println("\n2. Testing Default Methods in InterestBearing Interface:");
        System.out.println(String.format("%-8s | %-16s | %-12s | %-16s | %-20s",
                "Acc No", "Owner", "Rate", "Yearly Interest", "5-Yr Compounded Value"));
        System.out.println("----------------------------------------------------------------------------------");
        for (int i = 0; i < accountCount; i++) {
            Account acc = accounts[i];
            // Calling default method (1): yearlyInterest()
            double yearly = acc.yearlyInterest();
            // Calling supplementary default method (2): projectedBalance(years)
            double projected5Yr = acc.projectedBalance(5);

            System.out.println(String.format("%-8s | %-16s | %-10.1f%% | Rs. %-12.2f | Rs. %-16.2f",
                    acc.getAccountNumber(), acc.getOwnerName(), acc.interestRate(), yearly, projected5Yr));
        }
        System.out.println("----------------------------------------------------------------------------------");

        // 3. Marker Interface Check (Premium)
        System.out.println("\n3. Testing Premium Marker Interface Detection:");
        for (int i = 0; i < accountCount; i++) {
            Account acc = accounts[i];
            if (acc instanceof Premium) {
                System.out.println(" [VIP PRIVILEGE] " + acc.getAccountNumber() + " (" + acc.getOwnerName() 
                        + ") is marked as a PREMIUM account. Eligible for priority concierge & relationship manager.");
            } else {
                System.out.println(" [STANDARD] " + acc.getAccountNumber() + " (" + acc.getOwnerName() 
                        + ") is a Standard account.");
            }
        }

        // 4. Static Import Test (Using Validator methods directly without class qualifier)
        System.out.println("\n4. Testing Static Import of Validator Methods:");
        System.out.println(" - isValidMobile(\"9876543210\"): " + isValidMobile("9876543210"));
        System.out.println(" - isValidEmail(\"daksh@charusat.edu.in\"): " + isValidEmail("daksh@charusat.edu.in"));
        System.out.println(" - isValidPan(\"ABCDE1234F\"): " + isValidPan("ABCDE1234F"));
        System.out.println(" - isValidAmount(\"5000\"): " + isValidAmount("5000"));

        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n----------------- MINIBANK INTERACTIVE MENU -----------------");
            System.out.println("1. Open Account (Savings / Current / Fixed Deposit)");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer (Local)");
            System.out.println("5. View Official Account Statement");
            System.out.println("6. Verify Customer Credentials (Static Import Validator)");
            System.out.println("7. Check Bank Working Hours");
            System.out.println("8. Exit");
            System.out.println("------------------------------------------------------------");
            System.out.print("Please enter your choice (1-8): ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
            }

            MenuOption selectedOption = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.VIEW_STATEMENT;
                case 6 -> MenuOption.VERIFY_CREDENTIALS;
                case 7 -> MenuOption.WORKING_HOURS;
                case 8 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 8.");
                continue;
            }

            switch (selectedOption) {
                case OPEN_ACCOUNT -> {
                    if (accountCount >= accounts.length) {
                        System.out.println("\n[ERROR] Bank database is full.");
                        break;
                    }
                    System.out.println("\nSelect Account Type:");
                    System.out.println("1. Savings Account (4.0% Interest, Min Balance, Premium Status)");
                    System.out.println("2. Current Account (0.0% Interest, Overdraft Facility)");
                    System.out.println("3. Fixed Deposit Account (7.0% Interest, Locked Deposit, Premium Status)");
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

                    Account newAcc;
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
                        
                        // Validating withdrawal through lambda WithdrawRule
                        if (lambdaAtmLimitRule.allow(acc, amount)) {
                            if (acc.withdraw(amount)) {
                                System.out.println("[SUCCESS] Withdrawal completed. Updated details: " + acc);
                            }
                        } else {
                            System.out.println("[ERROR] Transaction rejected by WithdrawRule (Exceeds limit or insufficient funds).");
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
                        System.out.println("\n" + StatementFormatter.buildStatement(acc));
                        System.out.println(String.format("5-Year Compound Projection: Rs. %.2f", acc.projectedBalance(5)));
                    } else {
                        System.out.println("\n[ERROR] Account not found!");
                    }
                }
                case VERIFY_CREDENTIALS -> {
                    System.out.println("\n--- Credential Verification Form (via Static Imports) ---");
                    System.out.print("Enter Mobile Number: ");
                    String mob = scanner.nextLine().trim();
                    System.out.println(" -> Valid Mobile? " + isValidMobile(mob));

                    System.out.print("Enter Email Address: ");
                    String email = scanner.nextLine().trim();
                    System.out.println(" -> Valid Email? " + isValidEmail(email));

                    System.out.print("Enter PAN Card: ");
                    String pan = scanner.nextLine().trim();
                    System.out.println(" -> Valid PAN? " + isValidPan(pan));
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
