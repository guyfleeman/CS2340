package frontpage.bind.auth;

/**
 * @author willstuckey
 * <p></p>
 */
public interface UserAuthenticator {
    /**
     * attempts to authenticate user
     * @param un username
     * @param pw password
     * @return success
     * @throws InvalidCredentialsException if credentials are invalid
     * @throws UserAuthenticationException if other errors occur while trying
     *                                     to authenticate credentials
     */
    boolean authenticateUser(String un, char[] pw)
            throws UserAuthenticationException;
}
