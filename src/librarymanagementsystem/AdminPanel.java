package librarymanagementsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getSize().height / 2);

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(135, 206, 250);
                Color color2 = new Color(70, 130, 180);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        contentPanel.setLayout(new BorderLayout());
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/Logo.png"));
        Image i2 = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(i2));
        l1.setBounds(300, 30, 100, 100);
        contentPanel.add(l1, BorderLayout.NORTH);
        JLabel text = new JLabel("Library Management System - Admin Panel");
        text.setBounds(410, 40, 700, 100);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("serif", Font.BOLD, 30));
        contentPanel.add(text, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(150, 50, 10, 50));
        inputPanel.setOpaque(false);

        JLabel idLabel = new JLabel("Book ID:");
        idLabel.setForeground(Color.RED);
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(idLabel);
        idField = new JTextField();
        idField.setMargin(new Insets(5, 10, 5, 10));
        inputPanel.add(idField);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(titleLabel);
        titleField = new JTextField();
        titleField.setMargin(new Insets(5, 10, 5, 10));
        inputPanel.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setForeground(Color.RED);
        authorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(authorLabel);
        authorField = new JTextField();
        authorField.setMargin(new Insets(5, 10, 5, 10));
        inputPanel.add(authorField);

        JLabel publisherLabel = new JLabel("Publisher:");
        publisherLabel.setForeground(Color.RED);
        publisherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(publisherLabel);
        publisherField = new JTextField();
        publisherField.setMargin(new Insets(5, 10, 5, 10));
        inputPanel.add(publisherField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setForeground(Color.RED);
        yearLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(yearLabel);
        yearField = new JTextField();
        yearField.setMargin(new Insets(5, 10, 5, 10));
        inputPanel.add(yearField);

        contentPanel.add(inputPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add Book");
        addButton.setBounds(10, 10, 150, 30);
        JButton updateButton = new JButton("Update Book");
        updateButton.setBounds(165, 10, 150, 30);
        JButton deleteButton = new JButton("Delete Book");
        deleteButton.setBounds(320, 10, 150, 30);
        JButton viewButton = new JButton("View All Books");
        viewButton.setBounds(475, 10, 150, 30);
        JButton searchButton = new JButton("Search by ID");
        searchButton.setBounds(630, 10, 150, 30);
        JButton searchButtonByName = new JButton("Search by Name");
        searchButtonByName.setBounds(785, 10, 150, 30);

        addHoverEffect(addButton);
        addHoverEffect(updateButton);
        addHoverEffect(deleteButton);
        addHoverEffect(viewButton);
        addHoverEffect(searchButton);
        addHoverEffect(searchButtonByName);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(searchButtonByName);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        // Results Panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        resultsPanel.setOpaque(true);
        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
        resultsScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int desiredHeight = (int) (screenHeight * 0.35);
        resultsScrollPane.setPreferredSize(new Dimension(resultsScrollPane.getPreferredSize().width, desiredHeight));

        contentPanel.add(resultsScrollPane, BorderLayout.SOUTH);

        // Button Action Listeners
        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        viewButton.addActionListener(e -> viewAllBooks());
        searchButton.addActionListener(e -> searchBookById());
        searchButtonByName.addActionListener(e -> searchBookByName());
    }

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
            resultsPanel.removeAll();
            if (books.isEmpty()) {
                showMessage("No books found!");
            } else {
                for (Book book : books) {
                    displayBook(book);
                }
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
        bookPanel.add(new JLabel(" ID: " + book.getId()));
        bookPanel.add(Box.createVerticalStrut(5));
        bookPanel.add(new JLabel(" Title: " + book.getTitle()));
        bookPanel.add(Box.createVerticalStrut(5));
        bookPanel.add(new JLabel(" Author: " + book.getAuthor()));
        bookPanel.add(Box.createVerticalStrut(5));
        bookPanel.add(new JLabel(" Publisher: " + book.getPublisher()));
        bookPanel.add(Box.createVerticalStrut(5));
        bookPanel.add(new JLabel(" Year: " + book.getYear()));
        resultsPanel.add(bookPanel);
        resultsPanel.revalidate();
        resultsPanel.repaint();

    }

    public static void main(String[] args) {
        System.out.println("Protected Admin Panel");
    }
}
