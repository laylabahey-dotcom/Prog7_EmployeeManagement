package Files;

import java.io.*;
import java.util.*;
import javax.swing.*;
import Employees.*;

public class EmployeeHandler extends FileHandler<Employee> {

    @Override
    public void save(String fileName, ArrayList<Employee> employees) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Employee emp : employees) {
                if (emp instanceof FullTime) {
                    FullTime ft = (FullTime) emp;
                    pw.println(String.join(",", "Full_Time", ft.getID(),
                            ft.getFullName(), String.valueOf(ft.getAge()), ft.getDepartment(),
                            ft.getPosition(), String.valueOf(ft.getBaseSalary()),
                            String.valueOf(ft.getBonus())));
                } else if (emp instanceof PartTime) {
                    PartTime pt = (PartTime) emp;
                    pw.println(String.join(",", "Part_Time", pt.getID(),
                            pt.getFullName(), String.valueOf(pt.getAge()), pt.getDepartment(),
                            pt.getPosition(), String.valueOf(pt.getPayPerHour()), String.valueOf(pt.getWorkHours())));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(),"File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public ArrayList<Employee> load(String fileName) {
        ArrayList<Employee> emp = new ArrayList<>();
        if (!fileExists(fileName)) {
            return emp;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 6) {
                    continue;
                }
                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                int age = Integer.parseInt(parts[3]);
                String department = parts[4];
                String position = parts[5];
                if ("Full_Time".equals(type) && parts.length >= 8) {
                    double baseSalary = Double.parseDouble(parts[6]);
                    double bonus = Double.parseDouble(parts[7]);
                    emp.add(new FullTime(id, name, department, age, position, baseSalary, bonus));
                } else if ("Part_Time".equals(type) && parts.length >= 8) {
                    double payPerHour = Double.parseDouble(parts[6]);
                    int workHours = Integer.parseInt(parts[7]);
                    emp.add(new PartTime(id, name, department, age, position, payPerHour, workHours));
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage(),"File Error", JOptionPane.ERROR_MESSAGE);
        }
        return emp;
    }
}
