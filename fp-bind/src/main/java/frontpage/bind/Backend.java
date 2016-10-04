package frontpage.bind;

import frontpage.bind.auth.UserManager;
import frontpage.bind.profile.ProfileManager;

/**
 * @author willstuckey
 * @date 9/27/16
 * <p></p>
 */
public interface Backend {
    UserManager getUserManager();

    ProfileManager getProfileManager();
}
