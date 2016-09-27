package frontpage.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Created by George on 9/20/2016.
 */
public class User {

    private final StringProperty _username = new SimpleStringProperty();
    private final StringProperty _password = new SimpleStringProperty();

    public User(String name, String pass) {
        _username.set(name);
        _password.set(pass);
    }

    public String getUsername() {
        return _username.get();
    }
    public void setUsername(String name) {
        _username.set(name);
    }
    public String getPassword() {
        return _password.get();
    }
    public void setPassword(String pass) {
        _password.set(pass);
    }
}
