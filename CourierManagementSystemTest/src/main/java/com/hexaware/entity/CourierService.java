package com.hexaware.entity;

public class CourierService {
    private int serviceID;
    private String serviceName;
    private double cost;

    // Constructors
    public CourierService() {}

    public CourierService(int serviceID, String serviceName, double cost) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.cost = cost;
    }

    // Getters and Setters
    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getServiceId() {
        return serviceID;
    }

    @Override
    public String toString() {
        return "CourierService{" +
                "serviceID=" + serviceID +
                ", serviceName='" + serviceName + '\'' +
                ", cost=" + cost +
                '}';
    }

}
