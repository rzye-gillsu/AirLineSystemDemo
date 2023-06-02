package controlers.database;

import models.Flight;

import java.io.IOException;

public class FlightsPrimaryData {
    FlightsFile flightsFile = FlightsFile.getInstance();

    public FlightsPrimaryData() throws IOException {
        if (flightsFile.length() == 0) {
            flightsFile.writeRecord(new Flight(
                    "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
            flightsFile.writeRecord(new Flight(
                    "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
            flightsFile.writeRecord(new Flight(
                    "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
            flightsFile.writeRecord(new Flight(
                    "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
            flightsFile.writeRecord(new Flight(
                    "Yazd", "Tehran", "2023-05-01", "20:30",
                    1_000_000, 245).toString());
        }
    }
}
