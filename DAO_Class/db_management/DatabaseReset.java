package hospatalitymanagement.DAO_Class.db_management;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseReset {

    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/hospatality_managementdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Establish the database connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();

            // Disable safe update mode
            stmt.execute("SET SQL_SAFE_UPDATES = 0");

            // Delete all records from the 'Reservations' and 'Guests' tables
            stmt.execute("DELETE FROM Reservations");
            stmt.execute("DELETE FROM rooms");

            // Reset the auto-increment value to 1 for 'Reservations' and 'Guests'
            stmt.execute("ALTER TABLE Reservations AUTO_INCREMENT = 1");
            stmt.execute("ALTER TABLE rooms AUTO_INCREMENT = 1");

            // Re-enable safe update mode
            stmt.execute("SET SQL_SAFE_UPDATES = 1");

            System.out.println("Database reset operations completed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
