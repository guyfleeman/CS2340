package frontpage.controller;

import frontpage.FXMain;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.PurityReportManager;
import frontpage.bind.report.SourceReportManager;
import frontpage.model.report.PurityCondition;
import frontpage.model.report.PurityReport;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;

import java.util.LinkedList;

/**
 * @author willstuckey
 * <p></p>
 */
public class CreatePurityReportController implements Updatable {
    private static final String VIEW_URI = "/frontpage/view/CreatePurityReportScreen.fxml";

    private static Logger logger;
    private static Parent root;
    private static CreatePurityReportController loginController;

    static {
        logger = Logger.getLogger(LoginScreenController.class.getName());
    }

    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            loginController = new CreatePurityReportController();
            loader.setController(loginController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() {
        return root;
    }

    public static CreatePurityReportController getCreateSourceReportController() {
        return loginController;
    }

    private PurityReport activeReport;
    @FXML private TextField reportID;
    @FXML private TextField submitter;
    @FXML private TextField date;
    @FXML private TextArea loc;
    @FXML private ComboBox<PurityCondition> condition;
    @FXML private TextField virusPPM;
    @FXML private TextField contaminantPPM;

    @FXML
    public void initialize() {
        condition.setItems(FXCollections.observableList(new LinkedList<PurityCondition>(){{
            for (PurityCondition pc : PurityCondition.values())
                add(pc);
        }}));

        reportID.setDisable(true);
        submitter.setDisable(true);
        date.setDisable(true);
        condition.setValue(PurityCondition.UNAVAILABLE);

        //TODO: find solution for encoding new lines
        loc.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                event.consume();
            }
        });
    }

    public boolean update() {
        PurityReportManager pm = FXMain.getBackend().getPurityReportManager();
        activeReport = null;
        try {
            activeReport = PurityReport.createReport(pm, FXMain.getUser());
            activeReport.populateFromBackend(pm);
        } catch (BackendRequestException e) {
            DialogueUtils.showMessage("could not create report template");
            if (activeReport != null) {
                pm.__deletePurityReport_fs_na(FXMain.getUser().getEmail(),
                        FXMain.getUser().getTok(),
                        activeReport.getId());
            }
            return false;
        } catch (Exception e) {
            DialogueUtils.showMessage("internal error on update view call for create source report controller" +
                    " (type: " + e.getClass()
                    + "message: " + e.getMessage() + ")");
            e.printStackTrace();
            if (activeReport != null) {
                pm.__deletePurityReport_fs_na(FXMain.getUser().getEmail(),
                        FXMain.getUser().getTok(),
                        activeReport.getId());
            }
            return false;
        }

        try {
            System.out.println("-------------------------------------------");
            System.out.println(activeReport);
            reportID.setText(activeReport.getId());
            submitter.setText(activeReport.getUsername());
            loc.setText(activeReport.getLocation());
            PurityCondition pc = activeReport.getCondition();
            if (pc != null) {
                condition.setValue(pc);
            }
            date.setText(activeReport.getNormalizedDatetime());
            virusPPM.setText(activeReport.getVirusPPM());
            contaminantPPM.setText(activeReport.getContaminantPPM());
        } catch (Exception e) {
            DialogueUtils.showMessage(e.getClass() + ", " + e.getMessage() + ", " + e.getCause());
            e.printStackTrace();
        }
        return true;
    }

    @FXML
    public void handleCancelAction() {
        SourceReportManager rm = FXMain.getBackend().getSourceReportManager();
        try {
            activeReport.deleteFromBackend(rm, FXMain.getUser());
        } catch (Exception e) {
            logger.info("failed to clean up after cancel action");
        }
        FXMain.setView("main");
    }

    @FXML
    public void handleSubmitAction() {
        String loc = this.loc.getText();
        if (valid(loc)) {
            activeReport.setLocation(loc);
        } else {
            DialogueUtils.showMessage("Location must be filled.");
        }

        activeReport.setCondition(condition.getValue());

        String virusPPMStr = virusPPM.getText();
        if (isInt(virusPPMStr)) {
            int num = Integer.parseInt(virusPPMStr);
            if (bounded(num, 0, (int) 1e6)) {
                activeReport.setVirusPPM(Integer.toString(num));
            } else {
                DialogueUtils.showMessage("virus ppm must be between 0 and 1000000");
            }
        } else {
            DialogueUtils.showMessage("virus ppm must be a number");
        }

        String contaminantPPMStr = contaminantPPM.getText();
        if (isInt(contaminantPPMStr)) {
            int num = Integer.parseInt(contaminantPPMStr);
            if (bounded(num, 0, (int) 1e6)) {
                activeReport.setVirusPPM(Integer.toString(num));
            } else {
                DialogueUtils.showMessage("contaminant ppm must be between 0 and 1000000");
            }
        } else {
            DialogueUtils.showMessage("contaminant ppm must be a number");
        }

        SourceReportManager rm = FXMain.getBackend().getSourceReportManager();
        try {
            activeReport.writeToBackend(rm, FXMain.getUser());
        } catch (BackendRequestException e) {
            DialogueUtils.showMessage("failed to update report (problem: "
                    + e.getClass() + ", message: "
                    + e.getMessage() + ")");
        } catch (Exception e) {
            DialogueUtils.showMessage("internal exception in handleSubmitReport " +
                    "action handler (problem: "
                    + e.getClass() + ", message: "
                    + e.getMessage() + ")");
        }
        FXMain.setView("main");
    }

    private static boolean valid(final String dat) {
        return dat != null && dat.length() > 1;
    }

    private static boolean isInt(final String dat) {
        try {
            Integer.parseInt(dat);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static boolean bounded(int num, int lo, int hi) {
        return (num >= lo && num <= hi);
    }
}
