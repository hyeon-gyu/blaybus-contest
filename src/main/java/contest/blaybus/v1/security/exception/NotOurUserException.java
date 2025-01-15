package contest.blaybus.v1.security.exception;

import org.springframework.http.HttpStatus;

public class NotOurUserException extends RuntimeException {
    public NotOurUserException(final String message) {
        super(message);
    }

    public int getStatus() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
