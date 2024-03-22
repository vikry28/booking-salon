package com.booking.service;

import java.util.Scanner;

public class ValidationService {
    private static Scanner scanner = new Scanner(System.in);

    public static String validateCustomerId() {
        String customerId;
        do {
            System.out.print("Enter Customer ID: ");
            customerId = scanner.nextLine();
            if (!customerId.matches("Cust-\\d{2}")) {
                System.out.println("Invalid Customer ID format. Please enter in the format 'Cust-XX'.");
            }
        } while (!customerId.matches("Cust-\\d{2}"));
        return customerId;
    }

    public static String validateEmployeeId() {
        String employeeId;
        do {
            System.out.print("Enter Employee ID: ");
            employeeId = scanner.nextLine();
            if (!employeeId.matches("Emp-\\d{2}")) {
                System.out.println("Invalid Employee ID format. Please enter in the format 'Emp-XX'.");
            }
        } while (!employeeId.matches("Emp-\\d{2}"));
        return employeeId;
    }

    public static String validateServiceId() {
        String serviceId;
        do {
            System.out.print("Enter Service ID: ");
            serviceId = scanner.nextLine();
            if (!serviceId.matches("Serv-\\d{2}")) {
                System.out.println("Invalid Service ID format. Please enter in the format 'Serv-XX'.");
            }
        } while (!serviceId.matches("Serv-\\d{2}"));
        return serviceId;
    }
}
