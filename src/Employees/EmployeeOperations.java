
package Employees;

import Departments.Dep;
import java.util.List;

public interface EmployeeOperations {
    List<Employee> filterByDepartment(Dep department);
    
}
