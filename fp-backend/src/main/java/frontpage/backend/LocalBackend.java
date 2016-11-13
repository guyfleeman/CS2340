package frontpage.backend;

import frontpage.backend.profile.LocalProfileManager;
import frontpage.backend.profile.ProfileManagerFactory;
import frontpage.backend.user.LocalUserManager;
import frontpage.backend.user.UserManagerFactory;
import frontpage.bind.Backend;
import frontpage.bind.report.PurityReportManager;
import frontpage.bind.report.SourceReportManager;
import frontpage.bind.user.UserManager;
import frontpage.bind.errorhandling.ProfileManagementException;
import frontpage.bind.profile.ProfileManager;
import org.apache.log4j.Logger;

/**
 * @author willstuckey
 * <p>
 * This class represents the api provided for a local backend.
 *
 * This class under partial implementation.
 * </p>
 */
@SuppressWarnings("unused")
public class LocalBackend implements Backend {
    /**
     * class logger
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(LocalBackend.class);
    }

    /**
     * backend provided user authenticator
     */
    private UserManager userManager;

    /**
     * initializes the local backend
     */
    public LocalBackend() {
        try {
            LOGGER.info("initializing local user authenticator");
            UserManagerFactory.createInstance("local");
            ProfileManagerFactory.createInstance("local");
        } catch (UserManagerFactory.NoSuchUserAuthenticatorException
                | ProfileManagementException e) {
            LOGGER.fatal("could not provide backend", e);
        }
    }

    /**
     * gets local user manager
     * @return manager
     */
    @Override
    public UserManager getUserManager() {
        return UserManagerFactory.getInstance();
    }

    /**
     * get local profile manager
     * @return manager
     */
    @Override
    public ProfileManager getProfileManager() {
        LocalProfileManager.setLum(
                (LocalUserManager) UserManagerFactory.getInstance());
        return ProfileManagerFactory.getInstance();
    }

    /**
     * get source report manager
     * @return manager
     */
    @Override
    public SourceReportManager getSourceReportManager() {
        return null;
    }

    /**
     * get purity report manager
     * @return manager
     */
    @Override
    public PurityReportManager getPurityReportManager() {
        return null;
    }
}
