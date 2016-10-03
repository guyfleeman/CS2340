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
    public String authenticateUser(final String un, final String pw)
            throws UserAuthenticationException {
        if (un.equals("user") && pw.equals("pass")) {
            return "";
        }

        throw new InvalidCredentialsException(
                "the provided credentials did not exist or were invalid");
    }

    public boolean createUser(final String un,
                              final String pw,
                              final String email,
                              final String firstname,
                              final String lastname,
                              final String type)
            throws InvalidDataException {
        return false;
    }
}
