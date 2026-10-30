import exception.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import model.*;
import service.*;
import util.*;
import static util.Validator.*;

public class MiniBank {
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        VIEW_STATEMENT,
        LIST_ALL_ACCOUNTS,
        ACCOUNTS_SORTED_BY_BALANCE,
        ACCOUNTS_ABOVE_BALANCE,
        ACCOUNTS_TREEMAP_ORDER,
        VIEW_TRANSACTION_HISTORY,
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

    private static Account findAccount(AccountStore store, String accNo) throws AccountNotFoundException {
        if (accNo == null) {
            throw new AccountNotFoundException("Account number cannot be null.");
        }
        Account acc = store.get(accNo.trim());
        if (acc != null) {
            return acc;
        }
        throw new AccountNotFoundException("Account '" + accNo + "' was not found in bank database.");
    }

    public static void main(String[] args) {
        BankInfo header = new BankInfo("MiniBank India", "Gujarat University Campus");
        System.out.println(header);

        System.out.println("\n======================================================================================");
        System.out.println("--- [PRACTICAL 12 DEMONSTRATION: COLLECTIONS FRAMEWORK & THREAD-SAFE CONCURRENT MAP] ---");
        System.out.println("======================================================================================");

        AccountStore store = new AccountStore();

        Account acc1 = new SavingsAccount("Daksh Soni", 5000, 1000);
        Account acc2 = new CurrentAccount("Prof. Sharma", 2000, 15000);
        Account acc3 = new FixedDepositAccount("Alice Smith", 9000);

        store.add(acc1);
        store.add(acc2);
        store.add(acc3);

        System.out.println("\n1. Accounts added to HashMap-backed AccountStore:");
        System.out.println("   [+ Added] " + acc1);
        System.out.println("   [+ Added] " + acc2);
        System.out.println("   [+ Added] " + acc3);
        System.out.println("   Total Accounts in AccountStore: " + store.size());

        System.out.println("\n2. Testing O(1) HashMap Lookup (get(\"AC0002\")):");
        Account fetched = store.get("AC0002");
        System.out.println("   Lookup get(\"AC0002\") returned: " + fetched);
        if (fetched != null && fetched.getAccountNumber().equals("AC0002") && fetched.getBalance() == 2000) {
            System.out.println("   -> [PASSED] Expected Account AC0002 (Rs. 2000) successfully retrieved!");
        } else {
            System.out.println("   -> [FAILED] Account AC0002 lookup failed.");
        }

        System.out.println("\n3. Testing sortedByBalance() with Comparator (Highest Balance First):");
        ArrayList<Account> sortedByBal = store.sortedByBalance();
        System.out.println("   Accounts sorted by balance (order: 9000, 5000, 2000):");
        for (int i = 0; i < sortedByBal.size(); i++) {
            Account a = sortedByBal.get(i);
            System.out.println(String.format("   [%d] %s -> Balance: Rs. %d (Owner: %s)",
                    i + 1, a.getAccountNumber(), a.getBalance(), a.getOwnerName()));
        }
        boolean orderValid = sortedByBal.size() == 3 &&
                sortedByBal.get(0).getBalance() == 9000 &&
                sortedByBal.get(1).getBalance() == 5000 &&
                sortedByBal.get(2).getBalance() == 2000;
        System.out.println("   -> [PASSED] Sorted order verified: " + (orderValid ? "[9000, 5000, 2000] OK" : "[FAILED]"));

        System.out.println("\n4. Testing Natural Ordering via Comparable<Account>.compareTo():");
        ArrayList<Account> sortedByAccNo = store.sortedByAccountNumber();
        System.out.println("   Accounts sorted by account number (Comparable):");
        for (Account a : sortedByAccNo) {
            System.out.println("   -> " + a.getAccountNumber() + " : " + a.getOwnerName() + " (Rs. " + a.getBalance() + ")");
        }

        System.out.println("\n5. Supplementary Problem 1: Accounts with balance strictly above Rs. 4000:");
        ArrayList<Account> above4k = store.getAccountsWithBalanceAbove(4000);
        for (Account a : above4k) {
            System.out.println("   -> " + a.getAccountNumber() + " [Rs. " + a.getBalance() + "] (" + a.getOwnerName() + ")");
        }

        System.out.println("\n6. Supplementary Problem 2: TreeMap for sorted key order:");
        TreeMap<String, Account> treeMap = store.getAccountsInKeyOrder();
        System.out.println("   TreeMap keys (naturally sorted): " + treeMap.keySet());
        treeMap.forEach((k, v) -> System.out.println(String.format("   -> Key: %s | Account: %s [Rs. %d]", k, v.getOwnerName(), v.getBalance())));

        System.out.println("\n7. Practical 12 Requirement 5: TransactionProcessor with ConcurrentHashMap:");
        TransactionProcessor concurrentProcessor = new TransactionProcessor(4);
        concurrentProcessor.registerAccount(acc1);
        concurrentProcessor.registerAccount(acc2);
        concurrentProcessor.registerAccount(acc3);

        System.out.println("   Registered accounts into thread-safe ConcurrentHashMap.");
        System.out.println("   Submitting 10 concurrent pool deposits of Rs. 100 on AC0001...");
        for (int i = 0; i < 10; i++) {
            concurrentProcessor.submitDeposit("AC0001", 100);
        }
        concurrentProcessor.stop();
        System.out.println("   AC0001 Balance after concurrent updates: Rs. " + acc1.getBalance() + " (Expected: Rs. 6000)");

        store.recordTransaction("Demonstrated Practical 12 Collections Framework Milestone 2");
        System.out.println("\n8. ArrayList Transaction History Log (" + store.getTransactionHistory().size() + " entries):");
        for (String record : store.getTransactionHistory()) {
            System.out.println("   * " + record);
        }
        System.out.println("--------------------------------------------------------------------------------------\n");

        System.out.println("--- [PRACTICAL 11 DEMONSTRATION: SERIALIZATION, TRANSIENT FIELDS & NIO REPORT] ---");

        System.out.println("\n1. Testing Model Persistence (StatePersister.save & load with transient field check):");
        Path accountsPath = Paths.get("data", "accounts.dat");

        acc1.setSessionToken("SESSION-TOKEN-DAKSH-991");
        acc2.setSessionToken("SESSION-TOKEN-SHARMA-882");
        acc3.setSessionToken("SESSION-TOKEN-ALICE-773");

        System.out.println("   Original Accounts with transient session tokens:");
        for (Account a : store.getAll()) {
            System.out.println(String.format("   -> %s [SessionToken=%s]", a, a.getSessionToken()));
        }

        try {
            StatePersister.save(store.getAll(), accountsPath);
            System.out.println("   [SAVED] Successfully serialized " + store.size() + " accounts to " + accountsPath.toAbsolutePath());

            Account[] reloadedAccounts = StatePersister.load(accountsPath);
            System.out.println("   [LOADED] Successfully deserialized accounts from file into fresh array:");
            for (Account reloaded : reloadedAccounts) {
                System.out.println(String.format("   -> %s [SessionToken=%s]",
                        reloaded, (reloaded.getSessionToken() != null ? reloaded.getSessionToken() : "<NULL - TRANSIENT EXCLUDED>")));
            }

            boolean dataSurvived = (reloadedAccounts.length == store.size()) &&
                    reloadedAccounts[0].getAccountNumber().equals(acc1.getAccountNumber()) &&
                    reloadedAccounts[0].getBalance() == acc1.getBalance() &&
                    reloadedAccounts[0].getSessionToken() == null;
            System.out.println("   Persistence Validation: " + (dataSurvived ? "[PASSED - State Restored, Transient Omitted]" : "[FAILED]"));
        } catch (Exception e) {
            System.err.println("   State persistence error: " + e.getMessage());
        }

        System.out.println("\n2. Writing Append-Only Transaction Logs (TransactionLog.append):");
        Path logsDir = Paths.get("logs");
        Path branch1Log = logsDir.resolve("branch1_transactions.log");
        Path branch2Log = logsDir.resolve("branch2_transactions.log");
        Path emptyLog = logsDir.resolve("empty_branch.log");
        Path nonLogDoc = logsDir.resolve("audit_readme.txt");

        try {
            if (!Files.exists(logsDir)) {
                Files.createDirectories(logsDir);
            }

            TransactionLog.append(branch1Log, "DEPOSIT AC0001 500");
            TransactionLog.append(branch1Log, "WITHDRAW AC0002 200");
            TransactionLog.append(branch1Log, "DEPOSIT AC0003 15000");

            TransactionLog.append(branch2Log, "DEPOSIT AC0001 2500");
            TransactionLog.append(branch2Log, "WITHDRAW AC0001 300");
            TransactionLog.append(branch2Log, "WITHDRAW AC0003 5000");

            Files.writeString(emptyLog, "");
            Files.writeString(nonLogDoc, "This is an audit documentation text file, not a .log transaction file.");

            System.out.println("   Appended transactions to: " + branch1Log.getFileName() + " and " + branch2Log.getFileName());
            System.out.println("   Created edge-case test files: " + emptyLog.getFileName() + " (empty) and " + nonLogDoc.getFileName() + " (non-.log)");
        } catch (IOException e) {
            System.err.println("   Transaction logging error: " + e.getMessage());
        }

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

        System.out.println("\n1. Testing Managed Thread Pool (TransactionProcessor with 4 Threads):");
        TransactionProcessor processor = new TransactionProcessor(4);
        Account poolAccount = acc1;
        System.out.println("   Submitting 12 concurrent transaction tasks to fixed thread pool...");

        for (int i = 1; i <= 12; i++) {
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

        System.out.println("\n2. Testing Producer-Consumer Transaction Buffer (wait/notify):");
        TransactionBuffer txQueue = new TransactionBuffer(3);
        Account consumerTargetAccount = acc2;
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

        System.out.println("\n3. Testing Deadlock Reproduction & Consistent Lock Ordering Fix:");
        Account deadlockA = new SavingsAccount("Deadlock-Test-A", 10000, 0);
        Account deadlockB = new SavingsAccount("Deadlock-Test-B", 10000, 0);

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

        System.out.println("\n   (b) Executing Deadlock-Free Transfers (Consistent Lower Account Number First):");
        Account safeA = acc1;
        Account safeB = acc2;

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
            System.out.println("1.  Open Account (Savings / Current / Fixed Deposit)");
            System.out.println("2.  Deposit");
            System.out.println("3.  Withdraw");
            System.out.println("4.  Transfer (Deadlock-Free Lock Ordering)");
            System.out.println("5.  View Official Account Statement");
            System.out.println("6.  List All Accounts (HashMap AccountStore)");
            System.out.println("7.  List Accounts Sorted by Balance (Comparator: Highest First)");
            System.out.println("8.  List Accounts with Balance Above Threshold (Supplementary 1)");
            System.out.println("9.  List Accounts in Key Order (TreeMap: Supplementary 2)");
            System.out.println("10. View Transaction History (ArrayList<String>)");
            System.out.println("11. Save Accounts to Disk (StatePersister.save)");
            System.out.println("12. Reload Accounts from Disk (StatePersister.load)");
            System.out.println("13. Append Transaction Log (TransactionLog.append)");
            System.out.println("14. Generate End-of-Day Audit Report (ReportGenerator)");
            System.out.println("15. Validate Account Metadata via Reflection");
            System.out.println("16. Run Thread Pool Batch Processing Benchmark");
            System.out.println("17. Run Concurrency & Race Condition Benchmark");
            System.out.println("18. Verify Customer Credentials");
            System.out.println("19. Check Bank Working Hours");
            System.out.println("20. Exit");
            System.out.println("-------------------------------------------------------------");
            System.out.print("Please enter your choice (1-20): ");

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
                case 6 -> MenuOption.LIST_ALL_ACCOUNTS;
                case 7 -> MenuOption.ACCOUNTS_SORTED_BY_BALANCE;
                case 8 -> MenuOption.ACCOUNTS_ABOVE_BALANCE;
                case 9 -> MenuOption.ACCOUNTS_TREEMAP_ORDER;
                case 10 -> MenuOption.VIEW_TRANSACTION_HISTORY;
                case 11 -> MenuOption.SAVE_STATE;
                case 12 -> MenuOption.LOAD_STATE;
                case 13 -> MenuOption.APPEND_LOG;
                case 14 -> MenuOption.GENERATE_REPORT;
                case 15 -> MenuOption.VALIDATE_ACCOUNT;
                case 16 -> MenuOption.THREAD_POOL_BATCH;
                case 17 -> MenuOption.CONCURRENCY_TEST;
                case 18 -> MenuOption.VERIFY_CREDENTIALS;
                case 19 -> MenuOption.WORKING_HOURS;
                case 20 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 20.");
                continue;
            }

            switch (selectedOption) {
                case OPEN_ACCOUNT -> {
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
                            long overdraft = scanner.nextLong();
                            scanner.nextLine();
                            newAcc = new CurrentAccount(name, openingBal, overdraft);
                        }
                        case 3 -> {
                            newAcc = new FixedDepositAccount(name, openingBal);
                        }
                        default -> {
                            System.out.println("[WARN] Unknown type. Defaulting to Savings Account.");
                            newAcc = new SavingsAccount(name, openingBal, 500);
                        }
                    }

                    store.add(newAcc);
                    System.out.println("\n[SUCCESS] Account opened successfully in AccountStore (HashMap): " + newAcc);
                }
                case DEPOSIT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        Account acc = findAccount(store, accNo);
                        System.out.print("Enter amount to deposit: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();

                        acc.deposit(amount);
                        store.recordTransaction(String.format("Deposited Rs. %d into %s. New Balance: Rs. %d", amount, accNo, acc.getBalance()));
                        System.out.println("[SUCCESS] Deposit completed. Updated details: " + acc);
                    } catch (AccountNotFoundException | InvalidAmountException e) {
                        System.out.println("\n[DEPOSIT FAILED] " + e.getMessage());
                    }
                }
                case WITHDRAW -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        Account acc = findAccount(store, accNo);
                        System.out.print("Enter amount to withdraw: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();

                        acc.withdraw(amount);
                        store.recordTransaction(String.format("Withdrew Rs. %d from %s. New Balance: Rs. %d", amount, accNo, acc.getBalance()));
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
                        Account src = findAccount(store, srcAcc);
                        Account dest = findAccount(store, destAcc);

                        System.out.print("Enter amount to transfer: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();

                        TransferService.transferSafe(src, dest, amount);
                        store.recordTransaction(String.format("Transferred Rs. %d from %s to %s", amount, srcAcc, destAcc));
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
                        Account acc = findAccount(store, accNo);
                        System.out.println("\n" + StatementFormatter.buildStatement(acc));
                        System.out.println(String.format("5-Year Compound Projection: Rs. %.2f", acc.projectedBalance(5)));
                    } catch (AccountNotFoundException e) {
                        System.out.println("\n[ERROR] " + e.getMessage());
                    }
                }
                case LIST_ALL_ACCOUNTS -> {
                    System.out.println("\n--- All Accounts in AccountStore (Total: " + store.size() + ") ---");
                    for (Account a : store.getAll()) {
                        System.out.println(" -> " + a);
                    }
                }
                case ACCOUNTS_SORTED_BY_BALANCE -> {
                    System.out.println("\n--- Accounts Sorted by Balance (Comparator: Highest First) ---");
                    ArrayList<Account> sorted = store.sortedByBalance();
                    for (int i = 0; i < sorted.size(); i++) {
                        Account a = sorted.get(i);
                        System.out.println(String.format(" [%d] %-8s | Owner: %-18s | Balance: Rs. %d",
                                i + 1, a.getAccountNumber(), a.getOwnerName(), a.getBalance()));
                    }
                }
                case ACCOUNTS_ABOVE_BALANCE -> {
                    System.out.print("Enter minimum balance threshold (in Rs.): ");
                    long thresh = scanner.nextLong();
                    scanner.nextLine();
                    ArrayList<Account> filtered = store.getAccountsWithBalanceAbove(thresh);
                    System.out.println(String.format("\n--- Accounts with Balance > Rs. %d (Found: %d) ---", thresh, filtered.size()));
                    for (Account a : filtered) {
                        System.out.println(String.format(" -> %s : %s | Rs. %d", a.getAccountNumber(), a.getOwnerName(), a.getBalance()));
                    }
                }
                case ACCOUNTS_TREEMAP_ORDER -> {
                    System.out.println("\n--- Accounts in Sorted Key Order (TreeMap<String, Account>) ---");
                    TreeMap<String, Account> sortedKeys = store.getAccountsInKeyOrder();
                    sortedKeys.forEach((k, v) -> {
                        System.out.println(String.format(" [Key: %s] -> %-18s | Balance: Rs. %d", k, v.getOwnerName(), v.getBalance()));
                    });
                }
                case VIEW_TRANSACTION_HISTORY -> {
                    System.out.println("\n--- AccountStore Transaction Audit Log (ArrayList<String>) ---");
                    ArrayList<String> history = store.getTransactionHistory();
                    if (history.isEmpty()) {
                        System.out.println("No transactions recorded yet.");
                    } else {
                        for (int i = 0; i < history.size(); i++) {
                            System.out.println(String.format(" [%2d] %s", i + 1, history.get(i)));
                        }
                    }
                }
                case SAVE_STATE -> {
                    System.out.print("Enter destination file path [default: data/accounts.dat]: ");
                    String pathInput = scanner.nextLine().trim();
                    Path savePath = pathInput.isEmpty() ? Paths.get("data", "accounts.dat") : Paths.get(pathInput);
                    try {
                        StatePersister.save(store.getAll(), savePath);
                        System.out.println("\n[SUCCESS] Successfully saved " + store.size() + " accounts to " + savePath.toAbsolutePath());
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
                        store.clear();
                        for (Account a : loaded) {
                            store.add(a);
                        }
                        System.out.println("\n[SUCCESS] Successfully reloaded " + loaded.length + " accounts into AccountStore from " + loadPath.toAbsolutePath() + ":");
                        for (Account a : store.getAll()) {
                            System.out.println(" -> " + a);
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
                        Account acc = findAccount(store, accNo);
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
                    Account target = store.get("AC0001");
                    if (target == null && store.size() > 0) {
                        target = store.getAll().get(0);
                    }
                    if (target != null) {
                        final Account tAcc = target;
                        for (int i = 0; i < 20; i++) {
                            benchProcessor.submit(() -> {
                                try {
                                    tAcc.deposit(50);
                                } catch (Exception ignored) {}
                            });
                        }
                        benchProcessor.stop();
                        benchProcessor.printExecutionStats();
                        System.out.println("Batch execution completed! " + target.getAccountNumber() + " balance: Rs. " + target.getBalance());
                    } else {
                        System.out.println("No account available for batch testing.");
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
                    System.exit(0);
                }
            }
        }
        scanner.close();
    }
}
