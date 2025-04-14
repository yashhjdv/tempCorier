package com.hexaware.dao;

import com.hexaware.entity.*;
import com.hexaware.exception.*;
import com.hexaware.util.DbConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourierServiceDb implements ICourierUserService, ICourierAdminService {
    private Connection connection;

    public CourierServiceDb() throws DbConnectionException {
        this.connection = DbConnectionUtil.getDbConnection();
    }


    @Override
    public String placeOrder(Courier courier) throws DbConnectionException {
        String sql = "INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, " +
                "Weight, Status, TrackingNumber, DeliveryDate, EmployeeID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, courier.getSenderName());
            ps.setString(2, courier.getSenderAddress());
            ps.setString(3, courier.getReceiverName());
            ps.setString(4, courier.getReceiverAddress());
            ps.setDouble(5, courier.getWeight());
            ps.setString(6, courier.getStatus());
            ps.setString(7, courier.getTrackingNumber()); // Auto-generated in Courier class
            ps.setDate(8, Date.valueOf(courier.getDeliveryDate()));
            ps.setInt(9, courier.getEmployee().getEmployeeID());

            ps.executeUpdate();
            return courier.getTrackingNumber();
        } catch (SQLException e) {
            throw new DbConnectionException("Failed to place order: " + e.getMessage());
        }
    }

    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
        String sql = "SELECT Status FROM Courier WHERE TrackingNumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, trackingNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Status");
            } else {
                throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
            }
        } catch (SQLException e) {
            try {
                throw new DbConnectionException("Database error: " + e.getMessage());
            } catch (DbConnectionException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
        String sql = "UPDATE Courier SET Status = 'Cancelled' WHERE TrackingNumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, trackingNumber);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
            }
            return true;
        } catch (SQLException e) {
            try {
                throw new DbConnectionException("Failed to cancel order: " + e.getMessage());
            } catch (DbConnectionException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public List<Courier> getAssignedOrder(int courierStaffId) {
        List<Courier> assignedOrders = new ArrayList<>();
        String sql = "SELECT * FROM Courier WHERE EmployeeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, courierStaffId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Courier courier = new Courier(
                        rs.getInt("CourierID"),
                        rs.getString("SenderName"),
                        rs.getString("SenderAddress"),
                        rs.getString("ReceiverName"),
                        rs.getString("ReceiverAddress"),
                        rs.getDouble("Weight"),
                        rs.getString("Status"),
                        rs.getString("TrackingNumber"),
                        rs.getDate("DeliveryDate").toLocalDate(),
                        null, // User object (lazy load if needed)
                        new Employee(courierStaffId, null, null, null, null, 0), // Minimal employee
                        null  // Service object (lazy load)
                );
                assignedOrders.add(courier);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching assigned orders: " + e.getMessage());
        }
        return assignedOrders;
    }

    @Override
    public int addCourierStaff(Employee employee) throws InvalidEmployeeIdException {
        String sql = "INSERT INTO Employee (Name, Email, ContactNumber, Role, Salary) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getContactNumber());
            ps.setString(4, employee.getRole());
            ps.setDouble(5, employee.getSalary());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new InvalidEmployeeIdException("Failed to generate EmployeeID");
            }
        } catch (SQLException e) {
            throw new InvalidEmployeeIdException("Database error: " + e.getMessage());
        }
    }

  //--------------------

    public List<Map<String, Object>> generateRevenueReport() {
        List<Map<String, Object>> report = new ArrayList<>();
        String sql = "SELECT p.PaymentID, p.Amount, p.PaymentDate, " +
                "l.LocationName, c.TrackingNumber, u.Name AS CustomerName " +
                "FROM Payment p " +
                "JOIN Location l ON p.LocationID = l.LocationID " +
                "JOIN Courier c ON p.CourierID = c.CourierID " +
                "JOIN User u ON c.SenderName = u.Name " +
                "ORDER BY p.PaymentDate DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("PaymentID", rs.getInt("PaymentID"));
                row.put("Amount", rs.getDouble("Amount"));
                row.put("PaymentDate", rs.getDate("PaymentDate"));
                row.put("Location", rs.getString("LocationName"));
                row.put("TrackingNumber", rs.getString("TrackingNumber"));
                row.put("Customer", rs.getString("CustomerName"));
                report.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Revenue report error: " + e.getMessage());
        }
        return report;
    }

    public List<Map<String, Object>> generateShipmentReport() {
        List<Map<String, Object>> report = new ArrayList<>();
        String sql = "SELECT c.TrackingNumber, c.Status, c.DeliveryDate, " +
                "e.Name AS EmployeeName, u.Name AS CustomerName, c.Weight " +
                "FROM Courier c " +
                "LEFT JOIN Employee e ON c.EmployeeID = e.EmployeeID " +
                "LEFT JOIN User u ON c.SenderName = u.Name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("TrackingNumber", rs.getString("TrackingNumber"));
                row.put("Status", rs.getString("Status"));
                row.put("DeliveryDate", rs.getDate("DeliveryDate"));
                row.put("Employee", rs.getString("EmployeeName"));
                row.put("Customer", rs.getString("CustomerName"));
                row.put("Weight", rs.getDouble("Weight"));
                report.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Shipment report error: " + e.getMessage());
        }
        return report;
    }

    public List<Map<String, Object>> getPendingShipments() {
        List<Map<String, Object>> alerts = new ArrayList<>();
        String sql = "SELECT c.TrackingNumber, c.SenderName, c.DeliveryDate, " +
                "DATEDIFF(CURRENT_DATE, c.DeliveryDate) AS DaysDelayed, " +
                "e.Name AS AssignedEmployee, u.ContactNumber " +
                "FROM Courier c " +
                "LEFT JOIN Employee e ON c.EmployeeID = e.EmployeeID " +
                "LEFT JOIN User u ON c.SenderName = u.Name " +
                "WHERE c.Status NOT IN ('Delivered', 'Cancelled') " +
                "AND c.DeliveryDate < CURRENT_DATE " +
                "ORDER BY DaysDelayed DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("TrackingNumber", rs.getString("TrackingNumber"));
                row.put("Sender", rs.getString("SenderName"));
                row.put("DeliveryDate", rs.getDate("DeliveryDate"));
                row.put("DaysDelayed", rs.getInt("DaysDelayed"));
                row.put("Employee", rs.getString("AssignedEmployee"));
                row.put("Contact", rs.getString("ContactNumber"));
                alerts.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Pending shipments error: " + e.getMessage());
        }
        return alerts;
    }

    public List<Map<String, Object>> getCourierPerformance() {
        List<Map<String, Object>> analytics = new ArrayList<>();
        String sql = "SELECT e.EmployeeID, e.Name, " +
                "COUNT(c.CourierID) AS TotalDeliveries, " +
                "SUM(CASE WHEN c.Status = 'Delivered' THEN 1 ELSE 0 END) AS SuccessfulDeliveries " +
                "FROM Employee e " +
                "LEFT JOIN Courier c ON e.EmployeeID = c.EmployeeID " +
                "GROUP BY e.EmployeeID " +
                "ORDER BY SuccessfulDeliveries DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("EmployeeID", rs.getInt("EmployeeID"));
                row.put("Name", rs.getString("Name"));
                row.put("TotalDeliveries", rs.getInt("TotalDeliveries"));
                row.put("SuccessfulDeliveries", rs.getInt("SuccessfulDeliveries"));
                analytics.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Performance analytics error: " + e.getMessage());
        }
        return analytics;
    }
}