package hospatalitymanagement.Entity_Class;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reservation {
    private int id;
    private int guestId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation() {}

    public Reservation(int id, int guestId, int roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = id;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Overloaded constructor that takes String dates
    public Reservation(int id, int guestId, int roomId, String checkInDateStr, String checkOutDateStr) throws DateTimeParseException {
        this.id = id;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = convertStringToLocalDate(checkInDateStr);
        this.checkOutDate = convertStringToLocalDate(checkOutDateStr);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckInDate(String checkInDateStr) throws DateTimeParseException {
        this.checkInDate = convertStringToLocalDate(checkInDateStr);
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setCheckOutDate(String checkOutDateStr) throws DateTimeParseException {
        this.checkOutDate = convertStringToLocalDate(checkOutDateStr);
    }

    // Utility method to convert String to LocalDate
    private LocalDate convertStringToLocalDate(String dateStr) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateStr, formatter);
    }
}
