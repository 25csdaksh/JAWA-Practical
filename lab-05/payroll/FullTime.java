public class FullTime extends Employee {
    private final double fixedMonthlySalary;

    public FullTime(String id, String name, double fixedMonthlySalary) {
        super(id, name);
        this.fixedMonthlySalary = fixedMonthlySalary;
    }

    public double getFixedMonthlySalary() {
        return fixedMonthlySalary;
    }

    @Override
    public double monthlySalary() {
        return fixedMonthlySalary;
    }

    @Override
    public String toString() {
        return super.toString() + " (Full-Time)";
    }
}
