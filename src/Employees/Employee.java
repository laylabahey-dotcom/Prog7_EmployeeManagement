
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
    
    public void setID(String ID) {
        if (ID == null || ID.isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty.");
        }
        this.ID = ID;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty.");
        }
        this.fullName = fullName;
    }

    public void setDepartment(String department) {
        if (department == null || department.isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty.");
        }
        this.Department = department;
    }

    public void setAge(int age) {
        if (age < 18 || age > 100) {
            throw new IllegalArgumentException("Age must be between 18 and 100.");
        }
        this.age = age;
    }

    public void setPosition(String position) {
        if (position == null || position.isEmpty()) {
            throw new IllegalArgumentException("Position cannot be null or empty.");
        }
        this.Position = position;
    }

    public String getID() { return ID; }
    public String getFullName() { return fullName; }
    public String getDepartment() { return Department; }
    public int getAge() { return age; }
    public String getPosition() { return Position; }

    public abstract double calculateMonthly();  
    
}
