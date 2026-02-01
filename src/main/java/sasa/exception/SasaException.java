package sasa.exception;

/**
 * Represents exceptions specific to the sasa.Sasa chatbot application.
 * Used to handle user input errors and file processing issues.
 */
public class SasaException extends Exception{
    public SasaException(String message) {
        super(message);
    }
}
