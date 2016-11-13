package frontpage.bind.profile;

import frontpage.bind.errorhandling.ProfileManagementException;

import java.util.Map;

/**
 * @author willstuckey
 * <p>Profile Manager</p>
 */
public interface ProfileManager {
    /**
     * gets profile properties from the backend
     * @param email email for auth
     * @param tok token for auth
     * @return profile properties
     * @throws ProfileManagementException if something goes wrong
     */
    Map<String, String> getProfile(final String email,
                                   final String tok)
            throws ProfileManagementException;

    /**
     * set a profile on the backend
     * @param email email for auth
     * @param tok token for auth
     * @param profiles profile properties
     * @return success
     * @throws ProfileManagementException if something goes wrong
     */
    @SuppressWarnings({"UnusedReturnValue", "SameReturnValue"})
    boolean setProfile(final String email,
                       final String tok,
                       final Map<String, String> profiles)
            throws ProfileManagementException;
}
