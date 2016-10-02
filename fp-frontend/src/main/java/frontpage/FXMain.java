package frontpage;

import frontpage.backend.LocalBackend;
import frontpage.backend.RemoteBackend;
import frontpage.bind.Backend;
import frontpage.controller.LoginScreenController;
import frontpage.controller.MainScreenController;
import frontpage.controller.WelcomeScreenController;
import frontpage.model.User;
import frontpage.utils.SceneControllerEntry;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Application entry point.
 *
 * @author willstuckey
 */
public class FXMain extends Application {

    private static final int RES_WIDTH = 640;
    private static final int RES_HEIGHT = 480;
    // logger format
    private static final String LOG_FORMAT = "%d{ISO8601} [%t] %-5p %c %x - %m%n";
    private static final Map<String, SceneControllerEntry> viewSceneMap = new HashMap<>();

    private static Stage stage;
    private static Backend backend;
    private static User user;

    public static void main(final String[] args) {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout(LOG_FORMAT));
        console.setThreshold(Level.ALL);
        console.activateOptions();
        Logger.getRootLogger().addAppender(console);

        backend = new RemoteBackend();

        try {
            backend.getUserManager().createUser("guyfleeman", "password".toCharArray(), "guyfleeman@gmail.com", "will", "stuckey");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);

        launch(args);
    }

    public static Backend getBackend() {
        return backend;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FXMain.user = user;
    }

    /**
     * FX entry point
     *
     * @param primaryStage default stage
     */
    public final void start(final Stage primaryStage) {
        FXMain.stage = primaryStage;

        LoginScreenController.create();
        SceneControllerEntry<LoginScreenController> lse = new SceneControllerEntry<>(
                new Scene(LoginScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                LoginScreenController.getLoginController());
        viewSceneMap.put("login", lse);

        WelcomeScreenController.create();
        SceneControllerEntry<WelcomeScreenController> wse = new SceneControllerEntry<>(
                new Scene(WelcomeScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                WelcomeScreenController.getWelcomeController());
        viewSceneMap.put("welcome", wse);

        MainScreenController.create();
        SceneControllerEntry<MainScreenController> mse = new SceneControllerEntry<>(
                new Scene(MainScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                MainScreenController.getMainController());
        viewSceneMap.put("main", mse);

        setView("welcome");
        primaryStage.show();
//        Scene testScene = new Scene(new Label("TEST"), RES_WIDTH, RES_HEIGHT);
//        primaryStage.setScene(testScene);
//        primaryStage.show();

    }

    public static boolean setView(final String view) {
        Scene s = viewSceneMap.get(view.toLowerCase()).getScene();
        if (s == null) {
            return false;
        }

        stage.setScene(s);
        return true;
    }

    public static Object getController(final String view) {
        return viewSceneMap.get(view.toLowerCase()).getController();
    }
}
