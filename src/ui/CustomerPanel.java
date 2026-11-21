package ui;

import dao.CustomerDAO;
import models.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel {
    private CustomerDAO customerDAO = new CustomerDAO();
    private JTable customerTable;
    private DefaultTableModel tableModel;

    private JTextField nameField, emailField, phoneField, addressField;

    public CustomerPanel() {
        setLayout(new BorderLayout());
        
        // --- Form Panel ---
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Customer"));
        
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);
        
        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);
        
        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(e -> addCustomer());
        formPanel.add(new JLabel()); // Spacer
        formPanel.add(addButton);
        
        add(formPanel, BorderLayout.NORTH);

        // --- Table Panel ---
        String[] columns = {"ID", "Name", "Email", "Phone", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        customerTable = new JTable(tableModel);
        loadCustomerData();
        
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Customer List"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addCustomer() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields (Name, Email, Phone)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer customer = new Customer(0, name, email, phone, address);
        if (customerDAO.addCustomer(customer)) {
            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            nameField.setText(""); emailField.setText(""); phoneField.setText(""); addressField.setText("");
            loadCustomerData(); // Refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add customer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCustomerData() {
        tableModel.setRowCount(0); // clear existing
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer c : customers) {
            tableModel.addRow(new Object[]{c.getId(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress()});
        }
    }
}
