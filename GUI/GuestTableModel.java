package hospatalitymanagement.GUI;

import hospatalitymanagement.Entity_Class.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GuestTableModel extends AbstractTableModel {
    private final List<Guest> guests;
    private final String[] columnNames = {"ID", "Name", "Email", "Phone"};

    public GuestTableModel(List<Guest> guests) {
        this.guests = guests;
    }

    @Override
    public int getRowCount() {
        return guests.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Guest guest = guests.get(rowIndex);
        switch (columnIndex) {
            case 0: return guest.getId();
            case 1: return guest.getName();
            case 2: return guest.getEmail();
            case 3: return guest.getPhoneNumber();
            default: throw new IndexOutOfBoundsException("Column index out of range");
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Add the getGuestAt method
    public Guest getGuestAt(int rowIndex) {
        return guests.get(rowIndex);
    }
}
