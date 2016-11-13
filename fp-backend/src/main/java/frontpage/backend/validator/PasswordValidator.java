package frontpage.backend.validator;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings({"WeakerAccess", "SameReturnValue", "unused"})
public interface PasswordValidator {
    /**
     * validates a password
     * @param pw password
     * @return validity of password
     */
    boolean isValidPassword(final String pw);
}
