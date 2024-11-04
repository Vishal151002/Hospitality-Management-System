package hospatalitymanagement.DAO_Class.db_management;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GuestManagement {

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

            int guestIdToDelete = 3; // Example guest ID to delete

            // Step 1: Delete the specific guest
            String deleteQuery = "DELETE FROM Guests WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, guestIdToDelete);
                pstmt.executeUpdate();
            }

            // Step 2: Reorder the remaining IDs
            reorderGuestIds(conn);

            System.out.println("Guest deleted and IDs reordered successfully.");

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

    private static void reorderGuestIds(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        // Step 3: Set the initial value of @new_id
        stmt.execute("SET @new_id = 0");

        // Step 4: Update the guest IDs to be sequential
        String reorderQuery = "UPDATE Guests SET id = (@new_id := @new_id + 1) ORDER BY id";
        stmt.execute(reorderQuery);

        // Step 5: Reset the AUTO_INCREMENT to the max id + 1
        String resetAutoIncrementQuery = "ALTER TABLE Guests AUTO_INCREMENT = 1";
        stmt.execute(resetAutoIncrementQuery);

        stmt.close();
    }
}
