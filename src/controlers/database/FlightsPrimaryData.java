package controlers.database;

import models.Flight;

import java.io.IOException;

public class FlightsPrimaryData {
    FlightsFile flightsFile = FlightsFile.getInstance();

    public FlightsPrimaryData() throws IOException {
        if (flightsFile.length() == 0) {
            flightsFile.writeRecord(new Flight(
                    "HW2--0", "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
            flightsFile.writeRecord(new Flight(
                    "HW2--1", "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
            flightsFile.writeRecord(new Flight(
                    "HW2--2", "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
            flightsFile.writeRecord(new Flight(
                    "HW2--3", "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
            flightsFile.writeRecord(new Flight(
                    "HW2--4", "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
        }
    }
}
