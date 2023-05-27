package controlers.database;

import models.Passenger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PassengersFile implements Database<Passenger> {
    private File f;
    private RandomAccessFile file;

    public PassengersFile() throws IOException {
        f = new File("passengers.txt");
        f.createNewFile();
        file = new RandomAccessFile(f, "rw");
    }

    @Override
    public void writeRecord(String passenger) throws IOException {
        long pos = file.getFilePointer();
        if (!search(String.format("%20s", "X")))
            file.seek(pos);
        // not really sure if it's working correctly.
        writeString(passenger);
        writeString("\n");
    }

    @Override
    public Passenger readRecord(Passenger passenger) {
        return null;
    }

    @Override
    public void writeString(String str) throws IOException {
        file.writeChars(fixStringToWrite(str));
    }

    @Override
    public boolean search(String state) throws IOException {
        return true;
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
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < FIX; i++) {
            str.append(file.readChar());
        }
        return str.toString();
    }

    @Override
    public void close() throws IOException {

    }
}
