package frontpage.bind.user;

import frontpage.bind.errorhandling.AuthenticationException;
import frontpage.bind.errorhandling.BackendRequestException;

/**
 * @author willstuckey
 * <p></p>
 */
public interface UserManager {
    /**
     * attempts to authenticate user
     * @param email email
     * @param tok any token capable of authenticating the email
     * @return if security context is valid, tok for future auth
     *         if security context is invalid, empty string
     * @throws AuthenticationException if other errors occur while trying
     *                                     to authenticate credentials
     */
    String authenticateUser(final String email, final String tok)
            throws BackendRequestException;

    /**
     * fetches user type
     * TODO probably enumerate for additional requests
     * @param email email for auth
     * @param tok auth token
     * @return type
     * @throws BackendRequestException errors
     */
    String getUserType(final String email, final String tok)
            throws BackendRequestException;

    /**
     * creates a user
     * @param un username
     * @param pw password
     * @param email email
     * @param firstname firstname
     * @param lastname lastname
     * @throws BackendRequestException errors
     */
    @SuppressWarnings("SameParameterValue")
    void createUser(final String un, final String pw, final String email,
                       final String firstname, final String lastname, final String userClass)
            throws BackendRequestException;
}
