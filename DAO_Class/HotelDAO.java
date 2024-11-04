package hospatalitymanagement.DAO_Class;
import  hospatalitymanagement.Entity_Class.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class HotelDAO {
	   public static List<Hotel> getAllHotels() throws SQLException {
	        List<Hotel> hotels = new ArrayList<>();
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Hotels")) {
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    Hotel hotel = new Hotel();
	                    hotel.setId(rs.getInt("id")); 
	                    hotel.setName(rs.getString("name"));
	                    hotel.setLocation(rs.getString("location"));
	                    hotel.setAmenities(rs.getString("amenities"));
	                    hotels.add(hotel);
	                }
	            }
	        }
	        return hotels;
	    }

	    public static Hotel getHotelById(int id) throws SQLException {
	        Hotel hotel = null;
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Hotels WHERE id = ?")) {
	            stmt.setInt(1, id);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    hotel = new Hotel();
	                    hotel.setId(rs.getInt("id"));
	                    hotel.setName(rs.getString("name"));
	                    hotel.setLocation(rs.getString("location"));
	                    hotel.setAmenities(rs.getString("amenities"));
	                }
	            }
	        }
	        return hotel;
	    }

	    public static void addHotel(Hotel hotel) throws SQLException {
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Hotels (name, location, amenities) VALUES (?, ?, ?)")) {
	            stmt.setString(1, hotel.getName());
	            stmt.setString(2, hotel.getLocation());
	            stmt.setString(3, hotel.getAmenities());
	            stmt.executeUpdate();
	        }
	    }

	    public static void updateHotel(Hotel hotel) throws SQLException {
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("UPDATE Hotels SET name = ?, location = ?, amenities = ? WHERE id = ?")) {
	            stmt.setString(1, hotel.getName());
	            stmt.setString(2, hotel.getLocation());
	            stmt.setString(3, hotel.getAmenities());
	            stmt.setInt(4, hotel.getId());
	            stmt.executeUpdate();
	        }
	    }
	    
	    public static void updateHotelDetails(int hotelId, String newName, String newLocation, String newAmenities) {
	        try {
	            Hotel hotel = getHotelById(hotelId);
	            if (hotel != null) {
	                hotel.setName(newName);
	                hotel.setLocation(newLocation);
	                hotel.setAmenities(newAmenities);
	                updateHotel(hotel);
	                System.out.println("Hotel updated successfully.");
	            } else {
	                System.out.println("Hotel not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void deleteHotel(int id) throws SQLException {
	        try (Connection conn = DatabaseConnector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Hotels WHERE id = ?")) {
	            stmt.setInt(1, id);
	            stmt.executeUpdate();
	            
	            // Reset auto-increment value
	            try (Statement resetStmt = conn.createStatement()) {
	                resetStmt.execute("SET @new_id = 0");
	                resetStmt.execute("UPDATE Hotels SET id = (@new_id := @new_id + 1) ORDER BY id");
	                resetStmt.execute("ALTER TABLE Hotels AUTO_INCREMENT = 1");
	            }
	        }
	    }

	    
	    

}
