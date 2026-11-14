/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Employees;

/**
 *
 * @author layla-bahey
 */
public class FullTime extends Employee{
    private String type = "Full-time Employee";
    private String ID;
    private String fullName;
    private String Department;
    private int age;
    private String Position;
    private double baseSalary;
    private double bonus;
    private double monthlyPay;


    public FullTime(String ID, String fullName, String Department, int age, String Position, double baseSalary, double bonus) {
        super(ID, fullName, Department, age, Position);
        // salary 
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    @Override
    public void calculateMonthly() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
