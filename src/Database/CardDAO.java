package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import FileJava.Book;
import FileJava.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CardDAO {

    // Lấy thông tin của tất cả các Card từ bảng cards
    public static List<Card> getAllCards() throws SQLException {
        List<Card> cards = new ArrayList<>();

        String sql = "SELECT * FROM cards";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Card card = new Card(rs.getString("cardId"), rs.getString("borrowerId"),
                        rs.getDate("borrowDate").toLocalDate(), rs.getDate("returnDate").toLocalDate());
                cards.add(card);
            }
        }

        return cards;
    }

    // Phương thức thêm Card trong sql
    public static void addCard(Card card) throws SQLException {
        String sql = "INSERT INTO cards (cardId, borrowerId, borrowDate, returnDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, card.getCardId());
            stmt.setString(2, card.getBorrowerId());
            stmt.setDate(3, java.sql.Date.valueOf(card.getBorrowDate()));
            stmt.setDate(4, java.sql.Date.valueOf(card.getReturnDate()));

            stmt.executeUpdate();
        }
    }

    // Phương thức xóa Card trong sql
    public static void deleteCard(Card card) throws SQLException {
        String sql = "DELETE FROM cards WHERE cardId = ?";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, card.getCardId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting card: " + e.getMessage());
            throw e;
        }
    }

    // Phương thức tìm kiếm Card theo các tiêu chí
    public static List<Card> searchCards(String cardId, String borrowerId, LocalDate borrowDate, LocalDate returnDate) throws SQLException {
        List<Card> result = new ArrayList<>();

        String sql = "SELECT * FROM cards WHERE cardId LIKE ? AND borrowerId LIKE ? ";
        List<Object> params = new ArrayList<>(Arrays.asList("%" + cardId + "%", "%" + borrowerId + "%"));

        if (borrowDate != null) {
            sql += "AND borrowDate = ?";
            params.add(java.sql.Date.valueOf(borrowDate));
        }

        if (returnDate != null) {
            sql += "AND returnDate = ?";
            params.add(java.sql.Date.valueOf(returnDate));
        }

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object param : params) {
                stmt.setObject(index++, param);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Card card = new Card(rs.getString("cardId"), rs.getString("borrowerId"),
                        rs.getDate("borrowDate").toLocalDate(), rs.getDate("returnDate").toLocalDate());
                result.add(card);
            }
        }

        return result;
    }

    // Phương thức chỉnh sửa Card
    public static void updateCard(Card card) throws SQLException {
        String sql = "UPDATE cards SETborrowerId = ?, borrowDate = ?, returnDate = ? WHERE cardId = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, card.getBorrowerId());
            stmt.setDate(2, java.sql.Date.valueOf(card.getBorrowDate()));
            stmt.setDate(3, java.sql.Date.valueOf(card.getReturnDate()));
            stmt.setString(4, card.getCardId());

            stmt.executeUpdate();
        }
    }

    // Phương thức lấy danh sách sách mượn của một Card
    public static ObservableList<Book> getBorrowedBooks(String cardId) throws SQLException {
        ObservableList<Book> borrowedBooks = FXCollections.observableArrayList();

        String sql = "SELECT b.* FROM borrowed_books bb INNER JOIN books b ON bb.book_id = b.id WHERE bb.card_id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cardId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getString("id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("publisher"), rs.getInt("publicationYear"), rs.getInt("quantity"),
                        rs.getString("genre"), rs.getString("status"), rs.getString("location"));
                borrowedBooks.add(book);
            }
        }

        return borrowedBooks;
    }

    // Phương thức thêm sách vào danh sách sách mượn của một Card
    public static void addBookToCard(String cardId, String bookId) throws SQLException {
        String sql = "INSERT INTO borrowed_books (card_id, book_id) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cardId);
            stmt.setString(2, bookId);

            stmt.executeUpdate();
        }
    }

    // Phương thức xóa sách khỏi danh sách sách mượn của một Card
    public static void removeBookFromCard(String cardId, String bookId) throws SQLException {
        String sql = "DELETE FROM borrowed_books WHERE card_id = ? AND book_id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardId);
            pstmt.setString(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error removing book from card: " + e.getMessage());
            throw e;
        }
    }
}

