package service;

public interface InterestBearing {
    double interestRate();
    long getBalance();

    // Default method (1): calculates annual interest earned based on rate and current balance
    default double yearlyInterest() {
        if (getBalance() <= 0) {
            return 0.0;
        }
        return getBalance() * (interestRate() / 100.0);
    }

    // Supplementary default method (2): calculates compound projected balance over N years
    default double projectedBalance(int years) {
        if (getBalance() <= 0 || years <= 0) {
            return getBalance();
        }
        return getBalance() * Math.pow(1.0 + (interestRate() / 100.0), years);
    }
}
