/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import Employees.*;
import Departments.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class AddEmployeeWindow extends JFrame {
    private static final Color DARK = Color.decode("#344E41");
    private static final Color LIGHT = Color.decode("#DAD7CD");
    private static final Font LABEL_FONT = new Font("Times New Roman", Font.BOLD, 14);
    
    private final EmployeeManager manager;
    
    // basic employee fields
    //  Input components for the data every employee has
    private final JTextField idField = new JTextField(15);
    private final JTextField nameField = new JTextField(15);
    private final JTextField ageField = new JTextField(15);
    private final JComboBox<String> deptBox;
    private final JTextField positionField = new JTextField(15);
    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{"Full-Time", "Part-Time"});
 
    // salary fields 
    private final JTextField baseField = new JTextField(15);
    private final JTextField bonusField = new JTextField(15);
    private final JTextField payField = new JTextField(15);
    private final JTextField hoursField = new JTextField(15);
    
    // CardLayout stacks several panels in the same spot and shows only one of them.
    // use it to switch between the Full-Time card and the Part-Time card.
    private final CardLayout salaryCards = new CardLayout();
    private final JPanel salaryPanel = new JPanel(salaryCards);
    
    public AddEmployeeWindow(EmployeeManager empManager, DepartmentManager depManager) {
        this.manager = empManager;
        deptBox = new JComboBox<>(getDepartmentNames(depManager));
 
        setTitle("Employee Management System - Add Employee");
        setSize(440, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //X button closes ONLY this window (EXIT_ON_CLOSE would quit the app)
        setLocationRelativeTo(null);
        setResizable(false);
 
        // header
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        top.setBackground(DARK);
        JLabel title = new JLabel("Add New Employee");
        title.setForeground(LIGHT);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        top.add(title, BorderLayout.WEST);
 
        // form
        // form panel (all the input rows)
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(LIGHT);
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();   // components stretch to fill their cell's width
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
 
         // each call adds one row: label on the left (column 0), input on the right (column 1)
        addRow(form, gbc, 0, "Employee ID:", idField);
        addRow(form, gbc, 1, "Full Name:", nameField);
        addRow(form, gbc, 2, "Age:", ageField);
        addRow(form, gbc, 3, "Department:", deptBox);
        addRow(form, gbc, 4, "Position:", positionField);
        addRow(form, gbc, 5, "Employee Type:", typeBox);
 
        // salary panel: one card per employee type
        salaryPanel.setBackground(LIGHT);
         // The second argument is the card's NAME. It must match the strings in typeBox
        // ("Full-Time" / "Part-Time") because we show cards by those names below.
        salaryPanel.add(buildCard("Base Salary:", baseField, "Bonus:", bonusField), "Full-Time");
        salaryPanel.add(buildCard("Pay Per Hour:", payField, "Work Hours:", hoursField), "Part-Time");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        form.add(salaryPanel, gbc);
 
        // switch the visible salary fields when the type changes
        typeBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                salaryCards.show(salaryPanel, (String) typeBox.getSelectedItem());
            }
        });
 
        // buttons
        JButton add = styledButton("Add");
        JButton clear = styledButton("Clear");
        JButton close = styledButton("Close");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setBackground(LIGHT);
        buttons.add(add);
        buttons.add(clear);
        buttons.add(close);
        gbc.gridy = 7;
        form.add(buttons, gbc);
        //  put the panels in the window
        add(top, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        // button action
        add.addActionListener(e -> addEmployee()); // runs main logic down
        clear.addActionListener(e -> clearForm()); // clears all boxes
        close.addActionListener(e -> dispose()); // closes just this window
        getRootPane().setDefaultButton(add); // pressing Enter anywhere in the form triggers "Add"
    }
    
    private java.util.List<Dep> getDepartmentNames(DepartmentManager depManager) {
        return depManager.getAllDepartments();
    }
    
    
         /*
      Builds one "card" of the salary section: two labelled rows.
      Used once for Full-Time (base + bonus) and once for Part-Time (pay + hours).
     */
       private JPanel buildCard(String label1, JTextField f1, String label2, JTextField f2) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(LIGHT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 12, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        addRow(card, gbc, 0, label1, f1);
        gbc.insets = new Insets(0, 0, 0, 0);
        addRow(card, gbc, 1, label2, f2);
        return card;
    }
       
       /* Adds one row (a label + an input field) to a panel that uses GridBagLayout. */
    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String text, JComponent field) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(text);
        label.setForeground(DARK);
        label.setFont(LABEL_FONT);
        label.setPreferredSize(new Dimension(115, 22));
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
 
    
    private JButton styledButton(String text) {
        JButton b = new JButton(text);
        b.setFont(LABEL_FONT);
        b.setBackground(DARK);
        b.setForeground(LIGHT);
        b.setFocusPainted(false);
        return b;
    }
 
    // MAIN LOGIC: runs when the user clicks "Add"
    // Steps: required fields -> numeric checks -> unique ID -> salary checks
    //        -> create object -> add to manager (which saves) -> success message
    private void addEmployee() {
        try {
            //required fields
            String idText = required(idField, "Employee ID");
            String name = required(nameField, "Full Name");
            String ageText = required(ageField, "Age");
            String position = required(positionField, "Position");
            String dept = (String) deptBox.getSelectedItem();
            if (dept == null || dept.isEmpty()) {
                throw new IllegalArgumentException("Please select a department.");
            }
 
            // numeric validation (same rules as the setters in Employee/FullTime/PartTime)
            int id = wholeNumber(idField, idText, "Employee ID");
            if (id <= 0) {
                idField.requestFocus();
                throw new IllegalArgumentException("Employee ID must be a positive whole number.");
            }
            int age = wholeNumber(ageField, ageText, "Age");
            if (age < 18 || age > 100) {
                ageField.requestFocus();
                throw new IllegalArgumentException("Age must be between 18 and 100.");
            }
 
            // unique ID
            if (manager.searchById(id) != null) {
                idField.requestFocus();
                throw new IllegalArgumentException("Employee ID " + id + " already exists. Please use a different ID.");
            }
 
            // salary info + create the appropriate object
            Employee employee;
            if ("Full-Time".equals(typeBox.getSelectedItem())) {
                double base = nonNegative(baseField, "Base Salary");
                double bonus = nonNegative(bonusField, "Bonus");
                employee = new FullTime(id, name, dept, age, position, base, bonus);
            } else {
                double pay = nonNegative(payField, "Pay Per Hour");
                int hours = wholeNumber(hoursField, required(hoursField, "Work Hours"), "Work Hours");
                if (hours < 0 || hours > 744) {
                    hoursField.requestFocus();
                    throw new IllegalArgumentException("Work Hours must be between 0 and 744.");
                }
                employee = new PartTime(id, name, dept, age, position, pay, hours);
            }
 
            // add to the collection + save to file (done inside EmployeeManager)
            manager.addEmployee(employee);
            JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
 
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) { // e.g. a file problem while saving
            JOptionPane.showMessageDialog(this, "Could not add the employee: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // Returns the trimmed text of a field, or throws an error if the field is empty. */
    private String required(JTextField field, String label) {
        String text = field.getText().trim();
        if (text.isEmpty()) {
            field.requestFocus();
            throw new IllegalArgumentException(label + " is required.");
        }
        return text;
    }
    
    /* Converts text to an int, or throws an error with a friendly message. */
    private int wholeNumber(JTextField field, String text, String label) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            field.requestFocus();
            throw new IllegalArgumentException(label + " must be a whole number.");
        }
    }
    
     /* Reads a field as a double that must not be negative (used for salary, bonus, pay per hour). */
    private double nonNegative(JTextField field, String label) {
        String text = required(field, label);
        double value;
        try {
            value = Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            field.requestFocus();
            throw new IllegalArgumentException(label + " must be a valid number.");
        }
        if (Double.isNaN(value) || Double.isInfinite(value) || value < 0) {
            field.requestFocus();
            throw new IllegalArgumentException(label + " cannot be negative.");
        }
        return value;
    }
    
     /* Empties every field and resets both combo boxes to their first item. */
    private void clearForm() {
        for (JTextField f : new JTextField[]{idField, nameField, ageField, positionField,
                baseField, bonusField, payField, hoursField}) {
            f.setText("");
        }
        deptBox.setSelectedIndex(0);
        typeBox.setSelectedIndex(0);
        idField.requestFocus();
    }
}
    

    
