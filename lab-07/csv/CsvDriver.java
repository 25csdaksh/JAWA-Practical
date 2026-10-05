package csv;

public class CsvDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     PRACTICAL 7 - PART A3: CSV COLUMN MAPPER    ");
        System.out.println("=================================================");

        // Test 1: Complete Header and Data Row
        String[] headers1 = { "EMP_ID", "FULL_NAME", "DEPARTMENT", "SALARY", "IS_ACTIVE" };
        String[] row1 = { "E101", "Daksh Soni", "Software Engineering", "85000.50", "true" };

        System.out.println("\n--- [Test 1: Mapping Complete CSV Row] ---");
        System.out.println("Headers : " + java.util.Arrays.toString(headers1));
        System.out.println("Data    : " + java.util.Arrays.toString(row1));
        EmployeeRecord emp1 = CsvMapper.mapRow(EmployeeRecord.class, headers1, row1);
        System.out.println("Mapped Result: " + emp1);

        // Test 2: Incomplete Row with Missing Columns (department and is_active omitted)
        String[] headers2 = { "FULL_NAME", "EMP_ID", "SALARY" }; // Reordered + missing columns
        String[] row2 = { "Prof. Sharma", "E102", "120000.00" };

        System.out.println("\n--- [Test 2: Mapping CSV with Missing/Reordered Columns] ---");
        System.out.println("Headers : " + java.util.Arrays.toString(headers2));
        System.out.println("Data    : " + java.util.Arrays.toString(row2));
        EmployeeRecord emp2 = CsvMapper.mapRow(EmployeeRecord.class, headers2, row2);
        System.out.println("Mapped Result: " + emp2);

        System.out.println("=================================================\n");
    }
}
