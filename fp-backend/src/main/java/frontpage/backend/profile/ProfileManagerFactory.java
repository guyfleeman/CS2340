package frontpage.backend.profile;

import frontpage.bind.errorhandling.ProfileManagementException;
import frontpage.bind.profile.ProfileManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("UtilityClass")
public final class ProfileManagerFactory {
    /**
     * class logger
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final Logger LOGGER;

    /**
     * type map
     */
    private static final Map<String,
            Class<? extends ProfileManager>> USER_AUTHENTICATOR_MAP;

    /**
     * instance
     */
    private static ProfileManager instance;

    static {
        LOGGER = Logger.getLogger(ProfileManagerFactory.class);
        LOGGER.setLevel(Level.ALL);
        USER_AUTHENTICATOR_MAP = new HashMap<>();
        USER_AUTHENTICATOR_MAP.put("remote", RemoteProfileManager.class);
        LOGGER.trace("Added map <\"remote\", "
                + "frontpage.backend.auth.RESTUserManager>");
        USER_AUTHENTICATOR_MAP.put("local", LocalProfileManager.class);
        LOGGER.trace("Added map <\"local\", "
                + "frontpage.backend.auth.LocalUserManager>");
    }

    /**
     *
     * @param type authenticator [remote, local]
     * @throws ProfileManagementException if an invalid
     */
    public static void createInstance(final String type)
            throws ProfileManagementException {
        if ((type == null)
                || !USER_AUTHENTICATOR_MAP.containsKey(type.toLowerCase())) {
            throw new ProfileManagementException(
                    "Cannot create user authenticator for type " + type + ".");
        }

        Class<? extends ProfileManager> authClass =
                USER_AUTHENTICATOR_MAP.get(type);
        try {
            instance = authClass.newInstance();
        } catch (Exception e) {
            throw new ProfileManagementException(
                    "internal error creating user authenticator", e);
        }
    }

    /**
     * gets singleton authenticator
     * @return instance
     */
    public static ProfileManager getInstance() {
        return instance;
    }

    /**
     * Utility Constructor
     */
    private ProfileManagerFactory() { }
}
