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
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Set;
import java.util.TreeSet;


public class ViewEmployees extends JFrame {

    private static final Color DARK = Color.decode("#344E41");
    private static final Color LIGHT = Color.decode("#DAD7CD");
    private static final Font LABEL_FONT = new Font("Times New Roman", Font.BOLD, 14);
    private static final Font TABLE_FONT = new Font("Times New Roman", Font.PLAIN, 13);

    private final EmployeeManager employeeManager;
    private final DefaultTableModel tableModel;
    private final JTable employeeTable;
    private final JButton refresh;
    private final JButton back;
    private final JButton delete;
    private final JButton update;
    private final JTextField searchIdField;
    private final JTextField searchNameField;
    private final JComboBox<String> deptFilterBox;

    public ViewEmployees(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
        setTitle("View Employees");
        setSize(850, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        //header
        JPanel topPanel = new JPanel(new BorderLayout(0, 6));
        topPanel.setBackground(LIGHT);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBackground(LIGHT);

        JPanel idSearchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        idSearchRow.setBackground(LIGHT);

        JLabel searchIdLabel = new JLabel("Search Employee by ID:");
        searchIdLabel.setFont(LABEL_FONT);
        searchIdLabel.setForeground(DARK);

        searchIdField = new JTextField(8);
        searchIdField.setFont(TABLE_FONT);
        searchIdField.setBackground(Color.WHITE);
        searchIdField.setForeground(DARK);
        //add label and textfield to rowpanel
        idSearchRow.add(searchIdLabel);
        idSearchRow.add(searchIdField);

        JPanel nameSearchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        nameSearchRow.setBackground(LIGHT);

        JLabel searchNameLabel = new JLabel("Search Employee by Name:");
        searchNameLabel.setFont(LABEL_FONT);
        searchNameLabel.setForeground(DARK);

        searchNameField = new JTextField(16);
        searchNameField.setFont(TABLE_FONT);
        searchNameField.setBackground(Color.WHITE);
        searchNameField.setForeground(DARK);
        nameSearchRow.add(searchNameLabel);
        nameSearchRow.add(searchNameField);

        JPanel deptFilterRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        deptFilterRow.setBackground(LIGHT);

        JLabel deptFilterLabel = new JLabel("Filter by Department:");
        deptFilterLabel.setFont(LABEL_FONT);
        deptFilterLabel.setForeground(DARK);

        deptFilterBox = new JComboBox<>();
        deptFilterBox.setFont(TABLE_FONT);
        deptFilterBox.setBackground(Color.WHITE);
        deptFilterBox.setForeground(DARK);
        refreshDepartmentFilter();

        deptFilterRow.add(deptFilterLabel);
        deptFilterRow.add(deptFilterBox);

        searchPanel.add(idSearchRow);
        searchPanel.add(nameSearchRow);
        searchPanel.add(deptFilterRow);

        JLabel titleLabel = new JLabel("Registered Employees", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setForeground(DARK);

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.WEST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        //table
        String[] columns = {"ID", "Full Name", "Age", "Department", "Position", "Type", "Details", "Monthly Salary"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeTable = new JTable(tableModel);
        employeeTable.setFont(TABLE_FONT);
        employeeTable.setRowHeight(24);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setSelectionForeground(LIGHT);
        employeeTable.setSelectionBackground(DARK);
        employeeTable.setForeground(DARK);
        employeeTable.setBackground(Color.WHITE);

        JTableHeader header = employeeTable.getTableHeader();
        header.setFont(LABEL_FONT);
        header.setBackground(DARK);
        header.setForeground(LIGHT);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.getViewport().setBackground(LIGHT);
        scrollPane.setBorder(BorderFactory.createLineBorder(DARK, 2));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.setBackground(LIGHT);
        refresh = createButton("Refresh");
        back = createButton("Back to Dashboard");
        delete = createButton("Delete");
        update = createButton("Update");
        delete.addActionListener(e -> openDeleteForSelected());
        update.addActionListener(e -> openUpdateForSelected());
        refresh.addActionListener(e -> loadEmployeesToTable()); //imp
        back.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose(); //imp
        });

        buttonPanel.add(refresh);
        buttonPanel.add(delete);
        buttonPanel.add(update);
        buttonPanel.add(back);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        loadEmployeesToTable();
        attachSearchListener(searchIdField);
        attachSearchListener(searchNameField);
        deptFilterBox.addActionListener(e -> applyChecks());
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(LIGHT);
        button.setForeground(DARK);
        button.setFont(LABEL_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DARK, 2),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        return button;
    }

    private void attachSearchListener(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyChecks();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyChecks();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyChecks();
            }
        });
    }

    private void refreshDepartmentFilter() {
        String prev = (String) deptFilterBox.getSelectedItem();//saves prev before replacing combo model
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("All");//first added item in the list(default)
        
        Set<String> departments = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if (employeeManager != null&& employeeManager.getAllEmployees() !=null) {
            for (Employee emp : employeeManager.getAllEmployees()) {
                if (emp != null && emp.getDepartment() != null && !emp.getDepartment().trim().isEmpty()) {
                    departments.add(emp.getDepartment().trim());
                }}  
        }
        for (String dept : departments) {//adds unique elements to the list
            model.addElement(dept);
        }
        deptFilterBox.setModel(model);//replaces old list with new one
        if (prev!= null) {
            for (int i = 0; i < model.getSize(); i++) {//loops through new list
                if (prev.equalsIgnoreCase(model.getElementAt(i))) {
                    deptFilterBox.setSelectedIndex(i);
                    return;
                }
            }
        }
        deptFilterBox.setSelectedIndex(0);
    }

    public void loadEmployeesToTable() {
        refreshDepartmentFilter();
        if (employeeManager == null) {
            modifyTable(List.of());
            return;
        }
        modifyTable(employeeManager.getAllEmployees());
    }

    private void applyChecks() {
        if (employeeManager == null) {
            modifyTable(List.of());
            return;
        }
        String idQuery = searchIdField.getText().trim();
        String nameQuery = searchNameField.getText().trim();
        String selectedDept = (String) deptFilterBox.getSelectedItem();//current selected item in list=selectedDept
        boolean hasDept = selectedDept!=null && !"All".equalsIgnoreCase(selectedDept);
        //both empty -> show all employees ;no filter
        if (idQuery.isEmpty() && nameQuery.isEmpty() && !hasDept) {
            loadEmployeesToTable();
            return;
        }
        if (!idQuery.isEmpty()) {
            List<Employee> idResult = new ArrayList<>();
            for (Employee emp : employeeManager.getAllEmployees()) {
                if (emp != null && String.valueOf(emp.getID()).startsWith(idQuery)) {
                    idResult.add(emp);
                }
            }
            modifyTable(idResult);
            return;
        }
        //searchByName uses String.contains ; live update in searchByName
        if(!nameQuery.isEmpty()){
        List<Employee> nameResult = new ArrayList<>();
        List<Employee> matchesByName = employeeManager.searchByName(nameQuery);
        for (Employee emp : matchesByName) {
            if (emp != null && emp.getFullName() != null) {
                nameResult.add(emp);
            }
        }
        modifyTable(nameResult);
        return;   
        }
        List<Employee> deptResult = new ArrayList<>();
        for(Employee emp : employeeManager.getAllEmployees()){
            if(emp != null && selectedDept.equalsIgnoreCase(emp.getDepartment())){//selected dept = emp dept
                deptResult.add(emp);//add emp to the filtered list
            }
        }
        modifyTable(deptResult);
    }

    private void modifyTable(List<Employee> list) {
        tableModel.setRowCount(0);
        if (list == null) {
            return;
        }
        for (Employee emp : list) {
            String type;
            String detail;
            if (emp instanceof FullTime) {
                FullTime ft = (FullTime) emp;
                type = "Full Time";
                detail = "Base: " + ft.getBaseSalary() + ", Bonus: " + ft.getBonus();
            } else if (emp instanceof PartTime) {
                PartTime pt = (PartTime) emp;
                type = "Part Time";
                detail = "Rate per hour: " + pt.getPayPerHour() + ", Hours: " + pt.getWorkHours();
            } else {
                type = "Employee";
                detail = "Unknown";
            }
            Object[] row = {
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

    private void openDeleteForSelected() {
        int selectedId = getSelectedEmployeeId();
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee from the table.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            DeleteEmployee deleteFrame = new DeleteEmployee(employeeManager, selectedId);
            deleteFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loadEmployeesToTable();
                }
            });
            deleteFrame.setVisible(true);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            loadEmployeesToTable();
        }
    }

    private void openUpdateForSelected() {
        int selectedId = getSelectedEmployeeId();
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee from the table.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        try {
            UpdateEmployee updateFrame = new UpdateEmployee(employeeManager, selectedId);
            updateFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loadEmployeesToTable();
                }
            });
            updateFrame.setVisible(true);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            loadEmployeesToTable();
        }
    }

    public int getSelectedEmployeeId() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (int) tableModel.getValueAt(selectedRow, 0);

    }
}
/*
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
 */
