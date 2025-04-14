package com.hexaware.entity;

import java.time.LocalDate;

public class Courier {
    private static int trackingCounter = 1000; // static counter to auto-generate tracking numbers

    private int courierID;
    private String senderName;
    private String senderAddress;
    private String receiverName;
    private String receiverAddress;
    private double weight;
    private String status;
    private String trackingNumber;
    private LocalDate deliveryDate;

    // Composition
    private User user;
    private Employee employee;
    private CourierService service;

    // Default constructor
    public Courier() {
        super();
    }

    // Constructor with manual tracking number (for loading from DB or updates)
    public Courier(int courierID, String senderName, String senderAddress, String receiverName, String receiverAddress,
                   double weight, String status, String trackingNumber, LocalDate deliveryDate,
                   User user, Employee employee, CourierService service) {
        this.courierID = courierID;
        this.senderName = senderName;
        this.senderAddress = senderAddress;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.weight = weight;
        this.status = status;
        this.trackingNumber = trackingNumber;
        this.deliveryDate = deliveryDate;
        this.user = user;
        this.employee = employee;
        this.service = service;
    }

    // Constructor with auto-generated tracking number (used for placing new orders)
    public Courier(String senderName, String senderAddress, String receiverName, String receiverAddress,
                   double weight, String status, LocalDate deliveryDate,
                   User user, Employee employee, CourierService service) {

        this.courierID = trackingCounter;
        this.trackingNumber = "TRK" + trackingCounter++;
        this.senderName = senderName;
        this.senderAddress = senderAddress;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.weight = weight;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.user = user;
        this.employee = employee;
        this.service = service;
    }

    // Getters and Setters
    public int getCourierID() {
        return courierID;
    }

    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public CourierService getService() {
        return service;
    }

    public void setService(CourierService service) {
        this.service = service;
    }

    // toString method
    @Override
    public String toString() {
        return "Courier{" +
                "courierID=" + courierID +
                ", senderName='" + senderName + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", weight=" + weight +
                ", status='" + status + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", user=" + (user != null ? user.getUserID() : "null") +
                ", employee=" + (employee != null ? employee.getEmployeeID() : "null") +
                ", service=" + (service != null ? service.getServiceID() : "null") +
                '}';
    }

    // Optional: Reset method for counter (useful in testing or reloading from DB)
    public static void resetTrackingCounter(int value) {
        trackingCounter = value;
    }
}
