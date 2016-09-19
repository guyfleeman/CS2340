package frontpage.bind.auth;

/**
 * @author willstuckey
 * <p></p>
 */
public class InvalidCredentialsException extends UserAuthenticationException {
    /**
     * default constructor
     * @param message message
     */
    public InvalidCredentialsException(final String message) {
        super(message);
    }
}
