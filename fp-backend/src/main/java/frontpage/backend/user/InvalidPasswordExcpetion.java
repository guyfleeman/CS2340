package frontpage.backend.user;

import frontpage.bind.errorhandling.InvalidDataException;

/**
 * @author willstuckey
 * <p></p>
 */
public class InvalidPasswordExcpetion extends InvalidDataException {
    /**
     * default constructor
     */
    public InvalidPasswordExcpetion() {
        super();
    }

    /**
     * invalid password
     * @param message message
     */
    public InvalidPasswordExcpetion(final String message) {
        super(message);
    }

    /**
     * invalid password
     * @param t cause
     */
    public InvalidPasswordExcpetion(final Throwable t) {
        super(t);
    }

    /**
     * invalid password
     * @param message message
     * @param t cause
     */
    public InvalidPasswordExcpetion(final String message, final Throwable t) {
        super(message, t);
    }
}
