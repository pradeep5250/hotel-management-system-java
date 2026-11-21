package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Hotel Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Customers", new CustomerPanel());
        tabbedPane.addTab("Rooms", new RoomPanel());
        tabbedPane.addTab("Check-In & Check-Out", new BookingPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Ensure UI is built on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set native look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainFrame().setVisible(true);
        });
    }
}
