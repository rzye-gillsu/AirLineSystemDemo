package controlers.database;

import models.Ticket;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TicketsFile implements Database<Ticket> {

    public final static int SIZE_OF_RECORD = 61; // 3 * 20 + 1

    private static final TicketsFile instance;

    static {
        try {
            instance = new TicketsFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TicketsFile getInstance() {
        return instance;
    }

    private File f;
    private RandomAccessFile file;

    private TicketsFile() throws IOException {
        f = new File("tickets.txt");
        f.createNewFile();
        file = new RandomAccessFile(f, "rwd");
    }

    @Override
    public void writeRecord(String ticket) throws IOException {
        file.writeBytes(ticket);
        file.writeByte('\n');
    }

    @Override
    public Ticket readRecord(Ticket ticket) throws IOException {
        ticket.setUsername(readFixedString());
        ticket.setTicketId(readFixedString());
        ticket.setFlightId(readFixedString());
        file.readLine();
        return ticket;
    }

    @Override
    public void writeString(String str) throws IOException {
        file.writeBytes(fixStringToWrite(str));
    }

    @Override
    public boolean search(String str, long pos) throws IOException {
        // a problem with contains key that i have is that if you enter yaz it counts yazd as well.
        // one way to solve the issue and yet using contains method is to add spaces before and after the input.
        file.seek(pos);
        String line;
        while ((line = file.readLine()) != null)
            if (line.contains(str)) {
                file.seek(file.getFilePointer() - SIZE_OF_RECORD);
                return true;
            }
        return false;
    }

    @Override
    public String readRecords(long seek, int n) throws IOException {
        file.seek(seek);
        StringBuilder tickets = new StringBuilder();
        Ticket ticket = new Ticket();
        if (n == -1) {
            while (file.getFilePointer() < file.length()) {
                tickets.append(readRecord(ticket).toString());
                tickets.append("\n");
            }
        } else if (n > 0) {
            while (n > 0) {
                tickets.append(readRecord(ticket).toString());
                n--;
            }
        } else {
            return null;
        }
        return tickets.toString();
    }

    @Override
    public String readRecords(long seek) throws IOException {
        return readRecords(seek, -1);
    }

    @Override
    public void removeRecord(String str) throws IOException {
        long currentPos = file.getFilePointer();
        System.out.println(currentPos);
        String tickets = readRecords(currentPos + SIZE_OF_RECORD);
        file.seek(currentPos);
        file.writeBytes(tickets);
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
        byte[] bytes = new byte[FIX];
        file.read(bytes);
        return new String(bytes);
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}
