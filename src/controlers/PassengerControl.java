package controlers;

import controlers.database.PassengersFile;
import models.Passenger;
import views.PassengerMenu;

import java.io.IOException;

public class PassengerControl {
    private PassengersFile passengersFile = PassengersFile.getInstance();
    private TicketControl ticketControl = TicketControl.getInstance();
    private PassengerMenu passengerMenu = PassengerMenu.getInstance();

    Passenger passenger;

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
        ticketControl.setPassenger(passenger);
    }

    public PassengerControl() throws IOException {
    }

    public void menu(Passenger passenger) throws IOException {
        setPassenger(passenger);
        notifyUser();
        sign_out:
        while (true) {
            int option = -1;
            while (option == -1 || option == -2) {
                String checkOption = passengerMenu.menu();
                option = InputHandler.getInstance().checkPassengerMenuInput(checkOption);
            }

            controlers.enumtools.PassengerMenu passengerMenu = controlers.enumtools.PassengerMenu.values()[option];
            switch (passengerMenu) {
                case CHANGE_PASSWORD -> changePassword();
                case SEARCH_TICKET -> searchTicket();
                case BOOK_TICKET -> bookTicket();
                case CANCEL_TICKET -> cancelTicket();
                case ADD_CHARGE -> addCharge();
                case TICKETS_CHART -> ticketChart();
                case SIGN_OUT -> { break sign_out; }
            }
        }

    }

    /**
     * When admin removes or updates a flight,
     * users that have that flightId reserved, are notified.
     * @throws IOException
     */
    private void notifyUser() throws IOException {
        passengerMenu.printNotification(passenger.getNotifyUser());
        if (passenger.getNotifyUser() > 0) {
            passenger.setNotifyUser(0);
            passengersFile.search(passenger.getUsername(), 0, "username");
            passengersFile.writeRecord(passenger.toString());
        }
    }

    private void changePassword() throws IOException {
        passengerMenu.printPreviousPassword(passenger.getPassword());
        passengersFile.search(passenger.getUsername(), 0, "username");
        String password = passengerMenu.password();
        if (password.length() < 4) {
            passengerMenu.messages(0);
            return;
        }
        passenger.setPassword(password);
        passengersFile.writeRecord(passenger.toString());
    }

    private void searchTicket() throws IOException {
        passengerMenu.dropOutSearchFilters();
        ticketControl.searchTicket();
    }

    private void bookTicket() throws IOException {
        ticketControl.makeNewTicket(passengerMenu.getId("flightId"));
    }

    private void cancelTicket() throws IOException {
        String ticketId = passengerMenu.getId("ticketId");
        ticketControl.ticketCancellation(ticketId);
    }

    private void addCharge() throws IOException {
        passengersFile.search(passenger.getUsername(), 0, "username");
        passengerMenu.previousCharge(passenger.getCharge());
        String charge = passengerMenu.charge();
        if (!InputHandler.getInstance().isInteger(charge))
            return;
        passenger.setCharge(passenger.getCharge() + Integer.parseInt(charge));
        passengersFile.writeRecord(passenger.toString());
    }

    private void ticketChart() throws IOException {
        passengerMenu.printTicketHeader();
        ticketControl.printChart();
    }
}
