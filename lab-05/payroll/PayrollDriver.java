public class PayrollDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       PRACTICAL 5 - PART A2: PAYROLL SYSTEM     ");
        System.out.println("=================================================");

        Employee[] employees = new Employee[] {
            new FullTime("EMP101", "Aarav Patel", 85000.0),
            new PartTime("EMP102", "Diya Shah", 60.0, 750.0),
            new Intern("INT201", "Daksh Soni", 25000.0, "CHARUSAT University"),
            new FullTime("EMP103", "Prof. Sharma", 110000.0),
            new PartTime("EMP104", "Rohan Mehta", 45.0, 600.0),
            new Intern("INT202", "Ananya Verma", 22000.0, "Gujarat University")
        };

        double totalPayroll = 0.0;

        System.out.println(String.format("%-8s | %-16s | %-14s | %-34s", "ID", "Name", "Monthly Salary", "Special Notes / Details"));
        System.out.println("------------------------------------------------------------------------------------");

        for (Employee emp : employees) {
            double salary = emp.monthlySalary(); // Dynamic method dispatch (Polymorphism)
            totalPayroll += salary;

            String note = "";
            // Pattern matching instanceof for Java 17+
            if (emp instanceof Intern intern) {
                note = "[INTERN NOTICE] Stipend verified. Univ: " + intern.getUniversity();
            } else if (emp instanceof FullTime) {
                note = "Standard Full-Time Package";
            } else if (emp instanceof PartTime pt) {
                note = String.format("Logged %.1f hrs @ Rs. %.2f/hr", pt.getHoursWorked(), pt.getHourlyRate());
            }

            System.out.println(String.format("%-8s | %-16s | Rs. %-10.2f | %-34s", 
                emp.getId(), emp.getName(), salary, note));
        }

        System.out.println("------------------------------------------------------------------------------------");
        System.out.println(String.format("Total Monthly Payroll: Rs. %.2f across %d employees.", totalPayroll, employees.length));
        System.out.println("=================================================\n");
    }
}
