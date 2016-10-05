package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * @date 10/3/16
 * <p></p>
 */
public class FailedToCreateUserException extends BackendRequestException {
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
