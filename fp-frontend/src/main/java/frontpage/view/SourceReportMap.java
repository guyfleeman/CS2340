package frontpage.view;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import frontpage.controller.SourceReportMapControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author willstuckey
 * @date 10/22/16
 * <p></p>
 */
public class SourceReportMap implements MapComponentInitializedListener {
    private static SourceReportMap instance;

    public static void create(SourceReportMapControllerInterface cb) {
        instance = new SourceReportMap(cb);
    }

    public static SourceReportMap instance() {
        return instance;
    }

    private Parent root;
    private GoogleMapView mapView;
    private GoogleMap map;
    private SourceReportMapControllerInterface cb;

    public SourceReportMap(SourceReportMapControllerInterface cb) {
        this.cb = cb;
        createRoot();
    }

    public void attachController(SourceReportMapControllerInterface cb) {
        this.cb = cb;
    }

    private void createRoot() {
        VBox troot = new VBox(); {
            mapView = new GoogleMapView(); {
                mapView.addMapInializedListener(this);
            }
            HBox buttonContainer = new HBox(); {
                buttonContainer.setAlignment(Pos.CENTER);
                Button ret = new Button("return");
                ret.setStyle("-fx-spacing: 10");
                ret.setOnAction(event -> cb.handleReturnAction());
                buttonContainer.getChildren().add(ret);
            }
            troot.getChildren().addAll(mapView, buttonContainer);
        }

        root = troot;
    }

    public Parent getRoot() {
        return root;
    }

    @Override
    public void mapInitialized() {
        mapView.addMapInializedListener(this);

        MapOptions options = new MapOptions();
        // gatech: 33.7795209,-84.4020793
        LatLong center = new LatLong(33.7795209, -84.4020793);
        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.TERRAIN);
        map = mapView.createMap(options);

        cb.handleMapInitAction(mapView, map);
    }
}
