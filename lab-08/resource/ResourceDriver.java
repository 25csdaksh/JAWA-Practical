package resource;

public class ResourceDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  PRACTICAL 8 - PART A3: AUTO-CLOSEABLE RESOURCE ");
        System.out.println("=================================================");

        // Scenario 1: Normal execution with try-with-resources
        System.out.println("\n--- [Scenario 1: Normal Execution with Automatic Resource Cleanup] ---");
        try (DatabaseConnection db = new DatabaseConnection("jdbc:mysql://localhost:3306/bank_db")) {
            db.executeQuery("SELECT * FROM accounts WHERE status = 'ACTIVE'");
            System.out.println("   [OPERATION COMPLETE] Normal transaction finished.");
        } catch (Exception e) {
            System.out.println("   [CAUGHT EXCEPTION] " + e.getMessage());
        }
        System.out.println("End of Scenario 1.\n");

        // Scenario 2: Exception thrown inside try-with-resources block
        System.out.println("--- [Scenario 2: Exception Inside Block - Proving Resource Closes & Error Propagates] ---");
        try (DatabaseConnection db = new DatabaseConnection("jdbc:mysql://localhost:3306/audit_db")) {
            System.out.println("   [DOING WORK] Attempting query on corrupted table...");
            db.executeQuery("SELECT * FROM transactions_CORRUPT_BLOCK");
            System.out.println("   [UNREACHABLE] This line will never execute.");
        } catch (Exception e) {
            System.out.println(String.format("   [ORIGINAL EXCEPTION REPORTED] %s: %s", 
                    e.getClass().getSimpleName(), e.getMessage()));
            if (e.getSuppressed().length > 0) {
                System.out.println("   [SUPPRESSED EXCEPTION] " + e.getSuppressed()[0]);
            }
        }
        System.out.println("End of Scenario 2.");
        System.out.println("=================================================\n");
    }
}
