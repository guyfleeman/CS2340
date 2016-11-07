package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
public class BackendRequestException extends Exception {
    public BackendRequestException() {
        super();
    }

    public BackendRequestException(final String message) {
        super(message);
    }

    public BackendRequestException(final String message, final Throwable t) {
        super(message, t);
    }

    public BackendRequestException(final Throwable t) {
        super(t);
    }
}
