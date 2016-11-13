package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * <p>Profile Management Exception</p>
 */
@SuppressWarnings("unused")
public class ProfileManagementException extends Exception {
    /**
     * creates a profile management exception
     */
    public ProfileManagementException() {
        super();
    }

    /**
     * creates a profile management exception
     * @param message message
     */
    public ProfileManagementException(final String message) {
        super(message);
    }

    /**
     * creates a profile management exception
     * @param message message
     * @param t cause
     */
    public ProfileManagementException(final String message, final Throwable t) {
        super(message, t);
    }

    /**
     * creates a profile management exception
     * @param t cause
     */
    public ProfileManagementException(final Throwable t) {
        super(t);
    }
}
