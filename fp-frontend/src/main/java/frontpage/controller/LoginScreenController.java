package frontpage.controller;

import frontpage.FXMain;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.errorhandling.AuthenticationException;
import frontpage.bind.user.UserManager;
import frontpage.model.user.User;
import frontpage.model.user.UserClass;
import frontpage.utils.DialogueUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

/**
 * Created by George on 9/22/2016.
 *
 * @author George
 * @author willstuckey
 */
@SuppressWarnings("unused")
public final class LoginScreenController {
    private static final String VIEW_URI =
            "/frontpage/view/LoginScreen.fxml";

    private static final Logger LOGGER;
    private static Parent root;
    private static LoginScreenController loginController;

    static {
        LOGGER = Logger.getLogger(LoginScreenController.class.getName());
    }

    /**
     * creates an instance of the controller and its bound view
     */
    public static void create() {
        try {
            LOGGER.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(
                    FXMain.class.getResource(VIEW_URI));
            loginController = new LoginScreenController();
            loader.setController(loginController);
            root = loader.load();
        } catch (Exception e) {
            LOGGER.error("failed to load view", e);
        }
    }

    /**
     * gets the root node of the bound view
     * @return root node
     */
    public static Parent getRoot() {
        return root;
    }

    /**
     * gets the controller
     * @return controller
     */
    public static LoginScreenController getLoginController() {
        return loginController;
    }

    @FXML private Button logInBtn;
    @FXML private TextField unField;
    @FXML private PasswordField pwdField;
    @FXML private Button registerBtn;

    private LoginScreenController() {

    }

    /**
     * FXML initialization routine
     */
    @SuppressWarnings("EmptyMethod")
    @FXML
    public void initialize() {

    }

    @FXML
    private void handleLoginAction() {
        LOGGER.trace("Invoke -> LogInBtn::handleLoginAction()");
        UserManager ua = FXMain.getBackend().getUserManager();
        try {
            String sessionTok = ua.authenticateUser(unField.getText(),
                    pwdField.getText());
            User u = new User(unField.getText(), sessionTok);
            String type = ua.getUserType(u.getEmail(), u.getTok());
            u.setUserClass(UserClass.valueOf(type));
            FXMain.setUser(u);
            FXMain.getUser().loadProfile();
            unField.clear();
            pwdField.clear();
            FXMain.setView("main");
        } catch (AuthenticationException e) {
            DialogueUtils.showMessage("The provided credentials were "
                    + "invalid: " + e.getMessage());
            pwdField.clear();
        } catch (BackendRequestException e) {
            DialogueUtils.showMessage("A error occurred on the backend: "
                    + e.getMessage());
            pwdField.clear();
        } catch (Throwable t) {
            DialogueUtils.showMessage("A runtime error has occurred in the "
                    + "handleLoginAction() method");
            t.printStackTrace();
        }
    }

    @FXML
    private void handleCancelAction() {
        FXMain.setView("welcome");
    }

    @FXML
    private void handleRegisterAction() {
        unField.clear();
        pwdField.clear();
        FXMain.setView("register");
    }
}
