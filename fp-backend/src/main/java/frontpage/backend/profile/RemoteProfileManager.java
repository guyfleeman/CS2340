package frontpage.backend.profile;

import frontpage.bind.profile.ProfileManagementException;
import frontpage.bind.profile.ProfileManager;

import java.util.Map;

/**
 * @author willstuckey
 * @date 10/3/16
 * <p></p>
 */
public class RemoteProfileManager implements ProfileManager {
    public Map<String, String> getProfile(final String email,
                                          final String tok)
            throws ProfileManagementException {
        return null;
    }

    public boolean setProfile(final String email,
                              final String tok,
                              final Map<String, String> profiles)
            throws ProfileManagementException {
        return false;
    }
}
