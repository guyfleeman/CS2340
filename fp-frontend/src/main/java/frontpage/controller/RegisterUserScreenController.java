package frontpage.controller;

import frontpage.FXMain;
import frontpage.utils.DialogueUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.util.LinkedList;

/**
 *
 */
public class RegisterUserScreenController {
    private static final String VIEW_URI = "/frontpage/view/RegisterUserScreen.fxml";

    private static Logger logger;
    private static Parent root;
    private static RegisterUserScreenController mainController;

    static {
        logger = Logger.getLogger(MainScreenController.class.getName());
    }

    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            mainController = new RegisterUserScreenController();
            loader.setController(mainController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() {
        return root;
    }

    public static RegisterUserScreenController getMainController() {
        return mainController;
    }

    @FXML private TextField registerEmailField;
    @FXML private TextField registerUNField;
    @FXML private TextField registerPwdField;
    @FXML private TextField confirmRegisterPwdField;
    @FXML private ComboBox  userTypeBox;
    @FXML private Button registerCancelBtn;
    @FXML private Button registerConfirmBtn;

    private RegisterUserScreenController() {

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
        registerEmailField.clear();
        registerUNField.clear();
        registerPwdField.clear();
        confirmRegisterPwdField.clear();
        FXMain.setView("welcome");
    }

    @FXML
    private void handleConfirmAction() {
        FXMain.setView("welcome");
    }

}
