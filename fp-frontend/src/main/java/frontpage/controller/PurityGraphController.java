package frontpage.controller;

import frontpage.FXMain;
import frontpage.bind.errorhandling.AuthenticationException;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.PurityReportManager;
import frontpage.bind.user.UserManager;
import frontpage.model.report.PurityReport;
import frontpage.model.user.User;
import frontpage.model.user.UserClass;
import frontpage.utils.DialogueUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by George on 9/22/2016.
 *
 * @author George
 * @author willstuckey
 */
public class PurityGraphController implements Updatable {
    private static final String VIEW_URI = "/frontpage/view/PurityGraph.fxml";

    private static Logger logger;
    private static Parent root;
    private static PurityGraphController purityGraphController;

    static {
        logger = Logger.getLogger(PurityGraphController.class.getName());
    }

    public static void create() {
        try {
            logger.debug("loading view: " + VIEW_URI);
            FXMLLoader loader = new FXMLLoader(FXMain.class.getResource(VIEW_URI));
            purityGraphController = new PurityGraphController();
            loader.setController(purityGraphController);
            root = loader.load();
        } catch (Exception e) {
            logger.error("failed to load view", e);
        }
    }

    public static Parent getRoot() {
        return root;
    }

    public static PurityGraphController getPurityGraphController() {
        return purityGraphController;
    }

    private final ObservableList<PurityReport> reports =
            FXCollections.observableArrayList();
    @FXML private TextField searchField;
    @FXML private TextField yearField;
    @FXML private Button displayBtn;
    @FXML private LineChart<String, Integer> lineChart;
    @FXML private Button returnBtn;

    private PurityGraphController() {

    }

    @FXML
    public void initialize() {

    }

    public boolean update() {
        lineChart.getData().clear();
        reports.clear();

        PurityReportManager rm = FXMain.getBackend().getPurityReportManager();
        try {
            Map<String, String>[] reportsData = rm.getPurityReports(0);
            for (Map<String, String> reportData : reportsData) {
                if (reportData.containsKey("index")) {
                    reports.add(new PurityReport(reportData));
                }
            }

            return true;
        } catch (BackendRequestException e) {
            DialogueUtils.showMessage("purity graph bre");
        } catch (Exception e) {
            DialogueUtils.showMessage("purity graph exception (type: " + e.getClass()
                    + ", message: " + e.getMessage()
                    + ", cause: " + e.getCause());
        }

        return false;
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void handleDisplayAction() {
        // update()

        lineChart.getData().clear();

        // get params

        final String searchStr = searchField.getText();
        final String searchYear = yearField.getText();

        // format search params

        final String[] searchParams = searchStr.split(",");
        for (int i = 0; i < searchParams.length; i++) {
            searchParams[i] = searchParams[i].trim().toLowerCase();
        }

        // get year

        final int year;
        try {
            year = Integer.parseInt(searchYear);
            if (year < 1900 || year > 2016) {
                throw new IllegalArgumentException("invalid year, not in range");
            }
        } catch (Exception e) {
            DialogueUtils.showMessage("Invalid Year (use 1900-2016)");
            return;
        }

        // add matching reports to display list

        final ArrayList<PurityReport> reportsToDisplay = new ArrayList<>();
        for (final PurityReport pr : reports) {
            boolean containsAll = true;
            for (final String sp : searchParams) {
                if (!(pr.getLocation().toLowerCase().contains(sp)
                        && pr.getDatetime().getYear() == year)) {
                    containsAll = false;
                    break;
                }
            }

            if (containsAll) {
                reportsToDisplay.add(pr);
            }
        }

        // add display list to graph

        XYChart.Series<String, Integer> virusPPM = new XYChart.Series<>();
        virusPPM.setName("Virus PPM");
        XYChart.Series<String, Integer> contaminantPPM = new XYChart.Series<>();
        contaminantPPM.setName("Contaminant PPM");
        for (final PurityReport pr : reportsToDisplay) {
            System.out.println(pr);
            virusPPM.getData().add(
                    new XYChart.Data<>(pr.getNormalizedDatetime(),
                            Integer.parseInt(pr.getVirusPPM())));
            contaminantPPM.getData().add(
                    new XYChart.Data<>(pr.getNormalizedDatetime(),
                            Integer.parseInt(pr.getContaminantPPM())));
        }

        virusPPM.setData((ObservableList<XYChart.Data<String,Integer>>) avgListByKey(virusPPM.getData()));
        contaminantPPM.setData((ObservableList<XYChart.Data<String,Integer>>) avgListByKey(contaminantPPM.getData()));

        lineChart.getData().addAll(virusPPM, contaminantPPM);
    }

    @FXML
    private void handleReturnAction() {
        lineChart.getData().clear();
        reports.clear();

        FXMain.setView("main");
    }

    /**
     * im just so sorry... forgive me
     * @param orig
     * @param <K>
     * @param <V>
     * @return
     */
    @SuppressWarnings("unchecked")
    private static
    <K, V extends Integer>
    ObservableList<? extends XYChart.Data<K, V>> avgListByKey(
            final ObservableList<? extends XYChart.Data<K, V>> orig) {
        if (orig == null) {
            return null;
        }

        K curK = null;
        V curVsum = (V) new Integer(0);
        int ct = 0;
        ObservableList<XYChart.Data<K, V>> ret =
                FXCollections.observableArrayList();
        for (XYChart.Data<K, V> dat : orig) {
            if (dat.getXValue().equals(curK)) {
                curVsum = (V) new Integer(curVsum.intValue()
                        + dat.getYValue().intValue());
                ct++;
            } else {
                if (curK != null) {
                    ret.add(new XYChart.Data<>(curK,
                            (V) new Integer(curVsum.intValue() / ct)));
                }

                curK = dat.getXValue();
                curVsum = (V) new Integer(dat.getYValue());
                ct = 1;
            }
        }
        ret.add(new XYChart.Data<>(curK,
                (V) new Integer(curVsum.intValue() / ct)));

        return ret;
    }
}
