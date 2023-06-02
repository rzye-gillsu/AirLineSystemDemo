package models;

import java.util.Objects;
import java.util.Random;

public class Flight {
    private String flightId;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private int price;
    private int seat;

    private Random random = new Random(System.currentTimeMillis());

    public Flight() {
    }

    public Flight(String origin, String destination, String date, String time, int price, int seat) {
        this.flightId = createFlightId();
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.seat = seat;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String createFlightId() {
        return String.format("HW_F%s", generate6DigitNumber());
    }
    private String generate6DigitNumber() {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < 6; i++) num.append(random.nextInt(9));
        return num.toString();
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    public int getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return String.format("%20s%20s%20s%20s%20s%20d%20d",
                flightId, origin, destination, date, time, price, seat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return price == flight.price || seat == flight.seat ||
                Objects.equals(flightId, flight.flightId) ||
                Objects.equals(origin, flight.origin) ||
                Objects.equals(destination, flight.destination) ||
                Objects.equals(date, flight.date) && Objects.equals(time, flight.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, origin, destination, date, time, price, seat);
    }
}
