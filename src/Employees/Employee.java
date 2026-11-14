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
    

    public Employee(String ID, String fullName, String Department, int age, String Position){
        this.ID = ID;
        this.fullName = fullName;
        this.Department = Department;
        this.age = age;
        this.Position = Position;
        
    }
    
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDepartment() { return Department; }
    public void setDepartment(String department) { this.Department = department; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPosition() { return Position; }
    public void setPosition(String position) { this.Position = position; }

    public abstract double calculateMonthly();  
    
}
