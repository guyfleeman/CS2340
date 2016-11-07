package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("unused")
public class ProfileManagementException extends Exception {
    public ProfileManagementException() {
        super();
    }

    public ProfileManagementException(final String message) {
        super(message);
    }

    public ProfileManagementException(final String message, final Throwable t) {
        super(message, t);
    }

    public ProfileManagementException(final Throwable t) {
        super(t);
    }
}