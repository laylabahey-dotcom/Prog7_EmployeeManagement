package gui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        //frame
        setTitle("Employee Management System - Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //panels
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        top.setBackground(Color.BLUE);
        JLabel welcome = new JLabel("Welcome to the Employee Management System!!");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 16));
        top.add(welcome, BorderLayout.WEST);
        JButton logout = new JButton("Logout");
        top.add(logout, BorderLayout.EAST);

        JPanel centre = new JPanel(new GridLayout(3, 2, 15, 15));
        centre.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JButton addEmp = new JButton("Add New Employee");
        JButton viewEmp = new JButton("View & Search Employees");
        JButton updateEmp = new JButton("Update Employee Record");
        JButton deleteEmp = new JButton("Delete Employee");
        JButton dept = new JButton("Manage Departments");
        JButton exit = new JButton("Exit App");
        JButton[] navigation = {addEmp, viewEmp, updateEmp, deleteEmp, dept, exit};
        for (JButton button : navigation) {
            button.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            button.setFocusPainted(false);
        }
        centre.add(addEmp);
        centre.add(viewEmp);
        centre.add(updateEmp);
        centre.add(deleteEmp);
        centre.add(dept);
        centre.add(exit);

        //add panels to frame
        add(top, BorderLayout.NORTH);
        add(centre, BorderLayout.CENTER);

        //logout function
        logout.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            this.dispose();
        });

        addEmp.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Openning Add Employee Panel", "Navigation", JOptionPane.INFORMATION_MESSAGE);
            //add employee method connection
        });
        viewEmp.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Openning View/Search Table", "Navigation", JOptionPane.INFORMATION_MESSAGE);
            //view/search employee method connection
        });
        updateEmp.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Openning Update Employee Panel", "Navigation", JOptionPane.INFORMATION_MESSAGE);
            //view/search employee method connection
        });
        deleteEmp.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Openning Delete Employee Feature", "Navigation", JOptionPane.INFORMATION_MESSAGE);
            //view/search employee method connection
        });
        dept.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Openning Department Management Panel", "Navigation", JOptionPane.INFORMATION_MESSAGE);
            //department management method connection
        });

        exit.addActionListener(e -> System.exit(0));
    }

    //Main fn to try out bas i'll delete it isa
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new Dashboard().setVisible(true);
//        });
//    }
}
