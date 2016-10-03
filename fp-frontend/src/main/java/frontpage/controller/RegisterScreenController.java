package frontpage.controller;

import frontpage.FXMain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import javafx.scene.control.ComboBox;
/**
 * Created by Devan on 10/1/2016.
 */
public class RegisterScreenController {
    private static final String VIEW_URI = "/frontpage/view/RegisterUserScreen.fxml";

    private static Logger logger;
    private static Parent root;
    private static RegisterScreenController registerController;

    static {
        logger = Logger.getLogger(RegisterScreenController.class.getName());
    }


    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            registerController = new RegisterScreenController();
            loader.setController(registerController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() { return root; }
    public static RegisterScreenController getRegisterController() {return registerController; }

    @FXML private Button registerConfirmBtn;
    @FXML private Button registerCancel;
    @FXML private TextField RegisterUNField;
    @FXML private PasswordField registerPwdField;
    @FXML private PasswordField confirmRegisterPwdField;
    @FXML private ComboBox userTypeBox;

    private RegisterScreenController () {

    }


    @FXML
    public void initialize() {

    }
    @FXML
    private void handleCancelAction() {;
    }

    @FXML
    private void handleRegisterAction() {
    }

}