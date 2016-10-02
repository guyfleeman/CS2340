package frontpage.backend.user;

import frontpage.backend.rest.RESTHandler;
import frontpage.backend.user.validator.DefaultEmailValidator;
import frontpage.backend.user.validator.DefaultPasswordValidator;
import frontpage.bind.auth.InvalidDataException;
import frontpage.bind.auth.UserAuthenticationException;
import frontpage.bind.auth.UserManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * <p>User Authenticator bound to an SQL server. See GlobalProperties
 * for connection information.</p>
 */
public class RESTUserManager implements UserManager {
    /**
     * creates a SQL User Authenticator from the global properties
     */
    RESTUserManager() {
    }

    /**
     * attempts to authenticate a user
     * @param un username
     * @param pw password
     * @return success
     * @throws UserAuthenticationException if failure
     */
    public final boolean authenticateUser(final String un, final char[] pw)
            throws UserAuthenticationException {
        return false;
    }

    public final boolean createUser(final String un,
                                    final char[] pw,
                                    final String email,
                                    final String firstname,
                                    final String lastname)
        throws InvalidDataException {
        if (un == null || email == null || firstname == null || lastname == null) {
            throw new NullPointerException("one or more parameters was null");
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
