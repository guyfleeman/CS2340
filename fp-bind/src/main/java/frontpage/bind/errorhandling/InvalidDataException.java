package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class InvalidDataException extends BackendRequestException {
    public InvalidDataException() {
        super();
    }

    public InvalidDataException(final String message) {
        super(message);
    }

    public InvalidDataException(final Throwable t) {
        super(t);
    }

    public InvalidDataException(final String message, final Throwable t) {
        super(message, t);
    }
}
