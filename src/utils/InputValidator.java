package utils;

public class InputValidator {

    private static InputValidator inputValidator;

    private InputValidator() {
    }

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
            ErrorHandler.getErrorHandler().printError("Invalid Input Argument. FileName missing");
            return false;
        }
        if (args.length > 1) {
            ErrorHandler.getErrorHandler().printError("Invalid Input Argument. Extra inputs provided. Please provide FileName only");
            return false;
        }
        return true;
    }
}
