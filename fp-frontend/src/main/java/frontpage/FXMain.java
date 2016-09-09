package frontpage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Application entry point.
 *
 * @author willstuckey
 */
public class FXMain extends Application {
    /**
     * initial resolution
     */
    private static final int RES_WIDTH = 640;

    /**
     * initial resolution
     */
    private static final int RES_HEIGHT = 480;
    
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * FX entry point
     *
     * @param primaryStage default stage
     */
    public final void start(final Stage primaryStage) {
        Scene testScene = new Scene(new Label("TEST"), RES_WIDTH, RES_HEIGHT);
        primaryStage.setScene(testScene);
        primaryStage.show();
    }
}
