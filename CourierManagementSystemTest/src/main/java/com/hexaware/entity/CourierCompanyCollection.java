package com.hexaware.entity;

import java.util.ArrayList;
import java.util.List;

public class CourierCompanyCollection {
    private List<User> users = new ArrayList<>();
    private List<Courier> couriers = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private List<CourierService> courierServices = new ArrayList<>();

    private List<Courier> courierList;


    // Getters and Setters
    public List<User> getUsers() {
        return users;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<CourierService> getCourierServices() {
        return courierServices;
    }


    public CourierCompanyCollection() {
        this.courierList = new ArrayList<>();
    }

    public List<Courier> getCourierList() {
        return courierList;
    }

    public void setCourierList(List<Courier> courierList) {
        this.courierList = courierList;
    }

    public void addEmployee(Employee obj) {
    }
}
