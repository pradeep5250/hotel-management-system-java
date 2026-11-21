package dao;

import db.DatabaseConnection;
import models.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                rooms.add(new Room(
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getDouble("price_per_night"),
                    rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public List<Room> getAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms WHERE is_available = TRUE";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                rooms.add(new Room(
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getDouble("price_per_night"),
                    rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public boolean updateRoomAvailability(String roomNumber, boolean isAvailable) {
        String query = "UPDATE Rooms SET is_available=? WHERE room_number=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
             
            ps.setBoolean(1, isAvailable);
            ps.setString(2, roomNumber);
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Room getRoom(String roomNumber) {
        String query = "SELECT * FROM Rooms WHERE room_number=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
             
            ps.setString(1, roomNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Room(
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getBoolean("is_available")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
