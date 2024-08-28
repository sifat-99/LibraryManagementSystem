package librarymanagementsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UserPanel extends JFrame {
  private LibraryDatabaseUpdate libraryDatabaseUpdate;
  private JTextField titleField, authorField, publisherField, yearField, idField;
  private JTextArea outputArea;

  public UserPanel(User user) {
    libraryDatabaseUpdate = new LibraryDatabaseUpdate();
    setTitle("Library Management System -"+ user.getName());
    setLayout(null);
    setSize(1200, 800);
    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/Logo.png"));
    Image i2 = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
    JLabel l1 = new JLabel(new ImageIcon(i2));
    l1.setBounds(
      getSize().width / 2 - 50, 30, 100, 100
    );
    add(l1);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    // Input Panel
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new java.awt.GridLayout(5, 2));
    inputPanel.setBorder(new EmptyBorder(150, 50, 10, 50));

    // Create and configure the JLabel
    JLabel idLabel = new JLabel("Book ID:");
    idLabel.setForeground(Color.RED); // Set text color to red
    idLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font to Arial, bold, size 16
    inputPanel.add(idLabel);
    idField = new JTextField();
    inputPanel.add(idField);

    JLabel titleLabel = new JLabel("Title:");
    titleLabel.setForeground(Color.RED);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    inputPanel.add(titleLabel);
    titleField = new JTextField();
    inputPanel.add(titleField);

    JLabel authorLabel = new JLabel("Author:");
    authorLabel.setForeground(Color.RED);
    authorLabel.setFont(new Font("Arial", Font.BOLD, 16));
    inputPanel.add(authorLabel);
    authorField = new JTextField();
    inputPanel.add(authorField);

    JLabel publisherLabel = new JLabel("Publisher:");
    publisherLabel.setForeground(Color.RED);
    publisherLabel.setFont(new Font("Arial", Font.BOLD, 16));
    inputPanel.add(publisherLabel);
    publisherField = new JTextField();
    inputPanel.add(publisherField);

    JLabel yearLabel = new JLabel("Year:");
    yearLabel.setForeground(Color.RED);
    yearLabel.setFont(new Font("Arial", Font.BOLD, 16));
    inputPanel.add(yearLabel);
    yearField = new JTextField();
    inputPanel.add(yearField);
    add(inputPanel, BorderLayout.NORTH);

    // Button Panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    JButton addButton = new JButton("Borrow Book");
    JButton updateButton = new JButton("Return Book");
    JButton deleteButton = new JButton("Delete Book");
    JButton viewButton = new JButton("View All Books");
    JButton searchButton = new JButton("Search by ID");
    JButton searchButtonByName = new JButton("Search by Name");
    buttonPanel.add(addButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(viewButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(searchButtonByName);
    add(buttonPanel, BorderLayout.CENTER);

    // Output Area
    outputArea = new JTextArea();
outputArea.setEditable(false);
outputArea.setPreferredSize(new Dimension(800, (int) (getSize().height * 0.5)));
outputArea.setFont(new Font("Arial", Font.PLAIN, 16));
outputArea.setLineWrap(true);
outputArea.setWrapStyleWord(true);


// Add the outputArea to a JScrollPane
JScrollPane scrollPane = new JScrollPane(outputArea);
scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

// Set vertical scroll bar policy to always show and horizontal to show as needed
scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

// Create a panel to hold the scroll pane
JPanel outputPanel = new JPanel();
outputPanel.setBorder(new EmptyBorder(10, 40, 0, 40)); // Inner padding
outputPanel.setLayout(new BorderLayout());
outputPanel.add(scrollPane, BorderLayout.CENTER);

// Add the panel to the main container
add(outputPanel, BorderLayout.SOUTH);

    // Button Actions
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addBook();
      }
    });

    updateButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateBook();
      }
    });

    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        deleteBook();
      }
    });

    viewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        viewAllBooks();
      }
    });

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
  }

  private void addBook() {
    try {
      int id = Integer.parseInt(idField.getText());
      String title = titleField.getText();
      String author = authorField.getText();
      String publisher = publisherField.getText();
      int year = Integer.parseInt(yearField.getText());
      Book book = new Book(id, title, author, publisher, year);
      libraryDatabaseUpdate.addBook(book);
      outputArea.setText("Book added successfully!");
    } catch (SQLException | NumberFormatException ex) {
      outputArea.setText("Error adding book: " + ex.getMessage());
    }
  }

  private void updateBook() {
    try {
      int id = Integer.parseInt(idField.getText());
      Book book = libraryDatabaseUpdate.getBookById(id);
      if (book != null) {
        book.setTitle(titleField.getText());
        book.setAuthor(authorField.getText());
        book.setPublisher(publisherField.getText());
        book.setYear(Integer.parseInt(yearField.getText()));
        // libraryDatabaseUpdate.updateBook(book);
        outputArea.setText("Book updated successfully!");
      } else {
        outputArea.setText("Book not found!");
      }
    } catch (SQLException | NumberFormatException ex) {
      outputArea.setText("Error updating book: " + ex.getMessage());
    }
  }

  private void deleteBook() {
    try {
      int id = Integer.parseInt(idField.getText());
      libraryDatabaseUpdate.deleteBook(id);
      outputArea.setText("Book deleted successfully!");
    } catch (SQLException | NumberFormatException ex) {
      outputArea.setText("Error deleting book: " + ex.getMessage());
    }
  }

  private void viewAllBooks() {
    try {
      List<Book> books = libraryDatabaseUpdate.getAllBooks();
      StringBuilder output = new StringBuilder();
      for (Book book : books) {
        output.append("ID: ").append(book.getId()).append("\n")
            .append("Title: ").append(book.getTitle()).append("\n")
            .append("Author: ").append(book.getAuthor()).append("\n")
            .append("Publisher: ").append(book.getPublisher()).append("\n")
            .append("Year: ").append(book.getYear()).append("\n\n");
            
      }
      outputArea.setText(output.toString());
    } catch (SQLException ex) {
      outputArea.setText("Error retrieving books: " + ex.getMessage());
    }
  }

  private void searchBookById() {
    try {
        int id = Integer.parseInt(idField.getText());
        Book book = libraryDatabaseUpdate.getBookById(id);
        if (book != null) {
            outputArea.setText("ID: " + book.getId() + "\n" +
                "Title: " + book.getTitle() + "\n" +
                "Author: " + book.getAuthor() + "\n" +
                "Publisher: " + book.getPublisher() + "\n" +
                "Year: " + book.getYear());
            
            JButton borrowButton = new JButton("Borrow Book");
            borrowButton.setBounds(100, 100, 200, 50);
            borrowButton.setFont(new Font("serif", Font.BOLD, 20));
            borrowButton.setBackground(Color.BLACK);
            borrowButton.setForeground(Color.BLUE);
            outputArea.add(borrowButton);
            outputArea.revalidate(); // Refresh the output area to display the button
            
            borrowButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // libraryDatabaseUpdate.borrowBook(id);
                        JOptionPane.showMessageDialog(null, "Book borrowed successfully!");
                        outputArea.remove(borrowButton); // Remove the borrow button
                        outputArea.revalidate(); // Refresh the output area after removing the button
                        outputArea.repaint(); // Repaint the area to ensure proper display
                    } catch (Exception ex) {
                        outputArea.setText("Error borrowing book: " + ex.getMessage());
                    }
                }
            });
        } else {
            outputArea.setText("Book not found!");
        }
    } catch (SQLException | NumberFormatException ex) {
        outputArea.setText("Error searching book: " + ex.getMessage());
    }
}

  private void searchBookByName() {
    try {
      String title = titleField.getText();
      List<Book> books = libraryDatabaseUpdate.getBookByName(title);
      StringBuilder output = new StringBuilder();
      for (Book book : books) {
        output.append("ID: ").append(book.getId()).append("\n")
            .append("Title: ").append(book.getTitle()).append("\n")
            .append("Author: ").append(book.getAuthor()).append("\n")
            .append("Publisher: ").append(book.getPublisher()).append("\n")
            .append("Year: ").append(book.getYear()).append("\n\n");
            JButton borrowButton = new JButton("Borrow Book");
            borrowButton.setBounds(100, 100, 200, 50);
            borrowButton.setFont(new Font("serif", Font.BOLD, 20));
            borrowButton.setBackground(Color.BLACK);
            borrowButton.setForeground(Color.BLUE);
            outputArea.add(borrowButton);
            borrowButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                try {
                  // libraryDatabaseUpdate.borrowBook(id);
                  outputArea.setText("Book borrowed successfully!");
                } catch (Exception ex) {
                  outputArea.setText("Error borrowing book: " + ex.getMessage());
                }
              }
            });
      }
      outputArea.setText(output.toString());
    } catch (SQLException ex) {
      outputArea.setText("Error retrieving books: " + ex.getMessage());
    }
  }

  public static void main(String[] args) {
    System.out.println("UserPanel.main");
  }

}
