package hospatalitymanagement.DAO_Class;

import hospatalitymanagement.Entity_Class.Reservation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // Retrieve all reservations from the database
    public static List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Reservations")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setGuestId(rs.getInt("guest_id"));
                    reservation.setRoomId(rs.getInt("room_id"));
                    reservation.setCheckInDate(rs.getDate("check_in").toLocalDate());
                    reservation.setCheckOutDate(rs.getDate("check_out").toLocalDate());
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    // Retrieve a reservation by ID
    public static Reservation getReservationById(int id) throws SQLException {
        Reservation reservation = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Reservations WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setGuestId(rs.getInt("guest_id"));
                    reservation.setRoomId(rs.getInt("room_id"));
                    reservation.setCheckInDate(rs.getDate("check_in").toLocalDate());
                    reservation.setCheckOutDate(rs.getDate("check_out").toLocalDate());
                }
            }
        }
        return reservation;
    }

    // Add a new reservation to the database
    public static void addReservation(Reservation reservation) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Reservations (guest_id, room_id, check_in, check_out) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, reservation.getGuestId());
            stmt.setInt(2, reservation.getRoomId());
            stmt.setDate(3, Date.valueOf(reservation.getCheckInDate()));
            stmt.setDate(4, Date.valueOf(reservation.getCheckOutDate()));
            stmt.executeUpdate();
        }
    }

    // Update an existing reservation in the database
    public static void updateReservation(Reservation reservation) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Reservations SET guest_id = ?, room_id = ?, check_in = ?, check_out = ? WHERE id = ?")) {
            stmt.setInt(1, reservation.getGuestId());
            stmt.setInt(2, reservation.getRoomId());
            stmt.setDate(3, Date.valueOf(reservation.getCheckInDate()));
            stmt.setDate(4, Date.valueOf(reservation.getCheckOutDate()));
            stmt.setInt(5, reservation.getId());
            stmt.executeUpdate();
        }
    }

    // Delete a reservation from the database    
    public static void deleteReservation(int id) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Reservations WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            // Reset auto-increment value
            try (Statement resetStmt = conn.createStatement()) {
                resetStmt.execute("SET @new_id = 0");
                resetStmt.execute("UPDATE Reservations SET id = (@new_id := @new_id + 1) ORDER BY id");
                resetStmt.execute("ALTER TABLE Reservations AUTO_INCREMENT = 1");
            }
        }
    }

    // Retrieve reservations by room ID
    public List<Reservation> getReservationsByRoomId(int roomId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservations WHERE room_id = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setGuestId(rs.getInt("guest_id"));
                    reservation.setRoomId(rs.getInt("room_id"));
                    reservation.setCheckInDate(rs.getDate("check_in").toLocalDate());
                    reservation.setCheckOutDate(rs.getDate("check_out").toLocalDate());
                    reservations.add(reservation);
                }
            }
        }
        
        return reservations;
    }

    // Cancel a reservation
    public void cancelReservation(int reservationId) {
        try {
            Reservation reservation = getReservationById(reservationId);
            if (reservation != null) {
                deleteReservation(reservationId);
                System.out.println("Reservation canceled successfully.");
            } else {
                System.out.println("Reservation not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
