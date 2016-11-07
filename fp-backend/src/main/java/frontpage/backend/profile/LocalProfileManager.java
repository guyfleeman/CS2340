package frontpage.backend.profile;

import frontpage.backend.user.LocalUserManager;
import frontpage.bind.errorhandling.ProfileManagementException;
import frontpage.bind.profile.ProfileManager;
import frontpage.model.user.User;
import frontpage.model.user.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * <p>Fallback profile manager</p>
 */
public class LocalProfileManager implements ProfileManager {
    private static LocalUserManager lum;

    public LocalProfileManager() {
    }

    public static void setLum(LocalUserManager lum) {
        LocalProfileManager.lum = lum;
    }

    public Map<String, String> getProfile(final String email,
                                          final String tok)
        throws ProfileManagementException {
        for (User u : lum.getUsers()) {
            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getTok().equals(tok)) {
                UserProfile up = u.getUserProfile();
                Map<String, String> ret = new HashMap<>(5);
                ret.put("address", up.getAddress());
                ret.put("city", up.getCity());
                ret.put("state", up.getState());
                ret.put("zip", up.getZip());
                ret.put("title", up.getTitle());
                return ret;
            }
        }

        throw new ProfileManagementException("no matching user found");
    }

    public boolean setProfile(final String email,
                              final String tok,
                              final Map<String, String> profiles)
        throws ProfileManagementException {
        for (User u : lum.getUsers()) {
            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getTok().equals(tok)) {
                UserProfile up = u.getUserProfile();
                up.setAddress(profiles.get("address"));
                up.setCity(profiles.get("city"));
                up.setState(profiles.get("state"));
                up.setZip(profiles.get("zip"));
                up.setTitle(profiles.get("title"));
                return true;
            }
        }

        throw new ProfileManagementException("no matching user found");
    }
}
