package frontpage.model.report;

import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.ReportManager;
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
public class SourceReport {
    private static Logger logger;
    private static int numberOfReports = 0;

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

    public SourceReport(String user, String location, WaterType type, WaterCondition condition) {
        reportid = Integer.toString(++numberOfReports);
        username.set(user);
        loc.set(location);
        this.type = type;
        this.condition = condition;
    }

    public static SourceReport createReport(final ReportManager rm,
                                            final User auth)
            throws BackendRequestException {
        final SourceReport ret = new SourceReport();
        ret.reportid = rm.addSourceReport(auth.getEmail(), auth.getTok());
        ret.username.setValue(auth.getUsername());
        return ret;
    }

    public void populateFromBackend(final ReportManager rm)
            throws BackendRequestException {
        Map<String, String> res = rm.getSourceReport(reportid);
        reportid = res.get("reportid");
        title = res.get("name");
        String dt = res.get("reportdt");
        if (dt != null && dt.length() > 0) {
            reportTime = LocalDateTime.parse(dt);
        }
        loc.setValue(res.get("location"));
        username.setValue(res.get("username"));
        description = res.get("description");

        String type = res.get("type");
        if (type != null && type.length() > 0) {
            try {
                this.type = WaterType.valueOf(type);
            } catch (Exception e) {
                logger.warn("could not parse value for WaterType: " + type, e);
            }
        }

        String cond = res.get("cond");
        if (cond != null && cond.length() > 0) {
            try {
                this.condition = WaterCondition.valueOf(cond);
            } catch (Exception e) {
                logger.warn("could not parse value for WaterCondition: " + cond, e);
            }
        }
    }

    public void writeToBackend(final ReportManager rm,
                               final User auth)
            throws BackendRequestException {
        Map<String, String> attribs = new HashMap<>();
        attribs.put("reportid", reportid);
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

    public void deleteFromBackend(final ReportManager rm,
                                  final User auth)
            throws BackendRequestException {
        rm.deleteSourceReport(auth.getEmail(),
                auth.getUsername(),
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
}
