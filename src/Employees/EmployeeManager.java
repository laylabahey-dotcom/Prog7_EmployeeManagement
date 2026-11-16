
package Employees;

import Departments.Dep;
import Files.EmployeeHandler;
import java.util.ArrayList;
import java.util.List;
        
public class EmployeeManager implements EmployeeOperations{
    private final ArrayList<Employee> employees;
    private final String filePath;
    private final EmployeeHandler fHandler = new EmployeeHandler(); 
    
    public EmployeeManager(String filePath) {
        this.filePath = filePath;
        this.employees = fHandler.load(filePath);
    }
    
    @Override
    public List<Employee> filterByDepartment(Dep department) {
        List<Employee> match = new ArrayList<>();
        if (department == null) {//no restrictions
            match.addAll(employees);
            return match;
        }
        int targetId = department.getDepartmentId();
        
        for (Employee emp : employees) {
            Dep empDep = emp.getDepartment();
            if (empDep != null && empDep.getDepartmentId() == targetId) {
                match.add(emp);
            }
        }
        return match;
    }
}
