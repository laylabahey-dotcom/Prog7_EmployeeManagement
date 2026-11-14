/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Employees;
import java.util.*;

public abstract class Employee {
    private String ID;
    private String fullName;
    private String Department;
    private int age;
    private String Position;
    private String type;
    private double monthlyPay;

    Employee[] staff = new ArrayList();

    public Employee(String ID, String fullName, String Department, int age, String Position){
        this.ID = ID;
        this.fullName = fullName;
        this.Department = Department;
        this.age = age;
        this.Position = Position;
        
    }

    public abstract void calculateMonthly();
    
    
}
