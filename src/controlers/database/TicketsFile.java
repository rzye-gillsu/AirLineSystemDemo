package controlers.database;

import models.Ticket;

import java.io.IOException;

public class TicketsFile extends Database<Ticket>{
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
    public TicketsFile() throws IOException {
        super("tickets.txt", 61, new Ticket());
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
    public boolean search(String str, long pos, String state) throws IOException {
        file.seek(pos);
        String line;
        while ((line = file.readLine()) != null) {
            switch (state) {
                case "username" -> line = line.substring(0, FIX);
                case "ticketId" -> line = line.substring(FIX, FIX * 2);
                case "flightId" -> line = line.substring(FIX * 2, FIX * 3);
            }
            if (line.trim().equals(str)) {
                file.seek(file.getFilePointer() - SIZE_OF_RECORD);
                return true;
            }
        }
        return false;
    }
}
