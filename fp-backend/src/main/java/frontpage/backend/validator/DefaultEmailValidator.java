package frontpage.backend.validator;

import java.util.regex.Pattern;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
public class DefaultEmailValidator implements EmailValidator {
    /**
     * RFC compliant email validation regex
     */
    public static final String EMAIL_PATTERN =
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    /**
     * default constructor
     */
    public DefaultEmailValidator() { }

    /**
     * check is an email is valid.
     * Uses RFC compliant regex.
     * @param email email
     * @return validity of email.
     */
    public final boolean isValidEmail(final String email) {
        return Pattern.compile(EMAIL_PATTERN,
                Pattern.CASE_INSENSITIVE).matcher(email).find();
    }
}
