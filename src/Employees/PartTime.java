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
    private String type = "Part-time Employee";
    private String ID;
    private String fullName;
    private String Department;
    private int age;
    private String Position;
    private int workhours;
    private double payPerHour;
    private double monthlyPay;

    public PartTime(String ID, String fullName, String Department, int age, 
            String Position, int Workhours, double payPerHour,
            int workhours, double payPerHour) {
        super(ID, fullName, Department, age, Position);
        //salary 
        this.payPerHour = payPerHour;
        this.workhours = workhours;
        
    }
}
