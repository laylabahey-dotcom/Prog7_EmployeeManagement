
package Employees;
import java.util.*;

public abstract class Employee {
    private int ID;
    private String fullName;
    private String Department;
    private int age;
    private String Position;
    

    public Employee(int ID, String fullName, String Department, int age, String Position){
        setID(ID);
        setFullName(fullName);
        setDepartment(Department);
        setAge(age);
        setPosition(Position);
    }
    
    public void setID(int ID) {
        if (ID <= 0) {
            throw new IllegalArgumentException("Employee ID cannot be 0.");
        }
        this.ID = ID;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty.");
        }
        if(fullName.contains(",")) {
            throw new IllegalArgumentException("Full name cannot contain commas.");
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

    public int getID() { return ID; }
    public String getFullName() { return fullName; }
    public String getDepartment() { return Department; }
    public int getAge() { return age; }
    public String getPosition() { return Position; }

    public abstract double calculateMonthly();  
    
}
