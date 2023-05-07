package Database;

import FileJava.Borrower;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BorrowerDAO {
    // Lấy toàn bộ danh sách người mượn từ bảng borrowers
    public static List<Borrower> getAllBorrowers() throws SQLException {
        List<Borrower> borrowers = new ArrayList<>();
        // Chuẩn bị câu truy vấn SQL
        String sql = "SELECT * FROM borrowers";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Borrower borrower = new Borrower(
                        rs.getString("id"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getDate("dateOfBirth").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("type"));
                borrowers.add(borrower);
            }
            return borrowers;
        }
    }

    // Phương thức thêm người mượn vào cơ sở dữ liệu
    public static void addBorrower(Borrower borrower) throws SQLException {
        String sql = "INSERT INTO borrowers (id, fullName, phoneNumber, date_of_birth,  email,  type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, borrower.getId());
            stmt.setString(2, borrower.getFullName());
            stmt.setString(3, borrower.getPhoneNumber());
            stmt.setDate(4, java.sql.Date.valueOf(borrower.getDateOfBirth()));
            stmt.setString(5, borrower.getEmail());
            stmt.setString(6, borrower.getType());

            stmt.executeUpdate();
        }
    }

    // Phương thức xóa người mượn khỏi cơ sở dữ liệu
    public static void deleteBorrower(Borrower borrower) throws SQLException {
        String sql = "DELETE FROM borrowers WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, borrower.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting borrower: " + e.getMessage());
            throw e;
        }
    }

    // Phương thức tìm kiếm người mượn theo các tiêu chí
    public static List<Borrower> searchBorrowers(String id, String fullName, String phoneNumber, LocalDate dateOfBirth,
            String email, String type) throws SQLException {
        List<Borrower> result = new ArrayList<>();

        String sql = "SELECT * FROM borrowers WHERE id LIKE ? AND fullName LIKE ? AND phoneNumber LIKE ? AND email LIKE ? AND type LIKE ? ";
        List<Object> params = new ArrayList<>(Arrays.asList("%" + id + "%", "%" + fullName + "%",
                "%" + phoneNumber + "%", "%" + email + "%", "%" + type + "%"));

        if (dateOfBirth != null) {
            sql += "AND date_of_birth = ?";
            params.add(java.sql.Date.valueOf(dateOfBirth));
        }

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object param : params) {
                stmt.setObject(index++, param);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Borrower borrower = new Borrower(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("type"));
                result.add(borrower);
            }
        }

        return result;
    }

    // Phương thức chỉnh sửa thông tin người mượn
    public static void updateBorrower(Borrower borrower) throws SQLException {
        String sql = "UPDATE borrowers SET fullName = ?, phoneNumber = ?, date_of_birth = ?, email = ?, type = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, borrower.getFullName());
            stmt.setString(2, borrower.getPhoneNumber());
            stmt.setDate(3, java.sql.Date.valueOf(borrower.getDateOfBirth()));
            stmt.setString(4, borrower.getEmail());
            stmt.setString(5, borrower.getType());
            stmt.setString(6, borrower.getId());

            stmt.executeUpdate();
        }
    }
}
