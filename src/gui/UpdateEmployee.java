package gui;

import Employees.Employee;
import Employees.EmployeeManager;
import Employees.FullTime;
import Employees.PartTime;
import java.awt.*;
import javax.swing.*;

public class UpdateEmployee extends JFrame {
    private static final Color DARK=Color.decode("#344E41");
    private static final Color LIGHT=Color.decode("#DAD7CD");
    private static final Font LABEL_FONT=new Font("Times New Roman", Font.BOLD,14);
    private static final Font FIELD_FONT=new Font("Times New Roman", Font.PLAIN,13);
    
    private final EmployeeManager employeeManager;
    private final int originalId;
    private final Employee existingEmployee;
    
    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField ageField;
    private final JTextField departmentField;
    private final JTextField positionField;
    
    private final JLabel fullTimeLabel;
    private final JTextField baseSalaryField;
    private final JTextField bonusField;
    
    private final JLabel partTimeLabel;
    private final JTextField payPerHourField;
    private final JTextField workHoursField;
    
    private final JButton save;
    private final JButton cancel;
    
    public UpdateEmployee(EmployeeManager employeeManager,int employeeId) {
        this.employeeManager=employeeManager;
        this.originalId=employeeId;
        this.existingEmployee=(employeeManager!=null)? employeeManager.searchById(employeeId):null;
        if(this.existingEmployee==null) 
            throw new IllegalArgumentException("Employee with iD "+employeeId+" was not found.");
        
        setTitle("Update Employee - ID "+originalId);
        setSize(460,460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        
        JLabel titleLabel=new JLabel("Update Employee Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setForeground(DARK);
        mainPanel.add(titleLabel,BorderLayout.NORTH);
        
        JPanel formPanel=new JPanel(new GridBagLayout());
        formPanel.setBackground(LIGHT);
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(6,6,6,6);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=1.0;
        
        idField=createTextField(String.valueOf(existingEmployee.getID()));
        nameField=createTextField(existingEmployee.getFullName());
        ageField=createTextField(String.valueOf(existingEmployee.getAge()));
        departmentField=createTextField(existingEmployee.getDepartment());
        positionField=createTextField(existingEmployee.getPosition());
        
        baseSalaryField=createTextField("0.0");
        bonusField=createTextField("0.0");
        payPerHourField=createTextField("0.0");
        workHoursField=createTextField("0.0");
        
       int row=0;
       row=addFormRow(formPanel,gbc,row,"Employee ID:",idField);
       row=addFormRow(formPanel,gbc,row,"Full Name:",nameField);
       row=addFormRow(formPanel,gbc,row,"Age:",ageField);
       row=addFormRow(formPanel,gbc,row,"Department:",departmentField);
       row=addFormRow(formPanel,gbc,row,"Position:",positionField);
       
       if(existingEmployee instanceof FullTime) {
           FullTime ft=(FullTime) existingEmployee;
           baseSalaryField.setText(String.valueOf(ft.getBaseSalary()));
           bonusField.setText(String.valueOf(ft.getBonus()));
           fullTimeLabel=createLabel("Type: Full Time");
           partTimeLabel=null;
           gbc.gridx=0;
           gbc.gridy=row++;
           gbc.gridwidth=2;
           formPanel.add(fullTimeLabel,gbc);
           gbc.gridwidth=1;
           
           row=addFormRow(formPanel,gbc,row,"Base Salary:",baseSalaryField);
           row=addFormRow(formPanel,gbc,row,"Bonus:",bonusField);
       } else if (existingEmployee instanceof PartTime) {
           PartTime pt=(PartTime) existingEmployee;
           payPerHourField.setText(String.valueOf(pt.getPayPerHour()));
           workHoursField.setText(String.valueOf(pt.getWorkHours()));
           partTimeLabel=createLabel("Type: Part Time");
           fullTimeLabel=null;
           gbc.gridx=0;
           gbc.gridy=row++;
           gbc.gridwidth=2;
           formPanel.add(partTimeLabel,gbc);
           gbc.gridwidth=1;
           
           row=addFormRow(formPanel,gbc,row,"Pay Per Hour:",payPerHourField);
           row=addFormRow(formPanel,gbc,row,"Work Hours:",workHoursField);
       } else{
           fullTimeLabel=null;
           partTimeLabel=null;
       }
       mainPanel.add(formPanel,BorderLayout.CENTER);
       JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,12,0));
       buttonPanel.setBackground(LIGHT);
       save=createButton("Save Changes");
       cancel=createButton("Cancel");
       save.addActionListener(e->handleSave());
       cancel.addActionListener(e->this.dispose());
       
       buttonPanel.add(save);
       buttonPanel.add(cancel);
       mainPanel.add(buttonPanel,BorderLayout.SOUTH);
       add(mainPanel);
    }
    
    private JLabel createLabel(String text) {
        JLabel label= new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(DARK);
        return label;
    }
    
    private JTextField createTextField(String initial) {
        JTextField field= new JTextField(initial,16);
        field.setFont(FIELD_FONT);
        field.setForeground(DARK);
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private JButton createButton(String text) {
        JButton button= new JButton(text);
        button.setFont(LABEL_FONT);
        button.setForeground(DARK);
        button.setBackground(LIGHT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK,2),
            BorderFactory.createEmptyBorder(6,14,6,14)
        ));
        return button;
    }
    
    private int addFormRow(JPanel panel,GridBagConstraints gbc,int row, String labelText, JComponent input) {
        gbc.gridx=0;
        gbc.gridy=row;
        gbc.weightx=0.35;
        panel.add(createLabel(labelText),gbc);
        gbc.gridx=1;
        gbc.gridy=row;
        gbc.weightx=0.65;
        panel.add(input,gbc);
        
        return row+1;
    }
    
    private void handleSave(){
        try {
            int newId=Integer.parseInt(idField.getText().trim());
            int age=Integer.parseInt(ageField.getText().trim());
            String fullName=nameField.getText().trim();
            String department=departmentField.getText().trim();
            String position=positionField.getText().trim();
            
            Employee updatedEmp;
            if(existingEmployee instanceof FullTime) {
                double baseSalary=Double.parseDouble(baseSalaryField.getText().trim());
                double bonus=Double.parseDouble(bonusField.getText().trim());
                updatedEmp=new FullTime(newId,fullName,department,age,position,baseSalary,bonus);
            } else if(existingEmployee instanceof PartTime) {
                int workHours=Integer.parseInt(workHoursField.getText().trim());
                double payPerHour=Double.parseDouble(payPerHourField.getText().trim());
                updatedEmp=new PartTime(newId,fullName,department,age,position,payPerHour,workHours);
            } else {
                JOptionPane.showMessageDialog(this, "Unsupported employee type.","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            employeeManager.updateEmployee((originalId), updatedEmp);
            JOptionPane.showMessageDialog(this,"Employee updated successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter valid numeric values for ID, age, and salary/hour fields.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Validation Errors",JOptionPane.ERROR_MESSAGE);
        }
        
    }
   
}
