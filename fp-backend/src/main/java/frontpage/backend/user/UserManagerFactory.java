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
    private static final Logger logger;

    /**
     * type map
     */
    private static final Map<String,
            Class<? extends UserManager>> userAuthenticatorMap;

    /**
     * instance
     */
    private static UserManager instance;

    static {
        logger = Logger.getLogger(UserManagerFactory.class);
        logger.setLevel(Level.ALL);
        userAuthenticatorMap = new HashMap<>();
        userAuthenticatorMap.put("remote", RemoteUserManager.class);
        logger.trace("Added map <\"remote\", "
                + "frontpage.backend.auth.RESTUserManager>");
        userAuthenticatorMap.put("local", LocalUserManager.class);
        logger.trace("Added map <\"local\", "
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
                || !userAuthenticatorMap.containsKey(type.toLowerCase())) {
            throw new NoSuchUserAuthenticatorException(
                    "Cannot create user authenticator for type " + type + ".");
        }

        Class<? extends UserManager> authClass =
                userAuthenticatorMap.get(type);
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
