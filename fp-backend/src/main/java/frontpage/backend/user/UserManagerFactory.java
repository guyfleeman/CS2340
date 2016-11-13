package frontpage.backend.user;

import frontpage.bind.user.UserManager;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author willstuckey
 * <p></p>
 */
public final class UserManagerFactory {
    /**
     * class logger
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final Logger LOGGER;

    /**
     * type map
     */
    private static final Map<String,
            Class<? extends UserManager>> USER_AUTHENTICATOR_MAP;

    /**
     * instance
     */
    private static UserManager instance;

    static {
        LOGGER = Logger.getLogger(UserManagerFactory.class);
        LOGGER.setLevel(Level.ALL);
        USER_AUTHENTICATOR_MAP = new HashMap<>();
        USER_AUTHENTICATOR_MAP.put("remote", RemoteUserManager.class);
        LOGGER.trace("Added map <\"remote\", "
                + "frontpage.backend.auth.RESTUserManager>");
        USER_AUTHENTICATOR_MAP.put("local", LocalUserManager.class);
        LOGGER.trace("Added map <\"local\", "
                + "frontpage.backend.auth.LocalUserManager>");
    }

    /**
     *
     * @param type authenticator [sql, local]
     * @throws NoSuchUserAuthenticatorException if an invalid
     */
    public static void createInstance(final String type)
            throws NoSuchUserAuthenticatorException {
        if (type == null
                || !USER_AUTHENTICATOR_MAP.containsKey(type.toLowerCase())) {
            throw new NoSuchUserAuthenticatorException(
                    "Cannot create user authenticator for type " + type + ".");
        }

        Class<? extends UserManager> authClass =
                USER_AUTHENTICATOR_MAP.get(type);
        try {
            instance = authClass.newInstance();
        } catch (Exception e) {
            throw new NoSuchUserAuthenticatorException(
                    "internal error creating user authenticator", e);
        }
    }

    /**
     * gets singleton authenticator
     * @return instance
     */
    public static UserManager getInstance() {
        return instance;
    }

    /**
     * Utility Constructor
     */
    private UserManagerFactory() { }

    /**
     * NoSuchUserAuthenticatorException
     */
    @SuppressWarnings("UnusedParameters")
    public static final class NoSuchUserAuthenticatorException
            extends Exception {
        /**
         * default constructor
         */
        public NoSuchUserAuthenticatorException() {

        }

        /**
         * sun style constructor
         * @param message message
         */
        public NoSuchUserAuthenticatorException(final String message) {

        }

        /**
         * sun style constructor
         * @param t cause
         */
        public NoSuchUserAuthenticatorException(final Throwable t) {

        }

        /**
         * sun style constructor
         * @param message message
         * @param t cause
         */
        @SuppressWarnings("SameParameterValue")
        public NoSuchUserAuthenticatorException(
                final String message,
                final Throwable t) {

        }
    }
}
