package frontpage.backend;

import frontpage.backend.auth.UserAuthenticatorFactory;
import frontpage.bind.Backend;
import frontpage.bind.auth.UserAuthenticator;
import org.apache.log4j.Logger;

/**
 * @author willstuckey
 * @date 9/27/16
 * <p>This class represents the api provided for a local backend.</p>
 */
public class LocalBackend implements Backend {
    private static Logger logger;

    static {
        logger = Logger.getLogger(LocalBackend.class);
    }

    /**
     * backend provided user authenticator
     */
    protected UserAuthenticator userAuthenticator;

    /**
     * initializes the local backend
     */
    public LocalBackend() {
        try {
            logger.info("initializing local user authenticator");
            UserAuthenticatorFactory.createInstance("local");
        } catch (UserAuthenticatorFactory.NoSuchUserAuthenticatorException e) {
            logger.fatal("could not provide backend", e);
        }
    }

    public UserAuthenticator getUserAuthenticator() {
        return UserAuthenticatorFactory.getInstance();
    }
}
