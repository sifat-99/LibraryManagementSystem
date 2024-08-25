package librarymanagementsystem;

import javax.swing.*;
import java.awt.*;
// import java.sql.*;

public class Signup extends JFrame {

  Signup() {
    setTitle("Library Management System");
    setLayout(null);
    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/Logo.png"));
    Image i2 = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
    JLabel l1 = new JLabel(new ImageIcon(i2));
    l1.setBounds(150, 50, 100, 100);
    add(l1);
    JLabel text = new JLabel("Library Management System");
    add(text);
    text.setBounds(300, 50, 400, 100);
    text.setFont(new Font("serif", Font.BOLD, 30));

    JLabel username = new JLabel("Username");
    username.setFont(new Font("serif", Font.BOLD, 20));
    add(username);
    username.setBounds(50, 200, 100, 30);
    JTextField t1 = new JTextField();
    t1.setFont(new Font("serif", Font.BOLD, 20));
    add(t1);
    t1.setBounds(250, 200, 400, 30);
    JLabel password = new JLabel("Password");
    password.setFont(new Font("serif", Font.BOLD, 20));
    add(password);
    password.setBounds(50, 250, 100, 30);
    JPasswordField t2 = new JPasswordField();
    t2.setFont(new Font("serif", Font.BOLD, 20));
    add(t2);
    t2.setBounds(250, 250, 400, 30);

    JLabel confirmPassword = new JLabel("Confirm Password");
    confirmPassword.setFont(new Font("serif", Font.BOLD, 20));
    add(confirmPassword);
    confirmPassword.setBounds(50, 300, 200, 30);
    JPasswordField t3 = new JPasswordField();
    t3.setFont(new Font("serif", Font.BOLD, 20));
    add(t3);
    t3.setBounds(250, 300, 400, 30);

    JLabel alreadyHaveAnAccount = new JLabel("Already have an account?");
    alreadyHaveAnAccount.setFont(new Font("serif", Font.BOLD, 20));
    add(alreadyHaveAnAccount);
    alreadyHaveAnAccount.setBounds(150, 470, 300, 30);

    JButton login = new JButton("SIGN UP");
    login.setBounds(250, 400, 150, 50);
    login.setFont(new Font("serif", Font.BOLD, 20));
    add(login);

    JButton clear = new JButton("CLEAR");
    clear.setBounds(400, 400, 150, 50);
    clear.setFont(new Font("serif", Font.BOLD, 20));
    add(clear);

    JButton signup = new JButton("SIGN IN");
    signup.setBounds(300, 500, 200, 50);
    signup.setFont(new Font("serif", Font.BOLD, 20));
    add(signup);

    login.addActionListener(e -> {
      String username1 = t1.getText();
      @SuppressWarnings("deprecation")
      String password1 = t2.getText();
      @SuppressWarnings("deprecation")
      String confirmPassword1 = t3.getText();
      if (username1.equals("") || password1.equals("") || confirmPassword1.equals("")) {
        JOptionPane.showMessageDialog(null, "Please fill all the fields");
      } else if (!password1.equals(confirmPassword1)) {
        JOptionPane.showMessageDialog(null, "Password and Confirm Password should be same");
      } else {
        LibraryDatabaseUpdate libraryDatabaseUpdate = new LibraryDatabaseUpdate();
        libraryDatabaseUpdate.addNewUser(username1, password1);
        JOptionPane.showMessageDialog(null, "User added successfully");
        setVisible(false);
        new Login().setVisible(true);

      }
    });
    clear.addActionListener(e -> {
      t1.setText("");
      t2.setText("");
    });

    signup.addActionListener(e -> {
      setVisible(false);
      new Login().setVisible(true);
    });

    setSize(800, 600);
    setLocation(
        Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 400,
        Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 300
    );
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  public static void main(String[] args) {
    new Signup();
  }

}
