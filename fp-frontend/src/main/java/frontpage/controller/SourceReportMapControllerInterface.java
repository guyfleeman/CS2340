package frontpage.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;

/**
 * @author willstuckey
 * <p>Interface to bind the Map view to a controller</p>
 */
public interface SourceReportMapControllerInterface {
    /**
     * handles when the return button is clicked
     */
    void handleReturnAction();

    /**
     * callback for when the map needs to be populates
     * @param view map view
     * @param map map controller
     */
    void handleMapInitAction(GoogleMapView view, GoogleMap map);
}
