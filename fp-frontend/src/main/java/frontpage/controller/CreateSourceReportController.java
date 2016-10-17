package frontpage.controller;

import frontpage.FXMain;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.ReportManager;
import frontpage.model.report.SourceReport;
import frontpage.model.report.WaterCondition;
import frontpage.model.report.WaterType;
import frontpage.utils.DialogueUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.util.LinkedList;

/**
 * @author willstuckey
 * @date 10/17/16
 * <p></p>
 */
public class CreateSourceReportController implements Updatable {
    private static final String VIEW_URI = "/frontpage/view/CreateSourceReportScreen.fxml";

    private static Logger logger;
    private static Parent root;
    private static CreateSourceReportController loginController;

    static {
        logger = Logger.getLogger(LoginScreenController.class.getName());
    }

    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            loginController = new CreateSourceReportController();
            loader.setController(loginController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() {
        return root;
    }

    public static CreateSourceReportController getCreateSourceReportController() {
        return loginController;
    }

    private SourceReport activeReport;
    @FXML private TextField reportID;
    @FXML private TextField submitter;
    @FXML private TextField title;
    @FXML private TextArea location;
    @FXML private ComboBox<WaterType> type;
    @FXML private ComboBox<WaterCondition> condition;
    @FXML private TextField date;
    @FXML private TextArea description;

    @FXML
    public void initialize() {
        type.setItems(FXCollections.observableList(new LinkedList<WaterType>(){{
            for (WaterType wt : WaterType.values())
                add(wt);
        }}));

        condition.setItems(FXCollections.observableList(new LinkedList<WaterCondition>(){{
            for (WaterCondition wc : WaterCondition.values())
                add(wc);
        }}));

        reportID.setDisable(true);
        submitter.setDisable(true);
    }

    public void update() {
        ReportManager rm = FXMain.getBackend().getReportManager();
        try {
            activeReport = SourceReport.createReport(rm, FXMain.getUser());
            activeReport.populateFromBackend(rm);
        } catch (BackendRequestException e) {
            DialogueUtils.showMessage("could not create report template");
        }
    }

    @FXML
    public void handleCancelAction() {
        // delete functionality missing
    }

    @FXML
    public void handleSubmitAction() {

    }
}
