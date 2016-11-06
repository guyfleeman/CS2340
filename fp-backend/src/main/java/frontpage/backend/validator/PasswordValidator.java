package frontpage.backend.validator;

/**
 * @author willstuckey
 * <p></p>
 */
public interface PasswordValidator {
    /**
     * validates a password
     * @param pw password
     * @return validity of password
     */
    boolean isValidPassword(final String pw);
}
