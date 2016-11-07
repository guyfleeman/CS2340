package frontpage.controller;

import frontpage.FXMain;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.PurityReportManager;
import frontpage.model.report.PurityReport;
import frontpage.utils.DialogueUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.apache.log4j.Logger;
import javafx.scene.control.TableColumn;
import java.util.Map;


/**
 * @author Devan
 * <p></p>
 */
@SuppressWarnings("unused")
public class ViewPurityReportScreenController implements Updatable {
    private static final String VIEW_URI = "/frontpage/view/ViewWaterPurityReports.fxml";

    private static final Logger logger;
    private static Parent root;
    private static ViewPurityReportScreenController viewReportsController;

    static {
        logger = Logger.getLogger(ViewPurityReportScreenController.class.getName());
    }


    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            viewReportsController = new ViewPurityReportScreenController();
            loader.setController(viewReportsController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() { return root; }
    public static ViewPurityReportScreenController getViewReportController() {return viewReportsController; }


    private final ObservableList<PurityReport> reports = FXCollections.observableArrayList();
    @FXML private TableView<PurityReport> viewReportsTable;
    @FXML private TableColumn<PurityReport, String> dateTimeCol;
    @FXML private TableColumn<PurityReport, String> reportNumCol;
    @FXML private TableColumn<PurityReport, String> reporterCol;
    @FXML private TableColumn<PurityReport, String> locationCol;
    @FXML private TableColumn<PurityReport, String> conditionCol;
    @FXML private TableColumn<PurityReport, String> virusPPMCol;
    @FXML private TableColumn<PurityReport, String> contaminantPPMCol;
    @FXML private Button ViewReportsReturnBtn;

    private ViewPurityReportScreenController() {

    }

    @FXML
    private void initialize() {
        dateTimeCol.setCellValueFactory(cellData -> cellData.getValue().getDate_t());
        reportNumCol.setCellValueFactory(cellData -> cellData.getValue().getRptId_t());
        reporterCol.setCellValueFactory(cellData -> cellData.getValue().getReporter_t());
        locationCol.setCellValueFactory(cellData -> cellData.getValue().getLocation_t());
        conditionCol.setCellValueFactory(cellData -> cellData.getValue().getCondition_t());
        virusPPMCol.setCellValueFactory(cellData -> cellData.getValue().getVirusPPM_t());
        contaminantPPMCol.setCellValueFactory(cellData -> cellData.getValue().getContaminantPPM_t());
    }

    @Override
    public boolean update() {
        PurityReportManager rm = FXMain.getBackend().getPurityReportManager();
        try {
            Map<String, String>[] reportsData = rm.getPurityReports(0);
            for (Map<String, String> reportData : reportsData) {
                if (reportData.containsKey("index")) {
                    reports.add(new PurityReport(reportData));
                }
            }

            viewReportsTable.setItems(reports);
            return true;
        } catch (BackendRequestException e) {
            DialogueUtils.showMessage("view report bre");
        } catch (Exception e) {
            DialogueUtils.showMessage("view report exception (type: " + e.getClass()
                    + ", message: " + e.getMessage()
                    + ", cause: " + e.getCause());
        }

        return false;
    }

    @FXML
    private void handleReturnAction() {
        reports.clear();
        FXMain.setView("main");
    }
}
