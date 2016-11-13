package frontpage.backend.validator;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
public interface EmailValidator {
    /**
     * checks if an email is valid
     * @param email email
     * @return validity of email
     */
    boolean isValidEmail(final String email);
}
