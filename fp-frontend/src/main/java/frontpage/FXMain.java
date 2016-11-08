package frontpage;

import frontpage.backend.LocalBackend;
import frontpage.backend.RemoteBackend;
import frontpage.bind.Backend;
import frontpage.controller.CreatePurityReportController;
import frontpage.controller.CreateSourceReportController;
import frontpage.controller.LoginScreenController;
import frontpage.controller.MainScreenController;
import frontpage.controller.ProfileScreenController;
import frontpage.controller.RegisterScreenController;
import frontpage.controller.SourceReportMapController;
import frontpage.controller.Updatable;
import frontpage.controller.ViewPurityReportScreenController;
import frontpage.controller.ViewSourceReportScreenController;
import frontpage.controller.WelcomeScreenController;
import frontpage.model.user.User;
import frontpage.utils.DialogueUtils;
import frontpage.utils.SceneControllerEntry;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Application entry point.
 *
 * @author willstuckey
 */
public class FXMain extends Application {

    /**
     * default resolution width
     */
    private static final int RES_WIDTH = 640;

    /**
     * default resolution height
     */
    private static final int RES_HEIGHT = 480;

    /**
     * logger format
     */
    private static final String LOG_FORMAT = "%d{ISO8601} [%t] %-5p %c %x - %m%n";

    /**
     * map of string keys to views
     */
    private static final
    Map<String, SceneControllerEntry> VIEW_SCENE_MAP = new HashMap<>();

    /**
     * stage
     */
    private static Stage stage;

    /**
     * current backend reference
     */
    private static Backend backend;

    /**
     * active user
     */
    private static User user;

    /**
     * program entry point
     * @param args arguments
     */
    public static void main(final String[] args) {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout(LOG_FORMAT));
        console.setThreshold(Level.ALL);
        console.activateOptions();
        Logger.getRootLogger().addAppender(console);

        if (Arrays.asList(args).contains("--force-local")
                || !DialogueUtils.askYesNo("Use remote backend?")) {
            backend = new LocalBackend();
        } else {
            backend = new RemoteBackend();
        }
        User.setPm(backend.getProfileManager());

        launch(args);
    }

    /**
     * gets the current backend reference
     * @return backend
     */
    public static Backend getBackend() {
        return backend;
    }

    /**
     * gets the active user
     git * @return user
     */
    public static User getUser() {
        return user;
    }

    /**
     * sets the active user
     * @param user user
     */
    public static void setUser(final User user) {
        FXMain.user = user;
    }

    /**
     * FX entry point
     *
     * @param primaryStage default stage
     */
    public final void start(final Stage primaryStage) {
        FXMain.stage = primaryStage;


        RegisterScreenController.create();
        SceneControllerEntry<RegisterScreenController> rse =
                new SceneControllerEntry<>(
                new Scene(RegisterScreenController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                RegisterScreenController.getRegisterController());
        VIEW_SCENE_MAP.put("register", rse);

        LoginScreenController.create();
        SceneControllerEntry<LoginScreenController> lse =
                new SceneControllerEntry<>(
                new Scene(LoginScreenController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                LoginScreenController.getLoginController());
        VIEW_SCENE_MAP.put("login", lse);

        WelcomeScreenController.create();
        SceneControllerEntry<WelcomeScreenController> wse =
                new SceneControllerEntry<>(
                new Scene(WelcomeScreenController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                WelcomeScreenController.getWelcomeController());
        VIEW_SCENE_MAP.put("welcome", wse);

        MainScreenController.create();
        SceneControllerEntry<MainScreenController> mse =
                new SceneControllerEntry<>(
                new Scene(MainScreenController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                MainScreenController.getMainController());
        VIEW_SCENE_MAP.put("main", mse);

        ProfileScreenController.create();
        SceneControllerEntry<ProfileScreenController> psc =
                new SceneControllerEntry<>(
                new Scene(ProfileScreenController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                ProfileScreenController.getRegisterController()
        );
        VIEW_SCENE_MAP.put("profile", psc);

        ViewSourceReportScreenController.create();
        SceneControllerEntry<ViewSourceReportScreenController> vrsc =
                new SceneControllerEntry<>(
                new Scene(ViewSourceReportScreenController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                ViewSourceReportScreenController.getViewReportController());
        VIEW_SCENE_MAP.put("viewsourcerpts", vrsc);

        CreateSourceReportController.create();
        SceneControllerEntry<CreateSourceReportController> csrc =
                new SceneControllerEntry<>(
                new Scene(CreateSourceReportController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                CreateSourceReportController.getCreateSourceReportController());
        VIEW_SCENE_MAP.put("createsourcerpt", csrc);

        ViewPurityReportScreenController.create();
        SceneControllerEntry<ViewPurityReportScreenController> vpsc =
                new SceneControllerEntry<>(
                new Scene(ViewPurityReportScreenController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                ViewPurityReportScreenController.getViewReportController());
        VIEW_SCENE_MAP.put("viewpurityrpts", vpsc);

        CreatePurityReportController.create();
        SceneControllerEntry<CreatePurityReportController> cprc =
                new SceneControllerEntry<>(
                new Scene(CreatePurityReportController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                CreatePurityReportController.getPurityReportController()
        );
        VIEW_SCENE_MAP.put("createpurityrpt", cprc);

        SourceReportMapController.create();
        SceneControllerEntry<SourceReportMapController> srpc =
                new SceneControllerEntry<>(
                new Scene(SourceReportMapController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                SourceReportMapController.getSourceReportMapController());
        VIEW_SCENE_MAP.put("map", srpc);

        setView("welcome");
        primaryStage.show();

    }

    /**
     * sets the main scene
     * @param view string key for the scene
     * @return update success
     */
    @SuppressWarnings("UnusedReturnValue")
    public static boolean setView(final String view) {
        if (view == null) {
            return false;
        }

        Scene s = VIEW_SCENE_MAP.get(view.toLowerCase()).getScene();
        if (s == null) {
            return false;
        }

        Object con = VIEW_SCENE_MAP.get(view.toLowerCase()).getController();
        if (con instanceof Updatable) {
             if (!((Updatable) con).update()) {
                 return false;
             }
        }
        stage.setScene(s);
        return true;
    }

    /**
     * gets the controller of a scene
     * @param view string key for scene
     * @return controller
     */
    public static Object getController(final String view) {
        return VIEW_SCENE_MAP.get(view.toLowerCase()).getController();
    }
}
