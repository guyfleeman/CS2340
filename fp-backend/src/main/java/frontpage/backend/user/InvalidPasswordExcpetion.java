package frontpage.backend.user;

import frontpage.bind.auth.InvalidDataException;

/**
 * @author willstuckey
 * @date 10/1/16
 * <p></p>
 */
public class InvalidPasswordExcpetion extends InvalidDataException {
    public InvalidPasswordExcpetion() {
        super();
    }

    public InvalidPasswordExcpetion(final String message) {
        super(message);
    }

    public InvalidPasswordExcpetion(final Throwable t) {
        super(t);
    }

    public InvalidPasswordExcpetion(final String message, final Throwable t) {
        super(message, t);
    }
}
