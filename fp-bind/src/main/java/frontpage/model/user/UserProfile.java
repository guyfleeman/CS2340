package frontpage.model.user;

import frontpage.bind.errorhandling.ProfileManagementException;
import frontpage.bind.profile.ProfileManager;
import frontpage.utils.DialogueUtils;

import java.util.Map;
import java.util.HashMap;

/**
 * @author willstuckey
 * <p>User Profile data class</p>
 */
public class UserProfile {
    /**
     * users title (e.g. Ms.)
     */
    private String title;

    /**
     * users address, number then street
     */
    private String address;

    /**
     * users city
     */
    private String city;

    /**
     * users state
     */
    private String state;

    /**
     * users zip
     */
    private String zip;

    /**
     * creates a blank profile
     */
    public UserProfile() { }

    /**
     * creates user profile
     * @param title title
     * @param address address
     * @param city city
     * @param state state
     * @param zip zip
     */
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

    /**
     * gets title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets title
     * @param title title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * gets address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * sets address
     * @param address address
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * gets city
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * sets city
     * @param city city
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * gets state
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * sets state
     * @param state state
     */
    public void setState(final String state) {
        this.state = state;
    }

    /**
     * gets zip code
     * @return zip code
     */
    public String getZip() {
        return zip;
    }

    /**
     * sets zip code
     * @param zip zip code
     */
    public void setZip(final String zip) {
        this.zip = zip;
    }

    /**
     * fetches data from the bound ProfileManager
     * @param pm profile manager
     * @param user user for whom we load the profile
     */
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
            DialogueUtils.showMessage("Error populating from database: "
                    + e.getMessage() + "\r\n" + e.getCause());
        }
    }

    /**
     * fetch data from the bound ProfileManager
     * @param pm profile manager
     * @param user user for whom we write the profile
     */
    public void writeToDatabase(final ProfileManager pm,
                                final User user) {
        try {
            Map<String, String> props = new HashMap<>();
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
