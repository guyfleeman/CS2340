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
public class WaterPurityReportController {
    private static final String VIEW_URI = "/frontpage/view/SubmitWaterPurityReport.fxml";

    private static Logger logger;
    private static Parent root;
    private static WaterPurityReportController purityReportController;

    static {
        logger = Logger.getLogger(WaterPurityReportController.class.getName());
    }


    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            purityReportController = new WaterPurityReportController();
            loader.setController(purityReportController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() { return root; }
    public static WaterPurityReportController getPurityReportController() {return purityReportController; }

    @FXML private Button submitReportBtn;
    @FXML private Button cancelReportBtn;
    @FXML private TextField waterLocationField;
    @FXML private TextField contaminantPPMField;
    @FXML private TextField virusPPMField;
    @FXML private ComboBox waterConditionBox;

    private WaterPurityReportController() {
    }


    /**
     * Initialize Combo box options for waterConditionBox
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
        contaminantPPMField.clear();
        virusPPMField.clear();
        FXMain.setView("Welcome back!");
    }

    /**
     * Data validation for water report submission
     *
     */
    @FXML
    private void handleConfirmAction() {
        String waterLocation = waterLocationField.getText();
        String contaminantPPM = contaminantPPMField.getText();
        String virusPPM = virusPPMField.getText();
        String waterCondition = (String) waterConditionBox.getValue();
        if (waterLocation == null || waterLocation.length() == 0) {
            DialogueUtils.showMessage("The water location field must be filled.");
            waterLocationField.requestFocus();
            return;
        }

        if (contaminantPPM == null || contaminantPPM.length() == 0) {
            DialogueUtils.showMessage("Contaminant PPM field must be filled.");
            contaminantPPMField.requestFocus();
            return;
        }

        if (virusPPM == null || virusPPM.length() == 0) {
            DialogueUtils.showMessage("Virus PPM field must be filled.");
            virusPPMField.requestFocus();
            return;
        }
            try {
                //@TODO initialize purity report according to the user submitting it
                DialogueUtils.showMessage("Purity report submitted successfully.");
                waterLocationField.clear();
                contaminantPPMField.clear();
                virusPPMField.clear();
                FXMain.setView("Welcome back!");
                return;
            }
            //@TODO catch report submission failure exception
            catch (Throwable e) {
                DialogueUtils.showMessage("A runtime error has occurred when submitting report.");
            }
        }
    }