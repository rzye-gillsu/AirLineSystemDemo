package views;

import java.util.Scanner;

public class SigningMenu {
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
            case 0 -> System.out.println("You are predefined. Sign in to continue...");
            case 1 -> System.out.println("\n!!!Password must contain at least 4 characters. Try it again.");
            case 2 -> System.out.println("\nWelcome back Admin!\n");
        }
    }
}
