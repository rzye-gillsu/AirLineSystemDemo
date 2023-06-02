package views;

public class InputHandler {
    private static InputHandler instance = new InputHandler();

    public static InputHandler getInstance() {
        return instance;
    }

    private InputHandler() {
    }

    public void adminMenuMessages(int messageNumber, int start, int end) {
        switch (messageNumber) {
            case 0 -> System.out.printf("\n!!!Choose an input in the range %d-%d\n", start, end);
            case 1 -> System.out.printf("\n!!!Choose a valid integer type input in the range %d-%d\n", start, end);
        }
    }

    public void dateTimeMessage(String state) {
        System.out.printf("\n!!!Not a valid %s type. Enter it as xxxx-xx-xx\n", state);
    }

    public void integerMessage() {
        System.out.println("\n!!!Choose an integer type.");
    }
}
