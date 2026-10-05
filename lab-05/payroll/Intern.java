public class Intern extends Employee {
    private final double monthlyStipend;
    private final String university;

    public Intern(String id, String name, double monthlyStipend, String university) {
        super(id, name);
        this.monthlyStipend = monthlyStipend;
        this.university = university;
    }

    public Intern(String id, String name, double monthlyStipend) {
        this(id, name, monthlyStipend, "CHARUSAT");
    }

    public double getMonthlyStipend() {
        return monthlyStipend;
    }

    public String getUniversity() {
        return university;
    }

    @Override
    public double monthlySalary() {
        return monthlyStipend;
    }

    @Override
    public String toString() {
        return super.toString() + " (Intern from " + university + ")";
    }
}
