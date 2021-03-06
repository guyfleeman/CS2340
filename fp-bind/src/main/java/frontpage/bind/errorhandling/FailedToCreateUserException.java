//CHECKSTYLE.OFF: JavadocMethod
package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 *
 * @deprecated
 *
 * <p></p>
 */
@SuppressWarnings({"WeakerAccess", "unused", "JavaDoc"})
public class FailedToCreateUserException extends BackendRequestException {
    public FailedToCreateUserException() {
        super();
    }

    public FailedToCreateUserException(final String message) {
        super(message);
    }

    public FailedToCreateUserException(final String message,
                                       final Throwable t) {
        super(message, t);
    }

    public FailedToCreateUserException(final Throwable t) {
        super(t);
    }
}
