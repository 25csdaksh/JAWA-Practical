package resource;

public class DatabaseConnection implements AutoCloseable {
    private final String connectionUrl;
    private boolean open;

    public DatabaseConnection(String connectionUrl) {
        this.connectionUrl = connectionUrl;
        this.open = true;
        System.out.println(String.format("   [RESOURCE OPENED] Connected to '%s' successfully.", connectionUrl));
    }

    public void executeQuery(String sql) throws Exception {
        if (!open) {
            throw new IllegalStateException("Connection is closed.");
        }
        if (sql.contains("CORRUPT")) {
            throw new RuntimeException("SQL Query Execution Failed: Table corruption detected in '" + sql + "'");
        }
        System.out.println("   [SQL EXECUTED] Query: " + sql);
    }

    @Override
    public void close() {
        this.open = false;
        System.out.println(String.format("   [RESOURCE CLOSED] Connection to '%s' safely terminated and released.", connectionUrl));
    }
}
