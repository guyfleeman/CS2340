package frontpage.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDateTime;

/**
 * Created by George on 10/15/2016.
 */
public class SourceReport {

    private final LocalDateTime reportTime;
    private static int numberOfReports = 0;
    private final int reportNumber;
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty loc = new SimpleStringProperty();
    private final WaterType type;
    private WaterCondition condition;

    public SourceReport(String user, String location, WaterType type, WaterCondition condition) {

        reportTime = LocalDateTime.now();
        reportNumber = ++numberOfReports;
        username.set(user);
        loc.set(location);
        this.type = type;
        this.condition = condition;
    }

    public LocalDateTime getReportTime() {return reportTime;}
    public int getReportNumber() {return reportNumber;}
    public String getUsername() {return username.getValue();}

    public String getLoc() {return loc.getValue();}
    public WaterType getType() {return type;}
    public WaterCondition getCondition() {return condition;}
    public void setCondition(WaterCondition cond) {condition = cond;}



}
