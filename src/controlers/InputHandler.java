package controlers;

// one err to be fixed: its controller but im printing here!

public class InputHandler {
    private static InputHandler instance = new InputHandler();

    public static InputHandler getInstance() {
        return instance;
    }

    private InputHandler(){}

    private views.InputHandler inputHandler = views.InputHandler.getInstance();

    public int checkAdminMenuInput(String option) {
        if (isInteger(option)) {
            int o = Integer.parseInt(option);
            if ((o >= 0) && (o <= 4)) {
                return o;
            }
            inputHandler.adminMenuMessages(0, 0, 4);
            return -1;
        }
        inputHandler.adminMenuMessages(1, 0, 4);
        return -2;
    }

    public int checkAdminUpdateMenuInput(String option) {
        if (isInteger(option)) {
            int o = Integer.parseInt(option);
            if ((o >= 0) && (o <= 5)) {
                return o;
            }
            inputHandler.adminMenuMessages(0, 0, 5);
            return -1;
        }
        inputHandler.adminMenuMessages(1, 0, 5);
        return -2;
    }

    public int checkSigningMenuInput(String option) {
        if (isInteger(option)) {
            int o = Integer.parseInt(option);
            if ((o >= 0) && (o < 3)) {
                return o;
            }
            inputHandler.adminMenuMessages(0, 0, 2);
            return -1;
        }
        inputHandler.adminMenuMessages(1, 0, 5);
        return -2;
    }

    public int checkPassengerMenuInput(String option) {
        if (isInteger(option)) {
            int o = Integer.parseInt(option);
            if ((o >= 0) && (o <= 6)) {
                return o;
            }
            inputHandler.adminMenuMessages(0, 0, 6);
            return -1;
        }
        inputHandler.adminMenuMessages(1, 0, 6);
        return -2;
    }

    public boolean checkDateFormat(String date) {
        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return true;
        }
        inputHandler.dateTimeMessage("date");
        return false;
    }

    public boolean checkTimeFormat(String time) {
        if (time.matches("\\d{2}:\\d{2}")) {
            return true;
        }
        inputHandler.dateTimeMessage("time");
        return false;
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            inputHandler.integerMessage();
            return false;
        }
    }
}
