package frontpage.backend.validator;

/**
 * @author willstuckey
 * <p></p>
 */
public class DefaultPasswordValidator {
    /**
     * checks if a password is valid. This implementation accepts all
     * passwords.
     * @param pw password
     * @return true
     */
    @SuppressWarnings("SameReturnValue")
    public final boolean isValidPassword(final String pw) {
        return true;
    }
}
