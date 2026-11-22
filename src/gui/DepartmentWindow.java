package gui;

import javax.swing.*;
import java.awt.*;
import Departments.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;

public class DepartmentWindow extends JFrame {

    private final DepartmentManager deptManager;
    private final DefaultTableModel tableModel;
    private final JTable deptTable;
    private final JTextField searchFieldName;
    private final JTextField searchFieldId;
    private final JTextField idField;
    private final JTextField nameField;
    //helpers
    private static final Color green = Color.decode("#344E41");
    private static final Color base = Color.decode("#DAD7CD");
    private static final Color extra1 = new Color(235, 233, 224);
    private static final Color extra2 = new Color(163, 177, 138);

    private static final Font LABEL_FONT = new Font("Times New Roman", Font.BOLD, 16);
    private static final Font INPUT_FONT = new Font("Times New Roman", Font.PLAIN, 15);
    private static final Font BUTTON_FONT = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15);

    public DepartmentWindow(DepartmentManager deptManager) {
        if (deptManager == null) {
            throw new IllegalArgumentException("DepartmentManager cannot be null.");

        }
        this.deptManager = deptManager;
        //frame
        this.setTitle("Manage Departments");
        this.setSize(700, 560);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 8));
        this.getContentPane().setBackground(base);

        //search in top panel
        JPanel topPanel = new JPanel();//1
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));//adds components vertically
        topPanel.setBackground(base);

        JPanel searchNameRow = createRowPanel();//2
        searchFieldName = createTextField(20);
        searchNameRow.add(createLabel("Search by Name:"));
        searchNameRow.add(searchFieldName);

        JPanel searchIdRow = createRowPanel();//3
        searchFieldId = createTextField(10);
        searchIdRow.add(createLabel("Search by ID:"));
        searchIdRow.add(searchFieldId);
        
        //add both search name and id rows into topPanel
        topPanel.add(searchNameRow);
        topPanel.add(searchIdRow);
        add(topPanel, BorderLayout.NORTH);

        //table 
        String[] columns = {"Department ID", "Department Name"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        deptTable = new JTable(tableModel);
        deptTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deptTable.setRowHeight(26);
        deptTable.setFont(INPUT_FONT);
        deptTable.setBackground(extra1); 
        deptTable.setForeground(green); 
        deptTable.setSelectionBackground(green); 
        deptTable.setSelectionForeground(base); 
        deptTable.setGridColor(extra2); 
        JTableHeader header = deptTable.getTableHeader();
        header.setBackground(green); 
        header.setForeground(base); 
        header.setFont(new Font("Times New Roman", Font.BOLD, 16));
        header.setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(deptTable);
        scrollPane.getViewport().setBackground(extra1); 
        add(scrollPane, BorderLayout.CENTER);//table @center

        //add & delete department  @ bottom panel
        JPanel bottomPanel = new JPanel();//4
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(base);

        JPanel addRow = createRowPanel();//5 adds all components horizontally on same line
        idField = createTextField(6);
        nameField = createTextField(16);
        JButton addButton = createButton("Add Department");
        addRow.add(createLabel("ID:"));
        addRow.add(idField);
        addRow.add(createLabel("Name:"));
        addRow.add(nameField);
        addRow.add(addButton);

        JPanel deleteRow = createRowPanel();//6
        JButton deleteButton = createButton("Delete Department");
        deleteRow.add(deleteButton);
        
        //add addrow and deleterow to bottompanel @south
        bottomPanel.add(addRow);
        bottomPanel.add(deleteRow);
        add(bottomPanel, BorderLayout.SOUTH);

        //initial load
        applySearch();
        attachSearchListener(searchFieldName);
        attachSearchListener(searchFieldId);
        addButton.addActionListener(e -> handleAddDepartment());
        deleteButton.addActionListener(e -> handleDeleteDepartment());

    } //end of constructor

    private JPanel createRowPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));
        panel.setBackground(base);
        return panel;
    }

    private JTextField createTextField(int space) {
        JTextField text = new JTextField(space);
        text.setBackground(extra1);
        text.setForeground(green);
        text.setCaretColor(green);
        text.setFont(INPUT_FONT);
        return text;
    }
    
    private JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        label.setForeground(green);
        label.setFont(LABEL_FONT);
        return label;
    }
    private JButton createButton(String text){
        JButton button = new JButton(text);
        button.setBackground(base); // NEW
        button.setForeground(green); // NEW
        button.setFont(BUTTON_FONT);
        return button;
    }
    
    private void attachSearchListener(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applySearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applySearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applySearch();
            }
        });
    }

    public void applySearch() {
        String nameQuery = searchFieldName.getText().trim();
        String idQuery = searchFieldId.getText().trim();

        if (nameQuery.isEmpty() && idQuery.isEmpty()) {
            refreshTable(deptManager.getAllDepartments());
            return;
        }

        Dep foundByName = null;
        if (!nameQuery.isEmpty()) {
            foundByName = deptManager.searchbyName(nameQuery);
            if (foundByName == null) {
                tableModel.setRowCount(0);
                return;
            }
        }

        if (!idQuery.isEmpty()) {
            try {
                int targetId = Integer.parseInt(idQuery);
                Dep foundById = findById(targetId);

                if (foundById == null || (foundByName != null && foundByName.getDepartmentId() != foundById.getDepartmentId())) {
                    tableModel.setRowCount(0);
                    return;
                }

                refreshTable(List.of(foundById));
                return;
            } catch (NumberFormatException ex) {
                tableModel.setRowCount(0);
                return;
            }
        }

        refreshTable(List.of(foundByName));
    }

    private Dep findById(int id) {
        for (Dep dept : deptManager.getAllDepartments()) {
            if (dept.getDepartmentId() == id) {
                return dept;
            }
        }
        return null;
    }

    private void handleAddDepartment() {
        String idText = idField.getText().trim();
        String nameText = nameField.getText().trim();

        if (idText.isEmpty() || nameText.isEmpty()) {
            showError(idText.isEmpty() ? "Department ID cannot be empty." : "Department name cannot be empty.");
            return;
        }
        if (nameText.matches("\\d+")) {
        showError("Department name cannot be numbers only.");
        return;
        }
        try {
            int id = Integer.parseInt(idText);
            deptManager.addDepartment(id, nameText);

            idField.setText("");
            nameField.setText("");
            applySearch();

            JOptionPane.showMessageDialog(
                    this,
                    "Department added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException ex) {
            showError("Department ID must be a valid integer.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleDeleteDepartment() {
        int selectedRow = deptTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department row to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = String.valueOf(tableModel.getValueAt(selectedRow, 1));

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Delete department \"" + name + "\" (ID: " + id + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            deptManager.deleteDepById(id);
            applySearch();
            JOptionPane.showMessageDialog(
                    this,
                    "Department deleted successfully.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void refreshTable(List<Dep> departments) {
        tableModel.setRowCount(0);
        for (Dep dept : departments) {
            tableModel.addRow(new Object[]{dept.getDepartmentId(), dept.getDepartmentName()});
        }
    }
    /*
    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
            DepartmentManager test = new DepartmentManager("departments.csv");
           new DepartmentWindow(test).setVisible(true);
       });
    }
*/
}
