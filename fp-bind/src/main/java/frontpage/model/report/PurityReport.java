package frontpage.model.report;

import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.PurityReportManager;
import frontpage.bind.report.SourceReportManager;
import frontpage.model.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * @date 10/31/16
 * <p></p>
 */
public class PurityReport {
    private static Logger logger;

    static {
        logger = Logger.getLogger(PurityReport.class.getName());
    }

    private String id;
    private String sourceRptId;
    private String username;
    private LocalDateTime datetime = LocalDateTime.now();
    private String location;
    private PurityCondition condition;
    private String virusPPM;
    private String contaminantPPM;

    public PurityReport() {}

    public PurityReport(final Map<String, String> ldf) {
        loadFromMap(ldf);
    }

    public static PurityReport createReport(final PurityReportManager rm,
                                            final User auth)
            throws BackendRequestException {
        final PurityReport ret = new PurityReport();
        ret.id = rm.addPurityReport(auth.getEmail(), auth.getTok());
        ret.username = auth.getUsername();
        return ret;
    }

    public void populateFromBackend(final PurityReportManager rm)
            throws BackendRequestException {
        Map<String, String> res = rm.getPurityReport(id);
        loadFromMap(res);
    }

    private void loadFromMap(Map<String, String> map) {
        id = map.get("reportid");
        sourceRptId = map.get("sourceid");
        String dt = map.get("reportdt");
        if (dt != null && dt.length() > 0) {
            datetime = LocalDateTime.parse(dt.replace(' ', 'T'));
        }
        location = map.get("location");
        username = map.get("username");

        String type = map.get("cond");
        if (type != null && type.length() > 0) {
            try {
                this.condition = PurityCondition.valueOf(type);
            } catch (Exception e) {
                logger.warn("could not parse value for WaterType: " + type, e);
            }
        }

        virusPPM = map.get("virusppm");
        contaminantPPM = map.get("contaminantppm");
    }

    public void writeToBackend(final SourceReportManager rm,
                               final User auth)
            throws BackendRequestException {
        Map<String, String> attribs = new HashMap<>();
        attribs.put("reportid", id);
        attribs.put("sourceid", sourceRptId);
        attribs.put("reportdt", datetime.toString());
        attribs.put("location", location);
        attribs.put("cond", condition.toString());
        attribs.put("virusppm", virusPPM);
        attribs.put("contaminantppm", contaminantPPM);
        rm.updateSourceReport(auth.getEmail(),
                auth.getTok(),
                id,
                attribs);
    }

    public void deleteFromBackend(final SourceReportManager rm,
                                  final User auth)
            throws BackendRequestException {
        rm.deleteSourceReport(auth.getEmail(),
                auth.getTok(),
                id);
    }

    public String toString() {
        String ret = "";
        ret += "id: " + id + "\r\n";
        ret += "location: " + location + "\r\n";
        ret += "submitter: " + username + "\r\n";
        ret += "condition: " + condition.toString() + "\r\n";
        ret += "date: " + datetime.toString() + "\r\n";
        ret += "virus ppm: " + virusPPM + "\r\n";
        ret += "contaminant ppm: " + virusPPM + "\r\n";
        ret += "\r\n";
        return ret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceRptId() {
        return sourceRptId;
    }

    public void setSourceRptId(String sourceRptId) {
        this.sourceRptId = sourceRptId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public String getNormalizedDatetime() {
        return normalizeDT(datetime);
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PurityCondition getCondition() {
        return condition;
    }

    public void setCondition(PurityCondition condition) {
        this.condition = condition;
    }

    public String getVirusPPM() {
        return virusPPM;
    }

    public void setVirusPPM(String virusPPM) {
        this.virusPPM = virusPPM;
    }

    public String getContaminantPPM() {
        return contaminantPPM;
    }

    public void setContaminantPPM(String contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    public StringProperty getDate_t() {
        return new SimpleStringProperty(normalizeDT(datetime));
    }

    public StringProperty getRptId_t() {
        return new SimpleStringProperty(sourceRptId);
    }

    public StringProperty getReporter_t() {
        return new SimpleStringProperty(username);
    }

    public StringProperty getLocation_t() {
        return new SimpleStringProperty(location);
    }

    public StringProperty getCondition_t() {
        return new SimpleStringProperty(condition.toString());
    }

    public StringProperty getVirusPPM_t() {
        return new SimpleStringProperty(virusPPM);
    }

    public StringProperty getContaminantPPM_t() {
        return new SimpleStringProperty(contaminantPPM);
    }

    private static String normalizeDT(final LocalDateTime ldt) {
        return "" + ldt.getMonthValue() + "/" + ldt.getDayOfMonth() + "/" + ldt.getYear();
    }
}
