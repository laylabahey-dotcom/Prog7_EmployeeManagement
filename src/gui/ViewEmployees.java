package gui;

import Employees.Employee;
import Employees.EmployeeManager;
import Employees.FullTime;
import Employees.PartTime;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewEmployees extends JFrame {
    private static final Color DARK=Color.decode("#344E41");
    private static final Color LIGHT=Color.decode("#DAD7CD");
    private static final Font LABEL_FONT=new Font("Times New Roman", Font.BOLD,14);
    private static final Font TABLE_FONT=new Font("Times New Roman", Font.PLAIN,13);
    
    private final EmployeeManager employeeManager;
    private final DefaultTableModel tableModel;
    private final JTable employeeTable;
    private final JButton refreshButton;
    private final JButton backButton;
    
    public ViewEmployeesWindow(EmployeeManager employeeManager) {
        this.employeeManager=employeeManager;
        setTitle("View Employees");
        setSize(850,480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        //header
    }
    
    
    
    
}
