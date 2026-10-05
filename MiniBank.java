import exception.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        SAVE_STATE,
        LOAD_STATE,
        APPEND_LOG,
        GENERATE_REPORT,
        VALIDATE_ACCOUNT,
        THREAD_POOL_BATCH,
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

        System.out.println("\n--- [PRACTICAL 11 DEMONSTRATION: SERIALIZATION, TRANSIENT FIELDS & NIO REPORT GENERATION] ---");

        // 1. Serialization Round-Trip with StatePersister and Transient Field Validation
        System.out.println("\n1. Testing Model Persistence (StatePersister.save & load with transient field check):");
        Path accountsPath = Paths.get("data", "accounts.dat");

        // Set transient session tokens before serialization
        accounts[0].setSessionToken("SESSION-TOKEN-DAKSH-991");
        accounts[1].setSessionToken("SESSION-TOKEN-SHARMA-882");
        accounts[2].setSessionToken("SESSION-TOKEN-ALICE-773");

        System.out.println("   Original Accounts with transient session tokens:");
        for (int i = 0; i < accountCount; i++) {
            System.out.println(String.format("   -> %s [SessionToken=%s]", accounts[i], accounts[i].getSessionToken()));
        }

        try {
            // Save active accounts slice
            Account[] savedSlice = new Account[accountCount];
            System.arraycopy(accounts, 0, savedSlice, 0, accountCount);
            StatePersister.save(savedSlice, accountsPath);
            System.out.println("   [SAVED] Successfully serialized " + accountCount + " accounts to " + accountsPath.toAbsolutePath());

            // Reload accounts from file
            Account[] reloadedAccounts = StatePersister.load(accountsPath);
            System.out.println("   [LOADED] Successfully deserialized accounts from file into fresh array:");
            for (Account reloaded : reloadedAccounts) {
                System.out.println(String.format("   -> %s [SessionToken=%s]",
                        reloaded, (reloaded.getSessionToken() != null ? reloaded.getSessionToken() : "<NULL - TRANSIENT EXCLUDED>")));
            }

            // Confirm integrity
            boolean dataSurvived = (reloadedAccounts.length == accountCount) &&
                    reloadedAccounts[0].getAccountNumber().equals(accounts[0].getAccountNumber()) &&
                    reloadedAccounts[0].getBalance() == accounts[0].getBalance() &&
                    reloadedAccounts[0].getSessionToken() == null;
            System.out.println("   Persistence Validation: " + (dataSurvived ? "[PASSED - State Restored, Transient Omitted]" : "[FAILED]"));
        } catch (Exception e) {
            System.err.println("   State persistence error: " + e.getMessage());
        }

        // 2. Append-Only Transaction Logging with NIO (TransactionLog.append)
        System.out.println("\n2. Writing Append-Only Transaction Logs (TransactionLog.append):");
        Path logsDir = Paths.get("logs");
        Path branch1Log = logsDir.resolve("branch1_transactions.log");
        Path branch2Log = logsDir.resolve("branch2_transactions.log");
        Path emptyLog = logsDir.resolve("empty_branch.log");
        Path nonLogDoc = logsDir.resolve("audit_readme.txt");

        try {
            // Clean/ensure logs directory
            if (!Files.exists(logsDir)) {
                Files.createDirectories(logsDir);
            }

            // Write transaction logs
            TransactionLog.append(branch1Log, "DEPOSIT AC0001 500");
            TransactionLog.append(branch1Log, "WITHDRAW AC0002 200");
            TransactionLog.append(branch1Log, "DEPOSIT AC0003 15000");

            TransactionLog.append(branch2Log, "DEPOSIT AC0001 2500");
            TransactionLog.append(branch2Log, "WITHDRAW AC0001 300");
            TransactionLog.append(branch2Log, "WITHDRAW AC0003 5000");

            // Create an empty log file and a non-log file to test skipping logic
            Files.writeString(emptyLog, ""); // Empty file (0 bytes)
            Files.writeString(nonLogDoc, "This is an audit documentation text file, not a .log transaction file.");

            System.out.println("   Appended transactions to: " + branch1Log.getFileName() + " and " + branch2Log.getFileName());
            System.out.println("   Created edge-case test files: " + emptyLog.getFileName() + " (empty) and " + nonLogDoc.getFileName() + " (non-.log)");
        } catch (IOException e) {
            System.err.println("   Transaction logging error: " + e.getMessage());
        }

        // 3. NIO DirectoryStream Log Walk & End-of-Day Report Generation (ReportGenerator)
        System.out.println("\n3. Generating End-of-Day Financial Reconciliation Audit Report (ReportGenerator):");
        Path reportPath = Paths.get("reports", "daily_reconciliation_report.txt");
        try {
            String reportOutput = ReportGenerator.generateReport(logsDir, reportPath);
            System.out.println(reportOutput);
            System.out.println("   [REPORT SAVED] Report written to: " + reportPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("   Report generation error: " + e.getMessage());
        }

        System.out.println("--------------------------------------------------------------------------------------\n");

        System.out.println("--- [PRACTICAL 10 DEMONSTRATION: THREAD POOLS, PRODUCER-CONSUMER & DEADLOCKS] ---");

        // 1. Managed Thread Pool Batch Processing (TransactionProcessor)
        System.out.println("\n1. Testing Managed Thread Pool (TransactionProcessor with 4 Threads):");
        TransactionProcessor processor = new TransactionProcessor(4);
        Account poolAccount = accounts[0];
        System.out.println("   Submitting 12 concurrent transaction tasks to fixed thread pool...");

        for (int i = 1; i <= 12; i++) {
            final int taskId = i;
            final long depositAmt = 100;
            processor.submit(() -> {
                try {
                    poolAccount.deposit(depositAmt);
                    Thread.sleep(15);
                } catch (Exception ignored) {}
            });
        }

        processor.stop();
        processor.printExecutionStats();
        System.out.println("   Final Balance of AC0001 after 12 pool deposits: Rs. " + poolAccount.getBalance());

        // 2. Inter-Thread Coordination: Producer-Consumer with wait() and notify()
        System.out.println("\n2. Testing Producer-Consumer Transaction Buffer (wait/notify):");
        TransactionBuffer txQueue = new TransactionBuffer(3);
        Account consumerTargetAccount = accounts[1];
        int totalQueueItems = 6;

        Thread producerThread = new Thread(() -> {
            for (int i = 1; i <= totalQueueItems; i++) {
                try {
                    txQueue.produce(() -> {
                        try {
                            consumerTargetAccount.deposit(500);
                        } catch (Exception ignored) {}
                    });
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Tx-Producer");

        Thread consumerThread = new Thread(() -> {
            for (int i = 1; i <= totalQueueItems; i++) {
                try {
                    Runnable task = txQueue.consume();
                    task.run();
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Tx-Consumer");

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException ignored) {}

        System.out.println("   Producer-Consumer finished: 6 transactions processed seamlessly with 0 queue overflows/underflows.");
        System.out.println("   Target Account AC0002 Balance: Rs. " + consumerTargetAccount.getBalance());

        // 3. Deadlock Demonstration and Consistent Lock Ordering Fix
        System.out.println("\n3. Testing Deadlock Reproduction & Consistent Lock Ordering Fix:");
        Account deadlockA = new SavingsAccount("Deadlock-Test-A", 10000, 0);
        Account deadlockB = new SavingsAccount("Deadlock-Test-B", 10000, 0);

        // (a) Deadlock Reproduction Test
        System.out.println("   (a) Testing Reverse Lock Order Transfer (A -> B and B -> A)...");
        Thread deadlock1 = new Thread(() -> {
            try {
                TransferService.transferDeadlockProne(deadlockA, deadlockB, 100);
            } catch (Exception ignored) {}
        }, "Deadlock-Thread-A");

        Thread deadlock2 = new Thread(() -> {
            try {
                TransferService.transferDeadlockProne(deadlockB, deadlockA, 100);
            } catch (Exception ignored) {}
        }, "Deadlock-Thread-B");

        deadlock1.setDaemon(true);
        deadlock2.setDaemon(true);

        deadlock1.start();
        deadlock2.start();

        try {
            deadlock1.join(250);
            deadlock2.join(250);
        } catch (InterruptedException ignored) {}

        if (deadlock1.isAlive() && deadlock2.isAlive()) {
            System.out.println("   [DEADLOCK VERIFIED] Both transfer threads are frozen waiting for each other's lock!");
            System.out.println("   -> Deadlock-Thread-A State: " + deadlock1.getState() + " | Deadlock-Thread-B State: " + deadlock2.getState());
        }

        // (b) Deadlock-Free Safe Transfer with Canonical Lock Ordering
        System.out.println("\n   (b) Executing Deadlock-Free Transfers (Consistent Lower Account Number First):");
        Account safeA = accounts[0]; // AC0001
        Account safeB = accounts[1]; // AC0002

        Thread safe1 = new Thread(() -> {
            try {
                TransferService.transferSafe(safeA, safeB, 1000);
            } catch (Exception e) {
                System.out.println("Transfer 1 error: " + e.getMessage());
            }
        }, "Safe-Transfer-Thread-1");

        Thread safe2 = new Thread(() -> {
            try {
                TransferService.transferSafe(safeB, safeA, 500);
            } catch (Exception e) {
                System.out.println("Transfer 2 error: " + e.getMessage());
            }
        }, "Safe-Transfer-Thread-2");

        safe1.start();
        safe2.start();

        try {
            safe1.join();
            safe2.join();
        } catch (InterruptedException ignored) {}

        System.out.println("   [SUCCESS] Both concurrent transfers completed normally without deadlock!");
        System.out.println(String.format("   Final State: AC0001=Rs. %d | AC0002=Rs. %d", safeA.getBalance(), safeB.getBalance()));

        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n----------------- MINIBANK ENTERPRISE ENGINE -----------------");
            System.out.println("1. Open Account (Savings / Current / Fixed Deposit)");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer (Safe Deadlock-Free)");
            System.out.println("5. View Official Account Statement");
            System.out.println("6. Save Accounts to Disk (StatePersister.save)");
            System.out.println("7. Reload Accounts from Disk (StatePersister.load)");
            System.out.println("8. Append Transaction Log (TransactionLog.append)");
            System.out.println("9. Generate End-of-Day Audit Report (ReportGenerator)");
            System.out.println("10. Validate Account Metadata via Reflection");
            System.out.println("11. Run Thread Pool Batch Processing Benchmark (Practical 10)");
            System.out.println("12. Run Concurrency & Race Condition Benchmark (Practical 9)");
            System.out.println("13. Verify Customer Credentials (Static Import Validator)");
            System.out.println("14. Check Bank Working Hours");
            System.out.println("15. Exit");
            System.out.println("-------------------------------------------------------------");
            System.out.print("Please enter your choice (1-15): ");

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
                case 6 -> MenuOption.SAVE_STATE;
                case 7 -> MenuOption.LOAD_STATE;
                case 8 -> MenuOption.APPEND_LOG;
                case 9 -> MenuOption.GENERATE_REPORT;
                case 10 -> MenuOption.VALIDATE_ACCOUNT;
                case 11 -> MenuOption.THREAD_POOL_BATCH;
                case 12 -> MenuOption.CONCURRENCY_TEST;
                case 13 -> MenuOption.VERIFY_CREDENTIALS;
                case 14 -> MenuOption.WORKING_HOURS;
                case 15 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 15.");
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

                        TransferService.transferSafe(src, dest, amount);
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
                case SAVE_STATE -> {
                    System.out.print("Enter destination file path [default: data/accounts.dat]: ");
                    String pathInput = scanner.nextLine().trim();
                    Path savePath = pathInput.isEmpty() ? Paths.get("data", "accounts.dat") : Paths.get(pathInput);
                    try {
                        Account[] toSave = new Account[accountCount];
                        System.arraycopy(accounts, 0, toSave, 0, accountCount);
                        StatePersister.save(toSave, savePath);
                        System.out.println("\n[SUCCESS] Successfully saved " + accountCount + " accounts to " + savePath.toAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("\n[SAVE ERROR] Failed to serialize accounts: " + e.getMessage());
                    }
                }
                case LOAD_STATE -> {
                    System.out.print("Enter source file path [default: data/accounts.dat]: ");
                    String pathInput = scanner.nextLine().trim();
                    Path loadPath = pathInput.isEmpty() ? Paths.get("data", "accounts.dat") : Paths.get(pathInput);
                    try {
                        Account[] loaded = StatePersister.load(loadPath);
                        System.arraycopy(loaded, 0, accounts, 0, loaded.length);
                        accountCount = loaded.length;
                        System.out.println("\n[SUCCESS] Successfully reloaded " + loaded.length + " accounts from " + loadPath.toAbsolutePath() + ":");
                        for (int i = 0; i < accountCount; i++) {
                            System.out.println(" -> " + accounts[i]);
                        }
                    } catch (Exception e) {
                        System.out.println("\n[LOAD ERROR] Failed to deserialize accounts: " + e.getMessage());
                    }
                }
                case APPEND_LOG -> {
                    System.out.print("Enter log file path [default: logs/branch1_transactions.log]: ");
                    String pathInput = scanner.nextLine().trim();
                    Path logPath = pathInput.isEmpty() ? Paths.get("logs", "branch1_transactions.log") : Paths.get(pathInput);
                    System.out.print("Enter transaction entry (e.g. DEPOSIT AC0001 500): ");
                    String entry = scanner.nextLine().trim();
                    try {
                        TransactionLog.append(logPath, entry);
                        System.out.println("[SUCCESS] Appended log entry to " + logPath.toAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("\n[LOGGING ERROR] Failed to append entry: " + e.getMessage());
                    }
                }
                case GENERATE_REPORT -> {
                    System.out.print("Enter logs directory [default: logs]: ");
                    String logsInput = scanner.nextLine().trim();
                    Path targetLogsDir = logsInput.isEmpty() ? Paths.get("logs") : Paths.get(logsInput);

                    System.out.print("Enter output report file [default: reports/daily_reconciliation_report.txt]: ");
                    String repInput = scanner.nextLine().trim();
                    Path repPath = repInput.isEmpty() ? Paths.get("reports", "daily_reconciliation_report.txt") : Paths.get(repInput);

                    try {
                        String reportText = ReportGenerator.generateReport(targetLogsDir, repPath);
                        System.out.println("\n" + reportText);
                        System.out.println("[SUCCESS] Report saved to " + repPath.toAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("\n[REPORT ERROR] Failed to generate report: " + e.getMessage());
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
                case THREAD_POOL_BATCH -> {
                    System.out.println("\n--- Launching 20 Asynchronous Pool Transactions ---");
                    TransactionProcessor benchProcessor = new TransactionProcessor(4);
                    Account target = accounts[0];
                    for (int i = 0; i < 20; i++) {
                        benchProcessor.submit(() -> {
                            try {
                                target.deposit(50);
                            } catch (Exception ignored) {}
                        });
                    }
                    benchProcessor.stop();
                    benchProcessor.printExecutionStats();
                    System.out.println("Batch execution completed! AC0001 balance: Rs. " + target.getBalance());
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
                    System.exit(0);
                }
            }
        }
        scanner.close();
    }
}
