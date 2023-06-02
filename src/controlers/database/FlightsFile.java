package controlers.database;

import models.Flight;

import java.io.IOException;

public class FlightsFile extends Database<Flight> {

    private static final FlightsFile instance;

    static {
        try {
            instance = new FlightsFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FlightsFile getInstance() {
        return instance;
    }

    public FlightsFile() throws IOException {
        super("flights.txt", 141, new Flight());
    }

    @Override
    public Flight readRecord(Flight flight) throws IOException {
        flight.setFlightId(readFixedString());
        flight.setOrigin(readFixedString());
        flight.setDestination(readFixedString());
        flight.setDate(readFixedString());
        flight.setTime(readFixedString());
        flight.setPrice(Integer.parseInt(readFixedString().trim()));
        flight.setSeat(Integer.parseInt(readFixedString().trim()));
        file.readLine();
        return flight;
    }

    @Override
    public boolean search(String str, long pos, String state) throws IOException {
        file.seek(pos);
        String line;
        while ((line = file.readLine()) != null) {
            switch (state) {
                case "flightId" -> line = line.substring(0, FIX);
                case "origin" -> line = line.substring(FIX, FIX * 2);
                case "destination" -> line = line.substring(FIX * 2, FIX * 3);
                case "date" -> line = line.substring(FIX * 3, FIX * 4);
                case "time" -> line = line.substring(FIX * 4, FIX * 5);
                case "price" -> line = line.substring(FIX * 5, FIX * 6);
                case "seat" -> line = line.substring(FIX * 6, FIX * 7);
            }
            if (line.trim().equals(str)) {
                file.seek(file.getFilePointer() - SIZE_OF_RECORD);
                return true;
            }
        }
        return false;
    }
}
