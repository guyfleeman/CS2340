package frontpage.backend.user;

import frontpage.bind.errorhandling.InvalidDataException;
import frontpage.bind.errorhandling.AuthenticationException;
import frontpage.bind.user.UserManager;
import frontpage.model.user.User;
import frontpage.model.user.UserClass;

import java.util.ArrayList;

/**
 * @author willstuckey
 * <p></p>
 */
public final class LocalUserManager implements UserManager {
    /**
     * class logger
     */
    private ArrayList<User> users;

    /**
     * local constructor
     */
    LocalUserManager() {
        users = new ArrayList<>();
    }

    /**
     * attempts to authenticate a user
     * @param un username
     * @param pw password
     * @return success
     * @throws AuthenticationException if failure
     */
    public String authenticateUser(final String un, final String pw)
            throws AuthenticationException {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(un)
                    && u.getTok().equals(pw)) {
                return u.getTok();
            }
        }

        throw new AuthenticationException(
                "the username and password didn't match any users");
    }

    /**
     * gets the user type
     * @param email email
     * @param tok auth token
     * @return type
     * @throws AuthenticationException failure to authenticate
     * @throws InvalidDataException null params
     */
    public String getUserType(final String email,
                              final String tok)
        throws AuthenticationException, InvalidDataException {
        if (email == null || tok == null) {
            throw new InvalidDataException("auth not provided");
        }

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getTok().equals(tok)) {
                return u.getUserClass().toString();
            }
        }

        throw new AuthenticationException("invalid credentials");
    }

    /**
     * creates user
     * @param un username
     * @param pw password
     * @param email email
     * @param firstname firstname
     * @param lastname lastname
     * @param type type
     * @throws InvalidDataException user already registered
     */
    public void createUser(final String un,
                              final String pw,
                              final String email,
                              final String firstname,
                              final String lastname,
                              final String type)
            throws InvalidDataException {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new InvalidDataException("user already taken");
            }
        }

        users.add(new User(email, pw, un, UserClass.valueOf(type)));
    }

    /**
     * gets list of users
     * @return users
     */
    public ArrayList<User> getUsers() {
        return users;
    }
}
