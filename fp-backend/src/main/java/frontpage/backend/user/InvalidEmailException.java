package frontpage.backend.user;

import frontpage.bind.errorhandling.InvalidDataException;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
public class InvalidEmailException extends InvalidDataException {
    /**
     * invalid email
     */
    public InvalidEmailException() {
        super();
    }

    /**
     * invalid email
     * @param message message
     */
    @SuppressWarnings("SameParameterValue")
    public InvalidEmailException(final String message) {
        super(message);
    }

    /**
     * invalid email
     * @param t cause
     */
    public InvalidEmailException(final Throwable t) {
        super(t);
    }

    /**
     * invalid email
     * @param message message
     * @param t cause
     */
    public InvalidEmailException(final String message, final Throwable t) {
        super(message, t);
    }
}
