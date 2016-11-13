package frontpage.model.report;

import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.PurityReportManager;
import frontpage.model.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author George Tang
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
public class PurityReport {
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(PurityReport.class.getName());
    }

    private String id;
    private String sourceRptId;
    private String submitter;
    private LocalDateTime datetime = LocalDateTime.now();
    private String location;
    private PurityCondition condition = PurityCondition.UNAVAILABLE;
    private String virusPPM;
    private String contaminantPPM;

    /**
     * creates a report with no data
     */
    public PurityReport() { }

    /**
     * creates a report from a data map
     * @param ldf data map
     */
    public PurityReport(final Map<String, String> ldf) {
        loadFromMap(ldf);
    }

    /**
     * creates an empty report on the backend
     * @param rm report manager
     * @param auth user for auth
     * @return new report
     * @throws BackendRequestException if things go wrong
     */
    public static PurityReport createReport(final PurityReportManager rm,
                                            final User auth)
            throws BackendRequestException {
        final PurityReport ret = new PurityReport();
        ret.id = rm.addPurityReport(auth.getEmail(), auth.getTok());
        ret.submitter = auth.getUsername();
        return ret;
    }

    /**
     * populates report from the backend
     * @param rm report manger
     * @throws BackendRequestException if things go wrong
     */
    public void populateFromBackend(final PurityReportManager rm)
            throws BackendRequestException {
        Map<String, String> res = rm.getPurityReport(id);
        loadFromMap(res);
    }

    /**
     * loads the report from a data map
     * @param map data map
     */
    private void loadFromMap(final Map<String, String> map) {
        id = map.get("reportid");
        sourceRptId = map.get("sourcerptid");
        String dt = map.get("reportdt");
        if ((dt != null) && (dt.length() > 0)) {
            datetime = LocalDateTime.parse(dt.replace(' ', 'T'));
        }
        location = map.get("location");
        submitter = map.get("username");

        String type = map.get("cond");
        if ((type != null) && (type.length() > 0)) {
            try {
                this.condition = PurityCondition.valueOf(type);
            } catch (Exception e) {
                LOGGER.warn("could not parse value for WaterType: " + type, e);
            }
        }

        virusPPM = map.get("virusppm");
        contaminantPPM = map.get("contaminantppm");
    }

    /**
     * writes the report to the backend
     * @param rm report manager
     * @param auth user for auth
     * @throws BackendRequestException if things go wrong
     */
    public void writeToBackend(final PurityReportManager rm,
                               final User auth)
            throws BackendRequestException {
        Map<String, String> attribs = new HashMap<>();
        attribs.put("sourceid", sourceRptId);
        attribs.put("reportdt", datetime.toString());
        attribs.put("location", location);
        attribs.put("cond", condition.toString());
        attribs.put("virusppm", virusPPM);
        attribs.put("contaminantppm", contaminantPPM);
        rm.updatePurityReport(auth.getEmail(),
                auth.getTok(),
                id,
                attribs);
    }

    /**
     * deletes the report from the backend
     * @param rm report manager
     * @param auth user for auth
     * @throws BackendRequestException if things go wrong
     */
    public void deleteFromBackend(final PurityReportManager rm,
                                  final User auth)
            throws BackendRequestException {
        rm.deletePurityReport(auth.getEmail(),
                auth.getTok(),
                id);
    }

    /**
     * to string
     * @return string
     */
    public String toString() {
        String ret = "";
        ret += "id: " + id + "\r\n";
        ret += "location: " + location + "\r\n";
        ret += "submitter: " + submitter + "\r\n";
        ret += "condition: " + condition.toString() + "\r\n";
        ret += "date: " + datetime.toString() + "\r\n";
        ret += "virus ppm: " + virusPPM + "\r\n";
        ret += "contaminant ppm: " + virusPPM + "\r\n";
        ret += "\r\n";
        return ret;
    }

    /**
     * gets the report id
     * @return report id
     */
    public String getId() {
        return id;
    }

    /**
     * sets the report id
     * @param id report id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * gets the bound source report id
     * @return source report id
     */
    public String getSourceRptId() {
        return sourceRptId;
    }

    /**
     * sets the bound source report id
     * @param sourceRptId source report id
     */
    public void setSourceRptId(final String sourceRptId) {
        this.sourceRptId = sourceRptId;
    }

    /**
     * gets the submitter of the report
     * @return submitter
     */
    public String getSubmitter() {
        return submitter;
    }

    /**
     * sets the submitter of the report
     * @param submitter submitter
     */
    public void setSubmitter(final String submitter) {
        this.submitter = submitter;
    }

    /**
     * gets the datetime of report creation
     * @return datetime
     */
    public LocalDateTime getDatetime() {
        return datetime;
    }

    /**
     * gets a human readable string of the report creation date
     * @return date
     */
    public String getNormalizedDatetime() {
        return normalizeDT(datetime);
    }

    /**
     * sets the datetime of report creation
     * @param datetime datetime
     */
    public void setDatetime(final LocalDateTime datetime) {
        this.datetime = datetime;
    }

    /**
     * get location of report
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location of the report
     * @param location location
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * gets the water condition
     * @return water condition
     */
    public PurityCondition getCondition() {
        return condition;
    }

    /**
     * sets water condition
     * @param condition condition
     */
    public void setCondition(final PurityCondition condition) {
        this.condition = condition;
    }

    /**
     * gets virus parts per million
     * @return virus PPM
     */
    public String getVirusPPM() {
        return virusPPM;
    }

    /**
     * sets virus parts per million
     * @param virusPPM virus PPM
     */
    public void setVirusPPM(final String virusPPM) {
        this.virusPPM = virusPPM;
    }

    /**
     * gets contaminant parts per million
     * @return contaminant PPM
     */
    public String getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * sets contaminant parts per million
     * @param contaminantPPM contaminant PPM
     */
    public void setContaminantPPM(final String contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * gets date that is JFX embeddable
     * @return date
     */
    public StringProperty getDateT() {
        return new SimpleStringProperty(normalizeDT(datetime));
    }

    /**
     * gets report id that is JFX embeddable
     * @return report id
     */
    public StringProperty getRptIdT() {
        return new SimpleStringProperty(id);
    }

    /**
     * gets reporter that is JFX embeddable
     * @return reporter
     */
    public StringProperty getReporterT() {
        return new SimpleStringProperty(submitter);
    }

    /**
     * gets location that is JFX embeddable
     * @return location
     */
    public StringProperty getLocationT() {
        return new SimpleStringProperty(location);
    }

    /**
     * gets condition that is JFX embeddable
     * @return condition
     */
    public StringProperty getConditionT() {
        return new SimpleStringProperty(condition.toString());
    }

    /**
     * gets virus PPM that is JFX embeddable
     * @return virus PPM
     */
    public StringProperty getVirusPPMT() {
        return new SimpleStringProperty(virusPPM);
    }

    /**
     * gets contaminant PPM that is JFX embeddable
     * @return contaminant PPM
     */
    public StringProperty getContaminantPPMT() {
        return new SimpleStringProperty(contaminantPPM);
    }

    private static String normalizeDT(final LocalDateTime ldt) {
        return ("" + ldt.getMonthValue()
                + "/" + ldt.getDayOfMonth()
                + "/" + ldt.getYear());
    }
}
