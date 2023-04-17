package database;

import database.MySQLConnection;
import database.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
}