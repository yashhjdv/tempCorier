package com.hexaware.entity;

import java.time.LocalDate;

public class Payment {
    private int paymentID;

    private Courier courier;
    private Location location;
    //    ------------
    private double amount;
    private LocalDate paymentDate;


    public Payment(int paymentID, Courier courier, Location location, double amount, LocalDate paymentDate) {
        this.paymentID = paymentID;
        this.courier = courier;
        this.location = location;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }


    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", courier=" + (courier != null ? courier.getCourierID() : "null") +
                ", location=" + (location != null ? location.getLocationID() : "null") +
                '}';
    }
}


