package frontpage.backend.user;

import frontpage.bind.auth.InvalidCredentialsException;
import frontpage.bind.auth.InvalidDataException;
import frontpage.bind.auth.UserAuthenticationException;
import frontpage.bind.auth.UserManager;

import java.util.Arrays;

/**
 * @author willstuckey
 * <p></p>
 */
public final class LocalUserManager implements UserManager {
    /**
     * local constructor
     */
    LocalUserManager() {

    }

    /**
     * attempts to authenticate a user
     * @param un username
     * @param pw password
     * @return success
     * @throws UserAuthenticationException if failure
     */
    public boolean authenticateUser(final String un, final char[] pw)
            throws UserAuthenticationException {
        if (un.equals("user")
                && Arrays.equals(pw, new char[]{'p', 'a', 's', 's'})) {
            return true;
        }

        throw new InvalidCredentialsException(
                "the provided credentials did not exist or were invalid");
    }

    public boolean createUser(final String un,
                              final char[] pw,
                              final String email,
                              final String firstname,
                              final String lastname)
            throws InvalidDataException {
        return false;
    }
}
