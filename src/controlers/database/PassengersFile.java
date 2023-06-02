package controlers.database;

import models.Passenger;

import java.io.IOException;

public class PassengersFile extends Database<Passenger> {
    private static final PassengersFile instance;

    static {
        try {
            instance = new PassengersFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PassengersFile getInstance() {
        return instance;
    }

    public PassengersFile() throws IOException {
        super("passengers.txt", 81, new Passenger());
    }

    @Override
    public Passenger readRecord(Passenger passenger) throws IOException {
        passenger.setUsername(readFixedString());
        passenger.setPassword(readFixedString());
        passenger.setCharge(Integer.parseInt(readFixedString()));
        passenger.setNotifyUser(Integer.parseInt(readFixedString()));
        file.readLine();
        return passenger;
    }

    @Override
    public boolean search(String str, long pos, String state) throws IOException {
        file.seek(pos);
        String line;
        while ((line = file.readLine()) != null) {
            switch (state) {
                case "username" -> line = line.substring(0, FIX);
                case "password" -> line = line.substring(FIX, FIX * 2);
                case "charge" -> line = line.substring(FIX * 2, FIX * 3);
                case "notifyUser" -> line = line.substring(FIX * 3, FIX * 4);
            }
            if (line.trim().equals(str)) {
                file.seek(file.getFilePointer() - SIZE_OF_RECORD);
                return true;
            }
        }
        return false;
    }

    public boolean naiveSearch(String str, long pos) throws IOException {
        str = String.format("  %s  ", str);
        file.seek(pos);
        String line;
        while ((line = file.readLine()) != null)
            if (line.contains(str)) {
                file.seek(file.getFilePointer() - SIZE_OF_RECORD);
                return true;
            }
        return false;
    }
}
