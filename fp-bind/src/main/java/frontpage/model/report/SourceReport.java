package frontpage.model.report;

import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.ReportManager;
import frontpage.model.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by George on 10/15/2016.
 */
public class SourceReport {
    private static int numberOfReports = 0;

    private LocalDateTime reportTime;
    private String reportNumber;
    private String name;
    private String description;
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty loc = new SimpleStringProperty();
    private WaterType type;
    private WaterCondition condition;

    public SourceReport() {}

    public SourceReport(String user, String location, WaterType type, WaterCondition condition) {
        reportTime = LocalDateTime.now();
        reportNumber = Integer.toString(++numberOfReports);
        username.set(user);
        loc.set(location);
        this.type = type;
        this.condition = condition;
    }

    public LocalDateTime getReportTime() {return reportTime;}
    public String getReportNumber() {return reportNumber;}
    public String getUsername() {return username.getValue();}

    public String getLoc() {return loc.getValue();}
    public WaterType getType() {return type;}
    public WaterCondition getCondition() {return condition;}
    public void setCondition(WaterCondition cond) {condition = cond;}

    public static SourceReport createReport(final ReportManager rm,
                                            final User auth)
            throws BackendRequestException {
        final SourceReport ret = new SourceReport();
        ret.reportNumber = rm.addSourceReport(auth.getEmail(), auth.getTok());
        ret.username.setValue(auth.getUsername());
        return ret;
    }

    public void populateFromBackend(final ReportManager rm)
            throws BackendRequestException {
        Map<String, String> res = rm.getSourceReport(reportNumber);
        reportNumber = res.get("reportid");
        name = res.get("name");
        String dt = res.get("reportdt");
        if (dt != null) {
            reportTime = LocalDateTime.parse(dt);
        }
        loc.setValue(res.get("location"));
        username.setValue(res.get("username"));
        description = res.get("description");
        type = WaterType.valueOf(res.get("type"));
        condition = WaterCondition.valueOf(res.get("cond"));
    }

    public void writeToBackend(final ReportManager rm,
                               final User auth) {

    }
}
