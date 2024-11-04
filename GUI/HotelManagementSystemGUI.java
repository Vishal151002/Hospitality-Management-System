package hospatalitymanagement.GUI;
import hospatalitymanagement.Entity_Class.*;
import hospatalitymanagement.DAO_Class.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class HotelManagementSystemGUI {
    private JFrame frame;
    private JTextField hotelNameField;
    private JTextField locationField;
    private JTextField amenitiesField;
    private JTextField roomNumberField;
    private JTextField typeField;
    private JTextField priceField;
    private JTextField descriptionField;
    private JTextField guestNameField;
    private JTextField contactNumberField;
    private JTextField emailField;
    private JTextField checkInDateField;
    private JTextField checkOutDateField;
    private JTextField checkAvailabilityStartField;
    private JTextField checkAvailabilityEndField;
    private JTextField guestIdField;
    private JTextField roomIdField;
    private JButton addHotelButton;
    private JButton editHotelButton;
    private JButton deleteHotelButton;
    private JButton addRoomButton;
    private JButton editRoomButton;
    private JButton deleteRoomButton;
    private JButton addGuestButton;
    private JButton editGuestButton;
    private JButton deleteGuestButton;
    private JButton makeReservationButton;
    private JButton checkAvailabilityButton;
    private JTable hotelTable;
    private JTable roomTable;
    private JTable guestTable;
    private JTable reservationTable;

    private HotelDAO hotelDAO;
    private RoomDAO roomDAO;
    private GuestDAO guestDAO;
    private ReservationDAO reservationDAO;

    public HotelManagementSystemGUI() {
        hotelDAO = new HotelDAO();
        roomDAO = new RoomDAO();
        guestDAO = new GuestDAO();
        reservationDAO = new ReservationDAO();

        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Hotel Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Create panels for each section
        JPanel hotelPanel = createHotelPanel();
        JPanel roomPanel = createRoomPanel();
        JPanel guestPanel = createGuestPanel();
        JPanel reservationPanel = createReservationPanel();

        // Add panels to the tabbed pane
        tabbedPane.addTab("Hotel Management", hotelPanel);
        tabbedPane.addTab("Room Management", roomPanel);
        tabbedPane.addTab("Guest Management", guestPanel);
        tabbedPane.addTab("Reservation Management", reservationPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setSize(1000, 800); // Increased size for better visibility
        frame.setVisible(true);
    }

    private JPanel createHotelPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create and configure the input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Manage Hotels"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Hotel Name
        JLabel hotelNameLabel = new JLabel("Hotel Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(hotelNameLabel, gbc);

        hotelNameField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(hotelNameField, gbc);

        // Location
        JLabel locationLabel = new JLabel("Location:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(locationLabel, gbc);

        locationField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(locationField, gbc);

        // Amenities
        JLabel amenitiesLabel = new JLabel("Amenities:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(amenitiesLabel, gbc);

        amenitiesField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(amenitiesField, gbc);

        // Buttons
        addHotelButton = new JButton("Add Hotel");
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(addHotelButton, gbc);

        editHotelButton = new JButton("Edit Hotel");
        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(editHotelButton, gbc);

        deleteHotelButton = new JButton("Delete Hotel");
        gbc.gridx = 2;
        gbc.gridy = 3;
        inputPanel.add(deleteHotelButton, gbc);

        // Create and configure the hotel table
        hotelTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(hotelTable);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Add hotel button action listener
        addHotelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String hotelName = hotelNameField.getText();
                String location = locationField.getText();
                String amenities = amenitiesField.getText();

                if (hotelName.isEmpty() || location.isEmpty() || amenities.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                Hotel hotel = new Hotel(0, hotelName, location, amenities);
                try {
                    hotelDAO.addHotel(hotel);
                    hotelTable.setModel(new HotelTableModel(hotelDAO.getAllHotels()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error adding hotel: " + ex.getMessage());
                }
            }
        });

        // Edit hotel button action listener
        editHotelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = hotelTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a hotel to edit.");
                    return;
                }

                HotelTableModel model = (HotelTableModel) hotelTable.getModel();
                Hotel selectedHotel = model.getHotelAt(selectedRow);

                String newHotelName = hotelNameField.getText();
                String newLocation = locationField.getText();
                String newAmenities = amenitiesField.getText();

                if (newHotelName.isEmpty() || newLocation.isEmpty() || newAmenities.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                selectedHotel.setName(newHotelName);
                selectedHotel.setLocation(newLocation);
                selectedHotel.setAmenities(newAmenities);

                try {
                    hotelDAO.updateHotel(selectedHotel);
                    hotelTable.setModel(new HotelTableModel(hotelDAO.getAllHotels()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating hotel: " + ex.getMessage());
                }
            }
        });

        // Delete hotel button action listener
        deleteHotelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = hotelTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a hotel to delete.");
                    return;
                }

                HotelTableModel model = (HotelTableModel) hotelTable.getModel();
                Hotel selectedHotel = model.getHotelAt(selectedRow);

                int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this hotel?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        hotelDAO.deleteHotel(selectedHotel.getId());
                        hotelTable.setModel(new HotelTableModel(hotelDAO.getAllHotels()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error deleting hotel: " + ex.getMessage());
                    }
                }
            }
        });

        // Load hotels initially
        try {
            hotelTable.setModel(new HotelTableModel(hotelDAO.getAllHotels()));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading hotels: " + ex.getMessage());
        }

        return panel;
    }
    private void updateHotelDetails(Hotel hotel) throws SQLException {
        hotelDAO.updateHotel(hotel);
        JOptionPane.showMessageDialog(frame, "Hotel details updated successfully.");
    }

    // Similar implementations for Room and Guest Panels:
    private JPanel createRoomPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create and configure the input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add/Edit Room"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Room Number
        JLabel roomNumberLabel = new JLabel("Room Number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(roomNumberLabel, gbc);

        roomNumberField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(roomNumberField, gbc);

        // Type
        JLabel typeLabel = new JLabel("Type:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(typeLabel, gbc);

        JTextField typeField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(typeField, gbc);

        // Price
        JLabel priceLabel = new JLabel("Price:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(priceLabel, gbc);

        JTextField priceField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(priceField, gbc);

        // Description
        JLabel descriptionLabel = new JLabel("Status:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(descriptionLabel, gbc);

        JTextField descriptionField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(descriptionField, gbc);

        // Hotel
        JLabel hotelLabel = new JLabel("Hotel:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(hotelLabel, gbc);

        JComboBox<Hotel> hotelComboBox = new JComboBox<>();
        gbc.gridx = 1;
        inputPanel.add(hotelComboBox, gbc);

        // Add Room Button
        addRoomButton = new JButton("Add Room");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addRoomButton, gbc);

        // Update Room Button
        JButton updateRoomButton = new JButton("Update Room");
        gbc.gridx = 1;
        inputPanel.add(updateRoomButton, gbc);

        // Delete Room Button
        JButton deleteRoomButton = new JButton("Delete Room");
        gbc.gridx = 2;
        inputPanel.add(deleteRoomButton, gbc);

        // Create and configure the room table
        roomTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(roomTable);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Load hotels into the JComboBox
        try {
            List<Hotel> hotels = hotelDAO.getAllHotels();
            for (Hotel hotel : hotels) {
                hotelComboBox.addItem(hotel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading hotels: " + ex.getMessage());
        }

        // Add room button action listener
        addRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int roomNumber;
                double price;
                try {
                    roomNumber = Integer.parseInt(roomNumberField.getText());
                    price = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid room number or price.");
                    return;
                }

                String type = typeField.getText();
                String description = descriptionField.getText();

                Hotel selectedHotel = (Hotel) hotelComboBox.getSelectedItem();
                if (selectedHotel == null) {
                    JOptionPane.showMessageDialog(frame, "Please select a hotel.");
                    return;
                }
                int hotelId = selectedHotel.getId();
                Room room = new Room(0, hotelId, roomNumber, type, price, description);
                try {
                    roomDAO.addRoom(room);
                    roomTable.setModel(new RoomTableModel(roomDAO.getAllRooms()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error adding room: " + ex.getMessage());
                }
            }
        });

        // Update room button action listener
        updateRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = roomTable.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(frame, "Please select a room to update.");
                    return;
                }

                int roomId = (int) roomTable.getValueAt(selectedRow, 0); // Assuming room ID is in the first column

                int roomNumber;
                double price;
                try {
                    roomNumber = Integer.parseInt(roomNumberField.getText());
                    price = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid room number or price.");
                    return;
                }

                String type = typeField.getText();
                String description = descriptionField.getText();

                Hotel selectedHotel = (Hotel) hotelComboBox.getSelectedItem();
                if (selectedHotel == null) {
                    JOptionPane.showMessageDialog(frame, "Please select a hotel.");
                    return;
                }
                int hotelId = selectedHotel.getId();
                Room room = new Room(roomId, hotelId, roomNumber, type, price, description);
                try {
                    roomDAO.updateRoom(room);
                    roomTable.setModel(new RoomTableModel(roomDAO.getAllRooms()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating room: " + ex.getMessage());
                }
            }
        });

        // Delete room button action listener
        deleteRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = roomTable.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(frame, "Please select a room to delete.");
                    return;
                }

                int roomId = (int) roomTable.getValueAt(selectedRow, 0); // Assuming room ID is in the first column

                try {
                    roomDAO.deleteRoom(roomId);
                    roomTable.setModel(new RoomTableModel(roomDAO.getAllRooms()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error deleting room: " + ex.getMessage());
                }
            }
        });

        // Load rooms initially
        try {
            roomTable.setModel(new RoomTableModel(roomDAO.getAllRooms()));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading rooms: " + ex.getMessage());
        }

        return panel;
    }

    // Refresh hotel dropdown
    private void refreshHotelDropdown(JComboBox<Hotel> hotelComboBox) throws SQLException {
        hotelComboBox.removeAllItems(); // Clear existing items
        List<Hotel> hotels = HotelDAO.getAllHotels();
        for (Hotel hotel : hotels) {
            hotelComboBox.addItem(hotel);
        }
    }


    private JPanel createGuestPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create and configure the input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Manage Guests"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Guest Name
        JLabel guestNameLabel = new JLabel("Guest Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(guestNameLabel, gbc);

        guestNameField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(guestNameField, gbc);

        // Contact Number
        JLabel contactNumberLabel = new JLabel("Contact Number:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(contactNumberLabel, gbc);

        contactNumberField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(contactNumberField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);

        // Buttons
        addGuestButton = new JButton("Add Guest");
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(addGuestButton, gbc);

        editGuestButton = new JButton("Edit Guest");
        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(editGuestButton, gbc);

        deleteGuestButton = new JButton("Delete Guest");
        gbc.gridx = 2;
        gbc.gridy = 3;
        inputPanel.add(deleteGuestButton, gbc);

        // Create and configure the guest table
        guestTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(guestTable);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Add guest button action listener
        addGuestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guestName = guestNameField.getText();
                String contactNumber = contactNumberField.getText();
                String email = emailField.getText();

                if (guestName.isEmpty() || contactNumber.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                Guest guest = new Guest(0, guestName, contactNumber, email);
                try {
                    guestDAO.addGuest(guest);
                    guestTable.setModel(new GuestTableModel(guestDAO.getAllGuests()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error adding guest: " + ex.getMessage());
                }
            }
        });

        // Edit guest button action listener
        editGuestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = guestTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a guest to edit.");
                    return;
                }

                GuestTableModel model = (GuestTableModel) guestTable.getModel();
                Guest selectedGuest = model.getGuestAt(selectedRow);

                String newGuestName = guestNameField.getText();
                String newContactNumber = contactNumberField.getText();
                String newEmail = emailField.getText();

                if (newGuestName.isEmpty() || newContactNumber.isEmpty() || newEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                selectedGuest.setName(newGuestName);
                selectedGuest.setPhoneNumber(newContactNumber);
                selectedGuest.setEmail(newEmail);

                try {
                    guestDAO.updateGuest(selectedGuest);
                    guestTable.setModel(new GuestTableModel(guestDAO.getAllGuests()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating guest: " + ex.getMessage());
                }
            }
        });

        // Delete guest button action listener
        deleteGuestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = guestTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a guest to delete.");
                    return;
                }

                GuestTableModel model = (GuestTableModel) guestTable.getModel();
                Guest selectedGuest = model.getGuestAt(selectedRow);

                int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this guest?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        guestDAO.deleteGuest(selectedGuest.getId());
                        guestTable.setModel(new GuestTableModel(guestDAO.getAllGuests()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error deleting guest: " + ex.getMessage());
                    }
                }
            }
        });

        // Load guests initially
        try {
            guestTable.setModel(new GuestTableModel(guestDAO.getAllGuests()));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading guests: " + ex.getMessage());
        }

        return panel;
    }

    private JPanel createReservationPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create and configure the input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Manage Reservations"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Guest ID
        JLabel guestIdLabel = new JLabel("Guest ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(guestIdLabel, gbc);

        guestIdField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(guestIdField, gbc);

        // Room ID
        JLabel roomIdLabel = new JLabel("Room ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(roomIdLabel, gbc);

        roomIdField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(roomIdField, gbc);

        // Check-in Date
        JLabel checkInDateLabel = new JLabel("Check-in Date (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(checkInDateLabel, gbc);

        checkInDateField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(checkInDateField, gbc);

        // Check-out Date
        JLabel checkOutDateLabel = new JLabel("Check-out Date (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(checkOutDateLabel, gbc);

        checkOutDateField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(checkOutDateField, gbc);

        // Buttons
        JButton addReservationButton = new JButton("Add Reservation");
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(addReservationButton, gbc);

        JButton editReservationButton = new JButton("Edit Reservation");
        gbc.gridx = 1;
        gbc.gridy = 4;
        inputPanel.add(editReservationButton, gbc);

        JButton deleteReservationButton = new JButton("Delete Reservation");
        gbc.gridx = 2;
        gbc.gridy = 4;
        inputPanel.add(deleteReservationButton, gbc);

        // Create and configure the reservation table
        reservationTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(reservationTable);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Add reservation button action listener
        addReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guestId = guestIdField.getText().trim();
                String roomId = roomIdField.getText().trim();
                String checkInDate = checkInDateField.getText().trim();
                String checkOutDate = checkOutDateField.getText().trim();

                if (guestId.isEmpty() || roomId.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                try {
                    LocalDate checkInDateSql = LocalDate.parse(checkInDate);
                    LocalDate checkOutDateSql = LocalDate.parse(checkOutDate);
                    boolean isAvailable = roomDAO.isRoomAvailable(Integer.parseInt(roomId), checkInDate, checkOutDate);

                    if (!isAvailable) {
                        JOptionPane.showMessageDialog(frame, "The room is not available for the selected dates.");
                        return;
                    }

                    Reservation reservation = new Reservation(0, Integer.parseInt(guestId), Integer.parseInt(roomId), checkInDateSql, checkOutDateSql);
                    reservationDAO.addReservation(reservation);
                    updateReservationTable();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid dates in the format YYYY-MM-DD.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error adding reservation: " + ex.getMessage());
                }
            }
        });

        // Edit reservation button action listener
        editReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = reservationTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a reservation to edit.");
                    return;
                }

                ReservationTableModel model = (ReservationTableModel) reservationTable.getModel();
                Reservation selectedReservation = model.getReservationAt(selectedRow);

                String newGuestId = guestIdField.getText().trim();
                String newRoomId = roomIdField.getText().trim();
                String newCheckInDate = checkInDateField.getText().trim();
                String newCheckOutDate = checkOutDateField.getText().trim();

                if (newGuestId.isEmpty() || newRoomId.isEmpty() || newCheckInDate.isEmpty() || newCheckOutDate.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                try {
                    LocalDate newCheckInDateSql = LocalDate.parse(newCheckInDate);
                    LocalDate newCheckOutDateSql = LocalDate.parse(newCheckOutDate);
                    boolean isAvailable = roomDAO.isRoomAvailable(Integer.parseInt(newRoomId), newCheckInDate, newCheckOutDate);

                    if (!isAvailable) {
                        JOptionPane.showMessageDialog(frame, "The room is not available for the selected dates.");
                        return;
                    }

                    // Update Reservation details with the converted dates
                    selectedReservation.setGuestId(Integer.parseInt(newGuestId));
                    selectedReservation.setRoomId(Integer.parseInt(newRoomId));
                    selectedReservation.setCheckInDate(newCheckInDateSql);
                    selectedReservation.setCheckOutDate(newCheckOutDateSql);

                    reservationDAO.updateReservation(selectedReservation);
                    updateReservationTable();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid dates in the format YYYY-MM-DD.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating reservation: " + ex.getMessage());
                }
            }
        });

        // Delete reservation button action listener
        deleteReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = reservationTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a reservation to delete.");
                    return;
                }

                ReservationTableModel model = (ReservationTableModel) reservationTable.getModel();
                Reservation selectedReservation = model.getReservationAt(selectedRow);

                int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this reservation?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        reservationDAO.deleteReservation(selectedReservation.getId());
                        updateReservationTable();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error deleting reservation: " + ex.getMessage());
                    }
                }
            }
        });

        // Load reservations initially
        updateReservationTable();

        return panel;
    }

    private void updateReservationTable() {
        try {
            reservationTable.setModel(new ReservationTableModel(reservationDAO.getAllReservations()));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading reservations: " + ex.getMessage());
        }
    }
}