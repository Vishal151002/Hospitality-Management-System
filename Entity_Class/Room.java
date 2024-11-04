package hospatalitymanagement.Entity_Class;
import java.sql.SQLException;
import hospatalitymanagement.DAO_Class.*;

public class Room {
    private int id;
    private int hotelId;
    private int roomNumber;
    private String roomType;
    private double price;
    private String status;

    // Default constructor
    public Room() {}

    // Constructor for creating a Room with just the room number
    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    // Existing constructor for creating a Room with all attributes
    public Room(int id, int hotelId, int roomNumber, String roomType, double price, String status) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    // New constructor to match GUI code
    public Room(int hotelId, String roomNumberStr, String roomType, double price, String status) {
        this.hotelId = hotelId;
        try {
            this.roomNumber = Integer.parseInt(roomNumberStr); // Convert String to int
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.roomNumber = 0; // Default value if parsing fails
        }
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int newRoomNumber) {
        this.roomNumber = newRoomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + getHotelName() + ")"; // Display room number and hotel name
    }

    // Method to get hotel name (this can be a placeholder)
    public String getHotelName() {
        HotelDAO hotelDAO = new HotelDAO();
        try {
            Hotel hotel = hotelDAO.getHotelById(hotelId);
            return hotel.getName();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Unknown Hotel";
        }
    }
}
