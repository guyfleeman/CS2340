package frontpage.backend.user;

import frontpage.bind.errorhandling.InvalidDataException;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class InvalidPasswordException extends InvalidDataException {
    /**
     * default constructor
     */
    public InvalidPasswordException() {
        super();
    }

    /**
     * invalid password
     * @param message message
     */
    @SuppressWarnings("SameParameterValue")
    public InvalidPasswordException(final String message) {
        super(message);
    }

    /**
     * invalid password
     * @param t cause
     */
    public InvalidPasswordException(final Throwable t) {
        super(t);
    }

    /**
     * invalid password
     * @param message message
     * @param t cause
     */
    public InvalidPasswordException(final String message, final Throwable t) {
        super(message, t);
    }
}
