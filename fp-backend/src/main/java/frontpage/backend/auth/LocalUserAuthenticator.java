package frontpage.backend.auth;

import frontpage.bind.auth.InvalidCredentialsException;
import frontpage.bind.auth.UserAuthenticationException;
import frontpage.bind.auth.UserAuthenticator;

import java.util.Arrays;

/**
 * @author willstuckey
 * <p></p>
 */
public final class LocalUserAuthenticator implements UserAuthenticator {
    /**
     * local constructor
     */
    LocalUserAuthenticator() {

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
}
