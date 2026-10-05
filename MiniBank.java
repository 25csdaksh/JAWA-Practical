import exception.*;
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
        VALIDATE_ACCOUNT,
        VERIFY_CREDENTIALS,
        WORKING_HOURS,
        EXIT
    }

    private static Account findAccount(Account[] accounts, int count, String accNo) throws AccountNotFoundException {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountNumber().equalsIgnoreCase(accNo.trim())) {
                return accounts[i];
            }
        }
        throw new AccountNotFoundException("Account '" + accNo + "' was not found in bank database.");
    }

    public static void main(String[] args) {
        BankInfo header = new BankInfo("MiniBank India", "Gujarat University Campus");
        System.out.println(header);

        Account[] accounts = new Account[100];
        int accountCount = 0;

        // Pre-load accounts
        accounts[accountCount++] = new SavingsAccount("Daksh Soni", 25000, 1000);
        accounts[accountCount++] = new CurrentAccount("Prof. Sharma", 40000, 15000);
        accounts[accountCount++] = new FixedDepositAccount("Alice Smith", 100000);

        System.out.println("\n--- [PRACTICAL 8 DEMONSTRATION: CUSTOM EXCEPTIONS & FAULT TOLERANCE] ---");

        // 1. Mandatory Milestone Test: Account with balance 1000, withdraw(5000) throws InsufficientFundsException (shortfall = 4000)
        System.out.println("\n1. Mandatory Test: Withdrawing Rs. 5,000 from an Account with Balance Rs. 1,000:");
        Account milestoneAccount = new SavingsAccount("Test Account", 1000, 0);
        System.out.println("   Initial State: " + milestoneAccount);
        try {
            System.out.println("   Executing: milestoneAccount.withdraw(5000)...");
            milestoneAccount.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("   [EXPECTED EXCEPTION CAUGHT] " + e.getMessage());
            System.out.println("   -> Shortfall Amount Stored in Exception: Rs. " + e.getShortfall());
        } catch (BankException e) {
            System.out.println("   [BANK ERROR] " + e.getMessage());
        } finally {
            System.out.println("   [FINALLY BLOCK] Balance after attempt: Rs. " + milestoneAccount.getBalance() + " (Unchanged)");
        }

        // 2. Deposit Test with Negative Amount -> InvalidAmountException
        System.out.println("\n2. Testing Deposit with Negative Amount (-500):");
        try {
            System.out.println("   Executing: accounts[0].deposit(-500)...");
            accounts[0].deposit(-500);
        } catch (InvalidAmountException e) {
            System.out.println("   [EXPECTED EXCEPTION CAUGHT] InvalidAmountException: " + e.getMessage());
        } finally {
            System.out.println("   [FINALLY BLOCK] Account AC0001 balance remains: Rs. " + accounts[0].getBalance());
        }

        // 3. Transfer Test: try-catch-finally with Re-throwing BankException
        System.out.println("\n3. Testing Transfer Method (Success followed by Excessive Transfer):");
        try {
            // Valid transfer
            accounts[0].transfer(accounts[1], 5000);
            // Excessive transfer exceeding balance
            System.out.println("\n   Attempting Excessive Transfer of Rs. 80,000 from AC0001...");
            accounts[0].transfer(accounts[1], 80000);
        } catch (BankException e) {
            System.out.println("   [MAIN CAUGHT RE-THROWN EXCEPTION] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 4. Supplementary Problem: DailyLimitExceededException Demonstration
        System.out.println("\n4. Testing Supplementary DailyLimitExceededException (Daily ATM Cap: Rs. 50,000):");
        long dailyAtmCap = 50000;
        long attemptedWithdrawal = 75000;
        try {
            if (attemptedWithdrawal > dailyAtmCap) {
                throw new DailyLimitExceededException(
                    String.format("Transaction declined: Attempted Rs. %d exceeds maximum daily withdrawal cap of Rs. %d", 
                            attemptedWithdrawal, dailyAtmCap),
                    dailyAtmCap, attemptedWithdrawal
                );
            }
        } catch (DailyLimitExceededException e) {
            System.out.println("   [DAILY LIMIT EXCEEDED] " + e.getMessage());
            System.out.println("   -> Limit: Rs. " + e.getLimit() + " | Attempted: Rs. " + e.getAttempted());
        }

        // 5. Try-with-resources Demonstration using AutoCloseable BankingSession
        System.out.println("\n5. Testing Try-With-Resources using AutoCloseable BankingSession:");
        try (BankingSession session = new BankingSession("Daksh-Admin")) {
            session.logAudit("Initiated automated integrity & reflection audit");
            session.logAudit("Checked all 3 account status records");
            System.out.println("   [WORK DONE] Core transactional batch completed successfully.");
        } catch (Exception e) {
            System.out.println("   [ERROR IN SESSION] " + e.getMessage());
        }

        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n----------------- MINIBANK FAULT-TOLERANT MENU -----------------");
            System.out.println("1. Open Account (Savings / Current / Fixed Deposit)");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw (Structured Exception Handling)");
            System.out.println("4. Transfer (Local)");
            System.out.println("5. View Official Account Statement");
            System.out.println("6. Validate Account Metadata via Reflection");
            System.out.println("7. Verify Customer Credentials (Static Import Validator)");
            System.out.println("8. Check Bank Working Hours");
            System.out.println("9. Exit");
            System.out.println("---------------------------------------------------------------");
            System.out.print("Please enter your choice (1-9): ");

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
                case 6 -> MenuOption.VALIDATE_ACCOUNT;
                case 7 -> MenuOption.VERIFY_CREDENTIALS;
                case 8 -> MenuOption.WORKING_HOURS;
                case 9 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 9.");
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

                    String[] validationErrors = AnnotationValidator.validate(newAcc);
                    if (validationErrors.length > 0) {
                        System.out.println("\n[METADATA WARNING] Account created with annotation warnings:");
                        for (String err : validationErrors) {
                            System.out.println(" -> " + err);
                        }
                    }

                    accounts[accountCount++] = newAcc;
                    System.out.println("\n[SUCCESS] Account successfully opened:\n" + newAcc);
                }
                case DEPOSIT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        Account acc = findAccount(accounts, accountCount, accNo);
                        System.out.print("Enter amount to deposit: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();
                        acc.deposit(amount);
                        System.out.println("[SUCCESS] Deposit completed. Updated details: " + acc);
                    } catch (AccountNotFoundException | InvalidAmountException e) {
                        System.out.println("\n[DEPOSIT FAILED] " + e.getMessage());
                    }
                }
                case WITHDRAW -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        Account acc = findAccount(accounts, accountCount, accNo);
                        System.out.print("Enter amount to withdraw: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();

                        acc.withdraw(amount);
                        System.out.println("[SUCCESS] Withdrawal completed. Updated details: " + acc);
                    } catch (AccountNotFoundException e) {
                        System.out.println("\n[ERROR] " + e.getMessage());
                    } catch (InsufficientFundsException e) {
                        System.out.println(String.format("\n[WITHDRAWAL FAILED: INSUFFICIENT FUNDS] %s | Shortfall: Rs. %d",
                                e.getMessage(), e.getShortfall()));
                    } catch (InvalidAmountException e) {
                        System.out.println("\n[WITHDRAWAL FAILED: INVALID AMOUNT] " + e.getMessage());
                    } catch (BankException e) {
                        System.out.println("\n[WITHDRAWAL FAILED: BANK EXCEPTION] " + e.getMessage());
                    }
                }
                case TRANSFER -> {
                    System.out.print("Enter Source Account Number (Sender): ");
                    String srcAcc = scanner.nextLine().trim();
                    System.out.print("Enter Destination Account Number (Receiver): ");
                    String destAcc = scanner.nextLine().trim();
                    
                    try {
                        Account src = findAccount(accounts, accountCount, srcAcc);
                        Account dest = findAccount(accounts, accountCount, destAcc);
                        
                        System.out.print("Enter amount to transfer: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();

                        src.transfer(dest, amount);
                    } catch (AccountNotFoundException e) {
                        System.out.println("\n[TRANSFER FAILED] " + e.getMessage());
                    } catch (BankException e) {
                        System.out.println("\n[TRANSFER FAILED: BANK ERROR] " + e.getMessage());
                    }
                }
                case VIEW_STATEMENT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        Account acc = findAccount(accounts, accountCount, accNo);
                        System.out.println("\n" + StatementFormatter.buildStatement(acc));
                        System.out.println(String.format("5-Year Compound Projection: Rs. %.2f", acc.projectedBalance(5)));
                    } catch (AccountNotFoundException e) {
                        System.out.println("\n[ERROR] " + e.getMessage());
                    }
                }
                case VALIDATE_ACCOUNT -> {
                    System.out.print("Enter Account Number to validate: ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        Account acc = findAccount(accounts, accountCount, accNo);
                        System.out.println("\n--- Inspecting Metadata via AnnotationValidator ---");
                        String[] errors = AnnotationValidator.validate(acc);
                        if (errors.length == 0) {
                            System.out.println("[VALID] Account satisfies all metadata constraints (@Id, @Positive, @MaxLength)!");
                        } else {
                            System.out.println("[INVALID] Constraints violated (" + errors.length + "):");
                            for (String err : errors) {
                                System.out.println(" - " + err);
                            }
                        }
                    } catch (AccountNotFoundException e) {
                        System.out.println("\n[ERROR] " + e.getMessage());
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
