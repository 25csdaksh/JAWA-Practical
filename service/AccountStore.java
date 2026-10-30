package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Account;

/**
 * Milestone 2 — Collections Framework Storage Engine for MiniBank.
 * Replaces legacy plain arrays with HashMap lookups, ArrayList transaction history,
 * Comparator/Comparable sorting, and TreeMap key ordering.
 */
public class AccountStore {

    // 1. Primary storage: HashMap keyed by account number (O(1) lookups)
    private final Map<String, Account> accounts;

    // 2. Ordered transaction audit log in an ArrayList
    private final ArrayList<String> transactionHistory;

    public AccountStore() {
        this.accounts = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Adds an account to the HashMap store and records an audit log entry.
     */
    public void add(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        accounts.put(account.getAccountNumber(), account);
        recordTransaction(String.format("Added account %s for owner '%s' [Balance: Rs. %d]",
                account.getAccountNumber(), account.getOwnerName(), account.getBalance()));
    }

    /**
     * Retrieves an account by its unique account number in O(1) time.
     */
    public Account get(String accountNumber) {
        if (accountNumber == null) return null;
        return accounts.get(accountNumber.trim());
    }

    /**
     * Returns an ArrayList containing all stored accounts.
     */
    public ArrayList<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    /**
     * Returns total number of accounts in store.
     */
    public int size() {
        return accounts.size();
    }

    /**
     * Checks if an account exists by account number.
     */
    public boolean contains(String accountNumber) {
        return accountNumber != null && accounts.containsKey(accountNumber.trim());
    }

    /**
     * Records an event in the ArrayList transaction history.
     */
    public void recordTransaction(String entry) {
        if (entry != null && !entry.isBlank()) {
            transactionHistory.add(entry);
        }
    }

    /**
     * Returns a defensive copy of the transaction history.
     */
    public ArrayList<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    /**
     * Returns an ArrayList<Account> sorted by balance in descending order
     * using a custom Comparator (Expected Output: 9000, 5000, 2000).
     * In case of a tie, accounts are sorted by account number.
     */
    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = new ArrayList<>(accounts.values());

        Comparator<Account> balanceComparator = (a1, a2) -> {
            int balanceComp = Long.compare(a2.getBalance(), a1.getBalance()); // Descending (highest balance first)
            if (balanceComp != 0) {
                return balanceComp;
            }
            return a1.compareTo(a2); // Secondary tie-breaker using Comparable<Account>
        };

        list.sort(balanceComparator);
        return list;
    }

    /**
     * Returns accounts sorted by account number (natural ordering via Comparable<Account>).
     */
    public ArrayList<Account> sortedByAccountNumber() {
        ArrayList<Account> list = new ArrayList<>(accounts.values());
        Collections.sort(list);
        return list;
    }

    /**
     * Supplementary Problem 1:
     * Returns all accounts with a balance strictly above the given threshold value.
     */
    public ArrayList<Account> getAccountsWithBalanceAbove(long threshold) {
        ArrayList<Account> matchingAccounts = new ArrayList<>();
        for (Account acc : accounts.values()) {
            if (acc.getBalance() > threshold) {
                matchingAccounts.add(acc);
            }
        }
        return matchingAccounts;
    }

    /**
     * Supplementary Problem 2:
     * Uses a TreeMap so the accounts are kept in sorted key (account number) order.
     */
    public TreeMap<String, Account> getAccountsInKeyOrder() {
        return new TreeMap<>(accounts);
    }

    /**
     * Clears all accounts and transaction history.
     */
    public void clear() {
        accounts.clear();
        transactionHistory.clear();
    }
}

