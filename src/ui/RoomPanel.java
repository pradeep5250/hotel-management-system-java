package ui;

import dao.RoomDAO;
import models.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomPanel extends JPanel {
    private RoomDAO roomDAO = new RoomDAO();
    private JTable roomTable;
    private DefaultTableModel tableModel;

    public RoomPanel() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("Refresh Rooms");
        refreshButton.addActionListener(e -> loadRoomData());
        topPanel.add(refreshButton);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Room Number", "Room Type", "Price per Night", "Available"};
        tableModel = new DefaultTableModel(columns, 0);
        roomTable = new JTable(tableModel);
        loadRoomData();
        
        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Room Status"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadRoomData() {
        tableModel.setRowCount(0);
        List<Room> rooms = roomDAO.getAllRooms();
        for (Room r : rooms) {
            tableModel.addRow(new Object[]{r.getRoomNumber(), r.getRoomType(), r.getPricePerNight(), r.isAvailable() ? "Yes" : "No"});
        }
    }
}
