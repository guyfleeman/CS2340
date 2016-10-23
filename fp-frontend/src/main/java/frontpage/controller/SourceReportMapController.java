package frontpage.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import frontpage.FXMain;
import frontpage.bind.errorhandling.BackendRequestException;
import frontpage.bind.report.ReportManager;
import frontpage.model.report.SourceReport;
import frontpage.utils.DialogueUtils;
import frontpage.view.SourceReportMap;
import javafx.scene.Parent;
import netscape.javascript.JSObject;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author willstuckey
 * @date 10/22/16
 * <p></p>
 */
public class SourceReportMapController implements SourceReportMapControllerInterface, Updatable {
    private static Logger logger;
    private static Parent root;
    private static SourceReportMapController sourceReportMapController;

    static {
        logger = Logger.getLogger(SourceReportMapController.class.getName());
    }

    public static void create() {
        logger.trace("creating view");
        sourceReportMapController = new SourceReportMapController();
        SourceReportMap.create(sourceReportMapController);
        root = SourceReportMap.instance().getRoot();
    }

    public static Parent getRoot() {
        return root;
    }

    public static SourceReportMapController getSourceReportMapController() {
        return sourceReportMapController;
    }

    private GoogleMapView view;
    private GoogleMap map;

    public void handleReturnAction() {
        FXMain.setView("main");
    }

    @Override
    public void handleMapInitAction(GoogleMapView view, GoogleMap map) {
        System.out.println("map initialized" + map);
        this.view = view;
        this.map = map;
    }

    public boolean update() {
        return loadReports();
    }

    private boolean loadReports() {
        Vector<SourceReport> reports = new Vector<>();
        ReportManager rm = FXMain.getBackend().getReportManager();
        try {
            Map<String, String>[] reportsData = rm.getSourceReports(0);
            for (Map<String, String> reportData : reportsData) {
                if (reportData.containsKey("index")) {
                    reports.add(new SourceReport(reportData));
                }
            }

            for (SourceReport r: reports) {
                String location = r.getLoc();
                String[] coords = location.split(",");
                if (coords.length == 2) {
                    try {
                        double lat = Double.parseDouble(coords[0]);
                        double lng = Double.parseDouble(coords[1]);
                        System.out.println("found valid location: " + lat + "," + lng);
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLong loc = new LatLong(lat, lng);
                        markerOptions.position(loc)
                                .visible(Boolean.TRUE)
                                .title(r.getTitle());
                        Marker marker = new Marker(markerOptions);
                        map.addUIEventHandler(marker,
                                UIEventType.click,
                                (JSObject obj) -> {
                                    InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                                    infoWindowOptions.content(r.getDescription() );
                                    InfoWindow window = new InfoWindow(infoWindowOptions);
                                    window.open(map, marker);});
                        map.addMarker(marker);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.getClass());
                    }
                }
            }
        } catch (BackendRequestException e) {
            DialogueUtils.showMessage("view report map bre");
            return false;
        } catch (Exception e) {
            DialogueUtils.showMessage("view report map exception (type: " + e.getClass()
                    + ", message: " + e.getMessage()
                    + ", cause: " + e.getCause());
            return false;
        }

        return true;
    }
}
