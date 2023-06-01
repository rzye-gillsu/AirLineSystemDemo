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
        this.ticketId = username + "_" + generate6DigitNumber() + "_" + flightId;
    }

    private String generate6DigitNumber() {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < 6; i++) num.append(random.nextInt(9));
        return num.toString();
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
