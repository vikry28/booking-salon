package com.booking.models;

import java.util.List;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
                       String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
        this.workstage = workstage;
    }

    private double calculateReservationPrice() {
        double totalPrice = 0;
        for (Service service : services) {
            totalPrice += service.getPrice();
        }
        double discount = 0;
        if (customer.getMember().getMembershipName().equalsIgnoreCase("silver")) {
            discount = totalPrice * 0.05;
        } else if (customer.getMember().getMembershipName().equalsIgnoreCase("gold")) {
            discount = totalPrice * 0.10;
        }
        return totalPrice - discount;
    }

    public void finishReservation() {
        if (this.workstage.equalsIgnoreCase("In Process")) {
            this.workstage = "Finish";
            System.out.println("Reservation with ID " + this.reservationId + " has been finished.");
        } else {
            System.out.println("This reservation cannot be finished as it's not in process.");
        }
    }

    public void cancelReservation() {
        if (this.workstage.equalsIgnoreCase("In Process")) {
            this.workstage = "Canceled";
            System.out.println("Reservation with ID " + this.reservationId + " has been canceled.");
        } else {
            System.out.println("This reservation cannot be canceled as it's not in process.");
        }
    }
}
