package frontpage.model.user;

import frontpage.bind.profile.ProfileManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Created by George on 9/20/2016.
 *
 * @author georgetang
 * @author willstuckey
 */
@SuppressWarnings("WeakerAccess")
public class User {
    private static ProfileManager pm;

    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty tok = new SimpleStringProperty();
    private UserClass userClass;
    private UserProfile userProfile = new UserProfile();

    /**
     * creates a user
     * @param email email
     * @param tok auth token
     */
    public User(final String email,
                final String tok) {
        this(email, tok, null, null);
    }

    /**
     * creates a user
     * @param email email
     * @param tok auth token
     * @param username username
     * @param userClass class
     */
    public User(final String email,
                final String tok,
                final String username,
                final UserClass userClass) {
        this.username.set(username);
        this.email.set(email);
        this.tok.set(tok);
        this.userClass = userClass;
    }

    /**
     * gets profile manager
     * @return profile manager
     */
    public static ProfileManager getPm() {
        return pm;
    }

    /**
     * sets profile manager
     * @param pm profile manager
     */
    public static void setPm(final ProfileManager pm) {
        User.pm = pm;
    }

    /**
     * gets username
     * @return username
     */
    public String getUsername() {
        return username.getValue();
    }

    /**
     * sets username
     * @param username username
     */
    public void setUsername(final String username) {
        this.username.setValue(username);
    }

    /**
     * gets users email
     * @return email
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * sets the users email
     * @param email email
     */
    public void setEmail(final String email) {
        this.email.set(email);
    }

    /**
     * gets the current auth token
     * @return auth token
     */
    public String getTok() {
        return tok.get();
    }

    /**
     * sets the current auth token
     * @param tok token
     */
    public void setTok(final String tok) {
        this.tok.set(tok);
    }

    /**
     * gets the user class
     * @return user class
     */
    public UserClass getUserClass() {
        return userClass;
    }

    /**
     * sets the user class
     * @param userClass user class
     */
    public void setUserClass(final UserClass userClass) {
        this.userClass = userClass;
    }

    /**
     * gets user profile
     * @return user profile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * sets the profile from an object
     * @param userProfile profile
     */
    public void setUserProfile(final UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * loads the profile from the backend
     */
    public void loadProfile() {
        userProfile.populateFromDatabase(pm, this);
    }

    /**
     * stores the current profile in the backend
     */
    public void storeProfile() {
        userProfile.writeToDatabase(pm, this);
    }
}
