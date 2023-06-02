package views;

import java.util.Scanner;

public class SigningMenu {
    private static SigningMenu instance = new SigningMenu();
    public static SigningMenu getInstance() { return instance; }
    private SigningMenu() {}
    private Scanner input = new Scanner(System.in);
    public String menu() {
        System.out.print("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                           WELCOME TO AIRLINE RESERVATION SYSTEM
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                ..........................MENU OPTIONS........................

                    <1> Sign in
                    <2> Sign up
                    <0> Exit
                 Your option:\040""");
        return input.next();
    }

    public String username() {
        System.out.print("Username: ");
        return input.next();
    }
    public String password() {
        System.out.print("Password (must be at least 4 characters): ");
        return input.next();
    }

    public void messages(int messageNumber) {
        switch (messageNumber) {
            case 0 -> System.out.println("\n!!!You are predefined. Sign in to continue...\n");
            case 1 -> System.out.println("\n!!!Password must contain at least 4 characters. Try it again.\n");
            case 2 -> System.out.println("\nWelcome back Admin!\n");
            case 3 -> System.out.println("\n!!! Passenger with given username and password is not found!\n");
            case 4 -> System.out.println("\n!!! This username already exists.\n");
        }
    }

    public void welcomeUser(String username) {
        System.out.printf("\nWelcome dear %s!\n\n", username);
    }
}
