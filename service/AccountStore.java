package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Account;

public class AccountStore {
    private final Map<String, Account> accounts;

    private final ArrayList<String> transactionHistory;

    public AccountStore() {
        this.accounts = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    public void add(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        accounts.put(account.getAccountNumber(), account);
        recordTransaction(String.format("Added account %s for owner '%s' [Balance: Rs. %d]",
                account.getAccountNumber(), account.getOwnerName(), account.getBalance()));
    }

    public Account get(String accountNumber) {
        if (accountNumber == null) return null;
        return accounts.get(accountNumber.trim());
    }

    public ArrayList<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public int size() {
        return accounts.size();
    }

    public boolean contains(String accountNumber) {
        return accountNumber != null && accounts.containsKey(accountNumber.trim());
    }

    public void recordTransaction(String entry) {
        if (entry != null && !entry.isBlank()) {
            transactionHistory.add(entry);
        }
    }

    public ArrayList<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = new ArrayList<>(accounts.values());

        Comparator<Account> balanceComparator = (a1, a2) -> {
            int balanceComp = Long.compare(a2.getBalance(), a1.getBalance());
            if (balanceComp != 0) {
                return balanceComp;
            }
            return a1.compareTo(a2);
        };

        list.sort(balanceComparator);
        return list;
    }

    public ArrayList<Account> sortedByAccountNumber() {
        ArrayList<Account> list = new ArrayList<>(accounts.values());
        Collections.sort(list);
        return list;
    }

    public ArrayList<Account> getAccountsWithBalanceAbove(long threshold) {
        ArrayList<Account> matchingAccounts = new ArrayList<>();
        for (Account acc : accounts.values()) {
            if (acc.getBalance() > threshold) {
                matchingAccounts.add(acc);
            }
        }
        return matchingAccounts;
    }

    public TreeMap<String, Account> getAccountsInKeyOrder() {
        return new TreeMap<>(accounts);
    }

    public void clear() {
        accounts.clear();
        transactionHistory.clear();
    }
}
