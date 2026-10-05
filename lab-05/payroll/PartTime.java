public class PartTime extends Employee {
    private final double hoursWorked;
    private final double hourlyRate;

    public PartTime(String id, String name, double hoursWorked, double hourlyRate) {
        super(id, name);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public double monthlySalary() {
        return hoursWorked * hourlyRate;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (Part-Time: %.1f hrs @ Rs. %.2f/hr)", hoursWorked, hourlyRate);
    }
}
