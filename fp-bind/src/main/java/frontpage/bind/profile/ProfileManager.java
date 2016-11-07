package frontpage.bind.profile;

import frontpage.bind.errorhandling.ProfileManagementException;

import java.util.Map;

/**
 * @author willstuckey
 * @date 10/3/16
 * <p></p>
 */
public interface ProfileManager {
    Map<String, String> getProfile(final String email,
                                   final String tok)
            throws ProfileManagementException;

    @SuppressWarnings({"UnusedReturnValue", "SameReturnValue"})
    boolean setProfile(final String email,
                       final String tok,
                       final Map<String, String> profiles)
            throws ProfileManagementException;
}
