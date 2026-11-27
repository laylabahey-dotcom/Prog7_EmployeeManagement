
package Departments;

public class Dep {
    private int departmentId;
    private String departmentName;
    
    public Dep(int departmentId, String departmentName){
        setDepartmentId(departmentId);
        setDepartmentName(departmentName);
    }
    
    public void setDepartmentId(int departmentId){
        if(departmentId <= 0){
            throw new IllegalArgumentException("Department Id must be a positive integer.");  
        }
        this.departmentId = departmentId;
        
    }
    public void setDepartmentName(String departmentName){
        if (departmentName == null || departmentName.isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }
        if(departmentName.contains(",")) {
            throw new IllegalArgumentException("Department name cannot contain commas.");
        }
        this.departmentName = departmentName;
    }
    
    public int getDepartmentId(){
        return departmentId;
    }
    public String getDepartmentName(){
        return departmentName;
    }
    @Override
    public String toString(){
        return departmentName;
    }
    
}
