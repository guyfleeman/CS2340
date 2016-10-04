package frontpage.backend.validator;

import java.util.regex.Pattern;

/**
 * @author willstuckey
 * @date 10/1/16
 * <p></p>
 */
public class DefaultEmailValidator implements EmailValidator {
    public static final String EMAIL_PATTERN = "";
    public DefaultEmailValidator() {}

    public boolean isValidEmail(final String email) {
        return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE).matcher(email).find();
    }
}
