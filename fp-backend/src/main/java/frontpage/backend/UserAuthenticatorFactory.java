package frontpage.backend;

import frontpage.backend.local.LocalUserAuthenticator;
import frontpage.backend.remote.SQLUserAuthenticator;
import frontpage.bind.auth.UserAuthenticator;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author willstuckey
 * @date 9/18/16
 * <p></p>
 */
public class UserAuthenticatorFactory {
    private static Logger logger;
    private static Map<String, Class<? extends UserAuthenticator>> userAuthenticatorMap;
    private static UserAuthenticator instance;

    static {
        logger = Logger.getLogger(UserAuthenticatorFactory.class);
        logger.setLevel(Level.ALL);
        userAuthenticatorMap = new HashMap<String, Class<? extends UserAuthenticator>>();
        userAuthenticatorMap.put("sql", SQLUserAuthenticator.class);
        logger.trace("Added map <\"sql\", frontpage.backend.remote.SQLUserAuthenticator>");
        userAuthenticatorMap.put("local", LocalUserAuthenticator.class);
        logger.trace("Added map <\"local\", frontpage.backend.local.LocalUserAuthenticator>");
    }

    /**
     *
     * @param type authenticator [sql, local]
     * @throws NoSuchUserAuthenticatorException if an invalid
     */
    public static void createInstance(final String type) throws NoSuchUserAuthenticatorException {
        if (type == null || !userAuthenticatorMap.containsKey(type.toLowerCase())) {
            throw new NoSuchUserAuthenticatorException("Cannot create user authenticator for type " + type +".");
        }

        Class<? extends UserAuthenticator> authClass = userAuthenticatorMap.get(type);
        try {
            instance = authClass.newInstance();
        } catch (Exception e) {
            throw new NoSuchUserAuthenticatorException("internal error creating user authenticator", e);
        }
    }

    public static UserAuthenticator getInstance() {
        return instance;
    }

    /**
     *
     */
    public static class NoSuchUserAuthenticatorException extends Exception {
        /**
         *
         */
        public NoSuchUserAuthenticatorException() {

        }

        /**
         *
         * @param message
         */
        public NoSuchUserAuthenticatorException(String message) {

        }

        /**
         *
         * @param t
         */
        public NoSuchUserAuthenticatorException(Throwable t) {

        }

        /**
         *
         * @param message
         * @param t
         */
        public NoSuchUserAuthenticatorException(String message, Throwable t) {

        }
    }
}
