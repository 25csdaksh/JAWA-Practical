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
        CONCURRENCY_TEST,
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

        System.out.println("\n--- [PRACTICAL 9 DEMONSTRATION: MULTITHREADING & SYNCHRONIZATION] ---");

        // 1. Race Condition Demonstration: Unsynchronized Deposits
        System.out.println("\n1. Demonstrating Multi-threaded Race Condition on Account (Unsynchronized):");
        Account raceAccount = new SavingsAccount("Concurrent Test User", 0, 0);
        int numThreads = 10;
        int depositsPerThread = 1000;
        long amountPerDeposit = 1;
        long expectedTotal = numThreads * depositsPerThread * amountPerDeposit; // 10,000

        System.out.println(String.format("   Initial Balance: Rs. %d | Starting %d threads (each %d deposits of Rs. %d)",
                raceAccount.getBalance(), numThreads, depositsPerThread, amountPerDeposit));
        System.out.println("   Expected Final Balance: Rs. " + expectedTotal);

        Thread[] unsafeThreads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            unsafeThreads[i] = new Thread(
                new AccountWorker(raceAccount, depositsPerThread, amountPerDeposit, true), // unsafeMode = true
                "UnsafeWorker-" + (i + 1)
            );
        }

        // Observe thread lifecycle: NEW -> RUNNABLE -> TERMINATED
        System.out.println("   Thread state before start: " + unsafeThreads[0].getName() + " is " + unsafeThreads[0].getState());
        for (Thread t : unsafeThreads) t.start();
        System.out.println("   Thread state during execution: " + unsafeThreads[0].getName() + " is " + unsafeThreads[0].getState());

        for (Thread t : unsafeThreads) {
            try {
                t.join(); // Wait for completion
            } catch (InterruptedException ignored) {}
        }
        System.out.println("   Thread state after completion: " + unsafeThreads[0].getName() + " is " + unsafeThreads[0].getState());

        long unsafeFinalBalance = raceAccount.getBalance();
        System.out.println("   Actual Final Balance: Rs. " + unsafeFinalBalance);
        System.out.println("   Discrepancy (Lost Deposits): Rs. " + (expectedTotal - unsafeFinalBalance));
        System.out.println("   Status: " + (unsafeFinalBalance < expectedTotal ? "[RACE CONDITION OBSERVED - BALANCE WRONG]" : "[PASS]"));

        // 2. Synchronized Thread-Safe Demonstration (The Fix)
        System.out.println("\n2. Demonstrating Synchronized Thread-Safe Multi-threaded Deposits (The Fix):");
        Account syncAccount = new SavingsAccount("Sync Test User", 0, 0);

        Thread[] safeThreads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            safeThreads[i] = new Thread(
                new AccountWorker(syncAccount, depositsPerThread, amountPerDeposit, false), // synchronized
                "SafeWorker-" + (i + 1)
            );
        }

        for (Thread t : safeThreads) t.start();
        for (Thread t : safeThreads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {}
        }

        long safeFinalBalance = syncAccount.getBalance();
        System.out.println("   Actual Final Balance: Rs. " + safeFinalBalance);
        System.out.println("   Discrepancy: Rs. " + (expectedTotal - safeFinalBalance));
        System.out.println("   Status: " + (safeFinalBalance == expectedTotal ? "[THREAD-SAFE - EXACT 10,000 GUARANTEED]" : "[FAILED]"));

        // 3. Supplementary Test: Mixed Concurrent Deposits and Withdrawals
        System.out.println("\n3. Supplementary Test: Concurrent Mixed Deposits & Withdrawals:");
        Account mixedAccount = new SavingsAccount("Mixed Concurrency User", 5000, 0);
        System.out.println("   Initial Balance: Rs. " + mixedAccount.getBalance());

        Thread[] mixedThreads = new Thread[10];
        // 5 threads depositing Rs. 1,000 each (Total +5,000)
        for (int i = 0; i < 5; i++) {
            mixedThreads[i] = new Thread(
                new AccountWorker(mixedAccount, 1, 1000, AccountWorker.Operation.DEPOSIT, false),
                "DepositWorker-" + (i + 1)
            );
        }
        // 5 threads withdrawing Rs. 500 each (Total -2,500)
        for (int i = 5; i < 10; i++) {
            mixedThreads[i] = new Thread(
                new AccountWorker(mixedAccount, 1, 500, AccountWorker.Operation.WITHDRAW, false),
                "WithdrawWorker-" + (i - 4)
            );
        }

        for (Thread t : mixedThreads) t.start();
        for (Thread t : mixedThreads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {}
        }

        long expectedMixedBalance = 5000 + (5 * 1000) - (5 * 500); // 7500
        System.out.println(String.format("   Expected Balance: Rs. %d | Actual Balance: Rs. %d -> %s",
                expectedMixedBalance, mixedAccount.getBalance(), 
                (mixedAccount.getBalance() == expectedMixedBalance ? "[EXACT MATCH]" : "[MISMATCH]")));

        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n----------------- MINIBANK ENTERPRISE MENU -----------------");
            System.out.println("1. Open Account (Savings / Current / Fixed Deposit)");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer (Local)");
            System.out.println("5. View Official Account Statement");
            System.out.println("6. Validate Account Metadata via Reflection");
            System.out.println("7. Run Concurrency & Race Condition Benchmark (Practical 9)");
            System.out.println("8. Verify Customer Credentials (Static Import Validator)");
            System.out.println("9. Check Bank Working Hours");
            System.out.println("10. Exit");
            System.out.println("-----------------------------------------------------------");
            System.out.print("Please enter your choice (1-10): ");

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
                case 7 -> MenuOption.CONCURRENCY_TEST;
                case 8 -> MenuOption.VERIFY_CREDENTIALS;
                case 9 -> MenuOption.WORKING_HOURS;
                case 10 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 10.");
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
                case CONCURRENCY_TEST -> {
                    System.out.println("\n--- Running Live Multithreading Concurrency Benchmark ---");
                    Account benchAcc = new SavingsAccount("Stress Test Account", 0, 0);
                    Thread[] ths = new Thread[10];
                    for (int i = 0; i < 10; i++) {
                        ths[i] = new Thread(new AccountWorker(benchAcc, 1000, 1, false), "WorkerThread-" + (i + 1));
                        ths[i].start();
                    }
                    for (Thread t : ths) {
                        try { t.join(); } catch (InterruptedException ignored) {}
                    }
                    System.out.println("10 threads x 1,000 synchronized deposits completed!");
                    System.out.println("Final Balance: Rs. " + benchAcc.getBalance() + " (Expected: Rs. 10000) -> " 
                            + (benchAcc.getBalance() == 10000 ? "[PASS - THREAD SAFE]" : "[FAIL]"));
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
