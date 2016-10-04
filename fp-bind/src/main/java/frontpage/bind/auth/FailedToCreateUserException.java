package frontpage.bind.auth;

/**
 * @author willstuckey
 * @date 10/3/16
 * <p></p>
 */
public class FailedToCreateUserException extends Exception {
    public FailedToCreateUserException() {
        super();
    }

    public FailedToCreateUserException(final String message) {
        super(message);
    }

    public FailedToCreateUserException(final String message, final Throwable t) {
        super(message, t);
    }

    public FailedToCreateUserException(final Throwable t) {
        super(t);
    }
}
