package frontpage.bind.auth;

/**
 * @author willstuckey
 * @date 10/1/16
 * <p></p>
 */
public class InvalidDataException extends Exception {
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
