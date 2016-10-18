package frontpage.controller;

import frontpage.FXMain;
import frontpage.utils.DialogueUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;

/**
 * main screen controller
 */
public class MainScreenController {
    private static final String VIEW_URI = "/frontpage/view/MainScreen.fxml";

    private static Logger logger;
    private static Parent root;
    private static MainScreenController mainController;

    static {
        logger = Logger.getLogger(MainScreenController.class.getName());
    }

    /**
     * creates the view/controller
     */
    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            mainController = new MainScreenController();
            loader.setController(mainController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() {
        return root;
    }

    public static MainScreenController getMainController() {
        return mainController;
    }

    @FXML private Label userLabel;
    @FXML private Button profileBtn;
    @FXML private Button logOutBtn;
    @FXML private Button createSourceReport;
    @FXML private Button viewSourceReports;

    private MainScreenController() {

    }

    public void setUserLabel(final String text) {
        userLabel.setText("Logged In As: " + text);
    }

    @FXML
    public void initialize() {
        userLabel.setText(null);
    }

    /**
     * handles profile switch
     */
    @FXML
    private void handleProfileAction() {
        FXMain.setView("profile");
    }

    /**
     * Handles source report submission
     */
    @FXML
    private void handleSourceReportAction() {
        FXMain.setView("Submit source report");
    }

    /**
     * Handles purity report submission
     */
    @FXML
    private void handlePurityReportAction() {
        FXMain.setView("Submit purity report");
    }



    /**
     * handles logout
     */
    @FXML
    private void handleLogoutAction() {
        logger.trace("Invoke -> LogInBtn::handleLoginAction()");
        FXMain.setUser(null);
        userLabel.setText(null);
        DialogueUtils.showMessage("You have been logged out.");
        FXMain.setView("welcome");
    }

    @FXML
    private void handleCreateSourceReportSwitch() {
        FXMain.setView("createsourcerpt");
    }

    @FXML
    private void handleViewSourceReportSwitch() {

    }
}
