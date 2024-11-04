package hospatalitymanagement.GUI;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import hospatalitymanagement.Entity_Class.Reservation;

public class ReservationTableModel extends AbstractTableModel {
    private List<Reservation> reservations;
    private final String[] columnNames = {"Reservation ID", "Guest ID", "Room ID", "Check-In Date", "Check-Out Date"};

    public ReservationTableModel(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public int getRowCount() {
        return reservations.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reservation reservation = reservations.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return reservation.getId();
            case 1:
                return reservation.getGuestId();
            case 2:
                return reservation.getRoomId();
            case 3:
                return reservation.getCheckInDate();
            case 4:
                return reservation.getCheckOutDate();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Reservation getReservationAt(int rowIndex) {
        return reservations.get(rowIndex);
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        fireTableDataChanged();
    }
}
