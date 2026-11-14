/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    
    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }

    public double getBonus() { return bonus; }
    public void setBonus(double bonus) { this.bonus = bonus; }

    @Override
    public double calculateMonthly() {
        return this.baseSalary + this.bonus;
    }
    
}
