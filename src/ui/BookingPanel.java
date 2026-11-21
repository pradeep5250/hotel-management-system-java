package ui;

import dao.BookingDAO;
import dao.RoomDAO;
import models.Booking;
import models.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class BookingPanel extends JPanel {
    private BookingDAO bookingDAO = new BookingDAO();
    private RoomDAO roomDAO = new RoomDAO();
    
    private JTextField customerIdField, checkInDateField;
    private JComboBox<String> roomComboBox;
    
    private JTextField bookingIdField, checkOutDateField;
    
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public BookingPanel() {
        setLayout(new BorderLayout());
        
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // --- Check-In Panel ---
        JPanel checkInPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        checkInPanel.setBorder(BorderFactory.createTitledBorder("Check-In"));
        
        checkInPanel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        checkInPanel.add(customerIdField);
        
        checkInPanel.add(new JLabel("Select Room:"));
        roomComboBox = new JComboBox<>();
        refreshRoomComboBox();
        checkInPanel.add(roomComboBox);
        
        checkInPanel.add(new JLabel("Check-In Date (YYYY-MM-DD):"));
        checkInDateField = new JTextField();
        checkInPanel.add(checkInDateField);
        
        JButton checkInButton = new JButton("Check-In");
        checkInButton.addActionListener(e -> processCheckIn());
        checkInPanel.add(new JLabel());
        checkInPanel.add(checkInButton);
        
        actionPanel.add(checkInPanel);
        
        // --- Check-Out Panel ---
        JPanel checkOutPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        checkOutPanel.setBorder(BorderFactory.createTitledBorder("Check-Out"));
        
        checkOutPanel.add(new JLabel("Booking ID:"));
        bookingIdField = new JTextField();
        checkOutPanel.add(bookingIdField);
        
        checkOutPanel.add(new JLabel("Check-Out Date (YYYY-MM-DD):"));
        checkOutDateField = new JTextField();
        checkOutPanel.add(checkOutDateField);
        
        JButton checkOutButton = new JButton("Check-Out");
        checkOutButton.addActionListener(e -> processCheckOut());
        checkOutPanel.add(new JLabel());
        checkOutPanel.add(checkOutButton);
        
        actionPanel.add(checkOutPanel);
        
        add(actionPanel, BorderLayout.NORTH);
        
        // --- Booking Table ---
        String[] columns = {"Booking ID", "Customer ID", "Room", "Check-In", "Check-Out", "Amount"};
        tableModel = new DefaultTableModel(columns, 0);
        bookingTable = new JTable(tableModel);
        loadBookingData();
        
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Bookings"));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void refreshRoomComboBox() {
        roomComboBox.removeAllItems();
        List<Room> rooms = roomDAO.getAvailableRooms();
        for (Room r : rooms) {
            roomComboBox.addItem(r.getRoomNumber() + " (" + r.getRoomType() + ")");
        }
    }
    
    private void processCheckIn() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            String roomSelection = (String) roomComboBox.getSelectedItem();
            if (roomSelection == null) return;
            String roomNumber = roomSelection.split(" ")[0]; // Extract room number
            Date checkInDate = Date.valueOf(checkInDateField.getText());
            
            Booking booking = new Booking(0, customerId, roomNumber, checkInDate, null, 0.0);
            if (bookingDAO.checkIn(booking)) {
                JOptionPane.showMessageDialog(this, "Check-in successful!");
                refreshRoomComboBox();
                loadBookingData();
            } else {
                JOptionPane.showMessageDialog(this, "Check-in failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the date format (YYYY-MM-DD) and Customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void processCheckOut() {
        try {
            int bookingId = Integer.parseInt(bookingIdField.getText());
            Date checkOutDate = Date.valueOf(checkOutDateField.getText());
            
            if (bookingDAO.checkOut(bookingId, checkOutDate)) {
                JOptionPane.showMessageDialog(this, "Check-out successful! Bill generated.");
                refreshRoomComboBox();
                loadBookingData();
            } else {
                JOptionPane.showMessageDialog(this, "Check-out failed. Ensure Booking ID is valid and not already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the date format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadBookingData() {
        tableModel.setRowCount(0);
        List<Booking> bookings = bookingDAO.getAllBookings();
        for (Booking b : bookings) {
            tableModel.addRow(new Object[]{
                b.getBookingId(), b.getCustomerId(), b.getRoomNumber(),
                b.getCheckInDate(), b.getCheckOutDate(), b.getTotalAmount()
            });
        }
    }
}
