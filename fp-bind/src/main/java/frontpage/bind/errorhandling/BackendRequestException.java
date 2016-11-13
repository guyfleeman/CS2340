package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * <p>Backend Request Exception</p>
 */
@SuppressWarnings({"WeakerAccess", "ClassWithTooManyDependents"})
public class BackendRequestException extends Exception {
    /**
     * crates a backend request exception
     */
    public BackendRequestException() {
        super();
    }

    /**
     * creates a backend request exception
     * @param message message
     */
    public BackendRequestException(final String message) {
        super(message);
    }

    /**
     * creates a backend request exception
     * @param message message
     * @param t cause
     */
    public BackendRequestException(final String message,
                                   final Throwable t) {
        super(message, t);
    }

    /**
     * creates a backend request exception
     * @param t cause
     */
    public BackendRequestException(final Throwable t) {
        super(t);
    }
}
