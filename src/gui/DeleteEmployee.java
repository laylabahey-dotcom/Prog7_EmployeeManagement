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
    private final int originalId;
    private final Employee targetEmployee;
    
    private final JButton save;
    private final JButton cancel;
    
}
