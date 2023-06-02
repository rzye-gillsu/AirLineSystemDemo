package controlers;

import controlers.database.FlightsFile;
import models.Flight;
import views.AdminMenu;

import java.io.IOException;

public class AdminControl {
    private AdminMenu adminMenu = AdminMenu.getInstance();
    private FlightsFile flightsFile = FlightsFile.getInstance();
    private TicketControl ticketControl = TicketControl.getInstance();
    private Flight flight = new Flight();

    public AdminControl() throws IOException {
    }

    public void menu() throws IOException {
        sign_out:
        while (true) {
            int option = -1;
            while (option == -1 || option == -2) {
                String checkOption = adminMenu.menu();
                option = InputHandler.getInstance().checkAdminMenuInput(checkOption);
            }

            controlers.enumtools.AdminMenu adminMenu = controlers.enumtools.AdminMenu.values()[option];
            switch (adminMenu) {
                case ADD -> add();
                case UPDATE -> update();
                case REMOVE -> remove();
                case FLIGHT_SCHEDULE -> flightSchedule();
                case SIGN_OUT -> { break sign_out; }
            }
        }
    }

    private void add() throws IOException {

        StringBuilder flight = new StringBuilder();
        flight.append(String.format("%20s", adminMenu.flightId()));
        flight.append(String.format("%20s", adminMenu.origin()));
        flight.append(String.format("%20s", adminMenu.destination()));
        String check = null;

        boolean isFalse = false;
        while (!isFalse) {
            check = String.format("%20s", adminMenu.date());
            isFalse = InputHandler.getInstance().checkDateFormat(check.trim());
        }
        isFalse = false;
        flight.append(check);
        check = "";
        while (!isFalse) {
            check = String.format("%20s", adminMenu.time());
            isFalse = InputHandler.getInstance().checkTimeFormat(check.trim());
        }
        isFalse = false;
        flight.append(check);
        while (!isFalse) {
            check = String.format("%20s", adminMenu.price());
            isFalse = InputHandler.getInstance().isInteger(check.trim());
        }
        isFalse = false;
        flight.append(check);
        while (!isFalse) {
            check = String.format("%20s", adminMenu.seat());
            isFalse = InputHandler.getInstance().isInteger(check.trim());
        }
        flight.append(check);
        flightsFile.setSeek(flightsFile.length());
        flightsFile.writeRecord(flight.toString());
    }

    private void update() throws IOException {
        String flightId = adminMenu.flightIdToUpdate();
        Flight flight = new Flight();
        if (!flightsFile.search(flightId, 0))
            adminMenu.messages(0);
        else {
            flight = flightsFile.readRecord(flight);

            int option = -1;
            while (option == -1 || option == -2) {
                String checkOption = adminMenu.featureToUpdate();
                option = InputHandler.getInstance().checkAdminUpdateMenuInput(checkOption);
            }

            controlers.enumtools.AdminUpdateMenu adminUpdateMenu = controlers.enumtools.AdminUpdateMenu.values()[option];
            String check = null;
            switch (adminUpdateMenu) {
                case ORIGIN -> flight.setOrigin(adminMenu.origin());
                case DESTINATION -> flight.setDestination(adminMenu.destination());
                case DATE -> {
                    boolean isFalse = false;
                    while (!isFalse) {
                        check = adminMenu.date();
                        isFalse = InputHandler.getInstance().checkDateFormat(check);
                    }
                    flight.setDate(check);
                }
                case TIME -> {
                    boolean isFalse = false;
                    while (!isFalse) {
                        check = adminMenu.time();
                        isFalse = InputHandler.getInstance().checkTimeFormat(check);
                    }
                    flight.setTime(check);
                }
                case PRICE -> {
                    boolean isFalse = false;
                    while (!isFalse) {
                        check = adminMenu.price();
                        isFalse = InputHandler.getInstance().isInteger(check);
                    }
                    flight.setPrice(Integer.parseInt(check));
                }
                case SEAT -> {
                    boolean isFalse = false;
                    while (!isFalse) {
                        check = adminMenu.seat();
                        isFalse = InputHandler.getInstance().isInteger(check);
                    }
                    flight.setSeat(Integer.parseInt(check));
                }
            }
            flightsFile.setSeek(flightsFile.getCursor() - FlightsFile.SIZE_OF_RECORD);
            flightsFile.writeRecord(flight.toString());
        }
        ticketControl.notifyUsers(flightId);
    }

    private void remove() throws IOException {
        String flightId = adminMenu.remove();
        if (flightsFile.search(flightId, 0)) {
            flight = flightsFile.readRecord(flight);
            flightsFile.removeRecord(String.format("%20s", flightId));
        }
        else adminMenu.messages(0);
        ticketControl.notifyUsers(flightId, flight.getPrice());
    }

    private void flightSchedule() throws IOException {
        adminMenu.printFlightHeader();
        flightsFile.setSeek(0);
        while (flightsFile.getCursor() < flightsFile.length()) {
            flight = flightsFile.readRecord(flight);
            adminMenu.printFlight(flight);
        }
    }
}
