package hospatalitymanagement.GUI;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import hospatalitymanagement.Entity_Class.Room;

public class RoomTableModel extends AbstractTableModel {
    private List<Room> rooms;
    private final String[] columnNames = {"ID", "Hotel ID", "Room Number", "Room Type", "Price", "Status"};

    public RoomTableModel(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public int getRowCount() {
        return rooms.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Room room = rooms.get(rowIndex);
        switch (columnIndex) {
            case 0: return room.getId();
            case 1: return room.getHotelId();
            case 2: return room.getRoomNumber();
            case 3: return room.getRoomType();
            case 4: return room.getPrice();
            case 5: return room.getStatus();
            default: throw new IndexOutOfBoundsException("Column index out of range");
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Method to get a Room at a specific row index
    public Room getRoomAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < rooms.size()) {
            return rooms.get(rowIndex);
        } else {
            throw new IndexOutOfBoundsException("Row index out of range");
        }
    }

    // Method to update the list of rooms and notify the table model
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
        fireTableDataChanged(); // Notify table that data has changed
    }
}
