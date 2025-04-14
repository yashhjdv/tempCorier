package com.hexaware.entity;

public class CourierCompany {


    public static final int MAX_ENTITIES = 100;

    private User[] users = new User[MAX_ENTITIES];
    private Courier[] couriers = new Courier[MAX_ENTITIES];
    private CourierService[] services = new CourierService[MAX_ENTITIES];
    private Employee[] employees = new Employee[MAX_ENTITIES];
    private Location[] locations = new Location[MAX_ENTITIES];
    private Payment[] payments = new Payment[MAX_ENTITIES];


    private int userCount = 0;
    private int courierCount = 0;
    private int serviceCount = 0;
    private int employeeCount = 0;
    private int locationCount = 0;
    private int paymentCount = 0;

    // Getters and adders for each entity
    public User[] getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (userCount < MAX_ENTITIES) {
            users[userCount++] = user;
        }
    }

    public Courier[] getCouriers() {
        return couriers;
    }

    public void addCourier(Courier courier) {
        if (courierCount < MAX_ENTITIES) {
            couriers[courierCount++] = courier;
        }
    }

    public CourierService[] getServices() {
        return services;
    }

    public void addCourierService(CourierService service) {
        if (serviceCount < MAX_ENTITIES) {
            services[serviceCount++] = service;
        }
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (employeeCount < MAX_ENTITIES) {
            employees[employeeCount++] = employee;
        }
    }

    public Location[] getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        if (locationCount < MAX_ENTITIES) {
            locations[locationCount++] = location;
        }
    }

    public Payment[] getPayments() {
        return payments;
    }

    public void addPayment(Payment payment) {
        if (paymentCount < MAX_ENTITIES) {
            payments[paymentCount++] = payment;
        }
    }
}
