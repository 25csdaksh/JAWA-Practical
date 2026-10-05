package service;

public class BankingSession implements AutoCloseable {
    private final String sessionUser;
    private final String sessionId;
    private boolean open;

    public BankingSession(String sessionUser) {
        this.sessionUser = sessionUser;
        this.sessionId = "SES-" + System.currentTimeMillis() % 100000;
        this.open = true;
        System.out.println(String.format("   [SESSION STARTED] Secure session %s initialized for operator '%s'.", 
                sessionId, sessionUser));
    }

    public void logAudit(String operation) {
        if (!open) {
            throw new IllegalStateException("Banking session is closed.");
        }
        System.out.println(String.format("   [AUDIT LOG] %s -> %s", sessionId, operation));
    }

    @Override
    public void close() {
        this.open = false;
        System.out.println(String.format("   [SESSION CLOSED] Secure session %s closed and resources flushed safely.", sessionId));
    }
}
