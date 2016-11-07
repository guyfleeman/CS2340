package frontpage.backend.user;

import frontpage.backend.rest.HTTPCodes;
import frontpage.backend.rest.RESTHandler;
import frontpage.backend.rest.RESTReport;
import frontpage.backend.validator.DefaultEmailValidator;
import frontpage.backend.validator.DefaultPasswordValidator;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.errorhandling.InvalidDataException;
import frontpage.bind.errorhandling.AuthenticationException;
import frontpage.bind.user.UserManager;
import frontpage.model.user.User;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * <p>User Authenticator bound to an SQL server. See GlobalProperties
 * for connection information.</p>
 */
@SuppressWarnings("WeakerAccess")
public class RemoteUserManager implements UserManager {
    /**
     * class logger
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final Logger logger;

    static {
        logger = Logger.getLogger(RemoteUserManager.class.getName());
    }

    /**
     * creates a SQL User Authenticator from the global properties
     */
    RemoteUserManager() { }


    /**
     * attempts to authenticate a user
     * @param email username
     * @param tok password
     * @return null on fail
     * @throws AuthenticationException if failure
     */
    public final String authenticateUser(final String email, final String tok)
            throws BackendRequestException {
        if (email == null || tok == null) {
            throw new InvalidDataException(
                    "one or more parameters was null or empty");
        }

        final Map<String, String> attribs = new HashMap<>(2);
        attribs.put("email", email);
        attribs.put("password", tok);
        final RESTReport rr = RESTHandler.apiRequest(
                RESTHandler.RestAction.GET,
                RESTHandler.ACCOUNT_AUTH_ENTRY_POINT,
                attribs);
        delegateExceptionGeneration(rr);
        return rr.getResponseValue("sessionid");
    }

    /**
     * gets a user property from the database
     * @param dataAttrib the property
     * @param user user for auth
     * @return property value
     * @throws BackendRequestException request failure
     */
    public String getUserProperty(final UserDataAttrib dataAttrib,
                                  final User user)
            throws BackendRequestException {
        return getUserProperty(dataAttrib, user.getEmail(), user.getTok());
    }

    /**
     * gets a user property from the database
     * @param dataAttrib the property
     * @param email email for auth
     * @param tok token for auth
     * @return property value
     * @throws BackendRequestException request failure
     */
    public String getUserProperty(final UserDataAttrib dataAttrib,
                                  final String email,
                                  final String tok)
            throws BackendRequestException {
        final String property = dataAttrib.toString().toLowerCase();
        final Map<String, String> attribs = new HashMap<>(2);
        attribs.put("email", email);
        attribs.put("tok", tok);
        attribs.put("property", property);
        final RESTReport rr = RESTHandler.apiRequest(
                RESTHandler.RestAction.GET,
                RESTHandler.ACCOUNT_USER_ENTRY_POINT,
                attribs);
        delegateExceptionGeneration(rr);
        return rr.getResponseValue(property);
    }

    /**
     * gets the user type from a post request
     * TODO abstract away generic protected property fetch
     * @param email email for auth
     * @param tok token for auth
     * @return user type
     * @throws AuthenticationException if auth fails
     * @throws InvalidDataException if null parameters are passed
     * @throws BackendRequestException other errors
     */
    public final String getUserType(final String email,
                              final String tok)
                    throws BackendRequestException {
        if (email == null || tok == null) {
            throw new InvalidDataException(
                    "one or more parameters was null or empty");
        }

        final Map<String, String> attribs = new HashMap<>(2);
        attribs.put("email", email);
        attribs.put("tok", tok);
        attribs.put("property", "type");
        final RESTReport rr = RESTHandler.apiRequest(
                RESTHandler.RestAction.GET,
                RESTHandler.ACCOUNT_USER_ENTRY_POINT,
                attribs);
        delegateExceptionGeneration(rr);
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
     * @throws BackendRequestException other errors
     */
    public final void createUser(final String un,
                                    final String pw,
                                    final String email,
                                    final String firstname,
                                    final String lastname,
                                    final String userClass)
        throws BackendRequestException {
        if (un == null || pw == null || email == null
                || firstname == null || lastname == null || userClass == null) {
            throw new InvalidDataException("one or more parameters was null");
        }

        //noinspection ConstantConditions
        if (!(new DefaultPasswordValidator().isValidPassword(pw))) {
            throw new InvalidPasswordExcpetion(
                    "the password failed validation");
        }

        if (!(new DefaultEmailValidator().isValidEmail(email))) {
            throw new InvalidEmailException(
                    "the email address failed validation");
        }

        final Map<String, String> attribs = new HashMap<>(5);
        attribs.put("username", un);
        attribs.put("password", pw);
        attribs.put("email", email);
        attribs.put("firstname", firstname);
        attribs.put("lastname", lastname);
        attribs.put("type", userClass);
        final RESTReport rr = RESTHandler.apiRequest(
                RESTHandler.RestAction.POST,
                RESTHandler.ACCOUNT_CREATION_ENTRY_POINT,
                attribs);
        delegateExceptionGeneration(rr);
    }

    /**
     * generates exceptions based on the reuslts of a RESTReport
     * NOTE: this is not done in the constructor of the RR because
     * response codes vary by implementation and context.
     * @param rr RESTReport
     * @throws BackendRequestException if the backend request cannot
     *                                 be made
     */
    protected final void delegateExceptionGeneration(final RESTReport rr)
            throws BackendRequestException {
        if (rr.wasInternalError()) {
            throw new BackendRequestException(
                    "internal error during user creation: "
                            + rr.getInternalErrorMessage());
        }

        if (rr.rejected()) {
            if (rr.getHttpResponseCode() == HTTPCodes.INTERNAL_SERVER_ERROR) {
                throw new BackendRequestException(
                        "an unrecoverable error occurred "
                                + "on the server");
            } else if (rr.getHttpResponseCode() == HTTPCodes.BAD_REQUEST) {
                throw new BackendRequestException(
                        "an unrecoverable error occurred "
                                + "with the request made to the server");
            } else if (rr.getHttpResponseCode() == HTTPCodes.UNAUTHORIZED) {
                throw new AuthenticationException(
                        "the authentication credentials "
                                + "were rejected by the server");
            }
        }

        if (!rr.success()) {
            if (rr.getSingleResponseMap()
                    .get("message")
                    .contains("credentials")) {
                throw new AuthenticationException(
                        "matching account not found");
            }
            throw new BackendRequestException(
                    "the request was unsuccessful: "
                            + rr.getResponseValue("message"));
        }
    }
}
