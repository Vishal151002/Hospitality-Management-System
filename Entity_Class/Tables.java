package hospatalitymanagement.Entity_Class;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class Tables {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "hospatality_managementdb";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Create Hotels table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Hotels (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "location VARCHAR(255), " +
                    "amenities VARCHAR(255))");

            // Create Rooms table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Rooms (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "hotel_id INT, " +
                    "room_number INT, " +
                    "room_type VARCHAR(255), " +
                    "price DECIMAL(10, 2), " +
                    "status VARCHAR(255), " +
                    "FOREIGN KEY (hotel_id) REFERENCES Hotels(id))");

            // Create Guests table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Guests (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "phone_number VARCHAR(255))");

            // Create Reservations table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reservations (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "guest_id INT, " +
                    "room_id INT, " +
                    "check_in DATE, " +
                    "check_out DATE, " +
                    "FOREIGN KEY (guest_id) REFERENCES Guests(id), " +
                    "FOREIGN KEY (room_id) REFERENCES Rooms(id))");

            System.out.println("Database schema created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating database schema: " + e.getMessage());
        }
    }

}
