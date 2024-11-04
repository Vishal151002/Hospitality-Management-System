package hospatalitymanagement.DAO_Class;

import hospatalitymanagement.Entity_Class.Room;
import hospatalitymanagement.Entity_Class.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Retrieve all rooms from the database
    public static List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Rooms")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setId(rs.getInt("id"));
                    room.setHotelId(rs.getInt("hotel_id"));
                    room.setRoomNumber(rs.getInt("room_number"));
                    room.setRoomType(rs.getString("room_type"));
                    room.setPrice(rs.getDouble("price"));
                    room.setStatus(rs.getString("status"));
                    rooms.add(room);
                }
            }
        }
        return rooms;
    }

    // Retrieve a room by its ID
    public static Room getRoomById(int id) throws SQLException {
        Room room = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Rooms WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    room = new Room();
                    room.setId(rs.getInt("id"));
                    room.setHotelId(rs.getInt("hotel_id"));
                    room.setRoomNumber(rs.getInt("room_number"));
                    room.setRoomType(rs.getString("room_type"));
                    room.setPrice(rs.getDouble("price"));
                    room.setStatus(rs.getString("status"));
                }
            }
        }
        return room;
    }

    // Check if a room is available for the given dates
    public static boolean isRoomAvailable(int roomId, String newCheckInDate, String newCheckOutDate) {
        try {
            LocalDate checkInDate = LocalDate.parse(newCheckInDate, DATE_FORMATTER);
            LocalDate checkOutDate = LocalDate.parse(newCheckOutDate, DATE_FORMATTER);

            ReservationDAO reservationDAO = new ReservationDAO();
            List<Reservation> reservations = reservationDAO.getReservationsByRoomId(roomId);

            for (Reservation reservation : reservations) {
                LocalDate reservedCheckInDate = reservation.getCheckInDate();
                LocalDate reservedCheckOutDate = reservation.getCheckOutDate();

                if (checkInDate.isBefore(reservedCheckOutDate) && checkOutDate.isAfter(reservedCheckInDate)) {
                    return false;
                }
            }
            return true;

        } catch (DateTimeParseException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add a new room to the database
    public static void addRoom(Room room) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnector.getConnection();

            // Check if the hotel_id exists in the hotels table
            String checkHotelQuery = "SELECT id FROM Hotels WHERE id = ?";
            stmt = conn.prepareStatement(checkHotelQuery);
            stmt.setInt(1, room.getHotelId());
            rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Error adding room: Hotel ID " + room.getHotelId() + " does not exist.");
            }

            // Insert the new room
            String insertRoomQuery = "INSERT INTO Rooms (hotel_id, room_number, room_type, price, status) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertRoomQuery);
            stmt.setInt(1, room.getHotelId());
            stmt.setInt(2, room.getRoomNumber());
            stmt.setString(3, room.getRoomType());
            stmt.setDouble(4, room.getPrice());
            stmt.setString(5, room.getStatus());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error adding room: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    // Update an existing room in the database
    public static void updateRoom(Room room) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Rooms SET hotel_id = ?, room_number = ?, room_type = ?, price = ?, status = ? WHERE id = ?")) {
            stmt.setInt(1, room.getHotelId());
            stmt.setInt(2, room.getRoomNumber());
            stmt.setString(3, room.getRoomType());
            stmt.setDouble(4, room.getPrice());
            stmt.setString(5, room.getStatus());
            stmt.setInt(6, room.getId());
            stmt.executeUpdate();
        }
    }

    // Remove a room from the database

    public static void deleteRoom(int id) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Rooms WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            // Reset auto-increment value
            try (Statement resetStmt = conn.createStatement()) {
                resetStmt.execute("SET @new_id = 0");
                resetStmt.execute("UPDATE Rooms SET id = (@new_id := @new_id + 1) ORDER BY id");
                resetStmt.execute("ALTER TABLE Rooms AUTO_INCREMENT = 1");
            }
        }
    }

}
