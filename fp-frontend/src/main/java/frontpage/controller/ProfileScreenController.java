package frontpage.controller;

import frontpage.FXMain;
import frontpage.model.UserProfile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

/**
 * Created by Devan on 10/1/2016.
 *
 * @author Devan
 * @author willstuckey
 */
public class ProfileScreenController implements Updatable {
    private static final String VIEW_URI = "/frontpage/view/ManageProfileScreen.fxml";

    private static Logger logger;
    private static Parent root;
    private static ProfileScreenController registerController;

    static {
        logger = Logger.getLogger(ProfileScreenController.class.getName());
    }


    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            registerController = new ProfileScreenController();
            loader.setController(registerController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() { return root; }
    public static ProfileScreenController getRegisterController() {return registerController; }

    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField stateField;
    @FXML private TextField zipField;
    @FXML private TextField titleField;
    @FXML private Button cancelBtn;
    @FXML private Button updateBtn;

    private ProfileScreenController() {

    }

    public void update() {
        addressField.clear();
        cityField.clear();
        stateField.clear();
        zipField.clear();
        titleField.clear();

        if (FXMain.getUser() != null) {
            FXMain.getUser().loadProfile();
            UserProfile up = FXMain.getUser().getUserProfile();
            addressField.setText(up.getAddress());
            cityField.setText(up.getCity());
            stateField.setText(up.getState());
            zipField.setText(up.getZip());
            titleField.setText(up.getTitle());
        }
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void handleCancelAction() {
        addressField.clear();
        cityField.clear();
        stateField.clear();
        zipField.clear();
        titleField.clear();
        FXMain.setView("main");
    }

    @FXML
    private void handleUpdateAction() {
        System.out.println("here");
        UserProfile up = FXMain.getUser().getUserProfile();
        up.setAddress(addressField.getText());
        up.setCity(cityField.getText());
        up.setState(stateField.getText());
        up.setZip(zipField.getText());
        up.setTitle(titleField.getText());
        FXMain.getUser().storeProfile();
    }

}