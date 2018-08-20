package pl.bartekk.exception;

public class NotEnoughFundsException extends RuntimeException {

    public NotEnoughFundsException() {
        super();
    }

    public NotEnoughFundsException(String message) {
        super(message);
    }
}
