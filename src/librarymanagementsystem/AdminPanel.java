package librarymanagementsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdminPanel extends JFrame {
  private LibraryDatabaseUpdate libraryDatabaseUpdate;
  private JTextField titleField, authorField, publisherField, yearField, idField;
  private JPanel resultsPanel;

  public AdminPanel() {
    libraryDatabaseUpdate = new LibraryDatabaseUpdate();
    setTitle("Library Management System - Admin Panel");
    setSize(1200, 800);
    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/Logo.png"));
    Image i2 = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
    JLabel l1 = new JLabel(new ImageIcon(i2));
    l1.setBounds(getSize().width / 2 - 50, 30, 100, 100);
    add(l1);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Input Panel
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new GridLayout(5, 2));
    inputPanel.setBorder(new EmptyBorder(150, 50, 10, 50));

    // Create and configure the JLabel
    JLabel idLabel = new JLabel("Book ID:");
    idLabel.setForeground(Color.RED);
    idLabel.setFont(new Font("Arial", Font.BOLD, 16));
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
    buttonPanel.setLayout(null);
    buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    JButton addButton = new JButton("Add Book");
    addButton.setBounds(10, 10, 100, 30);
    JButton updateButton = new JButton("Update Book");
    updateButton.setBounds(120, 10, 120, 30);
    JButton deleteButton = new JButton("Delete Book");
    deleteButton.setBounds(250, 10, 110, 30);
    JButton viewButton = new JButton("View All Books");
    viewButton.setBounds(370, 10, 130, 30);
    JButton searchButton = new JButton("Search by ID");
    searchButton.setBounds(510, 10, 120, 30);
    JButton searchButtonByName = new JButton("Search by Name");
    searchButtonByName.setBounds(640, 10, 140, 30);
    buttonPanel.add(addButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(viewButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(searchButtonByName);
    add(buttonPanel, BorderLayout.CENTER);

    // Results Panel
    resultsPanel = new JPanel();
    resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
    resultsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
    resultsScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    resultsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    // Calculate the height as 40% of the screen height
    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    int desiredHeight = (int) (screenHeight * 0.4);
    resultsScrollPane.setPreferredSize(new Dimension(resultsScrollPane.getPreferredSize().width, desiredHeight));

    add(resultsScrollPane, BorderLayout.SOUTH);

    // // Pagination Button Panel
    // nextButton = new JButton("Next");
    // prevButton = new JButton("Previous");
    // firstButton = new JButton("First");
    // lastButton = new JButton("Last");
    // JPanel paginationButtonPanel = new JPanel();
    // paginationButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    // paginationButtonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    // paginationButtonPanel.add(firstButton);
    // paginationButtonPanel.add(prevButton);
    // paginationButtonPanel.add(nextButton);
    // paginationButtonPanel.add(lastButton);

    // Add the pagination button panel at the bottom of the screen

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
      showMessage("Book added successfully!");
    } catch (SQLException | NumberFormatException ex) {
      showMessage("Error adding book: " + ex.getMessage());
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
        libraryDatabaseUpdate.updateBook(book);
        showMessage("Book updated successfully!");
      } else {
        showMessage("Book not found!");
      }
    } catch (SQLException | NumberFormatException ex) {
      showMessage("Error updating book: " + ex.getMessage());
    }
  }

  private void deleteBook() {
    try {
      int id = Integer.parseInt(idField.getText());
      libraryDatabaseUpdate.deleteBook(id);
      showMessage("Book deleted successfully!");
    } catch (SQLException | NumberFormatException ex) {
      showMessage("Error deleting book: " + ex.getMessage());
    }
  }

  private void viewAllBooks() {
    try {
      List<Book> books = libraryDatabaseUpdate.getAllBooks();
      for(Book book : books) {
        displayBook(book);
      }

    } catch (SQLException ex) {
      showMessage("Error viewing books: " + ex.getMessage());
    }
  }

  private void searchBookById() {
    try {
      int id = Integer.parseInt(idField.getText());
      Book book = libraryDatabaseUpdate.getBookById(id);
      resultsPanel.removeAll();
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
      resultsPanel.removeAll();
      if (books.isEmpty()) {
        showMessage("No books found!");
      } else {
        for (Book book : books) {
          displayBook(book);
        }
      }
    } catch (SQLException ex) {
      showMessage("Error searching book: " + ex.getMessage());
    }
  }

  private void showMessage(String message) {
    resultsPanel.removeAll();
    JLabel messageLabel = new JLabel(message);
    messageLabel.setForeground(Color.RED);
    resultsPanel.add(messageLabel);
    resultsPanel.revalidate();
    resultsPanel.repaint();
  }

  private void displayBook(Book book) {
    JPanel bookPanel = new JPanel();
    bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
    bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    bookPanel.add(new JLabel("ID: " + book.getId()));
    bookPanel.add(Box.createVerticalStrut(5));
    bookPanel.add(new JLabel("Title: " + book.getTitle()));
    bookPanel.add(Box.createVerticalStrut(5)); 
    bookPanel.add(new JLabel("Author: " + book.getAuthor()));
    bookPanel.add(Box.createVerticalStrut(5)); 
    bookPanel.add(new JLabel("Publisher: " + book.getPublisher()));
    bookPanel.add(Box.createVerticalStrut(5)); 
    bookPanel.add(new JLabel("Year: " + book.getYear()));
    resultsPanel.add(bookPanel);
    resultsPanel.revalidate();
    resultsPanel.repaint();
  }




  public static void main(String[] args) {
    AdminPanel adminPanel = new AdminPanel();
    adminPanel.setVisible(true);
  }
}
