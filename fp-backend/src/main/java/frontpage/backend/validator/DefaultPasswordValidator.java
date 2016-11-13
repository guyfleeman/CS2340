package frontpage.backend.validator;

/**
 * @author willstuckey
 * <p></p>
 */
public class DefaultPasswordValidator implements PasswordValidator {
    /**
     * checks if a password is valid. This implementation accepts all
     * passwords.
     * @param pw password
     * @return true
     */
    @Override
    @SuppressWarnings({"SameReturnValue", "UnusedParameters"})
    public final boolean isValidPassword(final String pw) {
        return true;
    }
}
