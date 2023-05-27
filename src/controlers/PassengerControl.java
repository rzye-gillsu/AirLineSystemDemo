package controlers;

import controlers.database.PassengersFile;
import views.PassengerMenu;

import java.io.IOException;

public class PassengerControl {
    private PassengersFile passengersFile = new PassengersFile();
    PassengerMenu passengerMenu = new PassengerMenu();

    public PassengerControl() throws IOException {
    }

    public void addPassenger(String username, String password) throws IOException {
        passengersFile.writeString(username);
        passengersFile.writeString(password);
    }

    public void menu() {
        boolean stay = true;
        while (stay) {
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
                case SIGN_OUT -> signOut();
            }
        }

    }

    private void changePassword() {

    }

    private void searchTicket() {
    }

    private void bookTicket() {
    }

    private void cancelTicket() {
    }

    private void addCharge() {
    }

    private void ticketChart() {
    }

    private void signOut() {
    }
}
