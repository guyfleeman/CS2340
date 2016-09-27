package frontpage.controller;

import frontpage.FXMain;
import frontpage.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;

import java.io.File;

/**
 *
 */
public class WelcomeScreenController {
    private static final String VIEW_URI =
            File.separator + "frontpage"
                    + File.separator + "view"
                    + File.separator + "WelcomeScreen.fxml";

    private static Logger logger;
    private static Parent root;
    private static WelcomeScreenController welcomeController;

    static {
        logger = Logger.getLogger(MainScreenController.class.getName());
    }

    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            welcomeController = new WelcomeScreenController();
            loader.setController(welcomeController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() {
        return root;
    }

    public static WelcomeScreenController getWelcomeController() {
        return welcomeController;
    }

    @FXML private Button logInBtn;

    private WelcomeScreenController () {

    }

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleLoginAction() {
        logger.trace("Invoke -> LogInBtn::handleLoginAction()");
        FXMain.setView("login");
    }

}
