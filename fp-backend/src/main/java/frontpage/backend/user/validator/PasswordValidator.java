package frontpage.backend.user.validator;

/**
 * @author willstuckey
 * @date 10/1/16
 * <p></p>
 */
public interface PasswordValidator {
    boolean isValidPassword(final char[] pw);
}
