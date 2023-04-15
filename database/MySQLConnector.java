package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_backup";
    private static final String USER = "root";
    private static final String PASS = "Akira123456.";
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
        return conn;
    }
}
