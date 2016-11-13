package frontpage.controller;

import frontpage.FXMain;
import frontpage.model.user.User;
import frontpage.model.user.UserClass;
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
@SuppressWarnings("unused")
public final class MainScreenController implements  Updatable {
    private static final String VIEW_URI =
            "/frontpage/view/MainScreen.fxml";

    private static final Logger LOGGER;
    private static Parent root;
    private static MainScreenController mainController;

    static {
        LOGGER = Logger.getLogger(
                MainScreenController.class.getName());
    }

    /**
     * creates the view/controller
     */
    public static void create() {
        try {
            LOGGER.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(
                    FXMain.class.getResource(VIEW_URI));
            mainController = new MainScreenController();
            loader.setController(mainController);
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
     * gets the main controller
     * @return controller
     */
    public static MainScreenController getMainController() {
        return mainController;
    }

    @FXML private Label userLabel;
    @FXML private Button profileBtn;
    @FXML private Button logOutBtn;
    @FXML private Button createSourceReport;
    @FXML private Button viewSourceReports;
    @FXML private Button createPurityReport;
    @FXML private Button viewPurityReports;
    @FXML private Button purityGraphBtn;
    @FXML private Button mapBtn;

    private MainScreenController() {

    }

    private void setUserLabel(final String text) {
        userLabel.setText("Logged In As: " + text);
    }

    /**
     * FXML initialization routine
     */
    @FXML
    public void initialize() {
        userLabel.setText(null);
        createPurityReport.setDisable(true);
        viewPurityReports.setDisable(true);
    }

    /**
     * change view update call
     * @return success
     */
    public boolean update() {
        User u = FXMain.getUser();
        setUserLabel(u.getEmail() + " [" + u.getUserClass() + "]");
        createPurityReport.setDisable(!(u.getUserClass() == UserClass.WORKER
                || u.getUserClass() == UserClass.MANAGER
                || u.getUserClass() == UserClass.ADMIN));
        viewPurityReports.setDisable(!(u.getUserClass() == UserClass.MANAGER
                || u.getUserClass() == UserClass.ADMIN));
        purityGraphBtn.setDisable(!(u.getUserClass() == UserClass.MANAGER
                || u.getUserClass() == UserClass.ADMIN));
        return true;
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

    @FXML
    private void handleMapAction() {
        FXMain.setView("map");
    }

    @FXML
    private void handleShowPurityGraphAction() {
        FXMain.setView("puritygraph");
    }

    /**
     * handles logout
     */
    @FXML
    private void handleLogoutAction() {
        LOGGER.trace("Invoke -> LogInBtn::handleLoginAction()");
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
        FXMain.setView("viewsourcerpts");
    }

    @FXML
    private void handleCreatePurityReportAction() {
        FXMain.setView("createpurityrpt");
    }

    @FXML
    private void handleViewPurityReportsAction() {
        FXMain.setView("viewpurityrpts");
    }
}
