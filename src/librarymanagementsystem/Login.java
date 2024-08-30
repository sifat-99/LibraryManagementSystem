package librarymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    Login() {
        setTitle("Library Management System - Login");
        setLayout(null);

        // Custom panel with gradient background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(135, 206, 250); // Light Sky Blue
                Color color2 = new Color(70, 130, 180); // Steel Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/Logo.png"));
        Image i2 = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(i2));
        l1.setBounds(150, 50, 100, 100);
        contentPanel.add(l1);

        JLabel text = new JLabel("Library Management System - Login");
        contentPanel.add(text);
        text.setBounds(300, 50, 400, 100);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("serif", Font.BOLD, 30));

        JLabel username = new JLabel("Username");
        username.setFont(new Font("serif", Font.BOLD, 20));
        contentPanel.add(username);
        username.setBounds(150, 200, 100, 30);

        JTextField t1 = new JTextField();
        t1.setFont(new Font("serif", Font.BOLD, 20));
        t1.setMargin(new Insets(5, 10, 5, 10)); // Add padding inside the input field
        contentPanel.add(t1);
        t1.setBounds(250, 200, 400, 30);

        JLabel password = new JLabel("Password");
        password.setFont(new Font("serif", Font.BOLD, 20));
        contentPanel.add(password);
        password.setBounds(150, 250, 100, 30);

        JPasswordField t2 = new JPasswordField();
        t2.setFont(new Font("serif", Font.BOLD, 20));
        t2.setMargin(new Insets(5, 10, 5, 10)); // Add padding inside the input field
        contentPanel.add(t2);
        t2.setBounds(250, 250, 400, 30);

        JButton login = new JButton("SIGN IN");
        login.setBounds(250, 300, 150, 50);
        login.setFont(new Font("serif", Font.BOLD, 20));
        contentPanel.add(login);

        JButton clear = new JButton("CLEAR");
        clear.setBounds(410, 300, 150, 50);
        clear.setFont(new Font("serif", Font.BOLD, 20));
        contentPanel.add(clear);

        JButton signup = new JButton("SIGN UP");
        signup.setBounds(300, 370, 200, 50);
        signup.setFont(new Font("serif", Font.BOLD, 20));
        contentPanel.add(signup);

        // Add hover effect to buttons
        addHoverEffect(login);
        addHoverEffect(clear);
        addHoverEffect(signup);

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

        setSize(800, 480);
        setVisible(true);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to add hover effect to buttons
    private void addHoverEffect(JButton button) {
        Color originalColor = button.getBackground();
        Color hoverColor = new Color(173, 216, 230); // Light Blue

        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
    }

    public static void main(String args[]) {
        new Login();
    }
}
