package controlers;

import controlers.database.FlightsFile;
import controlers.database.PassengersFile;
import controlers.database.TicketsFile;
import models.Flight;
import models.Passenger;
import models.Ticket;
import views.PassengerMenu;

import java.io.IOException;

public class TicketControl {
    private static TicketControl instance = new TicketControl();

    public static TicketControl getInstance() {
        return instance;
    }

    private TicketControl() {
    }

    private Passenger passenger = new Passenger();
    private Flight flight = new Flight();

    private FlightsFile flightsFile = FlightsFile.getInstance();
    private PassengersFile passengersFile = PassengersFile.getInstance();
    private TicketsFile ticketsFile = TicketsFile.getInstance();

    private PassengerMenu passengerMenu = PassengerMenu.getInstance();

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /**
     * here I have to read flights based on the flightId we get from passengerControl.
     * here we also access the user's array of tickets, so it means we should get user from passengerControl
     * somewhere here as well.
     * each flight we read from the file that the user want to reserve must be saved into the ticket's array.
     * But should it be saved as an object or as a string? by the way we don't need to change the flight which is reserved.
     */


    public void makeNewTicket(String flightId) throws IOException {
        if (flightsFile.search(flightId, 0)) {
            if (setParametersWhenTicketIsAdded()) {
                ticketsFile.setSeek(ticketsFile.length());
                ticketsFile.writeRecord(new Ticket(flightId, passenger.getUsername().trim()).toString());
            }
        } else
            passengerMenu.messages(1);
    }

    private boolean setParametersWhenTicketIsAdded() throws IOException {
        flight = flightsFile.readRecord(flight);
        if (passenger.getCharge() < flight.getPrice()) {
            passengerMenu.messages(2);
            return false;
        } else {
            flight.setSeat(flight.getSeat() - 1); // check if flight's seats is not zero.
            passenger.setCharge(passenger.getCharge() - flight.getPrice());

            flightsFile.search(flight.getFlightId(), 0);
            flightsFile.writeRecord(flight.toString());
            passengersFile.search(passenger.getUsername(), 0);
            passengersFile.writeRecord(passenger.toString());
        }
        return true;
    }

    public void ticketCancellation(String ticketId) throws IOException {
        if (ticketsFile.search(ticketId, 0))
            ticketsFile.removeRecord(ticketId);
        else passengerMenu.messages(3);

    }

    public void printChart() throws IOException {
        ticketsFile.setSeek(0);
        Ticket ticket = new Ticket();
        while (ticketsFile.getCursor() < ticketsFile.length())
            if (ticketsFile.search(passenger.getUsername(), ticketsFile.getCursor())) {
                ticket = ticketsFile.readRecord(ticket);
                flightsFile.search(ticket.getFlightId(), 0);
                flight = flightsFile.readRecord(flight);
                passengerMenu.printTicket(ticket, flight);
            }
    }
}
