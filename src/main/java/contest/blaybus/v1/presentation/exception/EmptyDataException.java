package contest.blaybus.v1.presentation.exception;

public class EmptyDataException extends RuntimeException {

    public EmptyDataException() {
    }

    public EmptyDataException(String message) {
        super(message);
    }
}
