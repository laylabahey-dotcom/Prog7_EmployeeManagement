package gui;

import Departments.*;
import Employees.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;


public class ViewEmployees extends JFrame {

    private static final Color DARK = Color.decode("#344E41");
    private static final Color LIGHT = Color.decode("#DAD7CD");
    private static final Font LABEL_FONT = new Font("Times New Roman", Font.BOLD, 14);
    private static final Font TABLE_FONT = new Font("Times New Roman", Font.PLAIN, 13);

    private final EmployeeManager employeeManager;
    private final DepartmentManager departmentManager;
    private final DefaultTableModel tableModel;
    private final JLabel status;
    private final JTable employeeTable;
    private final JButton refresh;
    private final JButton back;
    private JButton delete;
    private JButton update;
    private final JTextField searchIdField;
    private final JTextField searchNameField;
    private final JComboBox<String> deptFilterBox;

    public ViewEmployees(EmployeeManager employeeManager,DepartmentManager departmentManager,String mode) {
        this.employeeManager = employeeManager;
        this.departmentManager=departmentManager;
        setTitle(mode.equals("UPDATE") ? "Update Employee - Select from Table"
                : mode.equals("DELETE") ? "Delete Employee - Select from Table"
                : "View Employees");
        setSize(1020, 520);
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
        employeeTable.setRowHeight(28);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setSelectionForeground(LIGHT);
        employeeTable.setSelectionBackground(DARK);
        employeeTable.setForeground(DARK);
        employeeTable.setBackground(LIGHT); //change back to white if u dont like how it looks now
        employeeTable.setGridColor(DARK);
        employeeTable.setShowGrid(true);
        employeeTable.setIntercellSpacing(new Dimension(8,4));

        JTableHeader header = employeeTable.getTableHeader();
        header.setFont(LABEL_FONT);
        header.setBackground(DARK);
        header.setForeground(LIGHT);
        header.setPreferredSize(new Dimension(header.getWidth(),32));
        header.setReorderingAllowed(false);
        
        int[] columnWidths = {
            55, // 0:ID
            160, // 1: Name
            50, // 2: Age
            135, // 3: Dep
            135, // 4: Position
            95, // 5: Type
            230, // 6: Salary Details
            115 // 7: Monthly Salary
        };
        for(int i = 0;i < columnWidths.length;i++) {
            employeeTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.getViewport().setBackground(LIGHT);
        scrollPane.setBorder(BorderFactory.createLineBorder(DARK, 2));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.setBackground(LIGHT);
        refresh = createButton("Refresh");
        back = createButton("Back to Dashboard");
        refresh.addActionListener(e -> loadEmployeesToTable()); //imp
        back.addActionListener(e -> {
            this.dispose(); //imp
        });
        if("UPDATE".equals(mode)) {
            update = createButton("Update Selected");
            update.addActionListener(e -> openUpdateForSelected());
            buttonPanel.add(update);
        } else if("DELETE".equals(mode)) {
            delete = createButton("Delete Selected");
            delete.addActionListener(e -> openDeleteForSelected());
            buttonPanel.add(delete);
        }
        
        buttonPanel.add(refresh);
        buttonPanel.add(back);
        
        status = new JLabel(" ");
        status.setFont(LABEL_FONT);
        status.setForeground(new Color(153,27,27));
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(LIGHT);
        southPanel.add(status,BorderLayout.WEST);
        southPanel.add(buttonPanel,BorderLayout.EAST);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);
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
        
        List<Employee> filtered = new ArrayList<>();
        //start from selected dept or ALL if no dept is selected
        for(Employee emp : employeeManager.getAllEmployees()){
            if(emp == null){//no emp
                continue;
            }
            if(hasDept && !selectedDept.equalsIgnoreCase(emp.getDepartment())){
                continue;//skip emp if it's not of the selectedDept
                
            }
            filtered.add(emp);//match ; add emp to filtered list to search from it
        }
        
        if (!idQuery.isEmpty()) {
            List<Employee> idResult = new ArrayList<>();
            for (Employee emp : filtered) {
                if (String.valueOf(emp.getID()).startsWith(idQuery)) {
                    idResult.add(emp);
                }
            }
            filtered = idResult;
        }
        //searchByName uses String.contains ; live update in searchByName
        if(!nameQuery.isEmpty()){
        String lowerName = nameQuery.toLowerCase();
        List<Employee> nameResult = new ArrayList<>();
        for (Employee emp : filtered) {
            if (emp.getFullName() != null && emp.getFullName().toLowerCase().contains(lowerName)) {
                nameResult.add(emp);
            }
        }
        filtered = nameResult;   
        }
        
        modifyTable(filtered);
    }

    private void modifyTable(List<Employee> list) {
        tableModel.setRowCount(0);
        if (list == null || list.isEmpty()) {
            boolean hasEmp = employeeManager != null && !employeeManager.getAllEmployees().isEmpty();
            if(hasEmp) {
                status.setText("No matching employee found.");
            } else {
                status.setText("No employees registered yet.");
            }
            return;
        }
        status.setText(" ");
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
            UpdateEmployee updateFrame = new UpdateEmployee(employeeManager, departmentManager, selectedId);
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
