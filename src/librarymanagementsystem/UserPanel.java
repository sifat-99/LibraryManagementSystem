package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UserPanel extends JFrame {
    private LibraryDatabaseUpdate libraryDatabaseUpdate;
    private JTextField titleField, idField;
    private JPanel resultsPanel;
    private User currentUser;

    public UserPanel(User user) {
        currentUser = user;
        libraryDatabaseUpdate = new LibraryDatabaseUpdate();
        setTitle("Library Management System - " + user.getName());
        setLayout(null);

        setSize(1200, 800);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/Logo.png"));
        Image i2 = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        JLabel logoLabel = new JLabel(new ImageIcon(i2));
        logoLabel.setBounds(550, 10, 100, 100);
        add(logoLabel);

        // Input Panel Components
        JLabel idLabel = new JLabel("Book ID:");
        idLabel.setForeground(Color.RED);
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        idLabel.setBounds(50, 150, 100, 30);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(160, 150, 200, 30);
        add(idField);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(50, 200, 100, 30);
        add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(160, 200, 200, 30);
        add(titleField);

        // Button Panel Components
        JButton searchButton = new JButton("Search by ID");
        searchButton.setBounds(400, 150, 150, 30);
        add(searchButton);

        JButton searchButtonByName = new JButton("Search by Name");
        searchButtonByName.setBounds(400, 200, 150, 30);
        add(searchButtonByName);

        JButton borrowedBooksButton = new JButton("Borrowed Books");
        borrowedBooksButton.setBounds(400, 250, 150, 30);
        add(borrowedBooksButton);

        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.setBounds(400, 300, 150, 30);
        add(returnBookButton);

        // Output Area
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBounds(50, 350, 1000, 300);
        add(scrollPane);

        // Button Actions
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBookById();
            }
        });

        searchButtonByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBookByName();
            }
        });

        borrowedBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBorrowedBooks();
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
    }

    private void searchBookById() {
        resultsPanel.removeAll();
        try {
            int id = Integer.parseInt(idField.getText());
            Book book = libraryDatabaseUpdate.getBookById(id);
            if (book != null) {
                displayBook(book);
            } else {
                showMessage("Book not found!");
            }
        } catch (SQLException | NumberFormatException ex) {
            showMessage("Error searching book: " + ex.getMessage());
        }
    }

    private void searchBookByName() {
      try {
        String title = titleField.getText();
        List<Book> books = libraryDatabaseUpdate.getBookByName(title);
        // StringBuilder output = new StringBuilder();
        for (Book book : books) {
          displayBook(book);
        }
      } catch (SQLException ex) {
        showMessage("Error searching book: " + ex.getMessage());
      }
    }

    private void displayBooks(Book book) {
      JPanel bookPanel = new JPanel();
      bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
      bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
      bookPanel.add(new JLabel("ID: " + book.getId()));
      bookPanel.add(new JLabel("Title: " + book.getTitle()));
      bookPanel.add(new JLabel("Author: " + book.getAuthor()));
      bookPanel.add(new JLabel("Publisher: " + book.getPublisher()));
      bookPanel.add(new JLabel("Year: " + book.getYear()));
      bookPanel.add(Box.createVerticalStrut(10)); // Add vertical gap

      JButton borrowButton = new JButton("Return Book");
      borrowButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            libraryDatabaseUpdate.returnBook(book.getId(), currentUser.getName());
            JOptionPane.showMessageDialog(null, "Book Returned successfully!");
            borrowButton.setEnabled(false);
          } catch (Exception ex) {
            showMessage("Error borrowing book: " + ex.getMessage());
          }
        }
      });

      bookPanel.add(borrowButton);
      resultsPanel.add(bookPanel);
      resultsPanel.revalidate();
      resultsPanel.repaint();
    }

    private void displayBook(Book book) {
      JPanel bookPanel = new JPanel();
      bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
      bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
      bookPanel.add(new JLabel("ID: " + book.getId()));
      bookPanel.add(new JLabel("Title: " + book.getTitle()));
      bookPanel.add(new JLabel("Author: " + book.getAuthor()));
      bookPanel.add(new JLabel("Publisher: " + book.getPublisher()));
      bookPanel.add(new JLabel("Year: " + book.getYear()));
      bookPanel.add(Box.createVerticalStrut(10));

      JButton borrowButton = new JButton("Borrow Book");
      borrowButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            libraryDatabaseUpdate.borrowBook(book.getId(), currentUser.getName());
            JOptionPane.showMessageDialog(null, "Book borrowed successfully!");
            borrowButton.setEnabled(false);
          } catch (Exception ex) {
            showMessage("Error borrowing book: " + ex.getMessage());
          }
        }
      });
      bookPanel.add(borrowButton);
      resultsPanel.add(bookPanel);
      resultsPanel.revalidate();
      resultsPanel.repaint();
    }

    private void viewBorrowedBooks() {
        resultsPanel.removeAll();
        try {
            List<Book> books = libraryDatabaseUpdate.getBorrowedBooks(currentUser.getName());
            if (books.isEmpty()) {
                showMessage("No borrowed books found.");
            } else {
                for (Book book : books) {
                    displayBooks(book);
                }
            }
        } catch (SQLException ex) {
            showMessage("Error retrieving borrowed books: " + ex.getMessage());
        }
    }

    private void returnBook() {
        try {
            int id = Integer.parseInt(idField.getText());
            libraryDatabaseUpdate.returnBook(id, currentUser.getName());
            showMessage("Book returned successfully!");
        } catch (SQLException | NumberFormatException ex) {
            showMessage("Error returning book: " + ex.getMessage());
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        // You can test your UserPanel class here
        System.out.println("UserPanel main method called");
    }
}
