package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.repositories.ServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReservationService {
    private static Scanner input = new Scanner(System.in);

    public static void createReservation(List<Person> personList, List<Reservation> reservationList) {
        System.out.println("Membuat Reservasi\n");

        // Tampilkan daftar customer
        System.out.println("┌────┬─────────────────┬───────────┬──────────┬───────────┬──────────┐");
        System.out.println("│ No │       ID        │   Nama    │  Alamat  │ Membership│   Uang   │");
        System.out.println("├────┼─────────────────┼───────────┼──────────┼───────────┼──────────┤");
        int counter = 1;
        for (Person person : personList) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                String membership = customer.getMember() != null ? customer.getMember().getMembershipName() : "none";
                String formattedWallet = String.format("Rp%,.0f", customer.getWallet());
                System.out.printf("│ %-2d │ %-15s │ %-9s │ %-8s │ %-9s │ %-8s │\n", counter, customer.getId(), customer.getName(), customer.getAddress(), membership, formattedWallet);
                counter++;
            }
        }
        System.out.println("└────┴─────────────────┴───────────┴──────────┴───────────┴──────────┘");

        // Memilih customer berdasarkan ID
        System.out.print("\nSilahkan Masukkan Customer ID: ");
        String customerId = input.nextLine();
        Customer customer = (Customer) personList.stream()
                .filter(person -> person instanceof Customer && person.getId().equals(customerId))
                .findFirst().orElse(null);

        if (customer == null) {
            System.out.println("Customer tidak ditemukan.");
            return;
        }

        // Menampilkan daftar employee
        System.out.println("┌─────┬──────────┬─────────────┬────────────┬────────────┐");
        System.out.println("│ No. │    ID    │    Nama     │   Alamat   │ Pengalaman │");
        System.out.println("├─────┼──────────┼─────────────┼────────────┼────────────┤");
        counter = 1;
        for (Person person : personList) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                System.out.printf("│ %-3d │ %-8s │ %-11s │ %-10s │ %-10d │\n", counter, employee.getId(), employee.getName(), employee.getAddress(), employee.getExperience());
                counter++;
            }
        }    
        System.out.println("└─────┴──────────┴─────────────┴────────────┴────────────┘");

        // Memilih employee berdasarkan ID
        System.out.print("\nSilahkan Masukkan Employee ID: ");
        String employeeId = input.nextLine();
        Employee employee = (Employee) personList.stream()
                .filter(person -> person instanceof Employee && person.getId().equals(employeeId))
                .findFirst().orElse(null);

        if (employee == null) {
            System.out.println("Employee tidak ditemukan.");
            return;
        }

        // Menampilkan daftar service
        List<com.booking.models.Service> availableServices = ServiceRepository.getAllService();
        System.out.println("┌─────┬───────────┬───────────────────────────┬────────┐");
        System.out.println("│ No. │ ServiceID │         Service Name      │  Harga │");
        System.out.println("├─────┼───────────┼───────────────────────────┼────────┤");
        counter = 1;
        for (com.booking.models.Service service : availableServices) {
            System.out.printf("│ %-3d │ %-9s │ %-25s │ Rp%,6.0f │\n", counter, service.getServiceId(), service.getServiceName(), service.getPrice());
            counter++;
        }
        System.out.println("└─────┴───────────┴───────────────────────────┴────────┘");

        // Memilih service
        List<com.booking.models.Service> selectedServices = new ArrayList<>();
        boolean addingService = true;
        while (addingService) {
            System.out.print("\nSilahkan Masukkan Service ID: ");
            String serviceId = input.nextLine();

            com.booking.models.Service selectedService = availableServices.stream()
                    .filter(service -> service.getServiceId().equalsIgnoreCase(serviceId))
                    .findFirst().orElse(null);

            if (selectedService != null) {
                selectedServices.add(selectedService);
                availableServices.remove(selectedService);

                System.out.print("Ingin pilih service yang lain (Y/T)? ");
                String choice = input.nextLine();
                addingService = choice.equalsIgnoreCase("Y");
            } else {
                System.out.println("Service tidak ditemukan.");
            }
        }

        // Membuat reservasi
        String reservationId = "Rsv-" + String.format("%02d", reservationList.size() + 1);
        Reservation reservation = new Reservation(reservationId, customer, employee, selectedServices, "In Process");
        reservationList.add(reservation);
        System.out.println("\nBooking Berhasil!");
        System.out.println("Total Biaya Booking : Rp. " + reservation.getReservationPrice());
    }

    public static void finishOrCancelReservation(List<Reservation> reservationList) {
        System.out.println("\nFinish/Cancel Reservasi\n");

        // Menampilkan daftar reservasi yang sedang diproses
        System.out.println("┌───┬────────────────────┬───────────────┬────────────────────────────────────────────┬───────────────┐");
        System.out.println("│ No│         ID         │ Nama Customer │               Nama Service                 │ Total Biaya   │");
        System.out.println("├───┼────────────────────┼───────────────┼────────────────────────────────────────────┼───────────────┤");
        int counter = 1;
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("In Process")) {
                System.out.printf("│ %-3d │ %-18s │ %-13s │ %-40s │ Rp. %-9.0f │\n", counter, reservation.getReservationId(),
                        reservation.getCustomer().getName(), reservation.getServices().stream()
                                .map(com.booking.models.Service::getServiceName).collect(Collectors.joining(", ")),
                        reservation.getReservationPrice());
                counter++;
            }
        }
        System.out.println("└───┴────────────────────┴───────────────┴────────────────────────────────────────────┴───────────────┘");

        // Memilih reservasi berdasarkan ID
        System.out.print("\nSilahkan Masukkan Reservation ID: ");
        String reservationId = input.nextLine();

        Reservation reservation = reservationList.stream()
                .filter(res -> res.getReservationId().equals(reservationId))
                .findFirst().orElse(null);

        if (reservation == null) {
            System.out.println("Reservation tidak ditemukan.");
            return;
        }

        // Menyelesaikan atau membatalkan reservasi
        if (reservation.getWorkstage().equalsIgnoreCase("In Process")) {
            System.out.print("Selesaikan reservasi (Finish/Cancel): ");
            String option = input.nextLine();

            if (option.equalsIgnoreCase("Finish")) {
                reservation.setWorkstage("Finish");
                System.out.println("Reservasi dengan ID " + reservationId + " sudah Finish");
            } else if (option.equalsIgnoreCase("Cancel")) {
                reservation.setWorkstage("Canceled");
                System.out.println("Reservasi dengan ID " + reservationId + " sudah Cancel");
            } else {
                System.out.println("Opsi tidak valid.");
            }
        } else {
            System.out.println("Reservasi ini tidak dapat diselesaikan/dibatalkan karena sudah selesai/dibatalkan sebelumnya.");
        }
    }
}
