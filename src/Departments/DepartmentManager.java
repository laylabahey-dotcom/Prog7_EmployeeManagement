
package Departments;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class DepartmentManager {
    private final List<Dep> departments = new ArrayList<>(); // in-memory list holding all departments
    private final String filePath;
    
    public DepartmentManager(String filePath){//called when starting the manager
        this.filePath = filePath;
        loadFromFile();
        if(departments.isEmpty()){//if no deps were loaded
            createDefaultDepartments();
            
        }
    }
    private void createDefaultDepartments(){
        departments.add(new Dep(1, "IT"));
        departments.add(new Dep(2, "Human Resources"));
        departments.add(new Dep(3, "Finance"));
        departments.add(new Dep(4, "Marketing"));
        departments.add(new Dep(5, "Sales"));
        saveToFile();
    }
    public void addDepartment(int id, String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Department name cannot be empty.");
            
        }
        for(Dep dept : departments){//loops through every existing department in memory
            if(dept.getDepartmentId() == id){
                throw new IllegalArgumentException("Duplicate department id: " + id);
             
            }
            if(dept.getDepartmentName().equalsIgnoreCase(name)){ 
                throw new IllegalArgumentException("Duplicate department name:" + name);    
            }
            
        }
        departments.add(new Dep(id,name));
        saveToFile();//saves the updated list to the file
    }
    
    public List<Dep> getAllDepartments(){
        return Collections.unmodifiableList(departments);
        
    }
    public List<String> getDepartmentNames(){
        List<String> names = new ArrayList<>();
        for(Dep dept : departments){
            names.add(dept.getDepartmentName());
            
        }
        return names;
    }
    public void saveToFile() { 
        File file = new File(filePath); // file obj pointing to file path
        File parent = file.getParentFile();//if exists
        if (parent != null && !parent.exists()) { 
            parent.mkdirs(); // creates missing parent directories automatically
        }

        try (PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(file)))) { //Opens file for writing;auto-closes at end
            for (Dep dept : departments) { 
                p.println(dept.getDepartmentId() + "," + dept.getDepartmentName()); //writes "id,name" on its own line
            }
        } catch (IOException e) { // Catches disk/file writing errors
            System.err.println("Error saving departments: " + e.getMessage()); // Logs error without crashing app
        }
    }

    public void loadFromFile() { 
        File file = new File(filePath); 
        if (!file.exists()) { 
            return; 
        }

        departments.clear(); 
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { // Opens file for reading; auto-closes at end
            String line; // Holds each line read from the file
            while ((line = reader.readLine()) != null) { //reads line by line until end of file (null)
                if (line.isEmpty()) { //blank lines
                    continue; 
                }
                String[] parts = line.split(",", 2); 
                if (parts.length == 2) { 
                    int id = Integer.parseInt(parts[0]); // Converts the first text piece into an int ID
                    String name = parts[1]; // Takes the second piece as the department name
                    departments.add(new Dep(id, name)); // Reconstructs Department and adds to list
                }
            }
        } catch (IOException | NumberFormatException e) { 
            System.err.println("Error loading departments: " + e.getMessage());
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
   
}
