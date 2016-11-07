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
public final class ProfileManagerFactory {
    /**
     * class logger
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final Logger logger;

    /**
     * type map
     */
    private static final Map<String,
            Class<? extends ProfileManager>> userAuthenticatorMap;

    /**
     * instance
     */
    private static ProfileManager instance;

    static {
        logger = Logger.getLogger(ProfileManagerFactory.class);
        logger.setLevel(Level.ALL);
        userAuthenticatorMap = new HashMap<>();
        userAuthenticatorMap.put("remote", RemoteProfileManager.class);
        logger.trace("Added map <\"remote\", "
                + "frontpage.backend.auth.RESTUserManager>");
        userAuthenticatorMap.put("local", LocalProfileManager.class);
        logger.trace("Added map <\"local\", "
                + "frontpage.backend.auth.LocalUserManager>");
    }

    /**
     *
     * @param type authenticator [remote, local]
     * @throws ProfileManagementException if an invalid
     */
    public static void createInstance(final String type)
            throws ProfileManagementException {
        if (type == null
                || !userAuthenticatorMap.containsKey(type.toLowerCase())) {
            throw new ProfileManagementException(
                    "Cannot create user authenticator for type " + type + ".");
        }

        Class<? extends ProfileManager> authClass =
                userAuthenticatorMap.get(type);
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
