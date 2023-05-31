package models;

import java.util.Random;

public class Ticket {
    private String ticketId;
    private String flightId;
    private String username;

    Random random = new Random(System.currentTimeMillis());

    public Ticket() {}

    public Ticket(String flightId, String username) {
        this.flightId = flightId;
        this.username = username;
        createTicketId();
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void createTicketId() {
        this.ticketId = username + "_" +
                (random.nextInt(100) + String.valueOf(random.nextInt(100)) + random.nextInt(100))
                + "_" + flightId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format("%20s%20s%20s", username, ticketId, flightId);
    }
}
