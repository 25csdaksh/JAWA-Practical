import java.time.LocalDate;

public class FixedDepositAccount extends Account {
    private final LocalDate maturityDate;
    private boolean matured;

    public FixedDepositAccount(String ownerName, long balance, LocalDate maturityDate) {
        super(ownerName, balance);
        this.maturityDate = maturityDate;
        this.matured = (maturityDate != null && !LocalDate.now().isBefore(maturityDate));
    }

    public FixedDepositAccount(String ownerName, long balance) {
        this(ownerName, balance, LocalDate.now().plusYears(1)); // Default 1 year lock-in
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public boolean isMatured() {
        return matured || (maturityDate != null && !LocalDate.now().isBefore(maturityDate));
    }

    public void setMatured(boolean matured) {
        this.matured = matured;
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    // Supplementary: allows withdrawal only if the deposit has reached or passed maturity date
    @Override
    public boolean canWithdraw(long amount) {
        if (!isMatured()) {
            System.out.println("[LOCK NOTICE] Fixed Deposit is locked until maturity date: " + maturityDate);
            return false;
        }
        return getBalance() >= amount;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [MaturityDate=%s, Matured=%b]", maturityDate, isMatured());
    }
}
