package frontpage.backend.user;

import frontpage.backend.rest.RESTHandler;
import frontpage.backend.rest.RESTReport;
import frontpage.backend.validator.DefaultEmailValidator;
import frontpage.backend.validator.DefaultPasswordValidator;
import frontpage.bind.auth.FailedToCreateUserException;
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
public class RemoteUserManager implements UserManager {
    private static Logger logger;

    static {
        logger = Logger.getLogger(RemoteUserManager.class.getName());
    }

    /**
     * creates a SQL User Authenticator from the global properties
     */
    RemoteUserManager() {
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
        final RESTReport rr = RESTHandler.apiRequest(RESTHandler.RestAction.GET,
                RESTHandler.ACCOUNT_AUTH_ENTRY_POINT,
                attribs);
        // there was a blatant failure
        if (rr.wasInternalError()) {
            throw new UserAuthenticationException(rr.getInternalErrorMessage());
        }

        if (rr.rejected()) {
            throw new UserAuthenticationException(rr.getHttpResponseMessage());
        }

        if (!rr.success()) {
            throw new UserAuthenticationException(rr.getResponseValue("message"));
        }

        return rr.getResponseValue("sessionid");
    }

    /**
     * gets the user type from a post request
     * TODO abstract away generic protected property fetch
     * @param email email for auth
     * @param tok token for auth
     * @return user type
     * @throws UserAuthenticationException if auth fails
     * @throws InvalidDataException if null parameters are passed
     */
    public String getUserType(final String email,
                              final String tok)
                    throws UserAuthenticationException, InvalidDataException {
        if (email == null || tok == null) {
            throw new InvalidDataException("one or more parameters was null or empty");
        }

        final Map<String, String> attribs = new HashMap<>(2);
        attribs.put("email", email);
        attribs.put("tok", tok);
        attribs.put("property", "type");
        final RESTReport rr = RESTHandler.apiRequest(RESTHandler.RestAction.GET,
                RESTHandler.ACCOUNT_USER_ENTRY_POINT,
                attribs);
        // there was a blatant failure
        if (rr.wasInternalError()) {
            throw new UserAuthenticationException(rr.getInternalErrorMessage());
        }

        if (rr.rejected()) {
            throw new UserAuthenticationException(rr.getHttpResponseMessage());
        }

        if (!rr.success()) {
            throw new UserAuthenticationException(rr.getResponseValue("message"));
        }

        return rr.getResponseValue("type");
    }

    /**
     * creates a user
     * @param un username
     * @param pw password
     * @param email email
     * @param firstname firstname
     * @param lastname lastname
     * @param userClass user class
     * @throws InvalidDataException null parameters
     * @throws FailedToCreateUserException if there is a duplicate user
     */
    public final void createUser(final String un,
                                    final String pw,
                                    final String email,
                                    final String firstname,
                                    final String lastname,
                                    final String userClass)
        throws InvalidDataException, FailedToCreateUserException {
        if (un == null || pw == null || email == null
                || firstname == null || lastname == null || userClass == null) {
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
        attribs.put("password", pw);
        attribs.put("email", email);
        attribs.put("firstname", firstname);
        attribs.put("lastname", lastname);
        attribs.put("type", userClass);
        RESTReport rr = RESTHandler.apiRequest(RESTHandler.RestAction.POST,
                RESTHandler.ACCOUNT_CREATION_ENTRY_POINT,
                attribs);

        if (!rr.success()) {
            throw new FailedToCreateUserException(rr.getResponseValue("message"));
        }
    }
}
