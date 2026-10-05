package exception;

public class DailyLimitExceededException extends BankException {
    private final long limit;
    private final long attempted;

    public DailyLimitExceededException(String message, long limit, long attempted) {
        super(message);
        this.limit = limit;
        this.attempted = attempted;
    }

    public long getLimit() {
        return limit;
    }

    public long getAttempted() {
        return attempted;
    }
}
