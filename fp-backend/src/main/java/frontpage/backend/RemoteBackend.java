package frontpage.backend;

import frontpage.backend.profile.ProfileManagerFactory;
import frontpage.backend.report.ReportManagerFactory;
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
 * <p>Organization implemention for remote backend providers.</p>
 */
public class RemoteBackend implements Backend {
    /**
     * logger
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(RemoteBackend.class.getName());
    }

    /**
     * singleton instance from factory
     */
    private UserManager inst;

    /**
     * creates and initializes remote backend implementations
     */
    public RemoteBackend() {
        try {
            UserManagerFactory.createInstance("remote");
            ProfileManagerFactory.createInstance("remote");
            ReportManagerFactory.createInstance("remote");
        } catch (UserManagerFactory.NoSuchUserAuthenticatorException
                | ProfileManagementException e) {
            LOGGER.error("could not create remote backend");
        }
        inst = UserManagerFactory.getInstance();
    }

    /**
     * gets remote user manager
     * @return manager
     */
    public UserManager getUserManager() {
        return inst;
    }

    /**
     * gets remote profile manager
     * @return manager
     */
    public ProfileManager getProfileManager() {
        return ProfileManagerFactory.getInstance();
    }

    /**
     * gets remote source report manager
     * @return manager
     */
    public SourceReportManager getSourceReportManager() {
        return ReportManagerFactory.getSourceInstance();
    }

    /**
     * gets remote purity report manager
     * @return manager
     */
    public PurityReportManager getPurityReportManager() {
        return ReportManagerFactory.getPurityInstance();
    }
}
