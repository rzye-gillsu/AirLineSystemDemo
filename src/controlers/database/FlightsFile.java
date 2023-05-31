package controlers.database;

import models.Flight;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FlightsFile implements Database<Flight> {
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

    private File f;
    private RandomAccessFile file;
    public final static int SIZE_OF_RECORD = 141; // 20 * 7 + 1

    private FlightsFile() throws IOException {
        f = new File("flights.txt");
        f.createNewFile();
        file = new RandomAccessFile(f, "rwd");
    }

    @Override
    public void writeRecord(String flight) throws IOException {
        file.writeBytes(flight);
        file.writeByte('\n');
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
    public void writeString(String str) throws IOException {
        file.writeBytes(fixStringToWrite(str));
    }

    @Override
    public boolean search(String str, long pos) throws IOException {
        // naiveSearch
        file.seek(0);
        String line;
        while ((line = file.readLine()) != null)
            if (line.contains(str)) {
                file.seek(file.getFilePointer() - SIZE_OF_RECORD);
                return true;
            }
        return false;
//         seek cursor is not set.

        // reverseSearch
//        file.seek(0);
//        while (file.getFilePointer() < file.length()) {
//            byte[] bytes = new byte[FIX];
//            file.read(bytes);
//            if ((new String(bytes)).trim().equals(str)) {
//                file.seek(file.getFilePointer() - FIX);
//                return true;
//            }
//        }
//        return false;
    }

    public boolean search2(String str) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            byte[] bytes = new byte[FIX];
            file.read(bytes);
            if ((new String(bytes)).trim().equals(str)) {
                file.seek(file.getFilePointer() - FIX);
                return true;
            }
        }
        return false;
    }

    @Override
    public String readRecords(long seek, int n) throws IOException {
        file.seek(seek);
        StringBuilder flights = new StringBuilder();
        Flight flight = new Flight();
        if (n == -1) {
            while (file.getFilePointer() < file.length()) {
                flights.append(readRecord(flight).toString());
                flights.append("\n");
            }
        } else if (n > 0) {
            while (n > 0) {
                flights.append(readRecord(flight).toString());
                n--;
            }
        } else {
            return null;
        }
        return flights.toString();
    }

    @Override
    public String readRecords(long seek) throws IOException {
        return readRecords(seek, -1);
    }

    @Override
    public void removeRecord(String flightId) throws IOException {
        long currentPos = file.getFilePointer();
        System.out.println(currentPos);
        String flights = readRecords(currentPos + SIZE_OF_RECORD);
        file.seek(currentPos);
        file.writeBytes(flights);
        file.setLength(file.length() - SIZE_OF_RECORD);
    }

    @Override
    public void setSeek(long bytes) throws IOException {
        file.seek(bytes);
    }

    @Override
    public long getCursor() throws IOException {
        return file.getFilePointer();
    }

    @Override
    public long length() throws IOException {
        return file.length();
    }

    @Override
    public int numberOfRecords() throws IOException {
        return 0;
    }

    @Override
    public String fixStringToWrite(String str) {
        return String.format("%20s", str).substring(0, FIX);
    }

    @Override
    public String readFixedString() throws IOException {
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < FIX; i++) {
//            str.append(file.readByte());
//        }
//        return str.toString();
        byte[] bytes = new byte[FIX];
        file.read(bytes);
        return new String(bytes);
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}

//    @Override
//    public void update(String str, String state) throws IOException {
//        switch (state) {
//            case "flightId" -> {
//                file.writeBytes(fixStringToWrite(str));
//            }
//            case "origin" -> {
//                file.seek(file.getFilePointer() + FIXED_SIZE);
//                file.writeBytes(fixStringToWrite(str));
//            }
//            case "destination" -> {
//                file.seek(file.getFilePointer() + 2L * FIXED_SIZE);
//                file.writeBytes(fixStringToWrite(str));
//            }
//            case "date" -> {
//                file.seek(file.getFilePointer() + 3L * FIXED_SIZE);
//                file.writeBytes(fixStringToWrite(str));
//            }
//            case "time" -> {
//                file.seek(file.getFilePointer() + 4L * FIXED_SIZE);
//                file.writeBytes(fixStringToWrite(str));
//            }
//            case "price" -> {
//                file.seek(file.getFilePointer() + 5L * FIXED_SIZE);
//                file.writeBytes(fixStringToWrite(str));
//            }
//            case "seat" -> {
//                file.seek(file.getFilePointer() + 6L * FIXED_SIZE);
//                file.writeBytes(fixStringToWrite(str));
//            }
//        }
//    }
