package frontpage.controller;

import frontpage.FXMain;

import frontpage.bind.auth.FailedToCreateUserException;
import frontpage.bind.auth.InvalidDataException;
import frontpage.bind.auth.UserManager;
import frontpage.utils.DialogueUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import javafx.scene.control.ComboBox;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.LinkedList;

/**
 * Created by Devan on 10/1/2016.
 *
 * @author Devan
 * @author willstuckey
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
    @FXML private Button registerCancelBtn;
    @FXML private TextField registerEmailField;
    @FXML private TextField registerUNField;
    @FXML private PasswordField registerPwdField;
    @FXML private PasswordField confirmRegisterPwdField;
    @FXML private ComboBox userTypeBox;

    private RegisterScreenController () {

    }


    /**
     * initialize requires body for combo box
     */
    @FXML
    @SuppressWarnings("unchecked")
    public void initialize() {
        userTypeBox.setItems(FXCollections.observableList(new LinkedList<String>(){{
            add("USER");
            add("WORKER");
            add("MANAGER");
            add("ADMIN");
        }}));
    }

    @FXML
    private void handleCancelAction() {
        registerEmailField.clear();
        registerUNField.clear();
        clearPasswordFields();
        FXMain.setView("welcome");
    }

    /**
     * handles form validation and processing delgation for create user
     *
     * TODO at some point this should be threaded
     */
    @FXML
    private void handleConfirmAction() {
        String email = registerEmailField.getText();
        String username = registerUNField.getText();
        String password = registerPwdField.getText();
        String confirmPassword = confirmRegisterPwdField.getText();
        String type = (String) userTypeBox.getValue();
        if (email == null || email.length() == 0) {
            DialogueUtils.showMessage("Email field must be filled.");
            clearPasswordFields();
            return;
        }

        if (username == null || username.length() == 0) {
            DialogueUtils.showMessage("Username field must be filled.");
            clearPasswordFields();
            return;
        }

        if (type == null || type.length() == 0) {
            DialogueUtils.showMessage("Type must be selected.");
            clearPasswordFields();
            return;
        }

        if (password.equals(confirmPassword)) {
            UserManager um = FXMain.getBackend().getUserManager();
            try {
                um.createUser(username, password, email, "none", "none", type);
                DialogueUtils.showMessage("Account created successfully.");
                registerEmailField.clear();
                registerUNField.clear();
                clearPasswordFields();
                FXMain.setView("welcome");
                return;
            } catch (InvalidDataException e) {
                DialogueUtils.showMessage("Account creation failed: " + e.getMessage());
            } catch (FailedToCreateUserException e) {
                DialogueUtils.showMessage("Account creation failed: " + e.getMessage());
            }
        } else {
            DialogueUtils.showMessage("Passwords do not match.");
        }

        clearPasswordFields();
    }

    /**
     * helper method to clear passwords fields
     */
    private void clearPasswordFields() {
        registerPwdField.clear();
        confirmRegisterPwdField.clear();
    }
}