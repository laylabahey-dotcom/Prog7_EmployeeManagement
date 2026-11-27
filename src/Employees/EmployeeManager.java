package Employees;

import Departments.Dep;
import Files.EmployeeHandler;
import java.util.*; // changed to call all methods in util

public class EmployeeManager implements EmployeeOperations {

    private final ArrayList<Employee> employees;
    private final String filePath;
    private final EmployeeHandler fHandler = new EmployeeHandler();

    public EmployeeManager(String filePath) {
        this.filePath = filePath;
        this.employees = fHandler.load(filePath);
    }

    @Override
    public List<Employee> getAllEmployees() { // changed from ArrayList<> to List<>
        return Collections.unmodifiableList(employees); // called collections from java.util and made it unmodifiable list 3shan el user may8ayarsh feha
    }

    @Override
    public Employee searchById(int id) {
        for (Employee emp : employees) {
            if (emp.getID() == id) {
                return emp;
            }
        }
        return null;
    }

    private void saveChanges() {
        fHandler.save(filePath, employees);
    }

    @Override
    public void addEmployee(Employee newEmp) {
        if (newEmp == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        for (Employee emp : employees) {
            if (emp.getID() == newEmp.getID()) {
                throw new IllegalArgumentException("Employee ID " + newEmp.getID() + " already exists.");
            }
        }
        employees.add(newEmp);
        saveChanges();
    }

    @Override
    public void updateEmployee(int originalId, Employee updatedEmp) {
        Employee existing = searchById(originalId);
        if (existing == null) {
            throw new IllegalArgumentException("Employee with ID " + originalId + " not found.");
        }
        if (originalId != updatedEmp.getID()) {
            if (searchById(updatedEmp.getID()) != null) {
                throw new IllegalArgumentException("Cannot update. Employee ID " + updatedEmp.getID() + " already exists.");
            }
        }
        existing.setID(updatedEmp.getID());
        existing.setFullName(updatedEmp.getFullName());
        existing.setAge(updatedEmp.getAge());
        existing.setDepartment(updatedEmp.getDepartment());
        existing.setPosition(updatedEmp.getPosition());
        //updating type-specific variables
        if (existing instanceof FullTime && updatedEmp instanceof FullTime) {
            ((FullTime) existing).setBaseSalary(((FullTime) updatedEmp).getBaseSalary());
            ((FullTime) existing).setBonus(((FullTime) updatedEmp).getBonus());
        } else if (existing instanceof PartTime && updatedEmp instanceof PartTime) {
            ((PartTime) existing).setPayPerHour(((PartTime) updatedEmp).getPayPerHour());
            ((PartTime) existing).setWorkHours(((PartTime) updatedEmp).getWorkHours());
        }
        saveChanges();
    }

    @Override
    public List<Employee> searchByName(String nameQuery) {
        List<Employee> results = new ArrayList<>();
        if (nameQuery == null || nameQuery.trim().isEmpty()) {
            return results;
        }
        String query = nameQuery.toLowerCase().trim();
        for (Employee emp : employees) {
            if (emp.getFullName() != null && emp.getFullName().toLowerCase().contains(query)) {
                results.add(emp);
            }
        }
        return results;
    }

    @Override
    public boolean deleteEmployee(int id) {
        Employee emp = searchById(id);
        if (emp != null) {
            employees.remove(emp);
            saveChanges();
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> filterByDepartment(Dep department) {
        List<Employee> match = new ArrayList<>();
        if (department == null) {//no restrictions
            match.addAll(employees);
            return match;
        }
        String targetName = department.getDepartmentName();
        for (Employee emp : employees) {
            if (emp.getDepartment() != null && emp.getDepartment().equalsIgnoreCase(targetName)) {
                match.add(emp);
            }
        }
        return match;
    }
}
