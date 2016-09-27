package frontpage.controller;

import frontpage.FXMain;
import frontpage.bind.auth.UserAuthenticationException;
import frontpage.bind.auth.UserAuthenticator;
import frontpage.model.User;
import frontpage.utils.DialogueUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by George on 9/22/2016.
 *
 * @author George
 * @author willstuckey
 */
public class LoginScreenController {
    private static final String VIEW_URI =
            File.separator + "frontpage"
                    + File.separator + "view"
                    + File.separator + "LoginScreen.fxml";

    private static Logger logger;
    private static Parent root;
    private static LoginScreenController loginController;

    static {
        logger = Logger.getLogger(LoginScreenController.class.getName());
    }

    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            loginController = new LoginScreenController();
            loader.setController(loginController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() {
        return root;
    }

    public static LoginScreenController getLoginController() {
        return loginController;
    }

    @FXML private Button LogInBtn;
    @FXML private TextField UNField;
    @FXML private PasswordField PwdField;

    private LoginScreenController () {

    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void handleLoginAction() {
        logger.trace("Invoke -> LogInBtn::handleLoginAction()");
        UserAuthenticator ua = FXMain.getBackend().getUserAuthenticator();
        try {
            ua.authenticateUser(UNField.getText(), PwdField.getText().toCharArray());
            FXMain.setUser(new User(UNField.getText(), PwdField.getText()));
            ((MainScreenController) FXMain.getController("main")).setUserLabel(FXMain.getUser().getUsername());
            UNField.clear();
            FXMain.setView("main");
        } catch (UserAuthenticationException e) {
            DialogueUtils.showMessage("Invalid Login Credentials");
        } finally {
            PwdField.clear();
        }
    }

    @FXML
    private void handleCancelAction() {
        FXMain.setView("welcome");
    }

}
