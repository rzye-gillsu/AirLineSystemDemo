package models;

public class Ticket {
    private String ticketId;
    private String flightId;
    private Passenger passenger;
    private static int PASSENGER = 0;

    public Ticket() {}

    public Ticket(String ticketId, String flightId) {
        this.ticketId = ticketId;
        this.flightId = flightId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String flightId) {
        this.ticketId = passenger.getUsername() + PASSENGER++ + flightId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
}
