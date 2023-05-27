package models;

import java.util.ArrayList;
import java.util.List;

public class Passenger {
    private String username;
    private String password;
    private int charge = 0;
    private List<Ticket> tickets;

    public Passenger(String username, String password, int charge, List<Ticket> tickets) {
        this.username = username;
        this.password = password;
        this.charge = charge;
        this.tickets = tickets;
    }

    public Passenger() {
        this.tickets = new ArrayList<>();
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge += charge;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
