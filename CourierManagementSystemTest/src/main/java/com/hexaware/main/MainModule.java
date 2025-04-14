package com.hexaware.main;

        import com.hexaware.dao.*;
        import com.hexaware.entity.*;
        import com.hexaware.exception.*;

        import java.time.LocalDate;
        import java.util.*;

public class MainModule {
    private static CourierServiceDb courierService;

    static {
        try {
            courierService = new CourierServiceDb();
        } catch (DbConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    /* Alternative Implementations
    // Array-based implementation
    // private static CourierUserServiceImpl courierService = new CourierUserServiceImpl();
    // private static CourierAdminServiceImpl adminService = new CourierAdminServiceImpl();

    // Collection-based implementation
    // private static CourierUserServiceCollectionImpl courierService = new CourierUserServiceCollectionImpl();
    // private static CourierAdminServiceCollectionImpl adminService = new CourierAdminServiceCollectionImpl();
    */

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: placeOrder(); break;
                case 2: checkOrderStatus(); break;
                case 3: cancelOrder(); break;
                case 4: viewAssignedOrders(); break;
                case 5: addCourierStaff(); break;
                case 6: displayRevenueReport(); break;
                case 7: displayShipmentReport(); break;
                case 8: displayPendingShipments(); break;
                case 9: displayCourierPerformance(); break;
                case 10: running = false; break;
                default: System.out.println("Invalid choice!");
            }
        }
        System.out.println("Exiting system...");
    }

    private static void printMainMenu() {
        System.out.println("\n===== Courier Management System =====");
        System.out.println("1. Place Order");
        System.out.println("2. Check Order Status");
        System.out.println("3. Cancel Order");
        System.out.println("4. View Assigned Orders");
        System.out.println("5. Add Courier Staff");
        System.out.println("6. Revenue Report");
        System.out.println("7. Shipment Report");
        System.out.println("8. Pending Shipments Alert");
        System.out.println("9. Courier Performance Analytics");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }


    private static void placeOrder() {
        System.out.println("\n--- Place New Order ---");
        System.out.print("Enter sender name: ");
        String senderName = scanner.nextLine();

        System.out.print("Enter sender address: ");
        String senderAddress = scanner.nextLine();

        System.out.print("Enter receiver name: ");
        String receiverName = scanner.nextLine();

        System.out.print("Enter receiver address: ");
        String receiverAddress = scanner.nextLine();

        System.out.print("Enter weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter employee ID for assignment: ");
        int empId = scanner.nextInt();
        scanner.nextLine();

        // Simplified employee/user objects for demo
        Employee emp = new Employee(empId, null, null, null, null, 0);
        User user = new User(1, "Demo User", "user@demo.com", null, null, null);

        Courier courier = new Courier(
                senderName, senderAddress, receiverName, receiverAddress,
                weight, "Processing", LocalDate.now().plusDays(3), user, emp, null
        );

        try {
            String trackingNum = courierService.placeOrder(courier);
            System.out.printf("\nOrder placed successfully!\nTracking Number: %s\n", trackingNum);
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }

    private static void checkOrderStatus() {
        System.out.print("\nEnter tracking number: ");
        String trackingNum = scanner.nextLine();

        try {
            String status = courierService.getOrderStatus(trackingNum);
            System.out.printf("\n%-20s: %s\n", "Tracking Number", trackingNum);
            System.out.printf("%-20s: %s\n", "Status", status);
        } catch (TrackingNumberNotFoundException e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }

    private static void cancelOrder() {
        System.out.print("\nEnter tracking number to cancel: ");
        String trackingNum = scanner.nextLine();

        try {
            boolean cancelled = courierService.cancelOrder(trackingNum);
            if (cancelled) {
                System.out.printf("Order %s cancelled successfully.\n", trackingNum);
            }
        } catch (TrackingNumberNotFoundException e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }

    private static void viewAssignedOrders() {
        System.out.print("\nEnter employee ID: ");
        int empId = scanner.nextInt();
        scanner.nextLine();

        List<Courier> orders = courierService.getAssignedOrder(empId);
        System.out.println("\n=== Assigned Orders ===");
        System.out.printf("%-15s %-20s %-15s %-12s\n",
                "Tracking#", "Receiver", "Weight", "Status");

        for (Courier c : orders) {
            System.out.printf("%-15s %-20s %-15.2f %-12s\n",
                    c.getTrackingNumber(),
                    c.getReceiverName(),
                    c.getWeight(),
                    c.getStatus());
        }
    }

    private static void addCourierStaff() {
        System.out.println("\n--- Add Courier Staff ---");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter contact: ");
        String contact = scanner.nextLine();

        System.out.print("Enter role: ");
        String role = scanner.nextLine();

        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        Employee emp = new Employee(0, name, email, contact, role, salary);

        try {
            int newId = courierService.addCourierStaff(emp);
            System.out.printf("Employee added successfully! ID: %d\n", newId);
        } catch (InvalidEmployeeIdException e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }

    private static void displayRevenueReport() {
        System.out.println("\n=== Revenue Report ===");
        System.out.printf("%-12s %-10s %-15s %-20s %-15s %-20s\n",
                "PaymentID", "Amount", "Date", "Location", "Tracking#", "Customer");

        courierService.generateRevenueReport().forEach(row -> {
            System.out.printf("%-12d $%-10.2f %-15s %-20s %-15s %-20s\n",
                    row.get("PaymentID"),
                    row.get("Amount"),
                    row.get("PaymentDate"),
                    row.get("Location"),
                    row.get("TrackingNumber"),
                    row.get("Customer"));
        });
    }

    private static void displayShipmentReport() {
        System.out.println("\n=== Shipment Report ===");
        System.out.printf("%-15s %-15s %-15s %-20s %-20s %-10s\n",
                "Tracking#", "Status", "DeliveryDate", "Employee", "Customer", "Weight");

        courierService.generateShipmentReport().forEach(row -> {
            System.out.printf("%-15s %-15s %-15s %-20s %-20s %-10.2f\n",
                    row.get("TrackingNumber"),
                    row.get("Status"),
                    row.get("DeliveryDate"),
                    row.get("Employee"),
                    row.get("Customer"),
                    row.get("Weight"));
        });
    }

    private static void displayPendingShipments() {
        System.out.println("\n=== Pending Shipments Alert ===");
        System.out.printf("%-15s %-20s %-15s %-12s %-20s %-15s\n",
                "Tracking#", "Sender", "DeliveryDate", "DaysLate", "AssignedTo", "Contact");

        courierService.getPendingShipments().forEach(row -> {
            System.out.printf("%-15s %-20s %-15s %-12d %-20s %-15s\n",
                    row.get("TrackingNumber"),
                    row.get("Sender"),
                    row.get("DeliveryDate"),
                    row.get("DaysDelayed"),
                    row.get("Employee"),
                    row.get("Contact"));
        });
    }

    private static void displayCourierPerformance() {
        System.out.println("\n=== Courier Performance Analytics ===");
        System.out.printf("%-10s %-20s %-15s %-20s\n",
                "EmpID", "Name", "TotalOrders", "Successful");

        courierService.getCourierPerformance().forEach(row -> {
            System.out.printf("%-10d %-20s %-15d %-20d\n",
                    row.get("EmployeeID"),
                    row.get("Name"),
                    row.get("TotalDeliveries"),
                    row.get("SuccessfulDeliveries"));
        });
    }


}