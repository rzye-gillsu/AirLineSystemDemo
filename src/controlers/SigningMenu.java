package controlers;

import controlers.database.FlightsFile;

import java.io.IOException;

public class SigningMenu {
    private views.SigningMenu signingMenu = new views.SigningMenu();
    PassengerControl passengerControl = new PassengerControl();
    AdminControl adminControl = new AdminControl();

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
        Thread.sleep(500);
        System.exit(0);
    }

    private void signingUp() throws IOException {
        String username = signingMenu.username();
        String password = signingMenu.password();
        if (userIsValid(username, password)) {
            passengerControl.addPassenger(username, password);
        }
    }


    private boolean userIsValid(String username, String password) {
        if (username.equals("Admin") && password.equals("Admin")) {
            signingMenu.messages(0);
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
            adminControl.menu();
        }
    }
}
