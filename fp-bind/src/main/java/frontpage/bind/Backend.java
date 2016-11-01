package frontpage.bind;

import frontpage.bind.report.PurityReportManager;
import frontpage.bind.report.SourceReportManager;
import frontpage.bind.user.UserManager;
import frontpage.bind.profile.ProfileManager;

/**
 * @author willstuckey
 * @date 9/27/16
 * <p></p>
 */
public interface Backend {
    UserManager getUserManager();

    ProfileManager getProfileManager();

    SourceReportManager getSourceReportManager();

    PurityReportManager getPurityReportManager();
}
