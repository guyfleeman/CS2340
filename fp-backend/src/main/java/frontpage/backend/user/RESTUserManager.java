package frontpage.backend.user;

import frontpage.backend.rest.RESTHandler;
import frontpage.backend.user.validator.DefaultEmailValidator;
import frontpage.backend.user.validator.DefaultPasswordValidator;
import frontpage.bind.auth.InvalidDataException;
import frontpage.bind.auth.UserAuthenticationException;
import frontpage.bind.auth.UserManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * <p>User Authenticator bound to an SQL server. See GlobalProperties
 * for connection information.</p>
 */
public class RESTUserManager implements UserManager {
    private static Logger logger;

    static {
        logger = Logger.getLogger(RESTUserManager.class.getName());
    }

    /**
     * creates a SQL User Authenticator from the global properties
     */
    RESTUserManager() {
    }


    /**
     * attempts to authenticate a user
     * @param email username
     * @param tok password
     * @return null on fail
     * @throws UserAuthenticationException if failure
     */
    public final String authenticateUser(final String email, final String tok)
            throws UserAuthenticationException, InvalidDataException {
        if (email == null || tok == null) {
            throw new InvalidDataException("one or more parameters was null or empty");
        }

        final Map<String, String> attribs = new HashMap<>(2);
        attribs.put("email", email);
        attribs.put("password", tok);
        boolean res = RESTHandler.apiRequest(RESTHandler.RestAction.GET,
                RESTHandler.ACCOUNT_AUTH_ENTRY_POINT,
                attribs);
        // there was a blatant failure
        if (!res) {
            throw new UserAuthenticationException("authentication failed");
        }

        boolean success = false;
        String message = "";
        String sessionid = "";
        String[] lines = RESTHandler.getLastHttpsResponsePayload().split("\r\n");
        for (String s : lines) {
            if (s.contains("status")) {
                success = s.contains("success");
            } else if (s.contains("message")) {
                final String[] args = s.split("=");
                message = args[1];
            } else if (s.contains("sessionid")) {
                final String[] args = s.split("=");
                sessionid = args[1];
            }
        }

        if (!success) {
            return null;
        } else {
            return sessionid;
        }
    }

    public final boolean createUser(final String un,
                                    final char[] pw,
                                    final String email,
                                    final String firstname,
                                    final String lastname)
        throws InvalidDataException {
        if (un == null || email == null || firstname == null || lastname == null) {
            throw new InvalidDataException("one or more parameters was null");
        }

        if (!(new DefaultPasswordValidator().isValidPassword(pw))) {
            throw new InvalidPasswordExcpetion("the password failed validation");
        }

        if (!(new DefaultEmailValidator().isValidEmail(email))) {
            throw new InvalidEmailException("the email address failed validation");
        }

        final Map<String, String> attribs = new HashMap<>(5);
        attribs.put("username", un);
        attribs.put("password", new String(pw));
        attribs.put("email", email);
        attribs.put("firstname", firstname);
        attribs.put("lastname", lastname);
        return RESTHandler.apiRequest(RESTHandler.RestAction.POST,
                RESTHandler.ACCOUNT_CREATION_ENTRY_POINT,
                attribs);
    }
}
