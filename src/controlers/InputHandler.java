package controlers;

import java.util.Scanner;

// one err to be fixed: its controller but im printing here!

public class InputHandler {
    private static InputHandler instance = new InputHandler();

    public static InputHandler getInstance() {
        return instance;
    }

    private InputHandler(){}

    public int checkAdminMenuInput(String option) {
        if (isInteger(option)) {
            int o = Integer.parseInt(option);
            if ((o >= 0) && (o <= 4)) {
                return o;
            }
            System.out.println("\n!!!Choose an input in the range 0-4");
            return -1;
        }
        System.out.println("\n!!!Choose a valid integer type input in the range 0-4");
        return -2;
    }

    public int checkAdminUpdateMenuInput(String option) {
        if (isInteger(option)) {
            int o = Integer.parseInt(option);
            if ((o >= 0) && (o <= 5)) {
                return o;
            }
            System.out.println("\n!!!Choose an input in the range 0-5");
            return -1;
        }
        System.out.println("\n!!!Choose a valid integer type input in the range 0-5");
        return -2;
    }

    public int checkSigningMenuInput(String option) {
        if (isInteger(option)) {
            int o = Integer.parseInt(option);
            if ((o >= 0) && (o < 3)) {
                return o;
            }
            System.out.println("\n!!!Choose an input in the range 0-2");
            return -1;
        }
        System.out.println("\n!!!Choose a valid integer type input in the range 0-2");
        return -2;
    }

    public int checkPassengerMenuInput(String option) {
        if (isInteger(option)) {
            int o = Integer.parseInt(option);
            if ((o >= 0) && (o <= 6)) {
                return o;
            }
            System.out.println("\n!!!Choose an input in the range 0-4");
            return -1;
        }
        System.out.println("\n!!!Choose a valid integer type input in the range 0-4");
        return -2;
    }

    public boolean checkDateFormat(String date) {
        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return true;
        }
        System.out.println("\n!!!Not a valid date type. Enter it as xxxx-xx-xx");
        return false;
    }

    public boolean checkTimeFormat(String time) {
        if (time.matches("\\d{2}:\\d{2}")) {
            return true;
        }
        System.out.println("\n!!!Not a valid time type. Enter it as xx:xx");
        return false;
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
