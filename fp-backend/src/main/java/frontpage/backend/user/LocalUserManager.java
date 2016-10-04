package frontpage.backend.user;

import frontpage.bind.auth.InvalidCredentialsException;
import frontpage.bind.auth.InvalidDataException;
import frontpage.bind.auth.UserAuthenticationException;
import frontpage.bind.auth.UserManager;
import frontpage.model.User;
import frontpage.model.UserClass;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author willstuckey
 * <p></p>
 */
public final class LocalUserManager implements UserManager {
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
     * @throws UserAuthenticationException if failure
     */
    public String authenticateUser(final String un, final String pw)
            throws UserAuthenticationException {
        if (un == null || pw == null) {
            throw new InvalidCredentialsException(
                    "the provided credentials were null");
        }

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(un)
                    && u.getTok().equals(pw)) {
                return u.getTok();
            }
        }

        throw new InvalidCredentialsException(
                "the provided credentials did not exist or were invalid");
    }

    public String getUserType(final String email,
                              final String tok)
        throws UserAuthenticationException, InvalidDataException {
        if (email == null || tok == null) {
            throw new InvalidDataException("auth not provided");
        }

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)
                    && u.getTok().equals(tok)) {
                return u.getUserClass().toString();
            }
        }

        throw new UserAuthenticationException("invalid credentials");
    }

    public void createUser(final String un,
                              final String pw,
                              final String email,
                              final String firstname,
                              final String lastname,
                              final String type)
            throws InvalidDataException {
        for (User u : users ) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new InvalidDataException("user already taken");
            }
        }

        users.add(new User(email, pw, un, UserClass.valueOf(type)));
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
