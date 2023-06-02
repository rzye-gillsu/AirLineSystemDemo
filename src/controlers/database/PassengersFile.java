package controlers.database;

import models.Passenger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PassengersFile implements Database<Passenger> {
    public final static int SIZE_OF_RECORD = 81; // 4 * 20 + 1

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

    private File f;
    private RandomAccessFile file;

    private PassengersFile() throws IOException {
        f = new File("passengers.txt");
        f.createNewFile();
        file = new RandomAccessFile(f, "rwd");
    }

    @Override
    public void writeRecord(String passenger) throws IOException {
        file.writeBytes(passenger);
        file.writeByte('\n');
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
    public void writeString(String str) throws IOException {
        file.writeBytes(fixStringToWrite(str));
    }

    @Override
    public boolean search(String str, long pos) throws IOException {
        int elements = 4;
        file.seek(pos);
        String line;
        while ((line = file.readLine()) != null)
            for (int i = 0; i < elements; i++)
                if (line.substring(i * FIX, (i + 1) * FIX).trim().equals(str)) {
                    file.seek(file.getFilePointer() - SIZE_OF_RECORD);
                    return true;
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


    //    @Override
//    public void update(String str, String state) throws IOException {}
    @Override
    public String readRecords(long seek, int n) {
        return "";
    }

    @Override
    public String readRecords(long seek) {
        return readRecords(seek, -1);
    }

    @Override
    public void removeRecord(String str) throws IOException {
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
        byte[] bytes = new byte[FIX];
        file.read(bytes);
        return (new String(bytes).trim());
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}
