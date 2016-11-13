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
public final class ViewPurityReportScreenController implements Updatable {
    private static final String VIEW_URI =
            "/frontpage/view/ViewWaterPurityReports.fxml";

    private static final Logger LOGGER;
    private static Parent root;
    private static ViewPurityReportScreenController viewReportsController;

    static {
        LOGGER = Logger.getLogger(
                ViewPurityReportScreenController.class.getName());
    }

    /**
     * creates an instance of the controller and accompanying view
     */
    public static void create() {
        try {
            LOGGER.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(
                    FXMain.class.getResource(VIEW_URI));
            viewReportsController = new ViewPurityReportScreenController();
            loader.setController(viewReportsController);
            root = loader.load();
        } catch (Exception e) {
            LOGGER.error("failed to load view", e);
        }
    }

    /**
     * gets the root node of the view
     * @return root node
     */
    public static Parent getRoot() {
        return root;
    }

    /**
     * gets controller
     * @return controller
     */
    public static ViewPurityReportScreenController getViewReportController() {
        return viewReportsController;
    }

    private final ObservableList<PurityReport> reports =
            FXCollections.observableArrayList();
    @FXML private TableView<PurityReport> viewReportsTable;
    @FXML private TableColumn<PurityReport, String> dateTimeCol;
    @FXML private TableColumn<PurityReport, String> reportNumCol;
    @FXML private TableColumn<PurityReport, String> reporterCol;
    @FXML private TableColumn<PurityReport, String> locationCol;
    @FXML private TableColumn<PurityReport, String> conditionCol;
    @FXML private TableColumn<PurityReport, String> virusPPMCol;
    @FXML private TableColumn<PurityReport, String> contaminantPPMCol;
    @FXML private Button viewReportsReturnBtn;

    private ViewPurityReportScreenController() {

    }

    /**
     * FXML initialization routine
     */
    @FXML
    private void initialize() {
        dateTimeCol.setCellValueFactory(cellData
                -> cellData.getValue().getDateT());
        reportNumCol.setCellValueFactory(cellData
                -> cellData.getValue().getRptIdT());
        reporterCol.setCellValueFactory(cellData
                -> cellData.getValue().getReporterT());
        locationCol.setCellValueFactory(cellData
                -> cellData.getValue().getLocationT());
        conditionCol.setCellValueFactory(cellData
                -> cellData.getValue().getConditionT());
        virusPPMCol.setCellValueFactory(cellData
                -> cellData.getValue().getVirusPPMT());
        contaminantPPMCol.setCellValueFactory(cellData
                -> cellData.getValue().getContaminantPPMT());
    }

    /**
     * update call after view switch
     * @return success
     */
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
            DialogueUtils.showMessage("view report exception (type: "
                    + e.getClass()
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
