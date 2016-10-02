package frontpage.backend;

import frontpage.backend.user.UserManagerFactory;
import frontpage.bind.Backend;
import frontpage.bind.auth.UserManager;
import org.apache.log4j.Logger;

/**
 * @author willstuckey
 * @date 9/27/16
 * <p></p>
 */
public class RemoteBackend implements Backend {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(RemoteBackend.class.getName());
    }

    private UserManager inst;

    public RemoteBackend() {
        try {
            UserManagerFactory.createInstance("remote");
        } catch (UserManagerFactory.NoSuchUserAuthenticatorException e) {
            logger.error("could not create remote backend");
        }
        inst = UserManagerFactory.getInstance();
    }
    public UserManager getUserManager() {
        return inst;
    }
}
