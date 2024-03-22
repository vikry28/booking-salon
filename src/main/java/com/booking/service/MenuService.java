package com.booking.service;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    private static PrintService printService = new PrintService();

    public static void mainMenu() {
        String[] mainMenuArr = {"Tampilkan Data", "Membuat Reservasi", "Finish/Cancel Reservasi", "Exit"};

        boolean backToMainMenu = false;
        do {
            PrintService.printMenu("\tAplikasi Booking Salon\n", mainMenuArr);
            int optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    showDataSubMenu();
                    break;
                case 2:
                    ReservationService.createReservation(personList, reservationList);
                    break;
                case 3:
                    ReservationService.finishOrCancelReservation(reservationList);
                    break;
                case 4:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!backToMainMenu);
    }

    private static void showDataSubMenu() {
        String[] subMenuArr = {"Tampilkan Recent Reservation", "Tampilkan Customer", "Tampilkan Employee", "Tampilkan History Reservation + Total Keuntungan", "Back to Main Menu"};

        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("\tData Booking Salon\n", subMenuArr);
            int optionSubMenu = Integer.valueOf(input.nextLine());
            switch (optionSubMenu) {
                case 1:
                    printService.showRecentReservation(reservationList);
                    break;
                case 2:
                    PrintService.showAllCustomer(personList);
                    break;
                case 3:
                    PrintService.showAllEmployee(personList);
                    break;
                case 4:
                    printService.showHistoryReservation(reservationList);
                    break;
                case 5:
                    backToSubMenu = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!backToSubMenu);
    }
}
