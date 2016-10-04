package frontpage.model;

import frontpage.bind.profile.ProfileManagementException;
import frontpage.bind.profile.ProfileManager;
import frontpage.utils.DialogueUtils;

import java.util.Map;
import java.util.HashMap;

/**
 * @author willstuckey
 * @date 10/2/16
 * <p></p>
 */
public class UserProfile {
    private String title;
    private String address;
    private String city;
    private String state;
    private String zip;

    public UserProfile() {}

    public UserProfile(final String title,
                       final String address,
                       final String city,
                       final String state,
                       final String zip) {
        this.title = title;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void populateFromDatabase(final ProfileManager pm,
                                     final User user) {
        try {
            Map<String, String> props = pm.getProfile(
                    user.getEmail(),
                    user.getTok());
            setAddress(props.get("address"));
            setCity(props.get("city"));
            setState(props.get("state"));
            setZip(props.get("zip"));
            setTitle(props.get("title"));
        } catch (ProfileManagementException e) {
            DialogueUtils.showMessage(e.getMessage());
        }
    }

    public void writeToDatabase(final ProfileManager pm,
                                final User user) {
        try {
            Map<String, String> props = new HashMap<String, String>(5);
            props.put("address", address);
            props.put("city", city);
            props.put("state", state);
            props.put("zip", zip);
            props.put("title", title);
            pm.setProfile(user.getEmail(), user.getTok(), props);
        } catch (ProfileManagementException e) {
            DialogueUtils.showMessage(e.getMessage());
        }
    }
}
