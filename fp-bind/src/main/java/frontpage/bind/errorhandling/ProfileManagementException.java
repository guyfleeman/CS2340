package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * @date 10/3/16
 * <p></p>
 */
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