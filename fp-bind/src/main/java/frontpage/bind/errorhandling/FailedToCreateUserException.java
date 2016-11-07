package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * @date 10/3/16
 *
 * @deprecated
 *
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
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
