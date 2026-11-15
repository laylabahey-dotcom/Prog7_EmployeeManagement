package Files;

import java.io.*;
import java.util.*;
import javax.swing.*;
import Departments.*;

public class DepartmentHandler extends FileHandler<Departments> {

    @Override
    public void save(String fileName, ArrayList<Departments> items) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Departments dep : department) {
                pw.println(String.valueOf(dep.getID()) + "," + dep.getName());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(),"File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public ArrayList<Departments> load(String fileName) {
        ArrayList<Departments> dep = new ArrayList<>();
        if (!fileExists(fileName)) {
            return dep;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    dep.add(new Departments(id, name));
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage(),"File Error", JOptionPane.ERROR_MESSAGE);
        }
        return dep;
    }

}
