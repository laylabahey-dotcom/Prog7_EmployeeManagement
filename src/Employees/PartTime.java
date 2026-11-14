/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Employees;

/**
 *
 * @author layla-bahey
 */
public class PartTime extends Employee{
    public String type = "Part-time Employee";
    private int workhours;
    private double payPerHour;

    public PartTime(String ID, String fullName, String Department, int age, 
            String Position, int Workhours, double payPerHour,
            int workhours) {
        super(ID, fullName, Department, age, Position);
        //salary 
        this.payPerHour = payPerHour;
        this.workhours = workhours;
        
    }
    
    public int getWorkHours() { return workhours; }
    public void setWorkHours(int workHours) { this.workhours = workHours; }

    public double getPayPerHour() { return payPerHour; }
    public void setPayPerHour(double payPerHour) { this.payPerHour = payPerHour; }

    
    @Override
    public double calculateMonthly() {
        return this.payPerHour * this.workhours;
    }

    
    
    
    
    
}
