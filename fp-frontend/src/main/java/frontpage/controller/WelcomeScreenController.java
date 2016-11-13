package frontpage.controller;

import frontpage.FXMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

/**
 * @author willstuckey
 *
 * <p>Controller for the Welcome Screen View</p>
 */
@SuppressWarnings({"unused", "CyclicClassDependency"})
public final class WelcomeScreenController {
    private static final String VIEW_URI = "/frontpage/view/WelcomeScreen.fxml";

    private static final Logger LOGGER;
    private static Parent root;
    private static WelcomeScreenController welcomeController;

    static {
        LOGGER = Logger.getLogger(WelcomeScreenController.class.getName());
    }

    /**
     * creates an instance of the controller and accompanying view
     */
    public static void create() {
        try {
            LOGGER.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(
                    FXMain.class.getResource(VIEW_URI));
            welcomeController = new WelcomeScreenController();
            loader.setController(welcomeController);
            root = loader.load();
        } catch (Exception e) {
            LOGGER.error("failed to load view", e);
        }
    }

    /**
     * gets the root node
     * @return root node
     */
    public static Parent getRoot() {
        return root;
    }

    /**
     * gets the controller
     * @return controller
     */
    public static WelcomeScreenController getWelcomeController() {
        return welcomeController;
    }

    @FXML private Button logInBtn;

    private WelcomeScreenController() {

    }

    /**
     * initialization routine for view
     */
    @SuppressWarnings("EmptyMethod")
    @FXML
    public void initialize() {
    }

    @FXML
    private void handleLoginAction() {
        LOGGER.trace("Invoke -> LogInBtn::handleLoginAction()");
        FXMain.setView("login");
    }

}
