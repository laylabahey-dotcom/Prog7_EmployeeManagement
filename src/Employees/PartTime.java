package Employees;

public class PartTime extends Employee {

    public String type = "Part-time Employee";
    private int workhours;
    private double payPerHour;

    public PartTime(int ID, String fullName, String Department, int age,
            String Position, double payPerHour,
            int workhours) {
        super(ID, fullName, Department, age, Position);
        //salary 
        setPayPerHour(payPerHour);
        setWorkHours(workhours);

    }

    public void setWorkHours(int workHours) {
        if (workHours < 0 || workHours > 744) { // Max hours in a 31-day month
            throw new IllegalArgumentException("Work hours must be between 0 and 744.");
        }
        this.workhours = workHours;
    }

    public void setPayPerHour(double payPerHour) {
        if (payPerHour < 0) {
            throw new IllegalArgumentException("Pay per hour cannot be negative.");
        }
        this.payPerHour = payPerHour;
    }

    public int getWorkHours() {
        return workhours;
    }

    public double getPayPerHour() {
        return payPerHour;
    }

    @Override
    public double calculateMonthly() {
        return this.payPerHour * this.workhours;
    }

}
