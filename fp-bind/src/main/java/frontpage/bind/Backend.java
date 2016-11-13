package frontpage.bind;

import frontpage.bind.report.PurityReportManager;
import frontpage.bind.report.SourceReportManager;
import frontpage.bind.user.UserManager;
import frontpage.bind.profile.ProfileManager;

/**
 * @author willstuckey
 * <p>Backend</p>
 */
@SuppressWarnings("ClassWithTooManyDependents")
public interface Backend {
    /**
     * gets the user manager
     * @return user manager
     */
    UserManager getUserManager();

    /**
     * gets profile manager
     * @return profile manager
     */
    ProfileManager getProfileManager();

    /**
     * gets the source report manager
     * @return source report manager
     */
    SourceReportManager getSourceReportManager();

    /**
     * gets purity report manager
     * @return purity report manager
     */
    PurityReportManager getPurityReportManager();
}
