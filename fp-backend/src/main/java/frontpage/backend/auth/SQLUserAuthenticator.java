package frontpage.backend.auth;

import frontpage.bind.GlobalProperties;
import frontpage.bind.auth.UserAuthenticationException;
import frontpage.bind.auth.UserAuthenticator;

/**
 * @author willstuckey
 * <p>User Authenticator bound to an SQL server. See GlobalProperties
 * for connection information.</p>
 */
public class SQLUserAuthenticator implements UserAuthenticator {
    /**
     * server address
     */
    private String server;

    /**
     * server port
     */
    private String port;

    /**
     * creates a SQL User Authenticator from the global properties
     */
    SQLUserAuthenticator() {
        this.server = GlobalProperties.getProperties()
                .getProperty("remote-server");
        this.server = GlobalProperties.getProperties()
                .getProperty("remote-port");
    }

    /**
     * attempts to authenticate a user
     * @param un username
     * @param pw password
     * @return success
     * @throws UserAuthenticationException if failure
     */
    public final boolean authenticateUser(final String un, final char[] pw)
            throws UserAuthenticationException {
        return false;
    }
}
