package utils;

public class InputValidator {

    private static InputValidator inputValidator;

    private InputValidator() {
    }

    /*
    Ensuring single object is created for this class using singleton pattern. The method is made synchronised to resolve race conditions
   Though in the current use case since there will not be any chances of race conditions since,
    it will not be receiving multiple requests at the same time.
     */
    public synchronized static InputValidator getInputValidator() {
        if (inputValidator == null) {
            inputValidator = new InputValidator();
        }
        return inputValidator;
    }

    /*
    Validates input argument
     */
    public boolean validateInput(String[] args) {
        if (args.length == 0) {
            ErrorHandler.getErrorHandler().printError(Constants.FILE_NAME_MISSING);
            throw new RuntimeException(Constants.FILE_NAME_MISSING);
        }
        if (args.length > 1) {
            ErrorHandler.getErrorHandler().printError(Constants.INVALID_INPUT);
            throw new RuntimeException(Constants.INVALID_INPUT);

        }
        return true;
    }
}
