CREATE DATABASE IF NOT EXISTS hotelmanagement;

USE hotelmanagement;

CREATE TABLE IF NOT EXISTS Customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Rooms (
    room_number VARCHAR(10) PRIMARY KEY,
    room_type VARCHAR(50) NOT NULL,
    price_per_night DECIMAL(10, 2) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS Bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    room_number VARCHAR(10) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE,
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (customer_id) REFERENCES Customers(id) ON DELETE CASCADE,
    FOREIGN KEY (room_number) REFERENCES Rooms(room_number) ON DELETE CASCADE
);

-- Insert some dummy rooms
INSERT IGNORE INTO Rooms (room_number, room_type, price_per_night, is_available) VALUES ('101', 'Single', 1000.00, TRUE);
INSERT IGNORE INTO Rooms (room_number, room_type, price_per_night, is_available) VALUES ('102', 'Double', 1500.00, TRUE);
INSERT IGNORE INTO Rooms (room_number, room_type, price_per_night, is_available) VALUES ('103', 'Suite', 3000.00, TRUE);
INSERT IGNORE INTO Rooms (room_number, room_type, price_per_night, is_available) VALUES ('104', 'Double', 1500.00, TRUE);
