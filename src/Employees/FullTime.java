
package Employees;

public class FullTime extends Employee{
    public String type = "Full-time Employee";
    private double baseSalary;
    private double bonus;


    public FullTime(String ID, String fullName, String Department, int age, String Position, double baseSalary, double bonus) {
        super(ID, fullName, Department, age, Position);
        // salary 
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }
    
    public void setBaseSalary(double baseSalary) {
        if (baseSalary < 0) {
            throw new IllegalArgumentException("Base salary cannot be negative.");
        }
        this.baseSalary = baseSalary;
    }

    public void setBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Bonus cannot be negative.");
        }
        this.bonus = bonus;
    }

    public double getBaseSalary() { return baseSalary; }
    public double getBonus() { return bonus; }
    
    
    @Override
    public double calculateMonthly() {
        return this.baseSalary + this.bonus;
    }
    
}
