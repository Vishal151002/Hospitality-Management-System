package hospatalitymanagement.DAO_Class.db_management;
import hospatalitymanagement.DAO_Class.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    // Method to reset ID if only one record exists in the specified table
    public static void resetIdIfOnlyOneRecordExists(String tableName) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement countStmt = conn.prepareStatement("SELECT COUNT(*) FROM " + tableName);
             PreparedStatement selectStmt = conn.prepareStatement("SELECT id FROM " + tableName + " LIMIT 1");
             PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id != ?");
             PreparedStatement resetAutoIncrementStmt = conn.prepareStatement("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1")) {

            // Count how many records exist in the table
            try (ResultSet rs = countStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 1) {
                    // If only one record exists, get its ID
                    try (ResultSet selectRs = selectStmt.executeQuery()) {
                        if (selectRs.next()) {
                            int id = selectRs.getInt("id");
                            
                            // Delete all records except the one with the fetched ID
                            deleteStmt.setInt(1, id);
                            deleteStmt.executeUpdate();
                            
                            // Reset the auto-increment value to 1
                            resetAutoIncrementStmt.executeUpdate();
                            
                            System.out.println("ID reset to 1 for the only remaining record in table " + tableName + ".");
                        }
                    }
                }
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            resetIdIfOnlyOneRecordExists("Rooms");
            resetIdIfOnlyOneRecordExists("Hotels");
            resetIdIfOnlyOneRecordExists("Reservations");
            resetIdIfOnlyOneRecordExists("Guests");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
