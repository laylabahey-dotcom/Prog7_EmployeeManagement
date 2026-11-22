package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {

    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private JButton cancel;

    private static final Color DARK = Color.decode("#344E41");
    private static final Color LIGHT = Color.decode("#DAD7CD");
    private static final Font LABEL_FONT = new Font("Times New Roman", Font.BOLD, 14);

    public LoginScreen() {
        //frame
        setTitle("Employee Management System - Login");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //main panels
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(LIGHT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //username
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        username = new JTextField(15);
        username.setForeground(DARK);
        username.setFont(LABEL_FONT);
        panel.add(username, gbc);

        //password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        password = new JPasswordField(15);
        password.setFont(LABEL_FONT);
        password.setForeground(DARK);
        panel.add(password, gbc);

        //buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setBackground(LIGHT);
        login = new JButton("Login");
        cancel = new JButton("Cancel");
        login.setBackground(LIGHT);
        login.setForeground(DARK);
        login.setBorder(BorderFactory.createLineBorder(DARK, 2));
        login.setFont(LABEL_FONT);
        cancel.setBackground(LIGHT);
        cancel.setForeground(DARK);
        cancel.setBorder(BorderFactory.createLineBorder(DARK, 2));
        cancel.setFont(LABEL_FONT);
        buttons.add(login);
        buttons.add(cancel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttons, gbc);

        //adding panels made to frame (T-T)
        add(panel);

        //actionss
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = username.getText().trim();
                String passkey = new String(password.getPassword());
                if (user.equals("admin") && passkey.equals("admin123")) {
                    JOptionPane.showMessageDialog(java_this(), "Login Successful!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    openMainDashboard();
                } else {
                    JOptionPane.showMessageDialog(java_this(), "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancel.addActionListener(e -> System.exit(0));
    }

    private JFrame java_this() {
        return this;
    }

    private void openMainDashboard() {
        new Dashboard().setVisible(true);
        this.dispose();
    }

}
