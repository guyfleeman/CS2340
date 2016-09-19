package frontpage.backend.auth;

import frontpage.bind.auth.UserAuthenticator;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author willstuckey
 * <p></p>
 */
public final class UserAuthenticatorFactory {
    /**
     * class logger
     */
    private static Logger logger;

    /**
     * type map
     */
    private static Map<String,
            Class<? extends UserAuthenticator>> userAuthenticatorMap;

    /**
     * instance
     */
    private static UserAuthenticator instance;

    static {
        logger = Logger.getLogger(UserAuthenticatorFactory.class);
        logger.setLevel(Level.ALL);
        userAuthenticatorMap =
                new HashMap<String, Class<? extends UserAuthenticator>>();
        userAuthenticatorMap.put("sql", SQLUserAuthenticator.class);
        logger.trace("Added map <\"sql\", "
                + "frontpage.backend.auth.SQLUserAuthenticator>");
        userAuthenticatorMap.put("local", LocalUserAuthenticator.class);
        logger.trace("Added map <\"local\", "
                + "frontpage.backend.auth.LocalUserAuthenticator>");
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

        Class<? extends UserAuthenticator> authClass =
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
    public static UserAuthenticator getInstance() {
        return instance;
    }

    /**
     * Utility Constructor
     */
    private UserAuthenticatorFactory() { }

    /**
     * NoSuchUserAuthenticatorException
     */
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
        public NoSuchUserAuthenticatorException(
                final String message,
                final Throwable t) {

        }
    }
}
