package controlers;

import controlers.database.FlightsFile;
import controlers.database.PassengersFile;
import controlers.database.TicketsFile;
import models.Flight;
import models.Passenger;
import models.Ticket;
import views.AdminMenu;
import views.PassengerMenu;

import java.io.IOException;

public class TicketControl {
    private static TicketControl instance = new TicketControl();

    public static TicketControl getInstance() {
        return instance;
    }

    private TicketControl() {
    }
    private Ticket ticket = new Ticket();
    private Passenger passenger = new Passenger();
    private Flight flight = new Flight();

    private FlightsFile flightsFile = FlightsFile.getInstance();
    private PassengersFile passengersFile = PassengersFile.getInstance();
    private TicketsFile ticketsFile = TicketsFile.getInstance();

    private PassengerMenu passengerMenu = PassengerMenu.getInstance();
    private AdminMenu adminMenu = AdminMenu.getInstance();

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void makeNewTicket(String flightId) throws IOException {
        if (flightsFile.search(flightId, 0, "flightId")) {
            if (setParametersWhenTicketIsAdded()) {
                ticketsFile.setSeek(ticketsFile.length());
                ticketsFile.writeRecord(new Ticket(flightId, passenger.getUsername().trim()).toString());
            }
        } else
            passengerMenu.messages(1);
    }

    /**
     * Lessons the flight seats by 1,
     * and lesson the users charge when buying the ticket.
     * @return true it charge is enough and reserving the flight is possible.
     * @throws IOException
     */
    private boolean setParametersWhenTicketIsAdded() throws IOException {
        flight = flightsFile.readRecord(flight);
        if (flight.getSeat() == 0) {
            passengerMenu.messages(4);
            return false;
        }
        if (passenger.getCharge() < flight.getPrice()) {
            passengerMenu.messages(2);
            return false;
        } else {
            flight.setSeat(flight.getSeat() - 1);
            passenger.setCharge(passenger.getCharge() - flight.getPrice());

            flightsFile.search(flight.getFlightId(), 0, "flightId");
            flightsFile.writeRecord(flight.toString());
            passengersFile.search(passenger.getUsername(), 0, "username");
            passengersFile.writeRecord(passenger.toString());
        }
        return true;
    }

    public void ticketCancellation(String ticketId) throws IOException {
        if (ticketsFile.search(ticketId, 0, "ticketId")) {
            long pos = ticketsFile.getCursor();
            setParametersWhenTicketIsCancelled(pos);
            ticketsFile.setSeek(pos);
            ticketsFile.removeRecord(ticketId);
        }
        else passengerMenu.messages(3);
    }

    /**
     * Adds the flight seats by 1,
     * and Adds the users charge when buying the ticket.
     * @param pos indicates where ticket should be overwritten.
     * @throws IOException
     */
    private void setParametersWhenTicketIsCancelled(long pos) throws IOException {
        ticket = ticketsFile.readRecord(ticket);

        flightsFile.search(ticket.getFlightId(), 0, "flightId");
        long flightPos = flightsFile.getCursor();
        flight = flightsFile.readRecord(flight);
        flight.setSeat(flight.getSeat() + 1);
        flightsFile.setSeek(flightPos);
        flightsFile.writeRecord(flight.toString());

        passenger.setCharge(passenger.getCharge() + flight.getPrice());
        passengersFile.search(passenger.getUsername(), 0, "username");
        passengersFile.writeRecord(passenger.toString());
    }

    public void printChart() throws IOException {
        ticketsFile.setSeek(0);
        Ticket ticket = new Ticket();
        while (ticketsFile.getCursor() < ticketsFile.length())
            if (ticketsFile.search(passenger.getUsername(), ticketsFile.getCursor(), "username")) {
                ticket = ticketsFile.readRecord(ticket);
                flightsFile.search(ticket.getFlightId(), 0, "flightId");
                flight = flightsFile.readRecord(flight);
                passengerMenu.printTicket(ticket, flight);
            }
    }

    private Flight gettingInputs() throws IOException {
        Flight search = new Flight();
        search.setFlightId(adminMenu.flightId());
        search.setOrigin(adminMenu.origin());
        search.setDestination(adminMenu.destination());

        String check = null;
        boolean isFalse = false;

        while (!isFalse) {
            check = adminMenu.date();
            if (check.equals("0".trim()))
                break;
            isFalse = InputHandler.getInstance().checkDateFormat(check.trim());
        }
        search.setDate(check);
        isFalse = false;
        while (!isFalse) {
            check = adminMenu.time();
            if (check.equals("0".trim()))
                break;
            isFalse = InputHandler.getInstance().checkTimeFormat(check.trim());
        }
        search.setTime(check);
        isFalse = false;
        while (!isFalse) {
            check = adminMenu.price();
            if (check.equals("0".trim()))
                break;
            isFalse = InputHandler.getInstance().isInteger(check.trim());
        }
        search.setPrice(Integer.parseInt(check));
        isFalse = false;
        while (!isFalse) {
            check = adminMenu.seat();
            if (check.equals("0".trim()))
                break;
            isFalse = InputHandler.getInstance().isInteger(check.trim());
        }
        search.setSeat(Integer.parseInt(check));
        return search;
    }

    public void searchTicket() throws IOException {
        Flight search = gettingInputs();
        flightsFile.setSeek(0);
        passengerMenu.searchChart();
        while (flightsFile.getCursor() < flightsFile.length()) {
            flight = flightsFile.readRecord(flight);
            if (flight.equals(search)) adminMenu.printFlight(flight);
        }
    }

    public void notifyUsers(String flightId, int price) throws IOException {
        addingNotify(flightId, price);
    }

    public void notifyUsers(String flightId) throws IOException {
        addingNotify(flightId, 0);
    }

    private void addingNotify(String flightId, int price) throws IOException {
        ticketsFile.setSeek(0);
        long passengerPos = 0;
        while (ticketsFile.getCursor() < ticketsFile.length()) {
            long ticketsPos = ticketsFile.getCursor();
            if (ticketsFile.search(flightId, ticketsFile.getCursor(), "flightId")) {
                ticket = ticketsFile.readRecord(ticket);
                passengersFile.search(ticket.getUsername(), 0, "username");
                passengerPos = passengersFile.getCursor();
                passenger = passengersFile.readRecord(passenger);
                passenger.setNotifyUser(passenger.getNotifyUser() + 1);

                removingTicket(ticket.getFlightId(), price);
                passengersFile.setSeek(passengerPos);
                passengersFile.writeRecord(passenger.toString());
            }
            ticketsFile.setSeek(ticketsPos + ticketsFile.SIZE_OF_RECORD);
        }
    }

    private void removingTicket(String flightId, int price) throws IOException {
        if (price == 0) ticketCancellation(flightId);
        else {
            if (ticketsFile.search(flightId, 0, "flightId"))
                ticketsFile.removeRecord(flightId);
            passenger.setCharge(passenger.getCharge() + price);
        }
    }
}
