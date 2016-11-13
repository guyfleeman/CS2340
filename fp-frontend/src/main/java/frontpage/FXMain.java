package frontpage;

import frontpage.backend.LocalBackend;
import frontpage.backend.RemoteBackend;
import frontpage.bind.Backend;
import frontpage.controller.CreatePurityReportController;
import frontpage.controller.CreateSourceReportController;
import frontpage.controller.LoginScreenController;
import frontpage.controller.MainScreenController;
import frontpage.controller.ProfileScreenController;
import frontpage.controller.PurityGraphController;
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
@SuppressWarnings({"ALL", "ClassWithTooManyDependents",
        "CyclicClassDependency", "ClassWithTooManyDependencies"})
public class FXMain extends Application {
    private static final int MIN_MAJOR_VERSION = 8;
    private static final int MIN_MINOR_VERSION = 110;

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
    private static final String LOG_FORMAT =
            "%d{ISO8601} [%t] %-5p %c %x - %m%n";

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

        if (Arrays.asList(args).contains("--force-local")) {
            backend = new LocalBackend();
        } else {
            backend = new RemoteBackend();

            try {
                final String jreVersion = System.getProperty("java.version");
                final String major = "" + jreVersion.charAt(2);
                final String minor = jreVersion.split("_")[1];
                if ((Integer.parseInt(major) < MIN_MAJOR_VERSION)
                        || (Integer.parseInt(minor) < MIN_MINOR_VERSION)) {
                    System.err.println("Detected Java version before "
                            + "LetEncrypt certificate added.");
                    boolean res = DialogueUtils.askYesNo("Detected java "
                            + "version \"" + jreVersion + "\"\r\n\r\n"
                            + "This app requires a minimum java version "
                            + "of 1.8.0_110 for SSL certificates to "
                            + "work.\r\n\r\n"
                            + "The problem is usually only on windows, "
                            + "not Linux or Mac. Please upgrade your "
                            + " java version or install the lets encrypt "
                            + "root certificate to your certificate "
                            + "store.\r\n\r\n"
                            + "Continue? (should be okay for Mac/Linux)");
                    if (!res) {
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                DialogueUtils.showMessage("Unable to detect and identify "
                        + "problems with java version.");
            }
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

        PurityGraphController.create();
        SceneControllerEntry<PurityGraphController> pgc =
                new SceneControllerEntry<>(
                new Scene(PurityGraphController.getRoot(),
                        RES_WIDTH,
                        RES_HEIGHT),
                PurityGraphController.getPurityGraphController());
        VIEW_SCENE_MAP.put("puritygraph", pgc);

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
