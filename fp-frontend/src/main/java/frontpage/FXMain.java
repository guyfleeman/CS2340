package frontpage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author willstuckey
 * @date 9/7/16 <p></p>
 */
public class FXMain extends Application
{
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		Scene testScene = new Scene(new Label("TEST"), 640, 480);
		primaryStage.setScene(testScene);
		primaryStage.show();
	}
}
