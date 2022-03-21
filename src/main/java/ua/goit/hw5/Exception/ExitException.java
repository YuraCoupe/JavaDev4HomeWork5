package ua.goit.hw5.Exception;

public class ExitException extends RuntimeException{
    public ExitException(String message) {
        super(message);
    }

    public ExitException() {
        super();
    }
}
