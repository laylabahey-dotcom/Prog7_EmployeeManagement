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
    private final JButton refresh;
    private final JButton back;
    private final JButton delete;
    private final JButton update;
    
    public ViewEmployees(EmployeeManager employeeManager) {
        this.employeeManager=employeeManager;
        setTitle("View Employees");
        setSize(850,480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        //header
        JLabel titleLabel=new JLabel("Registered Employees", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setForeground(DARK);
        mainPanel.add(titleLabel,BorderLayout.NORTH);
        //table
        String[] columns={"ID","Full Name","Age","Department","Position","Type","Details","Monthly Salary"};
        tableModel=new DefaultTableModel(columns,0){
            @Override
            public boolean isCellEditable(int row,int column) {
                return false;
            }
        };
        employeeTable=new JTable(tableModel);
        employeeTable.setFont(TABLE_FONT);
        employeeTable.setRowHeight(24);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setSelectionForeground(LIGHT);
        employeeTable.setSelectionBackground(DARK);
        employeeTable.setForeground(DARK);
        employeeTable.setBackground(Color.WHITE);
        
        JTableHeader header=employeeTable.getTableHeader();
        header.setFont(LABEL_FONT);
        header.setBackground(DARK);
        header.setForeground(LIGHT);
        
        JScrollPane scrollPane=new JScrollPane(employeeTable);
        scrollPane.getViewport().setBackground(LIGHT);
        scrollPane.setBorder(BorderFactory.createLineBorder(DARK,2));
        mainPanel.add(scrollPane,BorderLayout.CENTER);
        
        //buttons
        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,12,0));
        buttonPanel.setBackground(LIGHT);
        refresh=createButton("Refresh");
        back=createButton("Back to Dashboard");
        delete=createButton("Delete");
        update=createButton("Update");
        delete.addActionListener(e->openDeleteForSelected());
        update.addActionListener(e->openUpdateForSelected());
        refresh.addActionListener(e->loadEmployeesToTable()); //imp
        back.addActionListener(e->{
            new Dashboard().setVisible(true);
            this.dispose(); //imp
        });
        
        buttonPanel.add(refresh);
        buttonPanel.add(delete);
        buttonPanel.add(update);
        buttonPanel.add(back);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);
        add(mainPanel);
        loadEmployeesToTable();
    }
    
    private JButton createButton(String text) {
        JButton button=new JButton(text);
        button.setBackground(LIGHT);
        button.setForeground(DARK);
        button.setFont(LABEL_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK,2),
            BorderFactory.createEmptyBorder(6,14,6,14)
        )); return button;
    }
    
    public void loadEmployeesToTable(){
        tableModel.setRowCount(0);
        if(employeeManager==null) {
            return;
        }
        List<Employee> list=employeeManager.getAllEmployees();
        for(Employee emp:list) {
            String type;
            String detail;
            if(emp instanceof FullTime) {
                FullTime ft=(FullTime) emp;
                type="Full Time";
                detail="Base: "+ft.getBaseSalary()+", Bonus: "+ft.getBonus();
            } else if (emp instanceof PartTime) {
                PartTime pt=(PartTime) emp;
                type="Part Time";
                detail="Rate per hour: "+pt.getPayPerHour()+", Hours: "+pt.getWorkHours();
            } else{
                type="Employee";
                detail="Unknown";
            }
            Object[] row={
            emp.getID(),
            emp.getFullName(),
            emp.getAge(),
            emp.getDepartment(),
            emp.getPosition(),
            type,
            detail,
            String.format("%.2f", emp.calculateMonthly())
            };
                    
        tableModel.addRow(row);
        }
    }   
    
    private void openDeleteForSelected(){
        int selectedId=getSelectedEmployeeId();
        if(selectedId==-1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee from the table.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            ); return;
        }
        
        try{
            DeleteEmployee deleteFrame=new DeleteEmployee(employeeManager,selectedId);
            deleteFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loadEmployeesToTable();
                }
            });
            deleteFrame.setVisible(true);
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            loadEmployeesToTable();
        }
    }
    
    private void openUpdateForSelected(){
        int selectedId=getSelectedEmployeeId();
        if(selectedId==-1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee from the table.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            ); return;
        }
        try {
            UpdateEmployee updateFrame=new UpdateEmployee(employeeManager,selectedId);
            updateFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loadEmployeesToTable();
                }
            });
            updateFrame.setVisible(true);
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            loadEmployeesToTable();
        }
    }
    
    public int getSelectedEmployeeId() {
        int selectedRow=employeeTable.getSelectedRow();
        if(selectedRow==-1) return -1;
        return (int) tableModel.getValueAt(selectedRow, 0);
        
    }
}
