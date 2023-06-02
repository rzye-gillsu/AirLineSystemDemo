package controlers;

import controlers.database.FlightsFile;
import controlers.database.PassengersFile;
import controlers.database.TicketsFile;
import models.Passenger;

import java.io.IOException;

public class SigningMenu {
    private views.SigningMenu signingMenu = views.SigningMenu.getInstance();
    PassengersFile passengersFile = PassengersFile.getInstance();

    public SigningMenu() throws InterruptedException, IOException {
        exit:
        while (true) {
            int option = -1;
            while (option == -1 || option == -2) {
                String checkOption = signingMenu.menu();
                option = InputHandler.getInstance().checkSigningMenuInput(checkOption);
            }
            controlers.enumtools.SigningMenu signingMenu = controlers.enumtools.SigningMenu.values()[option];
            switch (signingMenu) {
                case SIGN_IN -> signingIn();
                case SIGN_UP -> signingUp();
                case EXIT -> { break exit; }
            }
        }
        FlightsFile.getInstance().close();
        PassengersFile.getInstance().close();
        TicketsFile.getInstance().close();
        Thread.sleep(500);
        System.exit(0);
    }

    private void signingUp() throws IOException {
        String username = signingMenu.username();
        String password = signingMenu.password();
        if (userIsValid(username, password)) {
            passengersFile.setSeek(passengersFile.length()); // i changed here.
            passengersFile.writeRecord(String.format("%20s", username).concat(String.format("%20s", password))
                    .concat(String.format("%20d", 0)).concat(String.format("%20d", 0)));
        }
    }


    private boolean userIsValid(String username, String password) throws IOException {
        if (username.equals("Admin") && password.equals("Admin")) {
            signingMenu.messages(0);
            return false;
        }
        if (passengersFile.search(username, 0, "username")) {
            signingMenu.messages(4);
            return false;
        }
        if (password.length() < 4) {
            signingMenu.messages(1);
            return false;
        }
        return true;
    }

    private void signingIn() throws IOException {
        String username = signingMenu.username();
        String password = signingMenu.password();
        checkSignIn(username, password);
    }

    private void checkSignIn(String username, String password) throws IOException {
        if (username.equals("Admin") && password.equals("Admin")) {
            signingMenu.messages(2);
            (new AdminControl()).menu();
            return;
        }
        Passenger passenger = new Passenger();
        String user = username.concat(String.format("%20s", password));
        if (passengersFile.naiveSearch(user, 0)) {
            signingMenu.welcomeUser(username);
            passenger = passengersFile.readRecord(passenger);
            (new PassengerControl()).menu(passenger);
        } else {
            signingMenu.messages(3);
        }
    }
}
