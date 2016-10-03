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

    private final StringProperty _username = new SimpleStringProperty();
    private final StringProperty _tok = new SimpleStringProperty();
    private UserProfile userProfile = new UserProfile();

    public User(String name, String pass) {
        _username.set(name);
        _tok.set(pass);
    }

    public String getUsername() {
        return _username.get();
    }
    public void setUsername(String name) {
        _username.set(name);
    }
    public String getPassword() {
        return _tok.get();
    }
    public void setPassword(String pass) {
        _tok.set(pass);
    }
}
