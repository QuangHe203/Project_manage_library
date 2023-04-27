package Database;

import FileJava.Borrower;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getDate("date_of_birth").toLocalDate(),
                    rs.getString("email"),
                    rs.getString("type")
                );
                borrowers.add(borrower);
            }
            return borrowers;
        }
    }
}
