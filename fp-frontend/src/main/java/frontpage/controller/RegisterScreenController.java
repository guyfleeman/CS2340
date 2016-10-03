package frontpage.controller;

import frontpage.FXMain;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import javafx.scene.control.ComboBox;

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
        System.out.println("im here");
        registerEmailField.clear();
        registerUNField.clear();
        registerPwdField.clear();
        confirmRegisterPwdField.clear();
        FXMain.setView("welcome");
    }

    @FXML
    private void handleConfirmAction() {
    }

}