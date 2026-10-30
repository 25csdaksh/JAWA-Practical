package service;

public interface InterestBearing {
    double interestRate();
    long getBalance();

    default double yearlyInterest() {
        if (getBalance() <= 0) {
            return 0.0;
        }
        return getBalance() * (interestRate() / 100.0);
    }

    default double projectedBalance(int years) {
        if (getBalance() <= 0 || years <= 0) {
            return getBalance();
        }
        return getBalance() * Math.pow(1.0 + (interestRate() / 100.0), years);
    }
}
