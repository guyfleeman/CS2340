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
    private static final Logger logger;

    static {
        logger = Logger.getLogger(SourceReport.class);
    }

    private LocalDateTime reportTime = LocalDateTime.now();
    private String reportid;
    private String title;
    private String description;
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty loc = new SimpleStringProperty();
    private WaterType type = WaterType.UNAVAILABLE;
    private WaterCondition condition = WaterCondition.UNAVAILABLE;

    public SourceReport() {}

    public SourceReport(final Map<String, String> ldf) {
        loadFromMap(ldf);
    }

    public static SourceReport createReport(final SourceReportManager rm,
                                            final User auth)
            throws BackendRequestException {
        final SourceReport ret = new SourceReport();
        ret.reportid = rm.addSourceReport(auth.getEmail(), auth.getTok());
        ret.username.setValue(auth.getUsername());
        return ret;
    }

    public void populateFromBackend(final SourceReportManager rm)
            throws BackendRequestException {
        Map<String, String> res = rm.getSourceReport(reportid);
        loadFromMap(res);
    }

    private void loadFromMap(Map<String, String> map) {
        reportid = map.get("reportid");
        title = map.get("name");
        String dt = map.get("reportdt");
        if (dt != null && dt.length() > 0) {
            reportTime = LocalDateTime.parse(dt.replace(' ', 'T'));
        }
        loc.setValue(map.get("location"));
        username.setValue(map.get("username"));
        description = map.get("description");

        String type = map.get("type");
        if (type != null && type.length() > 0) {
            try {
                this.type = WaterType.valueOf(type);
            } catch (Exception e) {
                logger.warn("could not parse value for WaterType: " + type, e);
            }
        }

        String cond = map.get("cond");
        if (cond != null && cond.length() > 0) {
            try {
                this.condition = WaterCondition.valueOf(cond);
            } catch (Exception e) {
                logger.warn("could not parse value for WaterCondition: " + cond, e);
            }
        }
    }

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

    public void deleteFromBackend(final SourceReportManager rm,
                                  final User auth)
            throws BackendRequestException {
        rm.deleteSourceReport(auth.getEmail(),
                auth.getTok(),
                reportid);
    }

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

    public LocalDateTime getReportTime() {return reportTime;}
    public String getReportid() {return reportid;}
    public String getUsername() {return username.getValue();}
    public String getLoc() {return loc.getValue();}
    public WaterType getType() {return type;}
    public WaterCondition getCondition() {return condition;}
    public void setCondition(WaterCondition cond) {condition = cond;}
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLoc(final String loc) {
        this.loc.setValue(loc);
    }
    public void setType(WaterType type) {
        this.type = type;
    }

    public StringProperty getReportTime_t() { return new SimpleStringProperty(normalizeDT(reportTime)); }
    public StringProperty getReportID_t() { return new SimpleStringProperty(reportid); }
    public StringProperty getUsername_t() { return new SimpleStringProperty(username.getValue()); }
    public StringProperty getLocation_t() { return new SimpleStringProperty(loc.getValue()); }
    public StringProperty getType_t() { return new SimpleStringProperty(type.toString()); }
    public StringProperty getCondition_t() { return new SimpleStringProperty(condition.toString()); }

    private static String normalizeDT(final LocalDateTime ldt) {
        return "" + ldt.getMonthValue() + "/" + ldt.getDayOfMonth() + "/" + ldt.getYear();
    }
}
