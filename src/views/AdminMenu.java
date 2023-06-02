package views;

import models.Flight;

import java.util.Scanner;

public class AdminMenu {
    private static AdminMenu instance = new AdminMenu();
    public static AdminMenu getInstance() { return instance; }
    private AdminMenu() {}
    Scanner input = new Scanner(System.in);

    public String menu() {
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::
                           Admin MENU OPTIONS
                ::::::::::::::::::::::::::::::::::::::::
                 ......................................
                    <1> Add
                    <2> Update
                    <3> Remove
                    <4> Flight schedules
                    <0> Sign out
                    Your option(0-4):\040""");
        return input.next();
    }

    public String flightId() {
        System.out.print("flightId: ");
        return input.next();
    }

    public String origin() {
        System.out.print("origin: ");
        return input.next();
    }

    public String destination() {
        System.out.print("destination: ");
        return input.next();
    }

    public String date() {
        System.out.print("date ->\n\tSubmit the Year, the month and the day as xxxx-xx-xx: ");
        return input.next();
    }

    public String time() {
        System.out.print("time ->\n\tSubmit the hour and the minute as xx:xx: ");
        return input.next();
    }

    public String price() {
        System.out.print("price: ");
        return input.next();
    }

    public String seat() {
        System.out.print("seat: ");
        return input.next();
    }

    public String flightIdToUpdate() {
        System.out.print("Enter the flightId you wanna update: ");
        return input.next();
    }

    public String featureToUpdate() {
        System.out.print("""
                Which feature you wanna update?(enter a number)
                  (0) Origin
                  (1) Destination
                  (2) Date
                  (3) Time
                  (4) Price
                  (5) Seat
                  Your option:\040""");
        return input.next();
    }

    public void messages() {
        System.out.println("\n!!!Submitted flightId does not exist.\n");
    }

    public void printFlightHeader() {
        System.out.printf("%20s%20s%20s%20s%20s%20s%20s\n%s\n",
                "FlightId", "Origin", "Destination", "Date", "Time", "Price", "Seat", "-".repeat(140));
    }

    public void printFlight(Flight flight) {
        System.out.println(flight);
    }

}
