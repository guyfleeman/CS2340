package frontpage.controller;

import frontpage.FXMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.apache.log4j.Logger;

import javax.swing.table.TableColumn;

/**
 * Created by Devan on 10/16/2016.
 */
public class ViewReportScreenController {
    private static final String VIEW_URI = "/frontpage/view/ViewReports.fxml";

    private static Logger logger;
    private static Parent root;
    private static frontpage.controller.ViewReportScreenController viewController;

    static {
        logger = Logger.getLogger(frontpage.controller.ViewReportScreenController.class.getName());
    }


    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            viewController = new frontpage.controller.ViewReportScreenController();
            loader.setController(viewController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() { return root; }
    public static frontpage.controller.ViewReportScreenController getViewReportController() {return viewController; }


    @FXML private TableView viewReportsTable;
    @FXML private TableColumn dateTimeCol;
    @FXML private TableColumn reportNumCol;
    @FXML private TableColumn reporterCol;
    @FXML private TableColumn locationCol;
    @FXML private TableColumn waterSourceTypeCol;
    @FXML private TableColumn waterConditionCol;
    @FXML private Button ViewReportsReturnBtn;

    private ViewReportScreenController () {

    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void hadleReturnAction() {
        FXMain.setView("main");
    }




}
