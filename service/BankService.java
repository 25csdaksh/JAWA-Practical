package service;

import exception.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.*;
import model.annotation.Audit;
import util.ReportGenerator;
import util.StatePersister;
import util.StatementFormatter;

public class BankService {
    private final Repository<Account> repository;
    private final AccountStore accountStore;
    private final TransactionProcessor transactionProcessor;
    private final ArrayList<String> auditLogs = new ArrayList<>();

    public BankService() {
        this(new Repository<>(), new AccountStore(), new TransactionProcessor(4));
    }

    public BankService(Repository<Account> repository, AccountStore accountStore, TransactionProcessor processor) {
        this.repository = repository;
        this.accountStore = accountStore;
        this.transactionProcessor = processor;
    }

    @Audit(action = "OPEN_ACCOUNT")
    public Account openAccount(int typeChoice, String ownerName, long initialBalance, long extraParam) throws BankException {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new BankException("Owner name cannot be null or empty.");
        }
        if (initialBalance < 0) {
            throw new InvalidAmountException("Initial balance cannot be negative: " + initialBalance);
        }

        Account account = switch (typeChoice) {
            case 1 -> new SavingsAccount(ownerName.trim(), initialBalance, extraParam > 0 ? extraParam : 500);
            case 2 -> new CurrentAccount(ownerName.trim(), initialBalance, extraParam > 0 ? extraParam : 5000);
            case 3 -> new FixedDepositAccount(ownerName.trim(), initialBalance);
            default -> new SavingsAccount(ownerName.trim(), initialBalance, 500);
        };

        repository.save(account);
        accountStore.add(account);
        transactionProcessor.registerAccount(account);

        String entry = AuditLogger.log(BankService.class, "openAccount", account.getAccountNumber(), ownerName, initialBalance);
        auditLogs.add(entry);
        return account;
    }

    @Audit(action = "DEPOSIT")
    public void deposit(String accountNumber, long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be strictly positive: " + amount);
        }
        Account account = findAccount(accountNumber);
        account.deposit(amount);
        accountStore.recordTransaction(String.format("Deposited Rs. %d to %s", amount, accountNumber));

        String entry = AuditLogger.log(BankService.class, "deposit", accountNumber, amount);
        auditLogs.add(entry);
    }

    @Audit(action = "WITHDRAW")
    public void withdraw(String accountNumber, long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be strictly positive: " + amount);
        }
        Account account = findAccount(accountNumber);
        account.withdraw(amount);
        accountStore.recordTransaction(String.format("Withdrew Rs. %d from %s", amount, accountNumber));

        String entry = AuditLogger.log(BankService.class, "withdraw", accountNumber, amount);
        auditLogs.add(entry);
    }

    @Audit(action = "TRANSFER")
    public void transfer(String fromAccNo, String toAccNo, long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be strictly positive: " + amount);
        }
        Account from = findAccount(fromAccNo);
        Account to = findAccount(toAccNo);

        TransferService.transferSafe(from, to, amount);
        accountStore.recordTransaction(String.format("Transferred Rs. %d from %s to %s", amount, fromAccNo, toAccNo));

        String entry = AuditLogger.log(BankService.class, "transfer", fromAccNo, toAccNo, amount);
        auditLogs.add(entry);
    }

    @Audit(action = "STATEMENT")
    public String statement(String accountNumber) throws AccountNotFoundException {
        Account account = findAccount(accountNumber);
        String entry = AuditLogger.log(BankService.class, "statement", accountNumber);
        auditLogs.add(entry);
        return StatementFormatter.buildStatement(account);
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        if (accountNumber == null) {
            throw new AccountNotFoundException("Account number cannot be null.");
        }
        Account account = repository.findById(accountNumber.trim());
        if (account == null) {
            account = accountStore.get(accountNumber.trim());
        }
        if (account == null) {
            throw new AccountNotFoundException("Account '" + accountNumber + "' not found in banking system.");
        }
        return account;
    }

    public ArrayList<Account> listAccounts() {
        return repository.findAll();
    }

    public ArrayList<Account> sortedByBalance() {
        return accountStore.sortedByBalance();
    }

    public ArrayList<Account> searchByOwnerName(String name) {
        ArrayList<Account> matching = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return matching;
        }
        String query = name.trim().toLowerCase();
        for (Account a : repository.findAll()) {
            if (a.getOwnerName().toLowerCase().contains(query)) {
                matching.add(a);
            }
        }
        return matching;
    }

    public void processBatch(int taskCount, long depositAmount, String targetAccNo) throws AccountNotFoundException {
        Account target = findAccount(targetAccNo);
        for (int i = 0; i < taskCount; i++) {
            transactionProcessor.submit(() -> {
                try {
                    target.deposit(depositAmount);
                } catch (Exception ignored) {}
            });
        }
    }

    public String generateAuditReport(Path logsDir, Path reportPath) throws IOException {
        return ReportGenerator.generateReport(logsDir, reportPath);
    }

    public void saveState(Path file) throws IOException {
        StatePersister.save(listAccounts(), file);
    }

    public int loadState(Path file) throws Exception {
        Account[] loaded = StatePersister.load(file);
        repository.clear();
        accountStore.clear();
        for (Account a : loaded) {
            repository.save(a);
            accountStore.add(a);
            transactionProcessor.registerAccount(a);
        }
        return loaded.length;
    }

    public int getAccountCount() {
        return repository.count();
    }

    public Repository<Account> getRepository() {
        return repository;
    }

    public AccountStore getAccountStore() {
        return accountStore;
    }

    public TransactionProcessor getTransactionProcessor() {
        return transactionProcessor;
    }

    public ArrayList<String> getAuditLogs() {
        return new ArrayList<>(auditLogs);
    }
}
