package frontpage.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;

/**
 * @author willstuckey
 * @date 10/22/16
 * <p></p>
 */
public interface SourceReportMapControllerInterface {
    void handleReturnAction();

    void handleMapInitAction(GoogleMapView view, GoogleMap map);
}
