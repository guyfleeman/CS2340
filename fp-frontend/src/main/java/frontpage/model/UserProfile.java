package frontpage.model;

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

    public void populateFromDatabase(final User user) {

    }

    public void writeToDatabase(final User user) {

    }
}
