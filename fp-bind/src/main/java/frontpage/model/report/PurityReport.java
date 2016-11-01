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
 * Created by George on 10/31/2016.
 */
public class PurityReport {
    private static Logger logger;

    static {
        logger = Logger.getLogger(PurityReport.class);
    }

    private LocalDateTime reportTime = LocalDateTime.now();
    private String title;
    private String description;
    private String reportid;
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty loc = new SimpleStringProperty();
    private WaterEvaluation eval = WaterEvaluation.UNAVAILABLE;
    private String virusPPM;
    private String contaminantPPM;


    public PurityReport() {}

    public PurityReport(final Map<String, String> ldf) {
        loadFromMap(ldf);
    }



    public static PurityReport createReport(final ReportManager rm,
                                            final User auth)
            throws BackendRequestException {
        final PurityReport ret = new PurityReport();
        ret.reportid = rm.addPurityReport(auth.getEmail(), auth.getTok());
        ret.username.setValue(auth.getUsername());
        return ret;
    }

    public void populateFromBackend(final ReportManager rm)
            throws BackendRequestException {
        Map<String, String> res = rm.getPurityReport(reportid);
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
        virusPPM = map.get("virusPPM");
        contaminantPPM = map.get("contaminantPPM");

        String eval = map.get("eval");
        if (eval != null && eval.length() > 0) {
            try {
                this.eval = WaterEvaluation.valueOf(eval);
            } catch (Exception e) {
                logger.warn("could not parse value for WaterEvaluation: " + eval, e);
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
        attribs.put("eval", eval.toString());
        attribs.put("name", title);
        attribs.put("description", description);
        attribs.put("virusPPM", virusPPM);
        attribs.put("contaminantPPM", contaminantPPM);
        rm.updatePurityReport(auth.getEmail(),
                auth.getTok(),
                reportid,
                attribs);
    }

    public void deleteFromBackend(final ReportManager rm,
                                  final User auth)
            throws BackendRequestException {
        rm.deletePurityReport(auth.getEmail(),
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
        ret += "virus PPM: " + virusPPM + "\r\n";
        ret += "contaminant PPM: " + contaminantPPM + "\r\n";
        ret += "evaluation: " + eval.toString() + "\r\n";
        ret += "date: " + reportTime.toString() + "\r\n";
        ret += "\r\n";
        return ret;
    }

    public LocalDateTime getReportTime() {return reportTime;}
    public String getReportid() {return reportid;}
    public String getUsername() {return username.getValue();}
    public String getLoc() {return loc.getValue();}
    public WaterEvaluation getEval() {return eval;}
    public void setEval(WaterEvaluation eval) {this.eval = eval;}
    public String getVirusPPM() {return virusPPM;}
    public void setVirusPPM(String virus) {virusPPM = virus;}
    public String getContaminantPPM() {return contaminantPPM;}
    public void setContaminantPPM(String contam) {contaminantPPM = contam;}
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

    public StringProperty getReportTime_t() { return new SimpleStringProperty(normalizeDT(reportTime)); }
    public StringProperty getReportID_t() { return new SimpleStringProperty(reportid); }
    public StringProperty getUsername_t() { return new SimpleStringProperty(username.getValue()); }
    public StringProperty getLocation_t() { return new SimpleStringProperty(loc.getValue()); }
    public StringProperty getEval_t() { return new SimpleStringProperty(eval.toString()); }
    public StringProperty getVirusPPM_t() { return new SimpleStringProperty(virusPPM); }
    public StringProperty getContaminantPPM_t() { return new SimpleStringProperty(contaminantPPM); }


    private static String normalizeDT(final LocalDateTime ldt) {
        return "" + ldt.getMonthValue() + "/" + ldt.getDayOfMonth() + "/" + ldt.getYear();
    }

}
