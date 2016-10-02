package frontpage.backend.user;

import frontpage.bind.auth.InvalidDataException;

/**
 * @author willstuckey
 * @date 10/1/16
 * <p></p>
 */
public class InvalidEmailException extends InvalidDataException {
    public InvalidEmailException() {
        super();
    }

    public InvalidEmailException(final String message) {
        super(message);
    }

    public InvalidEmailException(final Throwable t) {
        super(t);
    }

    public InvalidEmailException(final String message, final Throwable t) {
        super(message, t);
    }
}
