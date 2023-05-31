package views;

import models.Flight;
import models.Ticket;

import java.util.Scanner;

public class PassengerMenu {
    private static PassengerMenu instance = new PassengerMenu();
    public static PassengerMenu getInstance() { return instance; }
    private PassengerMenu() {}
    Scanner input = new Scanner(System.in);
    public String menu() {
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::
                         PASSENGER MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                 ......................................
                    <1> Change password
                    <2> Search flight tickets
                    <3> Booking ticket
                    <4> Ticket cancellation
                    <5> Booked tickets
                    <6> Add charge
                    <0> Sign out
                    Your option:\040"""); // what does \s do?
        return input.next();
    }

    public void printPreviousPassword(String password) {
        System.out.printf("\nYour previous password: %s\n", password);
    }

    public String password() {
        System.out.print("Password (must be at least 4 characters): ");
        return input.next();
    }

    public void previousCharge(int charge) {
        if (charge == -1)
            charge = 0;
        System.out.printf("\nYour current charge: %d$\n", charge);
    }

    public String charge() {
        System.out.print("Charge: ");
        return input.next();
    }

    public String getFlightId(String id) {
        System.out.printf("The %s: ", id);
        return input.next();
    }

    public void messages(int messageNumber) {
        switch (messageNumber) {
            case 0 -> System.out.println("\n!!!It must be longer a bit! Try again later...\n");
            case 1 -> System.out.println("\n!!!Submitted flightId does not exist.\n");
            case 2 -> System.out.println("\n!!!Your charge is not enough!\n");
            case 3 -> System.out.println("\n!!!Submitted ticketId does not exist.\n");
        }
    }

    public void printTicketHeader() {
        System.out.printf("%20s%20s%20s%20s%20s%20s%20s%20s\n%s\n",
                "TicketId", "FlightId", "Origin", "Destination", "Date", "Time", "Price", "Seat", "-".repeat(160));
    }

    public void printTicket(Ticket ticket, Flight flight) {
        System.out.println(String.format("%20s", ticket.getTicketId()) + flight);
    }
}
