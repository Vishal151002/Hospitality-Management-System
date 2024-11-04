package hospatalitymanagement.DAO_Class;
import  hospatalitymanagement.Entity_Class.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class GuestDAO {
	 	public static List<Guest> getAllGuests() throws SQLException {
	        List<Guest> guests = new ArrayList<>();
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Guests")) {
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    Guest guest = new Guest();
	                    guest.setId(rs.getInt("id"));
	                    guest.setName(rs.getString("name"));
	                    guest.setEmail(rs.getString("email"));
	                    guest.setPhoneNumber(rs.getString("phone_number"));
	                    guests.add(guest);
	                }
	            }
	        }
	        return guests;
	    }

	    public static Guest getGuestById(int id) throws SQLException {
	        Guest guest = null;
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Guests WHERE id = ?")) {
	            stmt.setInt(1, id);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    guest = new Guest();
	                    guest.setId(rs.getInt("id"));
	                    guest.setName(rs.getString("name"));
	                    guest.setEmail(rs.getString("email"));
	                    guest.setPhoneNumber(rs.getString("phone_number"));
	                }
	            }
	        }
	        return guest;
	    }

	    public static void addGuest(Guest guest) throws SQLException {
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Guests (name, email, phone_number) VALUES (?, ?, ?)")) {
	            stmt.setString(1, guest.getName());
	            stmt.setString(2, guest.getEmail());
	            stmt.setString(3, guest.getPhoneNumber());
	            stmt.executeUpdate();
	        }
	    }

	    public static void updateGuest(Guest guest) throws SQLException {
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("UPDATE Guests SET name = ?, email = ?, phone_number = ? WHERE id = ?")) {
	            stmt.setString(1, guest.getName());
	            stmt.setString(2, guest.getEmail());
	            stmt.setString(3, guest.getPhoneNumber());
	            stmt.setInt(4, guest.getId());
	            stmt.executeUpdate();
	        }
	    }
	    
	    public void updateGuestDetails(int guestId, String newName, String newEmail, String newPhoneNumber) {
	        try {
	            Guest guest = GuestDAO.getGuestById(guestId);
	            if (guest != null) {
	                guest.setName(newName);
	                guest.setEmail(newEmail);
	                guest.setPhoneNumber(newPhoneNumber);
	                GuestDAO.updateGuest(guest);
	                System.out.println("Guest updated successfully.");
	            } else {
	                System.out.println("Guest not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


	    public static void deleteGuest(int id) throws SQLException {
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Guests WHERE id = ?")) {
	            stmt.setInt(1, id);
	            stmt.executeUpdate();
	            
	            // Reset auto-increment value
	            try (Statement resetStmt = conn.createStatement()) {
	                resetStmt.execute("SET @new_id = 0");
	                resetStmt.execute("UPDATE Guests SET id = (@new_id := @new_id + 1) ORDER BY id");
	                resetStmt.execute("ALTER TABLE Guests AUTO_INCREMENT = 1");
	            }
	        }
	    }

}
