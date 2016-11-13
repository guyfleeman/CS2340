package frontpage.backend.validator;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
public interface PasswordValidator {
    /**
     * validates a password
     * @param pw password
     * @return validity of password
     */
    boolean isValidPassword(final String pw);
}
