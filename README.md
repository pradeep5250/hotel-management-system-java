# 🏨 Hotel Management System (Java Swing + MySQL)

> **A desktop enterprise Hotel Management System built with Java Swing GUI, JDBC, DAO design pattern, and MySQL.**

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-red)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue?logo=mysql)
![Architecture](https://img.shields.io/badge/Pattern-DAO%20%2F%20MVC-brightgreen)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## 📌 Overview

**Hotel Management System** is a desktop application tailored for hospitality operations. It streamlines core operational workflows including guest check-in/check-out, real-time room inventory allocation, automated billing calculation, and customer record management. Built with standard Object-Oriented Programming (OOP) paradigms and the Data Access Object (DAO) pattern to maintain clean separation between UI components and persistence logic.

---

## ✨ Features

- **🛏️ Room Management:**
  - Track room status (Available, Occupied, Under Maintenance).
  - Categorization by room type (Single, Double, Deluxe, Suite).
  - Dynamic pricing and room occupancy updates.

- **👥 Guest & Customer Profiling:**
  - Maintain verified guest records, phone numbers, email, and government ID proofs.
  - Search customer records by name or contact number.

- **📅 Reservation & Booking Engine:**
  - Check-in and check-out tracking with automated day/rate calculation.
  - Room reassignment and booking conflict prevention.

- **💳 Invoice & Billing:**
  - Automated bill generation including room charges, stay duration, and tax calculations.
  - Persistent transaction logging.

- **🗄️ Relational Database (MySQL):**
  - Robust schema with relational integrity and foreign keys connecting rooms, customers, and bookings.

---

## 🛠️ Architecture & Technologies

```text
┌────────────────────────────────────────┐
│             Java Swing UI              │
│ (MainFrame, RoomPanel, BookingPanel)   │
└───────────────────┬────────────────────┘
                    │
┌───────────────────▼────────────────────┐
│          DAO Layer (Data Access)       │
│  (RoomDAO, CustomerDAO, BookingDAO)    │
└───────────────────┬────────────────────┘
                    │ JDBC Driver
┌───────────────────▼────────────────────┐
│            MySQL Database              │
│       (hotel_management_system)        │
└────────────────────────────────────────┘
```

- **Language:** Java (JDK 17 or higher recommended)
- **UI Framework:** Java Swing / AWT
- **Database:** MySQL 8.x
- **Connector:** MySQL Connector/J 9.x (`mysql-connector-j-9.7.0.jar`)
- **Design Pattern:** DAO (Data Access Object) + MVC (Model-View-Controller)

---

## 🚀 Getting Started

### Prerequisites
- [Java Development Kit (JDK 17+)](https://www.oracle.com/java/technologies/downloads/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/) running on port `3306`

### Database Setup
1. Open your MySQL client (MySQL Workbench, phpMyAdmin, or CLI).
2. Execute the included SQL initialization schema:
   ```sql
   SOURCE resources/schema.sql;
   ```
3. Update your database credentials in `src/db/DatabaseConnection.java` if different from default (`root` / `password`).

### Compilation & Running

#### Using Windows Command Prompt:
Simply execute the automated build batch script:
```cmd
run.bat
```

#### Manual Compilation (Terminal / Linux / Mac):
```bash
# 1. Create bin directory
mkdir -p bin

# 2. Compile Java source files
javac -cp ".;lib/*" -d bin src/db/*.java src/models/*.java src/dao/*.java src/ui/*.java

# 3. Launch application
java -cp "bin;lib/*" ui.MainFrame
```

---

## 📂 Project Directory Layout

```
HotelManagementSystem/
├── bin/                     # Compiled byte-code (.class files, git ignored)
├── lib/                     # JDBC MySQL connector library (.jar)
├── resources/
│   └── schema.sql           # Database schema initialization script
├── src/
│   ├── dao/                 # Data Access Objects (SQL queries & operations)
│   │   ├── BookingDAO.java
│   │   ├── CustomerDAO.java
│   │   └── RoomDAO.java
│   ├── db/                  # Database connection pool manager
│   │   └── DatabaseConnection.java
│   ├── models/              # Plain Old Java Objects (POJO domain models)
│   │   ├── Booking.java
│   │   ├── Customer.java
│   │   └── Room.java
│   └── ui/                  # Swing GUI frames and panels
│       ├── BookingPanel.java
│       ├── CustomerPanel.java
│       ├── MainFrame.java
│       └── RoomPanel.java
├── run.bat                  # One-click Windows build and launch script
└── README.md                # Documentation
```

---

## 👤 Author

Developed by **[Pradeep S](https://github.com/pradeep5250)**
