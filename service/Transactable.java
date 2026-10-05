package service;

public interface Transactable {
    void deposit(long amount);
    boolean withdraw(long amount);
}
