package hospatalitymanagement.GUI;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import hospatalitymanagement.Entity_Class.Hotel;

public class HotelTableModel extends AbstractTableModel {
    private final List<Hotel> hotels;
    private final String[] columnNames = {"ID", "Name", "Location", "Amenities"};

    public HotelTableModel(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public int getRowCount() {
        return hotels.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hotel hotel = hotels.get(rowIndex);
        switch (columnIndex) {
            case 0: return hotel.getId();
            case 1: return hotel.getName();
            case 2: return hotel.getLocation();
            case 3: return hotel.getAmenities();
            default: throw new IndexOutOfBoundsException("Column index out of range");
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Method to get the Hotel object at a specific row index
    public Hotel getHotelAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= hotels.size()) {
            throw new IndexOutOfBoundsException("Row index out of range");
        }
        return hotels.get(rowIndex);
    }
}
