package utils;

public class ErrorHandler {
    private static ErrorHandler errorHandler;

    private ErrorHandler() {
    }

    public synchronized static ErrorHandler getErrorHandler() {
        if (errorHandler == null) {
            errorHandler = new ErrorHandler();
        }
        return errorHandler;
    }

    public void printError(String message) {
        System.err.print("Error: " + message);
    }

}
