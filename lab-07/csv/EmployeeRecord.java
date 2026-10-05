package csv;

public class EmployeeRecord {
    @Column(name = "emp_id", required = true)
    private String id;

    @Column(name = "full_name", required = true)
    private String name;

    @Column(name = "department")
    private String department = "General"; // Default if missing

    @Column(name = "salary")
    private double salary = 0.0;

    @Column(name = "is_active")
    private boolean active = true;

    public EmployeeRecord() {}

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return String.format("EmployeeRecord[ID=%s, Name=%s, Dept=%s, Salary=Rs. %.2f, Active=%b]",
                id, name, department, salary, active);
    }
}
