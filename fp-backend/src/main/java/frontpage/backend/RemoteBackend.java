package frontpage.backend;

import frontpage.backend.profile.ProfileManagerFactory;
import frontpage.backend.report.ReportManagerFactory;
import frontpage.backend.user.UserManagerFactory;
import frontpage.bind.Backend;
import frontpage.bind.report.ReportManager;
import frontpage.bind.user.UserManager;
import frontpage.bind.errorhandling.ProfileManagementException;
import frontpage.bind.profile.ProfileManager;
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
            ProfileManagerFactory.createInstance("remote");
            ReportManagerFactory.createInstance("remote");
        } catch (UserManagerFactory.NoSuchUserAuthenticatorException
                | ProfileManagementException e) {
            logger.error("could not create remote backend");
        }
        inst = UserManagerFactory.getInstance();
    }

    public UserManager getUserManager() {
        return inst;
    }

    public ProfileManager getProfileManager() {
        return ProfileManagerFactory.getInstance();
    }

    public ReportManager getReportManager() {
        return ReportManagerFactory.getInstance();
    }
}
