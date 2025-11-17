package nl.novi.pizzeria_webAPI.exception;

public class InvalidDeletionException extends RuntimeException {
    public InvalidDeletionException(String message) {

        super(message);
    }
}
