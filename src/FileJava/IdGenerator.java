package FileJava;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Database.MySQLConnection;

public class IdGenerator {

    private static String generateNextId(String tableName, String idColumnName, String prefix) {
        String generatedId = null;
        try (Connection conn = MySQLConnection.getConnection()) {
            String query = "SELECT MAX(" + idColumnName + ") FROM " + tableName;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String currentMaxId = rs.getString(1);
                if (currentMaxId != null) {
                    String numericPart = currentMaxId.substring(prefix.length());
                    int nextId = Integer.parseInt(numericPart) + 1;
                    generatedId = prefix + String.format("%03d", nextId);
                } else {
                    generatedId = prefix + "001"; // Giá trị mặc định khi không có dữ liệu
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public static String generateNextBookId() {
        return generateNextId("books", "id", "BK");
    }

    public static String generateNextBorrowerId() {
        return generateNextId("borrowers", "id", "BR");
    }

    public static String generateNextCardId() {
        return generateNextId("cards", "cardId", "C");
    }
}
