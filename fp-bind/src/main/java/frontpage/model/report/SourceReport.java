package frontpage.model.report;

import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.SourceReportManager;
import frontpage.model.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by George on 10/15/2016.
 *
 * @author George Tang
 * @author willstuckey
 */
@SuppressWarnings("WeakerAccess")
public class SourceReport {
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(SourceReport.class);
    }

    private LocalDateTime reportTime = LocalDateTime.now();
    private String reportid;
    private String title;
    private String description;
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty loc = new SimpleStringProperty();
    private WaterType type = WaterType.UNAVAILABLE;
    private WaterCondition condition = WaterCondition.UNAVAILABLE;

    /**
     * creates a source report
     */
    public SourceReport() { }

    /**
     * creates a source report from a map of properties
     * @param ldf map to load from
     */
    public SourceReport(final Map<String, String> ldf) {
        loadFromMap(ldf);
    }

    /**
     * creates an empty source report on the backend
     * @param rm report manager
     * @param auth user for authentication
     * @return new report
     * @throws BackendRequestException if things go wrong
     */
    public static SourceReport createReport(final SourceReportManager rm,
                                            final User auth)
            throws BackendRequestException {
        final SourceReport ret = new SourceReport();
        ret.reportid = rm.addSourceReport(auth.getEmail(), auth.getTok());
        ret.username.setValue(auth.getUsername());
        return ret;
    }

    /**
     * populates a report from the backend, overwriting any data currently
     * in the report
     * @param rm report manager
     * @throws BackendRequestException if things go wrong
     */
    public void populateFromBackend(final SourceReportManager rm)
            throws BackendRequestException {
        Map<String, String> res = rm.getSourceReport(reportid);
        loadFromMap(res);
    }

    /**
     * load the report from a map of properties
     * @param map map of properties
     */
    private void loadFromMap(final Map<String, String> map) {
        reportid = map.get("reportid");
        title = map.get("name");
        String dt = map.get("reportdt");
        if ((dt != null) && (dt.length() > 0)) {
            reportTime = LocalDateTime.parse(dt.replace(' ', 'T'));
        }
        loc.setValue(map.get("location"));
        username.setValue(map.get("username"));
        description = map.get("description");

        String newType = map.get("type");
        if ((newType != null) && (newType.length() > 0)) {
            try {
                this.type = WaterType.valueOf(newType);
            } catch (Exception e) {
                LOGGER.warn("could not parse value for WaterType: "
                        + newType, e);
            }
        }

        String cond = map.get("cond");
        if (cond != null && cond.length() > 0) {
            try {
                this.condition = WaterCondition.valueOf(cond);
            } catch (Exception e) {
                LOGGER.warn("could not parse value for WaterCondition: "
                        + cond, e);
            }
        }
    }

    /**
     * writes the report to the backend
     * @param rm report manager
     * @param auth user for auth
     * @throws BackendRequestException if things go wrong
     */
    public void writeToBackend(final SourceReportManager rm,
                               final User auth)
            throws BackendRequestException {
        Map<String, String> attribs = new HashMap<>();
        attribs.put("reportdt", reportTime.toString());
        attribs.put("location", loc.getValue());
        attribs.put("type", type.toString());
        attribs.put("cond", condition.toString());
        attribs.put("name", title);
        attribs.put("description", description);
        rm.updateSourceReport(auth.getEmail(),
                auth.getTok(),
                reportid,
                attribs);
    }

    /**
     * deletes the report from the backend
     * @param rm report manager
     * @param auth user for auth
     * @throws BackendRequestException if things go wrong
     */
    public void deleteFromBackend(final SourceReportManager rm,
                                  final User auth)
            throws BackendRequestException {
        rm.deleteSourceReport(auth.getEmail(),
                auth.getTok(),
                reportid);
    }

    /**
     * to string
     * @return to string
     */
    public String toString() {
        String ret = "";
        ret += "id: " + reportid + "\r\n";
        ret += "title: " + title + "\r\n";
        ret += "location: " + loc.getValue() + "\r\n";
        ret += "description: " + description + "\r\n";
        ret += "submitter: " + username.getValue() + "\r\n";
        ret += "type: " + type.toString() + "\r\n";
        ret += "condition: " + condition.toString() + "\r\n";
        ret += "date: " + reportTime.toString() + "\r\n";
        ret += "\r\n";
        return ret;
    }

    /**
     * gets the time the report was creates
     * @return datetime
     */
    public LocalDateTime getReportTime() {
        return reportTime;
    }

    /**
     * gets the report id
     * @return report id
     */
    public String getReportid() {
        return reportid;
    }

    /**
     * gets the username of report submitter
     * @return username
     */
    public String getUsername() {
        return username.getValue();
    }

    /**
     * gets the location of the report
     * @return location
     */
    public String getLoc() {
        return loc.getValue();
    }

    /**
     * gets the water type
     * @return water type
     */
    public WaterType getType() {
        return type;
    }

    /**
     * gets water condition
     * @return water condition
     */
    public WaterCondition getCondition() {
        return condition;
    }

    /**
     * sets water condition
     * @param cond water condition
     */
    public void setCondition(final WaterCondition cond) {
        condition = cond;
    }

    /**
     * gets report title
     * @return report title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets report title
     * @param title title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * gets report description
     * @return report description
     */
    public String getDescription() {
        return description;
    }

    /**
     *sets the report description
     * @param description description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * sets the location
     * @param loc location
     */
    public void setLoc(final String loc) {
        this.loc.setValue(loc);
    }

    /**
     * sets the water type
     * @param type water type
     */
    public void setType(final WaterType type) {
        this.type = type;
    }

    /**
     * gets human readable date suitable for table embedding
     * @return report time
     */
    public StringProperty getReportTimeT() {
        return new SimpleStringProperty(normalizeDT(reportTime));
    }

    /**
     * gets report id suitable for JFX embedding
     * @return report id
     */
    public StringProperty getReportIDT() {
        return new SimpleStringProperty(reportid);
    }

    /**
     * gets username suitable for JFX embedding
     * @return username
     */
    public StringProperty getUsernameT() {
        return new SimpleStringProperty(username.getValue());
    }

    /**
     * gets location suitable for JFX embedding
     * @return location
     */
    public StringProperty getLocationT() {
        return new SimpleStringProperty(loc.getValue());
    }

    /**
     * gets type suitable for embedding
     * @return type
     */
    public StringProperty getTypeT() {
        return new SimpleStringProperty(type.toString());
    }

    /**
     * gets condition suitable for embedding
     * @return condition
     */
    public StringProperty getConditionT() {
        return new SimpleStringProperty(condition.toString());
    }

    /**
     * gets a human readable string from a datetime
     * @param ldt datetime
     * @return string
     */
    private static String normalizeDT(final LocalDateTime ldt) {
        return ("" + ldt.getMonthValue()
                + "/" + ldt.getDayOfMonth()
                + "/" + ldt.getYear());
    }
}
