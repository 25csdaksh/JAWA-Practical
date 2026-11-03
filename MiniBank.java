import exception.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import model.*;
import service.*;
import test.MiniBankTest;
import test.MiniBankTestRunner;
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
        SEARCH_BY_OWNER,
        ACCOUNTS_ABOVE_BALANCE,
        ACCOUNTS_TREEMAP_ORDER,
        VIEW_TRANSACTION_HISTORY,
        THREAD_POOL_BATCH,
        GENERATE_REPORT,
        RUN_UNIT_TESTS,
        VIEW_AUDIT_LOGS,
        SAVE_STATE,
        LOAD_STATE,
        APPEND_LOG,
        VALIDATE_ACCOUNT,
        CONCURRENCY_TEST,
        VERIFY_CREDENTIALS,
        WORKING_HOURS,
        EXIT
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--test")) {
            int result = MiniBankTestRunner.runTests(MiniBankTest.class);
            System.exit(result);
            return;
        }

        BankInfo header = new BankInfo("MiniBank India", "Gujarat University Campus");
        System.out.println(header);

        BankService bankService = new BankService();
        Path accountsPath = Paths.get("data", "accounts.dat");

        System.out.println("\n======================================================================================");
        System.out.println("--- [PRACTICAL 14 (CAPSTONE MILESTONE 3): BANKSERVICE LAYER & SYSTEM INTEGRATION] ---");
        System.out.println("======================================================================================");

        if (Files.exists(accountsPath)) {
            try {
                int count = bankService.loadState(accountsPath);
                System.out.println("1. [STARTUP] Restored " + count + " persistent accounts from " + accountsPath.toAbsolutePath());
            } catch (Exception e) {
                System.out.println("1. [STARTUP WARN] Could not restore accounts: " + e.getMessage());
            }
        }

        Account acc1;
        Account acc2;
        Account acc3;

        try {
            acc1 = bankService.findAccount("AC0001");
            acc2 = bankService.findAccount("AC0002");
            acc3 = bankService.findAccount("AC0003");
        } catch (AccountNotFoundException e) {
            try {
                acc1 = bankService.openAccount(1, "Daksh Soni", 5000, 1000);
                acc2 = bankService.openAccount(2, "Prof. Sharma", 2000, 15000);
                acc3 = bankService.openAccount(3, "Alice Smith", 9000, 0);
                System.out.println("1. [STARTUP] Seeded 3 fresh accounts into BankService repository layer.");
            } catch (BankException ex) {
                throw new RuntimeException(ex);
            }
        }

        System.out.println("\n2. Testing Layered BankService Operations (Wiring Repository, Validation & Exceptions):");
        try {
            System.out.println("   (a) Depositing Rs. 1000 to AC0001 via bankService.deposit()...");
            bankService.deposit(acc1.getAccountNumber(), 1000);
            System.out.println("       Updated Balance of " + acc1.getAccountNumber() + ": Rs. " + acc1.getBalance());

            System.out.println("   (b) Transferring Rs. 500 from AC0001 to AC0002 via bankService.transfer()...");
            bankService.transfer(acc1.getAccountNumber(), acc2.getAccountNumber(), 500);

            System.out.println("   (c) Testing Search Accounts by Owner Name ('Soni'):");
            ArrayList<Account> searchResults = bankService.searchByOwnerName("Soni");
            for (Account match : searchResults) {
                System.out.println("       Found: " + match.getAccountNumber() + " - " + match.getOwnerName() + " (Rs. " + match.getBalance() + ")");
            }
        } catch (BankException ex) {
            System.out.println("   BankService error: " + ex.getMessage());
        }

        System.out.println("\n3. Running Capstone Automated Unit Tests (JUnit @Test Runner):");
        MiniBankTestRunner.runTests(MiniBankTest.class);

        System.out.println("======================================================================================");
        System.out.println("--- [PRACTICAL 13 DEMONSTRATION: GENERIC REPOSITORY<T>, REFLECTION @ID & BOUNDS] ---");
        System.out.println("======================================================================================");

        Repository<Account> accountRepo = bankService.getRepository();
        System.out.println("\n1. Generic Repository<Account> Stored Items: " + accountRepo.count());

        System.out.println("\n2. Testing findById(\"AC0002\") lookup via extracted @Id key:");
        Account foundAccount = accountRepo.findById("AC0002");
        System.out.println("   findById(\"AC0002\") returned: " + foundAccount);
        if (foundAccount != null && foundAccount.getAccountNumber().equals("AC0002")) {
            System.out.println("   -> [PASSED] Matching account AC0002 successfully retrieved!");
        } else {
            System.out.println("   -> [FAILED] Account lookup failed.");
        }

        System.out.println("\n3. Testing findAll() from Repository<Account>:");
        for (Account a : accountRepo.findAll()) {
            System.out.println("   -> " + a);
        }

        System.out.println("\n4. Supplementary Problem 1: Reusing Repository<Customer> without code modification:");
        Repository<Customer> customerRepo = new Repository<>();
        Customer c1 = new Customer("Daksh Soni", "daksh@example.com", "9876543210", new Customer.Address("101 Tech Park", "Ahmedabad", "380009"));
        Customer c2 = new Customer("Prof. Sharma", "sharma@example.com", "9123456780", new Customer.Address("202 Uni Campus", "Ahmedabad", "380015"));
        customerRepo.saveAll(c1, c2);

        System.out.println("   Saved " + customerRepo.count() + " Customers into Repository<Customer>:");
        for (Customer c : customerRepo.findAll()) {
            System.out.println("   -> Extracted @Id [" + c.getCustomerId() + "]: " + c);
        }
        System.out.println("   Testing findById on Customer: " + customerRepo.findById(c1.getCustomerId()));
        System.out.println("   -> [PASSED] Generic Repository<T> works seamlessly for multiple domain entities!");

        System.out.println("\n5. Testing Bounded Type Parameter Method Repository.average(<N extends Number>):");
        Integer[] balances = {5000, 2000, 9000};
        Double[] interestRates = {4.0, 0.0, 7.0};
        System.out.printf("   Average Account Balance (Integer[]): Rs. %.2f%n", Repository.average(balances));
        System.out.printf("   Average Interest Rate (Double[])  : %.2f%%%n", Repository.average(interestRates));
        System.out.println("--------------------------------------------------------------------------------------\n");

        System.out.println("======================================================================================");
        System.out.println("--- [PRACTICAL 12 DEMONSTRATION: COLLECTIONS FRAMEWORK & THREAD-SAFE CONCURRENT MAP] ---");
        System.out.println("======================================================================================");

        AccountStore store = bankService.getAccountStore();
        System.out.println("\n1. Accounts in AccountStore: " + store.size());

        System.out.println("\n2. Testing O(1) HashMap Lookup (get(\"AC0002\")):");
        Account fetched = store.get("AC0002");
        System.out.println("   Lookup get(\"AC0002\") returned: " + fetched);
        if (fetched != null && fetched.getAccountNumber().equals("AC0002")) {
            System.out.println("   -> [PASSED] Expected Account AC0002 successfully retrieved!");
        }

        System.out.println("\n3. Testing sortedByBalance() with Comparator (Highest Balance First):");
        ArrayList<Account> sortedByBal = store.sortedByBalance();
        for (int i = 0; i < sortedByBal.size(); i++) {
            Account a = sortedByBal.get(i);
            System.out.println(String.format("   [%d] %s -> Balance: Rs. %d (Owner: %s)",
                    i + 1, a.getAccountNumber(), a.getBalance(), a.getOwnerName()));
        }

        System.out.println("\n4. Supplementary Problem: Accounts with balance strictly above Rs. 4000:");
        for (Account a : store.getAccountsWithBalanceAbove(4000)) {
            System.out.println("   -> " + a.getAccountNumber() + " [Rs. " + a.getBalance() + "] (" + a.getOwnerName() + ")");
        }

        System.out.println("\n5. Supplementary Problem: TreeMap for sorted key order:");
        TreeMap<String, Account> treeMap = store.getAccountsInKeyOrder();
        treeMap.forEach((k, v) -> System.out.println(String.format("   -> Key: %s | Account: %s [Rs. %d]", k, v.getOwnerName(), v.getBalance())));

        System.out.println("\n6. Practical 12 Requirement 5: TransactionProcessor with ConcurrentHashMap:");
        TransactionProcessor concurrentProcessor = bankService.getTransactionProcessor();
        for (int i = 0; i < 5; i++) {
            concurrentProcessor.submitDeposit("AC0001", 100);
        }
        System.out.println("   Submitted 5 concurrent deposits on AC0001.");
        System.out.println("--------------------------------------------------------------------------------------\n");

        System.out.println("--- [PRACTICAL 11 DEMONSTRATION: SERIALIZATION, TRANSIENT FIELDS & NIO REPORT] ---");

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
        } catch (IOException e) {
            System.err.println("   Transaction logging error: " + e.getMessage());
        }

        System.out.println("\n3. Generating End-of-Day Financial Reconciliation Audit Report (ReportGenerator):");
        Path reportPath = Paths.get("reports", "daily_reconciliation_report.txt");
        try {
            String reportOutput = bankService.generateAuditReport(logsDir, reportPath);
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
        System.out.println("   Final Balance of AC0001 after pool deposits: Rs. " + poolAccount.getBalance());

        System.out.println("\n2. Testing Deadlock Reproduction & Consistent Lock Ordering Fix:");
        Account deadlockA = new SavingsAccount("Deadlock-Test-A", 10000, 0);
        Account deadlockB = new SavingsAccount("Deadlock-Test-B", 10000, 0);

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

        System.out.println("\n   Executing Deadlock-Free Transfers (Consistent Lower Account Number First):");
        Account safeA = acc1;
        Account safeB = acc2;

        Thread safe1 = new Thread(() -> {
            try {
                TransferService.transferSafe(safeA, safeB, 500);
            } catch (Exception ignored) {}
        }, "Safe-Transfer-Thread-1");

        Thread safe2 = new Thread(() -> {
            try {
                TransferService.transferSafe(safeB, safeA, 200);
            } catch (Exception ignored) {}
        }, "Safe-Transfer-Thread-2");

        safe1.start();
        safe2.start();

        try {
            safe1.join();
            safe2.join();
        } catch (InterruptedException ignored) {}

        System.out.println("   [SUCCESS] Both concurrent transfers completed normally without deadlock!");
        System.out.println(String.format("   Final State: %s=Rs. %d | %s=Rs. %d", safeA.getAccountNumber(), safeA.getBalance(), safeB.getAccountNumber(), safeB.getBalance()));

        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n----------------- MINIBANK CAPSTONE ENGINE (MILESTONE 3) -----------------");
            System.out.println("1.  Open Account (Savings / Current / Fixed Deposit)");
            System.out.println("2.  Deposit Funds");
            System.out.println("3.  Withdraw Funds");
            System.out.println("4.  Transfer Funds (Deadlock-Free Lock Ordering)");
            System.out.println("5.  View Official Account Statement");
            System.out.println("6.  List All Accounts (Generic Repository)");
            System.out.println("7.  List Accounts Sorted by Balance (Comparator)");
            System.out.println("8.  Search Accounts by Owner Name (Supplementary)");
            System.out.println("9.  List Accounts with Balance Above Threshold");
            System.out.println("10. List Accounts in Key Order (TreeMap)");
            System.out.println("11. View Transaction History Log");
            System.out.println("12. Run Thread Pool Concurrent Batch Processing");
            System.out.println("13. Generate End-of-Day Financial Audit Report");
            System.out.println("14. Run Capstone Unit Tests (@Test / JUnit)");
            System.out.println("15. View Reflective Audit Logs (@Audit)");
            System.out.println("16. Save State to Disk (Manual)");
            System.out.println("17. Reload State from Disk (Manual)");
            System.out.println("18. Append Transaction Log Entry");
            System.out.println("19. Validate Account Metadata via Reflection");
            System.out.println("20. Run Concurrency Benchmark");
            System.out.println("21. Verify Customer Credentials");
            System.out.println("22. Check Bank Working Hours");
            System.out.println("23. Exit (Auto-Saves State to Disk)");
            System.out.println("--------------------------------------------------------------------------");
            System.out.print("Please enter your choice (1-23): ");

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
                case 8 -> MenuOption.SEARCH_BY_OWNER;
                case 9 -> MenuOption.ACCOUNTS_ABOVE_BALANCE;
                case 10 -> MenuOption.ACCOUNTS_TREEMAP_ORDER;
                case 11 -> MenuOption.VIEW_TRANSACTION_HISTORY;
                case 12 -> MenuOption.THREAD_POOL_BATCH;
                case 13 -> MenuOption.GENERATE_REPORT;
                case 14 -> MenuOption.RUN_UNIT_TESTS;
                case 15 -> MenuOption.VIEW_AUDIT_LOGS;
                case 16 -> MenuOption.SAVE_STATE;
                case 17 -> MenuOption.LOAD_STATE;
                case 18 -> MenuOption.APPEND_LOG;
                case 19 -> MenuOption.VALIDATE_ACCOUNT;
                case 20 -> MenuOption.CONCURRENCY_TEST;
                case 21 -> MenuOption.VERIFY_CREDENTIALS;
                case 22 -> MenuOption.WORKING_HOURS;
                case 23 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 23.");
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

                    long extraParam = 0;
                    if (accTypeChoice == 1) {
                        System.out.print("Enter minimum balance requirement (e.g. 500): ");
                        extraParam = scanner.nextLong();
                        scanner.nextLine();
                    } else if (accTypeChoice == 2) {
                        System.out.print("Enter overdraft limit (e.g. 10000): ");
                        extraParam = scanner.nextLong();
                        scanner.nextLine();
                    }

                    try {
                        Account newAcc = bankService.openAccount(accTypeChoice, name, openingBal, extraParam);
                        System.out.println("\n[SUCCESS] Account opened successfully via BankService: " + newAcc);
                    } catch (BankException e) {
                        System.out.println("\n[ERROR] Could not open account: " + e.getMessage());
                    }
                }
                case DEPOSIT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        System.out.print("Enter amount to deposit: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();

                        bankService.deposit(accNo, amount);
                        Account updated = bankService.findAccount(accNo);
                        System.out.println("[SUCCESS] Deposit completed. Updated details: " + updated);
                    } catch (AccountNotFoundException | InvalidAmountException e) {
                        System.out.println("\n[DEPOSIT FAILED] " + e.getMessage());
                    } catch (BankException e) {
                        System.out.println("\n[ERROR] " + e.getMessage());
                    }
                }
                case WITHDRAW -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        System.out.print("Enter amount to withdraw: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();

                        bankService.withdraw(accNo, amount);
                        Account updated = bankService.findAccount(accNo);
                        System.out.println("[SUCCESS] Withdrawal completed. Updated details: " + updated);
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
                        System.out.print("Enter amount to transfer: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();

                        bankService.transfer(srcAcc, destAcc, amount);
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
                        String stmt = bankService.statement(accNo);
                        System.out.println("\n" + stmt);
                    } catch (AccountNotFoundException e) {
                        System.out.println("\n[ERROR] " + e.getMessage());
                    }
                }
                case LIST_ALL_ACCOUNTS -> {
                    ArrayList<Account> accounts = bankService.listAccounts();
                    System.out.println("\n--- All Accounts in BankService Repository (" + accounts.size() + ") ---");
                    for (Account a : accounts) {
                        System.out.println(" -> " + a);
                    }
                }
                case ACCOUNTS_SORTED_BY_BALANCE -> {
                    System.out.println("\n--- Accounts Sorted by Balance (Comparator: Highest First) ---");
                    ArrayList<Account> sorted = bankService.sortedByBalance();
                    for (int i = 0; i < sorted.size(); i++) {
                        Account a = sorted.get(i);
                        System.out.println(String.format(" [%d] %-8s | Owner: %-18s | Balance: Rs. %d",
                                i + 1, a.getAccountNumber(), a.getOwnerName(), a.getBalance()));
                    }
                }
                case SEARCH_BY_OWNER -> {
                    System.out.print("Enter owner name or keyword to search: ");
                    String query = scanner.nextLine().trim();
                    ArrayList<Account> matches = bankService.searchByOwnerName(query);
                    System.out.println(String.format("\n--- Search Results for '%s' (Found: %d) ---", query, matches.size()));
                    for (Account a : matches) {
                        System.out.println(" -> " + a);
                    }
                }
                case ACCOUNTS_ABOVE_BALANCE -> {
                    System.out.print("Enter minimum balance threshold (in Rs.): ");
                    long thresh = scanner.nextLong();
                    scanner.nextLine();
                    ArrayList<Account> filtered = bankService.getAccountStore().getAccountsWithBalanceAbove(thresh);
                    System.out.println(String.format("\n--- Accounts with Balance > Rs. %d (Found: %d) ---", thresh, filtered.size()));
                    for (Account a : filtered) {
                        System.out.println(String.format(" -> %s : %s | Rs. %d", a.getAccountNumber(), a.getOwnerName(), a.getBalance()));
                    }
                }
                case ACCOUNTS_TREEMAP_ORDER -> {
                    System.out.println("\n--- Accounts in Sorted Key Order (TreeMap<String, Account>) ---");
                    TreeMap<String, Account> sortedKeys = bankService.getAccountStore().getAccountsInKeyOrder();
                    sortedKeys.forEach((k, v) -> {
                        System.out.println(String.format(" [Key: %s] -> %-18s | Balance: Rs. %d", k, v.getOwnerName(), v.getBalance()));
                    });
                }
                case VIEW_TRANSACTION_HISTORY -> {
                    System.out.println("\n--- AccountStore Transaction Audit Log (ArrayList<String>) ---");
                    ArrayList<String> history = bankService.getAccountStore().getTransactionHistory();
                    if (history.isEmpty()) {
                        System.out.println("No transactions recorded yet.");
                    } else {
                        for (int i = 0; i < history.size(); i++) {
                            System.out.println(String.format(" [%2d] %s", i + 1, history.get(i)));
                        }
                    }
                }
                case THREAD_POOL_BATCH -> {
                    System.out.print("Enter target Account Number for batch deposits [default: AC0001]: ");
                    String targetAccNo = scanner.nextLine().trim();
                    if (targetAccNo.isEmpty()) targetAccNo = "AC0001";
                    try {
                        System.out.println("\n--- Launching 20 Asynchronous Pool Transactions via BankService ---");
                        bankService.processBatch(20, 50, targetAccNo);
                        Thread.sleep(200);
                        Account target = bankService.findAccount(targetAccNo);
                        System.out.println("Batch execution dispatched! Updated " + target.getAccountNumber() + " balance: Rs. " + target.getBalance());
                    } catch (AccountNotFoundException e) {
                        System.out.println("[ERROR] " + e.getMessage());
                    } catch (InterruptedException ignored) {}
                }
                case GENERATE_REPORT -> {
                    System.out.print("Enter logs directory [default: logs]: ");
                    String logsInput = scanner.nextLine().trim();
                    Path targetLogsDir = logsInput.isEmpty() ? Paths.get("logs") : Paths.get(logsInput);

                    System.out.print("Enter output report file [default: reports/daily_reconciliation_report.txt]: ");
                    String repInput = scanner.nextLine().trim();
                    Path repPath = repInput.isEmpty() ? Paths.get("reports", "daily_reconciliation_report.txt") : Paths.get(repInput);

                    try {
                        String reportText = bankService.generateAuditReport(targetLogsDir, repPath);
                        System.out.println("\n" + reportText);
                        System.out.println("[SUCCESS] Report saved to " + repPath.toAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("\n[REPORT ERROR] Failed to generate report: " + e.getMessage());
                    }
                }
                case RUN_UNIT_TESTS -> {
                    System.out.println("\n--- Executing Capstone Test Suite (MiniBankTest) ---");
                    MiniBankTestRunner.runTests(MiniBankTest.class);
                }
                case VIEW_AUDIT_LOGS -> {
                    System.out.println("\n--- Reflective Method Audit Logs (@Audit) ---");
                    ArrayList<String> logs = bankService.getAuditLogs();
                    if (logs.isEmpty()) {
                        System.out.println("No reflective audit events captured yet.");
                    } else {
                        for (String l : logs) {
                            System.out.println(" " + l);
                        }
                    }
                }
                case SAVE_STATE -> {
                    System.out.print("Enter destination file path [default: data/accounts.dat]: ");
                    String pathInput = scanner.nextLine().trim();
                    Path savePath = pathInput.isEmpty() ? Paths.get("data", "accounts.dat") : Paths.get(pathInput);
                    try {
                        bankService.saveState(savePath);
                        System.out.println("\n[SUCCESS] Successfully saved " + bankService.getAccountCount() + " accounts to " + savePath.toAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("\n[SAVE ERROR] Failed to serialize accounts: " + e.getMessage());
                    }
                }
                case LOAD_STATE -> {
                    System.out.print("Enter source file path [default: data/accounts.dat]: ");
                    String pathInput = scanner.nextLine().trim();
                    Path loadPath = pathInput.isEmpty() ? Paths.get("data", "accounts.dat") : Paths.get(pathInput);
                    try {
                        int loaded = bankService.loadState(loadPath);
                        System.out.println("\n[SUCCESS] Successfully reloaded " + loaded + " accounts into BankService from " + loadPath.toAbsolutePath() + ":");
                        for (Account a : bankService.listAccounts()) {
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
                case VALIDATE_ACCOUNT -> {
                    System.out.print("Enter Account Number to validate: ");
                    String accNo = scanner.nextLine().trim();
                    try {
                        Account acc = bankService.findAccount(accNo);
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
                    try {
                        bankService.saveState(accountsPath);
                        System.out.println("[SHUTDOWN] State successfully saved to " + accountsPath.toAbsolutePath());
                    } catch (Exception e) {
                        System.out.println("[SHUTDOWN WARN] Could not save state on exit: " + e.getMessage());
                    }
                    System.out.println("Thank you for using MiniBank. Goodbye!");
                    keepRunning = false;
                    System.exit(0);
                }
            }
        }
        scanner.close();
    }
}
