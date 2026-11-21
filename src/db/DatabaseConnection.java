package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hotelmanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to database. Please check your credentials and ensure the database 'hotelmanagement' exists.");
        }
        return connection;
    }
}
