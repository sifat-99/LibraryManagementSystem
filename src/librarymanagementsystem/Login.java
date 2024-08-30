
package librarymanagementsystem;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    Login() {
        setTitle("Library Management System - Login");
        setLayout(null);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/Logo.png"));
        Image i2 = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(i2));
        l1.setBounds(150, 50, 100, 100);
        add(l1);
        JLabel text = new JLabel("Library Management System - Login");
        add(text);
        text.setBounds(300, 50, 400, 100);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("serif", Font.BOLD, 30));

        JLabel username = new JLabel("Username");
        username.setFont(new Font("serif", Font.BOLD, 20));
        add(username);
        username.setBounds(150, 200, 100, 30);
        JTextField t1 = new JTextField();
        t1.setFont(new Font("serif", Font.BOLD, 20));
        add(t1);
        t1.setBounds(250, 200, 400, 30);

        JLabel password = new JLabel("Password");
        password.setFont(new Font("serif", Font.BOLD, 20));
        add(password);
        password.setBounds(150, 250, 100, 30);
        JPasswordField t2 = new JPasswordField();
        t2.setFont(new Font("serif", Font.BOLD, 20));
        add(t2);
        t2.setBounds(250, 250, 400, 30);

        JButton login = new JButton("SIGN IN");
        login.setBounds(250, 300, 150, 50);
        login.setFont(new Font("serif", Font.BOLD, 20));
        add(login);

        JButton clear = new JButton("CLEAR");
        clear.setBounds(400, 300, 150, 50);
        clear.setFont(new Font("serif", Font.BOLD, 20));
        add(clear);

        JButton signup = new JButton("SIGN UP");
        signup.setBounds(300, 370, 200, 50);
        signup.setFont(new Font("serif", Font.BOLD, 20));
        add(signup);

        login.addActionListener(e -> {
            String username1 = t1.getText();
            @SuppressWarnings("deprecation")
            String password1 = t2.getText();
            if (username1.equals("admin") && password1.equals("admin")) {
                JOptionPane.showMessageDialog(null, "Admin Login Successful");
                new AdminPanel().setVisible(true);
                setVisible(false);
            } else {
                try {
                    LibraryDatabaseUpdate db = new LibraryDatabaseUpdate();
                    User user = db.getUserByUsername(username1);
                    if (user != null && user.getPassword().equals(password1)) {
                        JOptionPane.showMessageDialog(null, "Login Successful");
                        setVisible(false);
                        new UserPanel(user).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    }

                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }

        });

        clear.addActionListener(e -> {
            t1.setText("");
            t2.setText("");
        });

        signup.addActionListener(e -> {
            setVisible(false);
            new Signup().setVisible(true);
        });

        getContentPane().setBackground(Color.WHITE);
        setSize(800, 480);
        setVisible(true);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String args[]) {
        new Login();
    }

}
