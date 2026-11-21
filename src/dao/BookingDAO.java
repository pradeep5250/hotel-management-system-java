package dao;

import db.DatabaseConnection;
import models.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingDAO {

    private RoomDAO roomDAO = new RoomDAO();

    public boolean checkIn(Booking booking) {
        String query = "INSERT INTO Bookings (customer_id, room_number, check_in_date) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
             
            con.setAutoCommit(false); // start transaction
            
            ps.setInt(1, booking.getCustomerId());
            ps.setString(2, booking.getRoomNumber());
            ps.setDate(3, booking.getCheckInDate());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                // Update room to unavailable
                boolean roomUpdated = roomDAO.updateRoomAvailability(booking.getRoomNumber(), false);
                if (roomUpdated) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkOut(int bookingId, Date checkOutDate) {
        String getBookingQuery = "SELECT * FROM Bookings WHERE booking_id = ?";
        String updateBookingQuery = "UPDATE Bookings SET check_out_date=?, total_amount=? WHERE booking_id=?";
        
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            
            // Get booking details
            Booking booking = null;
            try (PreparedStatement ps = con.prepareStatement(getBookingQuery)) {
                ps.setInt(1, bookingId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        booking = new Booking(
                            rs.getInt("booking_id"),
                            rs.getInt("customer_id"),
                            rs.getString("room_number"),
                            rs.getDate("check_in_date"),
                            rs.getDate("check_out_date"),
                            rs.getDouble("total_amount")
                        );
                    }
                }
            }
            
            if (booking != null && booking.getCheckOutDate() == null) {
                // Calculate days stayed
                LocalDate checkIn = booking.getCheckInDate().toLocalDate();
                LocalDate checkOut = checkOutDate.toLocalDate();
                long daysStayed = ChronoUnit.DAYS.between(checkIn, checkOut);
                if (daysStayed <= 0) daysStayed = 1; // Minimum 1 day charge
                
                // Get room price
                double pricePerNight = roomDAO.getRoom(booking.getRoomNumber()).getPricePerNight();
                double totalAmount = daysStayed * pricePerNight;
                
                // Update booking
                try (PreparedStatement ps = con.prepareStatement(updateBookingQuery)) {
                    ps.setDate(1, checkOutDate);
                    ps.setDouble(2, totalAmount);
                    ps.setInt(3, bookingId);
                    ps.executeUpdate();
                }
                
                // Update room availability
                roomDAO.updateRoomAvailability(booking.getRoomNumber(), true);
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getString("room_number"),
                    rs.getDate("check_in_date"),
                    rs.getDate("check_out_date"),
                    rs.getDouble("total_amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}
