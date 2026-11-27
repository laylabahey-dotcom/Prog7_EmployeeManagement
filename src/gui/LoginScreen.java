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
    private static final Font BIG = new Font("Times New Roman", Font.BOLD, 16);

    public LoginScreen() {
        //frame
        setTitle("Employee Management System - Login");
        setSize(480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        //main panels
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER,10,15));
        header.setBackground(DARK);
        JLabel title = new JLabel("Employee Management System");
        title.setForeground(LIGHT);
        title.setFont(LABEL_FONT);
        header.add(title);
        add(header,BorderLayout.NORTH);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //username
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel user = new JLabel("Username:");
        user.setFont(BIG);
        user.setForeground(DARK);
        panel.add(user,gbc);
        gbc.gridx = 1;
        username = new JTextField(18);
        username.setForeground(DARK);
        username.setFont(BIG);
        username.setPreferredSize(new Dimension(220,32));
        panel.add(username, gbc);

        //password
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel pass = new JLabel("Password:");
        pass.setFont(BIG);
        pass.setForeground(DARK);
        panel.add(pass,gbc);
        gbc.gridx = 1;
        password = new JPasswordField(18);
        password.setFont(BIG);
        password.setForeground(DARK);
        password.setPreferredSize(new Dimension(220,32));
        panel.add(password, gbc);

        //buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttons.setBackground(LIGHT);
        login = new JButton("Login");
        cancel = new JButton("Cancel");
        for(JButton btn : new JButton[]{login,cancel}) {
            btn.setBackground(LIGHT);
            btn.setForeground(DARK);
            btn.setFont(BIG);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(110,36));
            btn.setBorder(BorderFactory.createLineBorder(DARK,2));
        }
        buttons.add(login);
        buttons.add(cancel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(18,10,5,10);
        panel.add(buttons, gbc);

        //adding panels made to frame (T-T)
        add(panel,BorderLayout.CENTER);
        getRootPane().setDefaultButton(login); // 3shan lama enter is pressed it logs in automatically

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
