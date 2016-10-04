package frontpage.controller;

import frontpage.FXMain;
import frontpage.bind.auth.InvalidDataException;
import frontpage.bind.auth.UserAuthenticationException;
import frontpage.bind.auth.UserManager;
import frontpage.model.User;
import frontpage.model.UserClass;
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
public class LoginScreenController {
    private static final String VIEW_URI = "/frontpage/view/LoginScreen.fxml";

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
    @FXML private Button RegisterBtn;

    private LoginScreenController () {

    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void handleLoginAction() {
        logger.trace("Invoke -> LogInBtn::handleLoginAction()");
        UserManager ua = FXMain.getBackend().getUserManager();
        try {
            String sessionTok = ua.authenticateUser(UNField.getText(),
                    PwdField.getText());
            User u = new User(UNField.getText(), sessionTok);
            String type = ua.getUserType(u.getEmail(), u.getTok());
            u.setUserClass(UserClass.valueOf(type));
            FXMain.setUser(u);
            FXMain.getUser().loadProfile();
            ((MainScreenController) FXMain.getController("main"))
                    .setUserLabel(FXMain.getUser().getEmail() + "[" + u.getUserClass() + "]");
            UNField.clear();
            PwdField.clear();
            FXMain.setView("main");
        } catch (UserAuthenticationException | InvalidDataException e) {
            DialogueUtils.showMessage("Invalid Login Credentials");
            PwdField.clear();
        } catch (Throwable t) {
            DialogueUtils.showMessage("Unexpected Internal Error.\r\n\r\n" + t.getMessage());
        }
    }

    @FXML
    private void handleCancelAction() {
        FXMain.setView("welcome");
    }

    @FXML
    private void handleRegisterAction() {
        UNField.clear();
        PwdField.clear();
        FXMain.setView("register");
    }
}
