package frontpage.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Created by George on 9/20/2016.
 *
 * @author georgetang
 * @author willstuckey
 */
public class User {

    private final StringProperty _email = new SimpleStringProperty();
    private final StringProperty _tok = new SimpleStringProperty();
    private UserProfile userProfile = new UserProfile();

    public User(String name, String tok) {
        _email.set(name);
        _tok.set(tok);
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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void loadProfile() {
        userProfile.populateFromDatabase(this);
    }

    public void storeProfile() {
        userProfile.writeToDatabase(this);
    }
}
