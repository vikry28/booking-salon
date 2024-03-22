package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;

import java.util.List;
import java.util.Scanner;

public class PrintService {
    @SuppressWarnings("unused")
    private static Scanner input = new Scanner(System.in);

    public static void printMenu(String title, String[] menuArr) {
        int num = 1;
        System.out.println(title);
        for (String menuItem : menuArr) {
            System.out.println(num + ". " + menuItem);
            num++;
        }
    }

    public String printServices(List<com.booking.models.Service> serviceList) {
        StringBuilder result = new StringBuilder();
        // Menggunakan Stream dan method forEach untuk mencetak daftar layanan
        serviceList.forEach(service -> result.append(service.getServiceName()).append(", "));
        return result.toString();
    }

    public void showRecentReservation(List<Reservation> reservationList) {
        System.out.println("\tTampilan Recent Reservation");
        System.out.printf("┌───────┬──────────────────────────────────────┬───────────────┬────────────────────────────────────────────────────┬───────────────┬───────────────┐\n");
        System.out.printf("│ %-5s  │ %-36s │ %-15s │ %-40s │ %-15s │ %-15s│\n",
                "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        System.out.printf("├───────┼──────────────────────────────────────┼───────────────┼────────────────────────────────────────────────────┼───────────────┼───────────────┤\n");
    
        int num = 1; // Variabel num dideklarasikan di luar ekspresi lambda agar bisa diakses dan ditingkatkan nilainya setiap kali iterasi
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("In Process")) {
                String serviceNames = printServices(reservation.getServices());
                // Jika serviceNames lebih panjang dari 40 karakter, potong dan tambahkan "..."
                if (serviceNames.length() > 40) {
                    serviceNames = serviceNames.substring(0, 37) + "...";
                }
                System.out.printf("│ %-5d │ %-36s │ %-15s │ %-40s │ %-15.0f │ %-15s │\n",
                        num++, reservation.getReservationId(), reservation.getCustomer().getName(), serviceNames, reservation.getReservationPrice(), reservation.getWorkstage());
            }
        }
        System.out.printf("└───────┴──────────────────────────────────────┴───────────────┴────────────────────────────────────────────────────┴───────────────┴───────────────┘\n");
    }
    
    
    // Metode untuk menampilkan semua pelanggan
    public static void showAllCustomer(List<Person> personList) {
        System.out.println("\tTampilan Semua Pelanggan");
        System.out.printf("┌────┬─────────────┬─────────────────┬───────────┬─────────────┬───────────┐\n");
        System.out.printf("│ %-2s │ %-11s │ %-15s │ %-9s │ %-10s │ %-6s │\n",
                "No", "ID", "Nama", "Alamat", "Membership", "Uang");
        System.out.printf("├────┼─────────────┼─────────────────┼───────────┼─────────────┼───────────┤\n");
    
        int counter = 1;
        for (Person person : personList) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                String membership = customer.getMember() != null ? customer.getMember().getMembershipName() : "none";
                String formattedWallet = String.format("Rp%,.0f", customer.getWallet());
                System.out.printf("│ %-2d │ %-11s │ %-15s │ %-9s │ %-10s │ %-9s │\n", counter, customer.getId(), customer.getName(), customer.getAddress(), membership, formattedWallet);
                counter++;
            }
        }
        System.out.printf("└────┴─────────────┴─────────────────┴───────────┴─────────────┴───────────┘\n");
    }
    
    // Metode untuk menampilkan semua karyawan
    public static void showAllEmployee(List<Person> personList) {
        int num = 1;
        System.out.println("┌─────┬─────────────────────┬────────┬──────────┬───────────┐");
        System.out.println("│ No. │ ID                  │ Nama   │ Alamat   │ Pengalaman│");
        System.out.println("├─────┼─────────────────────┼────────┼──────────┼───────────┤");
        for (Person person : personList) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                System.out.printf("│ %-4d│ %-20s│ %-7s│ %-9s│ %-10d│\n",
                        num++, employee.getId(), employee.getName(), employee.getAddress(), employee.getExperience());
            }
        }
        System.out.println("└─────┴─────────────────────┴────────┴──────────┴───────────┘");
    }
    
    // Metode untuk menampilkan riwayat reservasi dan total keuntungan
    public void showHistoryReservation(List<Reservation> reservationList) {
        double totalProfit = reservationList.stream()
                .filter(reservation -> reservation.getWorkstage().equalsIgnoreCase("Finish"))
                .mapToDouble(Reservation::getReservationPrice)
                .sum();
    
        System.out.println("┌─────┬─────────────────────┬────────┬──────────┬───────────┬────────────┐");
        System.out.println("│ No. │ ID                  │ Nama   │ Alamat   │ Pengalaman│ Total Biaya│");
        System.out.println("├─────┼─────────────────────┼────────┼──────────┼───────────┼────────────┤");
        int num = 1;
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Finish")) {
                System.out.printf("│ %-4d│ %-20s │ %-6s │ %-8s │ %-9s │ %-11.0f │\n",
                        num++, reservation.getReservationId(), reservation.getCustomer().getName(),
                        reservation.getCustomer().getAddress(), reservation.getEmployee().getExperience(),
                        reservation.getReservationPrice());
            }
        }
        System.out.println("├─────┴─────────────────────┴────────┴──────────┴───────────┴────────────┤");
        System.out.printf("│ Total Keuntungan: %-59.0f │\n", totalProfit);
        System.out.println("└──────────────────────────────────────────────────────────────────────────┘");
    }
      
}

