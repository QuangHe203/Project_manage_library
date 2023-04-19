package Database;

import FileJava.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDAO {
    // Lấy thông tin của tất cả các quyển sách từ bảng books
    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book(rs.getString("id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("publisher"), rs.getInt("publicationYear"), rs.getInt("quantity"),
                        rs.getString("genre"), rs.getString("status"), rs.getString("location"));
                books.add(book);
            }
        }

        return books;
    }
// Phương thức thêm sách trong sql
    public static void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (id, title, author, publisher, publicationYear, quantity, genre, status, location) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getPublisher());
            stmt.setInt(5, book.getPublicationYear());
            stmt.setInt(6, book.getQuantity());
            stmt.setString(7, book.getGenre());
            stmt.setString(8, book.getStatus());
            stmt.setString(9, book.getLocation());
    
            stmt.executeUpdate();
        }
    }
    
// Phương thức xóa sách trong sql
    public static void deleteBook(Book book) throws SQLException {
        // xóa sách khỏi cơ sở dữ liệu
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
            throw e;
        }
    }

    // Phương thức tìm kiếm sách theo các tiêu chí
    public static List<Book> searchBooks(String id, String title, String author, String publisher, String genre, Integer publishYear) throws SQLException {
        List<Book> result = new ArrayList<>();
    
        String sql = "SELECT * FROM books WHERE id LIKE ? AND title LIKE ? AND author LIKE ? AND publisher LIKE ? AND genre LIKE ? ";
        List<Object> params = new ArrayList<>(Arrays.asList("%" + id + "%", "%" + title + "%", "%" + author + "%", "%" + publisher + "%", "%" + genre + "%"));
    
        if (publishYear != null) {
            sql += "AND publicationYear = ?";
            params.add(publishYear);
        }
    
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object param : params) {
                stmt.setObject(index++, param);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getString("id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("publisher"), rs.getInt("publicationYear"), rs.getInt("quantity"),
                        rs.getString("genre"), rs.getString("status"), rs.getString("location"));
                result.add(book);
            }
        }
    
        return result;
    }
// Phương thức chỉnh sửa sách
    public static void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publicationYear = ?, quantity = ?, genre = ?, status = ?, location = ? WHERE id = ?";
    
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setInt(4, book.getPublicationYear());
            stmt.setInt(5, book.getQuantity());
            stmt.setString(6, book.getGenre());
            stmt.setString(7, book.getStatus());
            stmt.setString(8, book.getLocation());
            stmt.setString(9, book.getId());
    
            stmt.executeUpdate();
        }
    }
    
}