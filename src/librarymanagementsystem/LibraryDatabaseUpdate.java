package librarymanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LibraryDatabaseUpdate {

  public void addNewUser(String name, String username1, String password1) {
    String query = "INSERT INTO login (name, username, password) VALUES (?, ?, ?)";
    try {
      Connection conn = Conn.getConnection();
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setString(1, name);
      stmt.setString(2, username1);
      stmt.setString(3, password1);
      stmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public User getUserByUsername(String username) throws SQLException {
    // SQL query to select user details based on the username
    String query = "SELECT * FROM login WHERE username = ?";

    // Try-with-resources to ensure proper resource management
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {

      // Set the username parameter in the prepared statement
      stmt.setString(1, username);

      // Execute the query and store the result in ResultSet
      ResultSet rs = stmt.executeQuery();

      // Check if a result is found
      if (rs.next()) {
        // Return a new User object with the retrieved username and password
        return new User(
            rs.getString("name"),
            rs.getString("username"),
            rs.getString("password"));
      }
    } catch (SQLException e) {
      // Print the stack trace for debugging in case of SQL exception
      e.printStackTrace();
    }

    // Return null if no user is found or an exception occurs
    return null;
  }

  public void addBook(Book book) throws SQLException {
    String query = "INSERT INTO books (id, title, author, publisher, year) VALUES (?,?, ?, ?, ?)";
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, book.getId());
      stmt.setString(2, book.getTitle());
      stmt.setString(3, book.getAuthor());
      stmt.setString(4, book.getPublisher());
      stmt.setInt(5, book.getYear());
      stmt.executeUpdate();
    }
  }

  public void updateBook(Book book) throws SQLException {
    String query = "UPDATE books SET title = ?, author = ?, publisher = ?, year = ? WHERE id = ?";
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, book.getTitle());
      stmt.setString(2, book.getAuthor());
      stmt.setString(3, book.getPublisher());
      stmt.setInt(4, book.getYear());
      stmt.setInt(5, book.getId());
      stmt.executeUpdate();
    }
  }

  public void deleteBook(int id) throws SQLException {
    String query = "DELETE FROM books WHERE id = ?";
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    }
  }

  public List<Book> getAllBooks() throws SQLException {
    String query = "SELECT * FROM books";
    List<Book> books = new ArrayList<>();
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        books.add(new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("publisher"),
            rs.getInt("year")));
      }
    }
    return books;
  }

  public Book getBookById(int id) throws SQLException {
    String query = "SELECT * FROM books WHERE id = ?";
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("publisher"),
            rs.getInt("year"));
      }
    }
    return null;
  }

  public List<Book> getBookByName(String name) throws SQLException {
    String query = "SELECT * FROM books WHERE title = ?";
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, name);
      ResultSet rs = stmt.executeQuery();
      List<Book> books = new ArrayList<>();
      while (rs.next()) {
        books.add(new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("publisher"),
            rs.getInt("year")));
      }
      return books;
    }
  }

  public List<Book> getBorrowedBooks(String user) throws SQLException {
    String query = "SELECT * FROM borrowedBooks WHERE user = ?";
    List<Book> books = new ArrayList<>();
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      // Set the user parameter for the query
      stmt.setString(1, user);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          books.add(new Book(
              rs.getInt("id"),
              rs.getString("title"),
              rs.getString("author"),
              rs.getString("publisher"),
              rs.getInt("year")));
        }
      }
    }
    return books;
  }

  public void borrowBook(int bookId, String user) throws SQLException {
    String query = "INSERT INTO borrowedBooks (id, title, author, publisher, year, user) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      // Get the book details from the database
      Book book = getBookById(bookId);

      if (book != null) {
        stmt.setInt(1, book.getId());
        stmt.setString(2, book.getTitle());
        stmt.setString(3, book.getAuthor());
        stmt.setString(4, book.getPublisher());
        stmt.setInt(5, book.getYear());
        stmt.setString(6, user);
        stmt.executeUpdate();
      } else {
        throw new SQLException("Book not found");
      }
    }
  }

  public void returnBook(int bookId, String user) throws SQLException {
    String query = "DELETE FROM borrowedBooks WHERE id = ? AND user = ?";

    try (Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, bookId);
      stmt.setString(2, user);
      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("No book found with the given ID for this user.");
      }
    }
  }

}
