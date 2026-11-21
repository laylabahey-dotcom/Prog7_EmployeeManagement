package Departments;

import Files.DepartmentHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class DepartmentManager {

    private final ArrayList<Dep> departments;
    private final String filePath;
    private final DepartmentHandler fHandler = new DepartmentHandler();

    public DepartmentManager(String filePath) {//called when starting the manager
        this.filePath = filePath;
        this.departments = fHandler.load(filePath);
        if (departments.isEmpty()) {//if no deps were loaded
            createDefaultDepartments();

        }
    }

    private void createDefaultDepartments() {
        departments.add(new Dep(1, "IT"));
        departments.add(new Dep(2, "Human Resources"));
        departments.add(new Dep(3, "Finance"));
        departments.add(new Dep(4, "Marketing"));
        departments.add(new Dep(5, "Sales"));
        fHandler.save(filePath, departments);
    }

    public void addDepartment(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("Department id must be a positive integer.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");

        }
        for (Dep dept : departments) {//loops through every existing department in memory
            if (dept.getDepartmentId() == id) {
                throw new IllegalArgumentException("Duplicate department id: " + id);

            }
            if (dept.getDepartmentName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Duplicate department name:" + name);
            }

        }
        departments.add(new Dep(id, name));
        sortDepartmentsId();
        fHandler.save(filePath, departments);
    }
    private void sortDepartmentsId() {
    departments.sort(Comparator.comparingInt(Dep::getDepartmentId));
}
    public void deleteDepById(int id){
        for(Dep dept: departments){
            if(dept.getDepartmentId() == id){
                departments.remove(dept);
                fHandler.save(filePath, departments);
                return;
                            }
        }
        
        
    }
    public Dep searchById(int id) {
        for (Dep dept : departments) {
            if (dept.getDepartmentId() == id) {
                return dept;
            }
        }
        return null;
    }
    public Dep searchbyName(String name){
        if(name == null || name.isEmpty()){
            return null;
        }
        String target = name.toLowerCase();
        for(Dep dept: departments){//last is the listName i loop in
            if(dept.getDepartmentName().toLowerCase().equals(target)){
                return dept;
            }
        }
        return null;//if name not found
    }

    public List<Dep> getAllDepartments() {
        return Collections.unmodifiableList(departments);

    }

    public List<String> getDepartmentNames() {
        List<String> names = new ArrayList<>();
        for (Dep dept : departments) {
            names.add(dept.getDepartmentName());

        }
        return names;
    }

}
