package frontpage.controller;

import frontpage.FXMain;
import frontpage.utils.DialogueUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.util.LinkedList;

/**
 * Created by Kevin on 10/16/2016.
 *
 * @author Kevin
 */
public class SubmitWaterSourceReportController {
    private static final String VIEW_URI = "/frontpage/view/SubmitWaterSourceReport.fxml";

    private static Logger logger;
    private static Parent root;
    private static SubmitWaterSourceReportController sourceReportController;

    static {
        logger = Logger.getLogger(SubmitWaterSourceReportController.class.getName());
    }


    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            sourceReportController = new SubmitWaterSourceReportController();
            loader.setController(sourceReportController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("Failed to load view", e);
        }
    }

    public static Parent getRoot() { return root; }
    public static SubmitWaterSourceReportController getSourceReportController() {return sourceReportController; }

    @FXML private Button submitReportBtn;
    @FXML private Button cancelReportBtn;
    @FXML private TextField waterLocationField;
    @FXML private ComboBox waterTypeBox;
    @FXML private ComboBox waterConditionBox;

    private SubmitWaterSourceReportController() {
    }

    /**
     * Initialize Combo box options for waterConditionBox and waterTypeBox
     */
    @FXML
    @SuppressWarnings("unchecked")
    public void initialize() {
        waterConditionBox.setItems(FXCollections.observableList(new LinkedList<String>(){{
            add("Potable");
            add("Treatable-Muddy");
            add("Treatable-Clear");
            add("Waste");
        }}));
    }

    /*
     * Handles cancelReport action
     */
    @FXML
    private void handleCancelAction() {
        waterLocationField.clear();
        FXMain.setView("Welcome back!");
    }

    /**
     * Data validation for water report submission
     *
     */
    @FXML
    private void handleConfirmAction() {
        String waterLocation = waterLocationField.getText();
        String waterType = (String) waterTypeBox.getValue();
        String waterCondition = (String) waterConditionBox.getValue();
        if (waterLocation == null || waterLocation.length() == 0) {
            DialogueUtils.showMessage("The water location field must be filled.");
            waterLocationField.requestFocus();
            return;
        }
            try {
                //@TODO initialize source report according to the user submitting it
                DialogueUtils.showMessage("Source report submitted successfully.");
                waterLocationField.clear();
                FXMain.setView("Welcome back!");
                return;
            }
            //@TODO catch report submission failure exception
            catch (Throwable e) {
                DialogueUtils.showMessage("A runtime error has occurred when submitting report.");
            }
        }
    }