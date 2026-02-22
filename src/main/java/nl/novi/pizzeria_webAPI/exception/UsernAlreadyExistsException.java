package nl.novi.pizzeria_webAPI.exception;

public class UsernAlreadyExistsException extends RuntimeException {
    public UsernAlreadyExistsException(String message) {
        super(message);
    }
}
