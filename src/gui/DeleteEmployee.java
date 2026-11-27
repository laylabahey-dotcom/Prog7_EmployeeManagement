package gui;

import Employees.Employee;
import Employees.EmployeeManager;
import Employees.FullTime;
import Employees.PartTime;
import java.awt.*;
import javax.swing.*;

public class DeleteEmployee extends JFrame {
    private static final Color DARK=Color.decode("#344E41");
    private static final Color LIGHT=Color.decode("#DAD7CD");
    private static final Font LABEL_FONT=new Font("Times New Roman", Font.BOLD,14);
    private static final Font VALUE_FONT=new Font("Times New Roman", Font.PLAIN,13);
    
    private final EmployeeManager employeeManager;
    private final int employeeId;
    private final Employee targetEmployee;
    
    private final JButton confirmDelete;
    private final JButton cancel;
    
    public DeleteEmployee(EmployeeManager employeeManager,int employeeId) {
        this.employeeManager=employeeManager;
        this.employeeId=employeeId;
        this.targetEmployee=(employeeManager!=null)? employeeManager.searchById(employeeId):null;
        if(this.targetEmployee==null) 
            throw new IllegalArgumentException("Employee with iD "+employeeId+" was not found.");
        
        setTitle("Delete Employee - ID "+employeeId);
        setSize(440,340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        
        JLabel titleLabel=new JLabel("Confirm Employee Deletion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setForeground(DARK);
        mainPanel.add(titleLabel,BorderLayout.NORTH);
        
        JPanel detailsPanel=new JPanel(new GridBagLayout());
        detailsPanel.setBackground(LIGHT);
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(6,6,6,6);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=1.0;
        
        String typeText;
        if(targetEmployee instanceof FullTime){
            typeText="Full Time";
        } else if(targetEmployee instanceof PartTime){
            typeText="Part Time";
        } else 
            typeText="Employee";
        
        int row=0;
        row=addDetailRow(detailsPanel,gbc,row,"Employee ID:",String.valueOf(targetEmployee.getID()));
        row=addDetailRow(detailsPanel,gbc,row,"Full Name:",targetEmployee.getFullName());
        row=addDetailRow(detailsPanel,gbc,row,"Department:",targetEmployee.getDepartment());
        row=addDetailRow(detailsPanel,gbc,row,"Position:",targetEmployee.getPosition());
        row=addDetailRow(detailsPanel,gbc,row,"Type:",typeText);
        row=addDetailRow(detailsPanel,gbc,row,"Monthly Salary:",String.format("%.2f", targetEmployee.calculateMonthly()));
        
        mainPanel.add(detailsPanel,BorderLayout.CENTER);
        
        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,12,0));
        buttonPanel.setBackground(LIGHT);
        confirmDelete=createButton("Delete Employee");
        cancel=createButton("Cancel");
        confirmDelete.addActionListener(e->handleDelete());
        cancel.addActionListener(e->this.dispose());
        buttonPanel.add(confirmDelete);
        buttonPanel.add(cancel);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);
        add(mainPanel);
    
    }
    
    private int addDetailRow(JPanel panel,GridBagConstraints gbc,int row, String labelText, String valueText) {
        gbc.gridx=0;
        gbc.gridy=row;
        gbc.weightx=0.35;
        JLabel keyLabel=new JLabel(labelText);
        keyLabel.setFont(LABEL_FONT);
        keyLabel.setForeground(DARK);
        panel.add(keyLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=row;
        gbc.weightx=0.65;
        JLabel valueLabel=new JLabel(valueText);
        valueLabel.setFont(VALUE_FONT);
        valueLabel.setForeground(DARK);
        panel.add(valueLabel,gbc);
        return row+1;
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
    
    private void handleDelete(){
        int choice=JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to permananently delete "+targetEmployee.getFullName()+" ID: "+employeeId+"?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if(choice!=JOptionPane.YES_OPTION){
            return;
        }
        boolean deleted=(employeeManager!=null)&&employeeManager.deleteEmployee(employeeId);
        if(deleted){
            JOptionPane.showMessageDialog(this, "Employee deleted successfully.","Deleted",JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else{
            JOptionPane.showMessageDialog(this,"Could not delete employee.","Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
