package frontpage.view;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import frontpage.controller.SourceReportMapControllerInterface;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings({"WeakerAccess", "ChainedMethodCall", "unused"})
public class SourceReportMap implements MapComponentInitializedListener {
    private static final double LATITUDE = 33.7795209;
    private static final double LONGITUDE = -84.4020793;
    private static final int ZOOM = 9;
    private static SourceReportMap instance;

    /**
     * creates a source report map view singleton
     * @param cb controller callback
     */
    public static void create(final SourceReportMapControllerInterface cb) {
        instance = new SourceReportMap(cb);
    }

    /**
     * gets instance
     * @return instance
     */
    public static SourceReportMap instance() {
        return instance;
    }

    private Parent root;
    private GoogleMapView mapView;
    @SuppressWarnings("FieldCanBeLocal")
    private GoogleMap map;
    private SourceReportMapControllerInterface cb;

    /**
     * creates an instance of source report map view
     * @param cb callback
     */
    protected SourceReportMap(final SourceReportMapControllerInterface cb) {
        this.cb = cb;
        createRoot();
    }

    /**
     * attached a new controller to the instance
     * @param cb new controller
     */
    public void attachController(final SourceReportMapControllerInterface cb) {
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

    /**
     * gets the root node
     * @return root node
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * map initialized callback
     */
    @Override
    public void mapInitialized() {
        mapView.addMapInializedListener(this);

        MapOptions options = new MapOptions();
        // gatech: 33.7795209,-84.4020793
        LatLong center = new LatLong(LATITUDE, LONGITUDE);
        options.center(center)
                .zoom(ZOOM)
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
