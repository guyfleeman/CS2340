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
public class User {
    protected static ProfileManager pm;

    private final StringProperty _username = new SimpleStringProperty();
    private final StringProperty _email = new SimpleStringProperty();
    private final StringProperty _tok = new SimpleStringProperty();
    private UserClass _class;
    private UserProfile userProfile = new UserProfile();

    public User(final String email,
                final String tok) {
        this(email, tok, null, null);
    }

    public User(final String email,
                final String tok,
                final String username,
                final UserClass userClass) {
        _username.set(username);
        _email.set(email);
        _tok.set(tok);
        _class = userClass;
    }

    public static ProfileManager getPm() {
        return pm;
    }

    public static void setPm(ProfileManager pm) {
        User.pm = pm;
    }

    public String getUsername() {
        return _username.getValue();
    }
    public void setUsername(final String username) {
        _username.setValue(username);
    }
    public String getEmail() {
        return _email.get();
    }
    public void setEmail(final String name) {
        _email.set(name);
    }
    public String getTok() {
        return _tok.get();
    }
    public void setTok(final String tok) {
        _tok.set(tok);
    }
    public UserClass getUserClass() {
        return _class;
    }
    public void setUserClass(final UserClass userClass) {
        _class = userClass;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void loadProfile() {
        userProfile.populateFromDatabase(pm, this);
    }

    public void storeProfile() {
        userProfile.writeToDatabase(pm, this);
    }
}
