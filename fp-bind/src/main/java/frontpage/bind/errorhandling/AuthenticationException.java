package frontpage.bind.errorhandling;

/**
 * @author willstuckey
 * <p></p>
 */
public class AuthenticationException extends BackendRequestException {
    /**
     * default constructor
     * @param message message
     */
    public AuthenticationException(final String message) {
        super(message);
    }
}
