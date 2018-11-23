package utils;

public class ErrorHandler {
    private static ErrorHandler errorHandler;

    private ErrorHandler() {
    }

    /*
    Ensuring single object is created for this class using singleton pattern. The method is made synchronised to resolve race conditions
   Though in the current use case since there will not be any chances of race conditions since,
    it will not be receiving multiple requests at the same time.
     */
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
