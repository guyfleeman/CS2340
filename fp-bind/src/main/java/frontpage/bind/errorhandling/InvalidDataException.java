package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * <p>invalid Data Exception</p>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class InvalidDataException extends BackendRequestException {
    /**
     * creates an invalid data exception
     */
    public InvalidDataException() {
        super();
    }

    /**
     * creates an invalid data exception
     * @param message message
     */
    public InvalidDataException(final String message) {
        super(message);
    }

    /**
     * creates an invalid data exception
     * @param t cause
     */
    public InvalidDataException(final Throwable t) {
        super(t);
    }

    /**
     * creates an invalid data exception
     * @param message message
     * @param t cause
     */
    public InvalidDataException(final String message, final Throwable t) {
        super(message, t);
    }
}
