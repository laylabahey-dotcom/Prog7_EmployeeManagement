package Employees;

import Departments.Dep;
import java.util.List;

public interface EmployeeOperations {

    List<Employee> filterByDepartment(Dep department);

    List<Employee> getAllEmployees();

    Employee searchById(int id);

    void addEmployee(Employee newEmp);

    void updateEmployee(int originalId, Employee updatedEmp);

    List<Employee> searchByName(String nameQuery);

    boolean deleteEmployee(int id);
}
